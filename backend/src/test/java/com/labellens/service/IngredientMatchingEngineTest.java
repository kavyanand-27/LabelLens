package com.labellens.service;

import com.labellens.dto.AnalysisResponseDto;
import com.labellens.model.Ingredient;
import com.labellens.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngredientMatchingEngineTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientMatchingEngine matchingEngine;

    @Test
    public void testTokenizeIngredientsWithParentheses() {
        String label = "Refined Wheat Flour, Palm Oil, Emulsifier (INS 322), Salt, Raising Agent (INS 500(ii), INS 503(ii)).";
        List<String> tokens = matchingEngine.tokenizeIngredients(label);

        assertNotNull(tokens);
        assertEquals(5, tokens.size());
        assertEquals("Refined Wheat Flour", tokens.get(0));
        assertEquals("Palm Oil", tokens.get(1));
        assertEquals("Emulsifier (INS 322)", tokens.get(2));
        assertEquals("Salt", tokens.get(3));
        assertEquals("Raising Agent (INS 500(ii), INS 503(ii))", tokens.get(4));
    }

    @Test
    public void testMatchTokenByAdditiveCode() {
        Ingredient ing = new Ingredient();
        ing.setId(1L);
        ing.setCanonicalName("Sodium Benzoate");
        ing.setAdditiveCode("INS 211");
        ing.setCategory("Preservative");

        List<Ingredient> list = List.of(ing);
        IngredientMatchingEngine.ParseResult res = matchingEngine.matchToken("Preservative (INS 211)", list);

        assertEquals("IDENTIFIED", res.status);
        assertEquals("ADDITIVE_CODE", res.matchMethod);
        assertEquals("Sodium Benzoate", res.matchedIngredient.getCanonicalName());
    }

    @Test
    public void testUnknownIngredientRemainsUnidentified() {
        Ingredient ing = new Ingredient();
        ing.setId(1L);
        ing.setCanonicalName("Lecithin");
        ing.setAdditiveCode("INS 322");

        List<Ingredient> list = List.of(ing);
        IngredientMatchingEngine.ParseResult res = matchingEngine.matchToken("RareExoticPlantExtract99", list);

        assertEquals("NOT_IDENTIFIED", res.status);
        assertEquals("UNIDENTIFIED", res.matchMethod);
        assertNull(res.matchedIngredient);
    }
}
