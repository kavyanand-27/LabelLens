package com.labellens.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labellens.dto.AnalysisResponseDto;
import com.labellens.model.Analysis;
import com.labellens.model.Ingredient;
import com.labellens.model.User;
import com.labellens.model.UserPreference;
import com.labellens.repository.AnalysisRepository;
import com.labellens.repository.IngredientRepository;
import com.labellens.repository.UserPreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalysisService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMatchingEngine matchingEngine;
    private final AnalysisRepository analysisRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AnalysisService(IngredientRepository ingredientRepository,
                           IngredientMatchingEngine matchingEngine,
                           AnalysisRepository analysisRepository,
                           UserPreferenceRepository userPreferenceRepository) {
        this.ingredientRepository = ingredientRepository;
        this.matchingEngine = matchingEngine;
        this.analysisRepository = analysisRepository;
        this.userPreferenceRepository = userPreferenceRepository;
    }

    public AnalysisResponseDto analyzeText(String rawText, String productName, String brand,
                                          String inputType, String lang, User currentUser,
                                          List<String> explicitAllergenPrefs) {
        if (lang == null || lang.trim().isEmpty()) {
            lang = "en";
        }
        lang = lang.toLowerCase();

        List<Ingredient> allIngredients = ingredientRepository.findAll();
        List<String> tokens = matchingEngine.tokenizeIngredients(rawText);

        List<AnalysisResponseDto.ItemDto> items = new ArrayList<>();
        Map<String, List<String>> categoryBreakdown = new LinkedHashMap<>();
        int identifiedCount = 0;

        // Collect preferences
        Set<String> declaredAllergens = new HashSet<>();
        if (explicitAllergenPrefs != null) {
            for (String p : explicitAllergenPrefs) {
                if (p != null) declaredAllergens.add(p.trim().toLowerCase());
            }
        }
        if (currentUser != null) {
            List<UserPreference> savedPrefs = userPreferenceRepository.findByUserId(currentUser.getId());
            for (UserPreference up : savedPrefs) {
                if ("ALLERGEN".equalsIgnoreCase(up.getPreferenceType())) {
                    declaredAllergens.add(up.getPreferenceValue().trim().toLowerCase());
                }
            }
        }

        List<AnalysisResponseDto.AlertDto> alerts = new ArrayList<>();

        for (String token : tokens) {
            IngredientMatchingEngine.ParseResult pr = matchingEngine.matchToken(token, allIngredients);

            AnalysisResponseDto.ItemDto item = new AnalysisResponseDto.ItemDto();
            item.setOriginalTerm(pr.originalTerm);
            item.setMatchMethod(pr.matchMethod);
            item.setStatus(pr.status);

            if (pr.matchedIngredient != null) {
                identifiedCount++;
                Ingredient ing = pr.matchedIngredient;
                item.setIngredientId(ing.getId());
                item.setCanonicalName(ing.getCanonicalName());
                item.setAdditiveCode(ing.getAdditiveCode());
                item.setCategory(ing.getCategory());
                item.setAllergen(ing.isCommonAllergen());
                item.setAllergenType(ing.getAllergenType());
                item.setCommonFoods(ing.getCommonFoods());
                item.setSources(ing.getSources());

                // Multilingual field resolution (fall back to EN if requested language is unavailable)
                item.setLocalizedExplanation(getLocalizedExplanation(ing, lang));
                item.setLocalizedPurpose(getLocalizedPurpose(ing, lang));
                item.setLocalizedPrecautions(getLocalizedPrecautions(ing, lang));

                // Add to category grouping
                String cat = ing.getCategory() != null ? ing.getCategory() : "Other Food Ingredients";
                categoryBreakdown.computeIfAbsent(cat, k -> new ArrayList<>()).add(ing.getCanonicalName());

                // Check for declared allergen matches
                if (ing.isCommonAllergen() && ing.getAllergenType() != null) {
                    String aType = ing.getAllergenType().toLowerCase();
                    for (String pref : declaredAllergens) {
                        if (aType.contains(pref) || pref.contains(aType)) {
                            AnalysisResponseDto.AlertDto alert = new AnalysisResponseDto.AlertDto();
                            alert.setAlertType("ALLERGEN");
                            alert.setMatchedPreference(ing.getAllergenType());
                            alert.setOriginalTerm(pr.originalTerm);
                            alert.setExplanation(buildAllergenAlertMessage(ing.getCanonicalName(), ing.getAllergenType(), lang));
                            alerts.add(alert);
                            break;
                        }
                    }
                }
            } else {
                item.setLocalizedExplanation("This term could not be verified against the local food ingredient registry.");
                item.setLocalizedPurpose("Purpose undetermined.");
            }

            items.add(item);
        }

        AnalysisResponseDto dto = new AnalysisResponseDto();
        dto.setProductName(productName != null && !productName.trim().isEmpty() ? productName : "Analyzed Food Product");
        dto.setBrand(brand);
        dto.setInputType(inputType != null ? inputType : "MANUAL");
        dto.setTotalIngredients(tokens.size());
        dto.setIdentifiedIngredients(identifiedCount);
        dto.setAllergenAlertsCount(alerts.size());
        dto.setItems(items);
        dto.setAlerts(alerts);
        dto.setCategoryBreakdown(categoryBreakdown);

        // Persist analysis record
        try {
            Analysis analysis = new Analysis();
            analysis.setUser(currentUser);
            analysis.setProductName(dto.getProductName());
            analysis.setBrand(dto.getBrand());
            analysis.setInputType(dto.getInputType());
            analysis.setRawText(rawText);
            analysis.setTotalIngredients(dto.getTotalIngredients());
            analysis.setIdentifiedIngredients(dto.getIdentifiedIngredients());
            analysis.setAllergenAlertsCount(dto.getAllergenAlertsCount());
            analysis.setResultJson(objectMapper.writeValueAsString(dto));

            Analysis saved = analysisRepository.save(analysis);
            dto.setAnalysisId(saved.getId());
        } catch (Exception e) {
            // Log and continue gracefully
        }

        return dto;
    }

    private String getLocalizedExplanation(Ingredient ing, String lang) {
        return switch (lang) {
            case "hi" -> (ing.getExplanationHi() != null ? ing.getExplanationHi() : ing.getExplanationEn());
            case "ta" -> (ing.getExplanationTa() != null ? ing.getExplanationTa() : ing.getExplanationEn());
            case "te" -> (ing.getExplanationTe() != null ? ing.getExplanationTe() : ing.getExplanationEn());
            case "kn" -> (ing.getExplanationKn() != null ? ing.getExplanationKn() : ing.getExplanationEn());
            default -> ing.getExplanationEn();
        };
    }

    private String getLocalizedPurpose(Ingredient ing, String lang) {
        return switch (lang) {
            case "hi" -> (ing.getPurposeHi() != null ? ing.getPurposeHi() : ing.getPurposeEn());
            case "ta" -> (ing.getPurposeTa() != null ? ing.getPurposeTa() : ing.getPurposeEn());
            case "te" -> (ing.getPurposeTe() != null ? ing.getPurposeTe() : ing.getPurposeEn());
            case "kn" -> (ing.getPurposeKn() != null ? ing.getPurposeKn() : ing.getPurposeEn());
            default -> ing.getPurposeEn();
        };
    }

    private String getLocalizedPrecautions(Ingredient ing, String lang) {
        return switch (lang) {
            case "hi" -> (ing.getPrecautionsHi() != null ? ing.getPrecautionsHi() : ing.getPrecautionsEn());
            case "ta" -> (ing.getPrecautionsTa() != null ? ing.getPrecautionsTa() : ing.getPrecautionsEn());
            case "te" -> (ing.getPrecautionsTe() != null ? ing.getPrecautionsTe() : ing.getPrecautionsEn());
            case "kn" -> (ing.getPrecautionsKn() != null ? ing.getPrecautionsKn() : ing.getPrecautionsEn());
            default -> ing.getPrecautionsEn();
        };
    }

    private String buildAllergenAlertMessage(String canonicalName, String allergenType, String lang) {
        return switch (lang) {
            case "hi" -> "सावधानी: यह घटक (" + canonicalName + ") आपकी घोषित '" + allergenType + "' एलर्जी प्राथमिकता से मेल खाता है।";
            case "ta" -> "எச்சரிக்கை: இந்த மூலப்பொருள் (" + canonicalName + ") நீங்கள் தேர்வு செய்த '" + allergenType + "' அலர்ஜியுடன் பொருந்துகிறது.";
            case "te" -> "హెచ్చరిక: ఈ పదార్ధం (" + canonicalName + ") మీ '" + allergenType + "' అలెర్జీ ప్రాధాన్యతతో సరిపోలుతుంది.";
            case "kn" -> "ಎಚ್ಚರಿಕೆ: ಈ ಪದಾರ್ಥ (" + canonicalName + ") ನಿಮ್ಮ '" + allergenType + "' ಅಲರ್ಜಿ ಆಯ್ಕೆಯೊಂದಿಗೆ ಹೊಂದಾಣಿಕೆಯಾಗುತ್ತದೆ.";
            default -> "Warning: '" + canonicalName + "' is associated with '" + allergenType + "', matching your declared preference.";
        };
    }
}
