package com.labellens.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_preferences")
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String preferenceType; // ALLERGEN, DIETARY

    @Column(nullable = false)
    private String preferenceValue; // Milk, Peanut, Soy, Wheat, Vegetarian, Vegan, etc.

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getPreferenceType() { return preferenceType; }
    public void setPreferenceType(String preferenceType) { this.preferenceType = preferenceType; }
    public String getPreferenceValue() { return preferenceValue; }
    public void setPreferenceValue(String preferenceValue) { this.preferenceValue = preferenceValue; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
