package com.labellens.service;

import com.labellens.model.Product;
import com.labellens.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductSeedService implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductSeedService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) return;

        List<Product> sampleProducts = Arrays.asList(
            createProduct("Classic Butter Cookies", "Golden Bakery", "Biscuits & Bakery", "8901234567890", "150g",
                "Wheat Flour, Sugar, Palm Oil, Butter (Milk Solids), Invert Syrup, Emulsifier (INS 322), Raising Agents (INS 500(ii), INS 503(ii)), Salt, Nature Identical Flavours.",
                480.0, 6.5, 68.0, 24.5, 22.0, 20.0, 9.8, 0.1, 2.0, 320.0, true),

            createProduct("Instant Umami Masala Noodles", "QuickBites", "Instant Food", "8902345678901", "70g",
                "Refined Wheat Flour, Palm Oil, Salt, Wheat Gluten, Mineral (Calcium Carbonate), Thickener (INS 415), Masala Tastemaker: Salt, Sugar, Spices, Flavour Enhancer (INS 621), Acidity Regulator (INS 330), Colour (INS 102).",
                440.0, 9.2, 63.5, 2.1, 1.0, 16.8, 8.2, 0.05, 3.5, 980.0, true),

            createProduct("Diet Citrus Sparkle Soda", "FizzCo", "Beverages", "8903456789012", "300ml",
                "Carbonated Water, Acidity Regulator (INS 330), Sweetener (INS 955), Preservative (INS 211, INS 202), Natural Flavouring Substances, Colour (INS 102).",
                1.5, 0.0, 0.2, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 25.0, true),

            createProduct("Crunchy Roasted Peanut Butter", "PureNut", "Spreads & Jams", "8904567890123", "350g",
                "Roasted Peanuts (90%), Sugar, Hydrogenated Soybean Oil, Salt, Emulsifier (INS 322).",
                625.0, 25.0, 20.0, 6.5, 4.0, 50.0, 10.0, 0.0, 8.0, 150.0, true)
        );

        productRepository.saveAll(sampleProducts);
    }

    private Product createProduct(String name, String brand, String cat, String barcode, String size,
                                  String ingredients, Double kcal, Double prot, Double carb, Double sugar,
                                  Double addedSugar, Double fat, Double satFat, Double transFat, Double fiber,
                                  Double sodium, boolean verified) {
        Product p = new Product();
        p.setProductName(name);
        p.setBrand(brand);
        p.setCategory(cat);
        p.setBarcode(barcode);
        p.setPackageSize(size);
        p.setRawIngredients(ingredients);
        p.setEnergyKcal(kcal);
        p.setProteinG(prot);
        p.setCarbohydrateG(carb);
        p.setTotalSugarsG(sugar);
        p.setAddedSugarsG(addedSugar);
        p.setTotalFatG(fat);
        p.setSaturatedFatG(satFat);
        p.setTransFatG(transFat);
        p.setDietaryFiberG(fiber);
        p.setSodiumMg(sodium);
        p.setSource("Product Packaging Label - Verified Specimen");
        p.setVerified(verified);
        return p;
    }
}
