package com.labellens.dto;

import java.util.List;
import java.util.Map;

public class AnalysisResponseDto {
    private Long analysisId;
    private String productName;
    private String brand;
    private String inputType;
    private int totalIngredients;
    private int identifiedIngredients;
    private int allergenAlertsCount;
    private List<ItemDto> items;
    private List<AlertDto> alerts;
    private Map<String, List<String>> categoryBreakdown;

    public static class ItemDto {
        private String originalTerm;
        private Long ingredientId;
        private String canonicalName;
        private String additiveCode;
        private String category;
        private String matchMethod;
        private String status;
        private boolean allergen;
        private String allergenType;
        private String localizedExplanation;
        private String localizedPurpose;
        private String localizedPrecautions;
        private String commonFoods;
        private String sources;

        // Getters and Setters
        public String getOriginalTerm() { return originalTerm; }
        public void setOriginalTerm(String originalTerm) { this.originalTerm = originalTerm; }
        public Long getIngredientId() { return ingredientId; }
        public void setIngredientId(Long ingredientId) { this.ingredientId = ingredientId; }
        public String getCanonicalName() { return canonicalName; }
        public void setCanonicalName(String canonicalName) { this.canonicalName = canonicalName; }
        public String getAdditiveCode() { return additiveCode; }
        public void setAdditiveCode(String additiveCode) { this.additiveCode = additiveCode; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public String getMatchMethod() { return matchMethod; }
        public void setMatchMethod(String matchMethod) { this.matchMethod = matchMethod; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public boolean isAllergen() { return allergen; }
        public void setAllergen(boolean allergen) { this.allergen = allergen; }
        public String getAllergenType() { return allergenType; }
        public void setAllergenType(String allergenType) { this.allergenType = allergenType; }
        public String getLocalizedExplanation() { return localizedExplanation; }
        public void setLocalizedExplanation(String localizedExplanation) { this.localizedExplanation = localizedExplanation; }
        public String getLocalizedPurpose() { return localizedPurpose; }
        public void setLocalizedPurpose(String localizedPurpose) { this.localizedPurpose = localizedPurpose; }
        public String getLocalizedPrecautions() { return localizedPrecautions; }
        public void setLocalizedPrecautions(String localizedPrecautions) { this.localizedPrecautions = localizedPrecautions; }
        public String getCommonFoods() { return commonFoods; }
        public void setCommonFoods(String commonFoods) { this.commonFoods = commonFoods; }
        public String getSources() { return sources; }
        public void setSources(String sources) { this.sources = sources; }
    }

    public static class AlertDto {
        private String alertType; // ALLERGEN, DIETARY
        private String matchedPreference;
        private String originalTerm;
        private String explanation;

        public String getAlertType() { return alertType; }
        public void setAlertType(String alertType) { this.alertType = alertType; }
        public String getMatchedPreference() { return matchedPreference; }
        public void setMatchedPreference(String matchedPreference) { this.matchedPreference = matchedPreference; }
        public String getOriginalTerm() { return originalTerm; }
        public void setOriginalTerm(String originalTerm) { this.originalTerm = originalTerm; }
        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }
    }

    // Getters and Setters
    public Long getAnalysisId() { return analysisId; }
    public void setAnalysisId(Long analysisId) { this.analysisId = analysisId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getInputType() { return inputType; }
    public void setInputType(String inputType) { this.inputType = inputType; }
    public int getTotalIngredients() { return totalIngredients; }
    public void setTotalIngredients(int totalIngredients) { this.totalIngredients = totalIngredients; }
    public int getIdentifiedIngredients() { return identifiedIngredients; }
    public void setIdentifiedIngredients(int identifiedIngredients) { this.identifiedIngredients = identifiedIngredients; }
    public int getAllergenAlertsCount() { return allergenAlertsCount; }
    public void setAllergenAlertsCount(int allergenAlertsCount) { this.allergenAlertsCount = allergenAlertsCount; }
    public List<ItemDto> getItems() { return items; }
    public void setItems(List<ItemDto> items) { this.items = items; }
    public List<AlertDto> getAlerts() { return alerts; }
    public void setAlerts(List<AlertDto> alerts) { this.alerts = alerts; }
    public Map<String, List<String>> getCategoryBreakdown() { return categoryBreakdown; }
    public void setCategoryBreakdown(Map<String, List<String>> categoryBreakdown) { this.categoryBreakdown = categoryBreakdown; }
}
