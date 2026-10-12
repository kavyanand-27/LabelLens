package com.labellens.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String canonicalName;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String additiveCode; // e.g., "E621", "INS 322", "INS 211"

    private String category; // Preservative, Emulsifier, Sweetener, Flavour Enhancer, Colour, etc.

    private boolean commonAllergen;
    private String allergenType; // Milk, Peanut, Soy, Wheat, Egg, etc.

    @ElementCollection
    @CollectionTable(name = "ingredient_synonyms", joinColumns = @JoinColumn(name = "ingredient_id"))
    @Column(name = "synonym")
    private List<String> synonyms = new ArrayList<>();

    // Structured multilingual explanations (en, hi, ta, te, kn)
    @Column(columnDefinition = "TEXT")
    private String purposeEn;
    @Column(columnDefinition = "TEXT")
    private String purposeHi;
    @Column(columnDefinition = "TEXT")
    private String purposeTa;
    @Column(columnDefinition = "TEXT")
    private String purposeTe;
    @Column(columnDefinition = "TEXT")
    private String purposeKn;

    @Column(columnDefinition = "TEXT")
    private String explanationEn;
    @Column(columnDefinition = "TEXT")
    private String explanationHi;
    @Column(columnDefinition = "TEXT")
    private String explanationTa;
    @Column(columnDefinition = "TEXT")
    private String explanationTe;
    @Column(columnDefinition = "TEXT")
    private String explanationKn;

    @Column(columnDefinition = "TEXT")
    private String precautionsEn;
    @Column(columnDefinition = "TEXT")
    private String precautionsHi;
    @Column(columnDefinition = "TEXT")
    private String precautionsTa;
    @Column(columnDefinition = "TEXT")
    private String precautionsTe;
    @Column(columnDefinition = "TEXT")
    private String precautionsKn;

    private String commonFoods;
    private String sources; // Codex Alimentarius, FSSAI, WHO/FAO

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCanonicalName() { return canonicalName; }
    public void setCanonicalName(String canonicalName) { this.canonicalName = canonicalName; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAdditiveCode() { return additiveCode; }
    public void setAdditiveCode(String additiveCode) { this.additiveCode = additiveCode; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public boolean isCommonAllergen() { return commonAllergen; }
    public void setCommonAllergen(boolean commonAllergen) { this.commonAllergen = commonAllergen; }
    public String getAllergenType() { return allergenType; }
    public void setAllergenType(String allergenType) { this.allergenType = allergenType; }
    public List<String> getSynonyms() { return synonyms; }
    public void setSynonyms(List<String> synonyms) { this.synonyms = synonyms; }
    public String getPurposeEn() { return purposeEn; }
    public void setPurposeEn(String purposeEn) { this.purposeEn = purposeEn; }
    public String getPurposeHi() { return purposeHi; }
    public void setPurposeHi(String purposeHi) { this.purposeHi = purposeHi; }
    public String getPurposeTa() { return purposeTa; }
    public void setPurposeTa(String purposeTa) { this.purposeTa = purposeTa; }
    public String getPurposeTe() { return purposeTe; }
    public void setPurposeTe(String purposeTe) { this.purposeTe = purposeTe; }
    public String getPurposeKn() { return purposeKn; }
    public void setPurposeKn(String purposeKn) { this.purposeKn = purposeKn; }
    public String getExplanationEn() { return explanationEn; }
    public void setExplanationEn(String explanationEn) { this.explanationEn = explanationEn; }
    public String getExplanationHi() { return explanationHi; }
    public void setExplanationHi(String explanationHi) { this.explanationHi = explanationHi; }
    public String getExplanationTa() { return explanationTa; }
    public void setExplanationTa(String explanationTa) { this.explanationTa = explanationTa; }
    public String getExplanationTe() { return explanationTe; }
    public void setExplanationTe(String explanationTe) { this.explanationTe = explanationTe; }
    public String getExplanationKn() { return explanationKn; }
    public void setExplanationKn(String explanationKn) { this.explanationKn = explanationKn; }
    public String getPrecautionsEn() { return precautionsEn; }
    public void setPrecautionsEn(String precautionsEn) { this.precautionsEn = precautionsEn; }
    public String getPrecautionsHi() { return precautionsHi; }
    public void setPrecautionsHi(String precautionsHi) { this.precautionsHi = precautionsHi; }
    public String getPrecautionsTa() { return precautionsTa; }
    public void setPrecautionsTa(String precautionsTa) { this.precautionsTa = precautionsTa; }
    public String getPrecautionsTe() { return precautionsTe; }
    public void setPrecautionsTe(String precautionsTe) { this.precautionsTe = precautionsTe; }
    public String getPrecautionsKn() { return precautionsKn; }
    public void setPrecautionsKn(String precautionsKn) { this.precautionsKn = precautionsKn; }
    public String getCommonFoods() { return commonFoods; }
    public void setCommonFoods(String commonFoods) { this.commonFoods = commonFoods; }
    public String getSources() { return sources; }
    public void setSources(String sources) { this.sources = sources; }
}
