package com.labellens.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analyses")
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Optional (supports anonymous analysis)

    private String productName;
    private String brand;
    private String inputType; // MANUAL, IMAGE_OCR, PRODUCT_SELECTION

    @Column(columnDefinition = "TEXT")
    private String rawText;

    @Column(columnDefinition = "TEXT")
    private String resultJson; // Serialized complete analysis result

    private int totalIngredients;
    private int identifiedIngredients;
    private int allergenAlertsCount;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getInputType() { return inputType; }
    public void setInputType(String inputType) { this.inputType = inputType; }
    public String getRawText() { return rawText; }
    public void setRawText(String rawText) { this.rawText = rawText; }
    public String getResultJson() { return resultJson; }
    public void setResultJson(String resultJson) { this.resultJson = resultJson; }
    public int getTotalIngredients() { return totalIngredients; }
    public void setTotalIngredients(int totalIngredients) { this.totalIngredients = totalIngredients; }
    public int getIdentifiedIngredients() { return identifiedIngredients; }
    public void setIdentifiedIngredients(int identifiedIngredients) { this.identifiedIngredients = identifiedIngredients; }
    public int getAllergenAlertsCount() { return allergenAlertsCount; }
    public void setAllergenAlertsCount(int allergenAlertsCount) { this.allergenAlertsCount = allergenAlertsCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
