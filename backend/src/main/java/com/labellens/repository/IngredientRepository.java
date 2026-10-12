package com.labellens.repository;

import com.labellens.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByCanonicalNameIgnoreCase(String name);
    Optional<Ingredient> findByAdditiveCodeIgnoreCase(String code);

    @Query("SELECT DISTINCT i.category FROM Ingredient i WHERE i.category IS NOT NULL ORDER BY i.category ASC")
    List<String> findDistinctCategories();

    @Query("SELECT i FROM Ingredient i WHERE " +
           "LOWER(i.canonicalName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(i.additiveCode) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(i.description) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(i.commonFoods) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Ingredient> searchIngredients(@Param("q") String query);

    List<Ingredient> findByCategoryIgnoreCase(String category);
}
