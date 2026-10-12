package com.labellens.service;

import com.labellens.model.Ingredient;
import com.labellens.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IngredientMatchingEngine {

    private final IngredientRepository ingredientRepository;

    public IngredientMatchingEngine(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public static class ParseResult {
        public String originalTerm;
        public Ingredient matchedIngredient;
        public String matchMethod; // EXACT, ADDITIVE_CODE, SYNONYM, UNIDENTIFIED
        public String status; // IDENTIFIED, NOT_IDENTIFIED
    }

    /**
     * Splits label text safely considering nested parentheses, colons, percentages, and commas
     */
    public List<String> tokenizeIngredients(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // Clean headers like "INGREDIENTS:", "Ingredients (approx):", etc.
        String cleaned = rawText.replaceAll("(?i)^.*ingredients\\s*[:\\-]?\\s*", "");

        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int parenDepth = 0;

        for (int i = 0; i < cleaned.length(); i++) {
            char c = cleaned.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                parenDepth++;
                current.append(c);
            } else if (c == ')' || c == ']' || c == '}') {
                parenDepth = Math.max(0, parenDepth - 1);
                current.append(c);
            } else if ((c == ',' || c == ';' || c == '\n') && parenDepth == 0) {
                String token = cleanToken(current.toString());
                if (!token.isEmpty()) {
                    tokens.add(token);
                }
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        String finalToken = cleanToken(current.toString());
        if (!finalToken.isEmpty()) {
            tokens.add(finalToken);
        }

        return tokens;
    }

    private String cleanToken(String s) {
        return s.replaceAll("^[\\.\\-\\:\\s]+", "")
                .replaceAll("[\\.\\-\\:\\s]+$", "")
                .trim();
    }

    /**
     * Matches an ingredient token against database canonical names, INS/E codes, and synonyms
     */
    public ParseResult matchToken(String token, List<Ingredient> allIngredients) {
        ParseResult res = new ParseResult();
        res.originalTerm = token;

        String norm = token.toLowerCase();

        // 1. Look for explicit INS or E numbers inside the token (e.g., "INS 322", "E211", "330")
        Pattern codePattern = Pattern.compile("(?i)(?:ins|e)\\s*([0-9]{3,4}[a-z]?(?:\\([i-v]+\\))?)");
        Matcher codeMatcher = codePattern.matcher(token);
        if (codeMatcher.find()) {
            String extractedCode = codeMatcher.group(0).replaceAll("\\s+", " ").toUpperCase();
            for (Ingredient ing : allIngredients) {
                if (ing.getAdditiveCode() != null) {
                    String ingCodeNorm = ing.getAdditiveCode().replaceAll("\\s+", " ").toUpperCase();
                    if (extractedCode.contains(ingCodeNorm) || ingCodeNorm.contains(extractedCode)) {
                        res.matchedIngredient = ing;
                        res.matchMethod = "ADDITIVE_CODE";
                        res.status = "IDENTIFIED";
                        return res;
                    }
                }
            }
        }

        // 2. Exact or normalized canonical name match
        for (Ingredient ing : allIngredients) {
            String canon = ing.getCanonicalName().toLowerCase();
            if (norm.equals(canon) || norm.contains(canon) || canon.contains(norm)) {
                res.matchedIngredient = ing;
                res.matchMethod = "EXACT";
                res.status = "IDENTIFIED";
                return res;
            }
        }

        // 3. Synonym matching
        for (Ingredient ing : allIngredients) {
            if (ing.getSynonyms() != null) {
                for (String syn : ing.getSynonyms()) {
                    String synNorm = syn.toLowerCase();
                    if (norm.equals(synNorm) || norm.contains(synNorm) || synNorm.contains(norm)) {
                        res.matchedIngredient = ing;
                        res.matchMethod = "SYNONYM";
                        res.status = "IDENTIFIED";
                        return res;
                    }
                }
            }
        }

        // 4. Unidentified
        res.matchedIngredient = null;
        res.matchMethod = "UNIDENTIFIED";
        res.status = "NOT_IDENTIFIED";
        return res;
    }
}
