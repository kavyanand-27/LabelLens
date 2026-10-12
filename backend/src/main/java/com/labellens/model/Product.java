package com.labellens.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    private String brand;
    private String category;
    private String barcode;
    private String packageSize;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String rawIngredients;

    // Nutritional breakdown per 100g / 100ml
    private Double energyKcal;
    private Double proteinG;
    private Double carbohydrateG;
    private Double totalSugarsG;
    private Double addedSugarsG;
    private Double totalFatG;
    private Double saturatedFatG;
    private Double transFatG;
    private Double dietaryFiberG;
    private Double sodiumMg;

    private String source;
    private boolean verified;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getPackageSize() { return packageSize; }
    public void setPackageSize(String packageSize) { this.packageSize = packageSize; }
    public String getRawIngredients() { return rawIngredients; }
    public void setRawIngredients(String rawIngredients) { this.rawIngredients = rawIngredients; }
    public Double getEnergyKcal() { return energyKcal; }
    public void setEnergyKcal(Double energyKcal) { this.energyKcal = energyKcal; }
    public Double getProteinG() { return proteinG; }
    public void setProteinG(Double proteinG) { this.proteinG = proteinG; }
    public Double getCarbohydrateG() { return carbohydrateG; }
    public void setCarbohydrateG(Double carbohydrateG) { this.carbohydrateG = carbohydrateG; }
    public Double getTotalSugarsG() { return totalSugarsG; }
    public void setTotalSugarsG(Double totalSugarsG) { this.totalSugarsG = totalSugarsG; }
    public Double getAddedSugarsG() { return addedSugarsG; }
    public void setAddedSugarsG(Double addedSugarsG) { this.addedSugarsG = addedSugarsG; }
    public Double getTotalFatG() { return totalFatG; }
    public void setTotalFatG(Double totalFatG) { this.totalFatG = totalFatG; }
    public Double getSaturatedFatG() { return saturatedFatG; }
    public void setSaturatedFatG(Double saturatedFatG) { this.saturatedFatG = saturatedFatG; }
    public Double getTransFatG() { return transFatG; }
    public void setTransFatG(Double transFatG) { this.transFatG = transFatG; }
    public Double getDietaryFiberG() { return dietaryFiberG; }
    public void setDietaryFiberG(Double dietaryFiberG) { this.dietaryFiberG = dietaryFiberG; }
    public Double getSodiumMg() { return sodiumMg; }
    public void setSodiumMg(Double sodiumMg) { this.sodiumMg = sodiumMg; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
