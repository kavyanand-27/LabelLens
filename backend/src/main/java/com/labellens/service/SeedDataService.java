package com.labellens.service;

import com.labellens.model.Ingredient;
import com.labellens.repository.IngredientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SeedDataService implements CommandLineRunner {

    private final IngredientRepository ingredientRepository;

    public SeedDataService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) {
        if (ingredientRepository.count() > 0) {
            return; // Data already exists
        }

        List<Ingredient> seeds = Arrays.asList(
            // 1. Sodium Benzoate (Preservative)
            createIngredient("Sodium Benzoate", "sodium-benzoate", "INS 211", "Preservative",
                "A widely used food preservative preventing microbial spoilage in acidic foods.",
                Arrays.asList("E211", "Benzoate of soda", "INS 211", "Sodium salt of benzoic acid"),
                false, null,
                "Inhibits growth of yeasts, bacteria, and moulds in acidic food environments.",
                "அமிலத்தன்மை கொண்ட உணவுகளில் ஈஸ்ட் மற்றும் பாக்டீரியா வளர்ச்சியைத் தடுக்கிறது.",
                "ఆమ్ల ఆహారాలలో బ్యాక్టీరియా మరియు ఈస్ట్ పెరుగుదలను నిరోధిస్తుంది.",
                "अम्लीय खाद्य पदार्थों में यीस्ट और बैक्टीरिया के विकास को रोकता है।",
                "ಆಮ್ಲೀಯ ಆಹಾರಗಳಲ್ಲಿ ಬ್ಯಾಕ್ಟೀರಿಯಾ ಮತ್ತು ಈಸ್ಟ್ ಬೆಳವಣಿಗೆಯನ್ನು ತಡೆಯುತ್ತದೆ.",
                "A preservative used in foods and beverages to extend shelf life safely.",
                "உணவுப் பொருட்களின் ஆயுளை பாதுகாப்பாக நீட்டிக்க பயன்படும் பாதுகாப்புப் பொருள்.",
                "ఆహార పదార్థాల నిల్వ కాలాన్ని పెంచే సంరక్షణకారి.",
                "खाद्य पदार्थों को खराब होने से बचाने के लिए उपयोग किया जाने वाला संरक्षक।",
                "ಆಹಾರ ಪದಾರ್ಥಗಳು ಕೆಡದಂತೆ ಕಾಪಾಡುವ ಸಂರಕ್ಷಕ.",
                "Permitted within regulatory limits (Codex/FSSAI). Safe for general population.",
                "அங்கீகரிக்கப்பட்ட வரம்புகளுக்குள் பாதுகாப்பானது.",
                "నియంత్రణ పరిమితుల్లో సురಕ್ಷితమైనది.",
                "मानक सीमाओं के भीतर सुरक्षित।",
                "ನಿಯಂತ್ರಿತ ಮಿತಿಗಳಲ್ಲಿ ಸುರಕ್ಷಿತವಾಗಿದೆ.",
                "Carbonated drinks, jams, pickles, fruit juices, salad dressings",
                "Codex Alimentarius (CXS 192-1995), FSSAI Standards"),

            // 2. Lecithin (Emulsifier / Allergen: Soy/Egg)
            createIngredient("Lecithin", "lecithin", "INS 322", "Emulsifier",
                "A natural fatty substance that helps oil and water stay blended smoothly.",
                Arrays.asList("E322", "INS 322", "INS 322(i)", "Soya Lecithin", "Soy Lecithin", "Sunflower Lecithin"),
                true, "Soy",
                "Acts as an emulsifier to keep fat and water mixtures stable and homogenous.",
                "எண்ணெயும் தண்ணீரும் பிரிந்துவிடாமல் சீராக கலக்க உதவுகிறது.",
                "నూనె మరియు నీరు వేరుకాకుండా ఒకేలా కలవడానికి సహాయపడుతుంది.",
                "तेल और पानी को आपस में मिलाकर स्थिर रखने का काम करता है।",
                "ಎಣ್ಣೆ ಮತ್ತು ನೀರು ಬೆರೆಯುವಂತೆ ಮಾಡುವ ಎಮಲ್ಸಿಫೈಯರ್.",
                "A naturally derived emulsifier commonly extracted from soybeans or sunflower seeds.",
                "சோயாபீன் அல்லது சூரியகாந்தி விதைகளிலிருந்து பெறப்படும் இயற்கை எமல்சிஃபையர்.",
                "సోయా లేదా పొద్దుతిరుగుడు గింజల నుండి సేకరించిన సహజ ఎమల్సిఫైయర్.",
                "सोयाबीन या सूरजमुखी से प्राप्त एक प्राकृतिक पायसीकारी (इमल्सीफायर)।",
                "ಸೋಯಾ ಅಥವಾ ಸೂರ್ಯಕಾಂತಿಯಿಂದ ಪಡೆಯಲಾದ ನೈಸರ್ಗಿಕ ಎಮಲ್ಸಿಫೈಯರ್.",
                "Contains soy or egg derivatives in many products; individuals with soy allergy should review source label.",
                "சோயா அலர்ஜி உள்ளவர்கள் தயாரிப்பு லேபிளை சரிபார்க்க வேண்டும்.",
                "సోయా అలర్జీ ఉన్నవారు మూలాన్ని తనిఖీ చేయాలి.",
                "सोया से एलर्जी वाले व्यक्तियों को स्रोत की जांच करनी चाहिए।",
                "ಸೋಯಾ ಅಲರ್ಜಿ ಇರುವವರು ಎಚ್ಚರಿಕೆ ವಹಿಸಬೇಕು.",
                "Chocolates, baked goods, margarine, infant formula, instant powders",
                "FSSAI Guidelines, FAO/WHO JECFA"),

            // 3. Monosodium Glutamate (Flavour Enhancer)
            createIngredient("Monosodium Glutamate", "monosodium-glutamate", "INS 621", "Flavour Enhancer",
                "The sodium salt of glutamic acid that provides a savoury umami taste sensation.",
                Arrays.asList("MSG", "E621", "INS 621", "Flavour Enhancer 621", "Ajinomoto"),
                false, null,
                "Intensifies the savoury, meaty, umami flavour profile of prepared dishes.",
                "உணவின் சுவையையும் உமாமி சுவையையும் அதிகரிக்க உதவுகிறது.",
                "ఆహారానికి ఉమామి రుచిని మరియు ఘాటుదనాన్ని అందిస్తుంది.",
                "भोजन के उमामी (स्वादिष्ट) स्वाद को बढ़ाता है।",
                "ಆಹಾರಕ್ಕೆ ಉಮಾಮಿ ರುಚಿಯನ್ನು ಹೆಚ್ಚಿಸಲು ಬಳಸಲಾಗುತ್ತದೆ.",
                "A naturally occurring amino acid salt used as a flavour enhancer globally.",
                "உணவுச் சுவையை உயர்த்தும் சோடியம் குளூட்டமேட் உப்பு.",
                "ఆహార రుచిని పెంచే గ్లూటమేట్ లవణం.",
                "एक प्रसिद्ध स्वाद बढ़ाने वाला घटक।",
                "ಆಹಾರದ ರುಚಿ ವರ್ಧಕವಾಗಿ ಬಳಸಲಾಗುವ ಅಂಶ.",
                "Recognized as safe by WHO/FDA. Sensitive individuals may monitor personal tolerance.",
                "பொதுவாக பாதுகாப்பானது; தனிநபர் உணர்திறன் இருந்தால் கவனிக்கவும்.",
                "సాధారణంగా సురక్షితం; సున్నితత్వం ఉన్నవారు గమనించాలి.",
                "सामान्यतः सुरक्षित; अत्यधिक संवेदनशील व्यक्ति ध्यान दें।",
                "ಸಾಮಾನ್ಯವಾಗಿ ಸುರಕ್ಷಿತ; ಸೂಕ್ಷ್ಮ ಸಂವೇದನೆ ಇರುವವರು ಗಮನಿಸಬೇಕು.",
                "Noodles, seasonings, soups, savoury snacks, frozen meals",
                "WHO Food Additives Series, US FDA GRAS"),

            // 4. Citric Acid (Acidity Regulator / Antioxidant)
            createIngredient("Citric Acid", "citric-acid", "INS 330", "Acidity Regulator",
                "A natural organic acid found in citrus fruits used to control tartness and preserve freshness.",
                Arrays.asList("E330", "INS 330", "Acidity Regulator (330)"),
                false, null,
                "Regulates pH, imparts a pleasant tart citrus taste, and enhances antioxidant activity.",
                "உணவின் அமிலத்தன்மையை சமன்செய்து புளிப்பு சுவையை அளிக்கிறது.",
                "ఆహారంలో పులుపుదనాన్ని నియంత్రించి తాజాదనాన్ని కాపాడుతుంది.",
                "खाद्य पदार्थों के पीएच स्तर को नियंत्रित करता है और खट्टा स्वाद देता है।",
                "ಆಮ್ಲೀಯತೆಯನ್ನು ನಿಯಂತ್ರಿಸಿ ಹುಳಿ ರುಚಿ ನೀಡುತ್ತದೆ.",
                "A ubiquitous organic acid derived either from citrus fruit or controlled fermentation.",
                "சிட்ரஸ் பழங்களிலிருந்து பெறப்படும் இயற்கை கரிம அமிலம்.",
                "నిమ్మ జాతి పండ్లలో లభించే సహజ సేంద్రీయ ఆమ్లం.",
                "एक प्राकृतिक कार्बनिक अम्ल जो ताजगी बनाए रखता है।",
                "ತಾಜಾತನವನ್ನು ಕಾಪಾಡುವ ನೈಸರ್ಗಿಕ ಆಮ್ಲ.",
                "Extremely safe with no acceptable daily intake limitation specified by JECFA.",
                "முற்றிலும் பாதுகாப்பானது; உடல்நலத்திற்கு உகந்தது.",
                "చాలా సురక్షితమైనది; ఎలాంటి హాని లేదు.",
                "अत्यधिक सुरक्षित और प्राकृतिक।",
                "ಅತ್ಯಂತ ಸುರಕ್ಷಿತ ಮತ್ತು ನೈಸರ್ಗಿಕ.",
                "Soft drinks, candies, jams, canned vegetables, cheese",
                "Codex Alimentarius, JECFA Evaluations"),

            // 5. Xanthan Gum (Stabilizer / Thickener)
            createIngredient("Xanthan Gum", "xanthan-gum", "INS 415", "Stabilizer",
                "A polysaccharide produced by plant fermentation used to thicken and stabilize emulsions.",
                Arrays.asList("E415", "INS 415", "Stabilizer 415", "Thickener (415)"),
                false, null,
                "Increases viscosity and prevents ingredients from settling or separating.",
                "உணவுப் பொருட்களின் அடர்த்தியையும் நிலைத்தன்மையையும் மேம்படுத்துகிறது.",
                "ద్రవాల చిక్కదనాన్ని పెంచి పదార్థాలు వేరుపడకుండా చూస్తుంది.",
                "खाद्य पदार्थों को गाढ़ा और स्थिर बनाने में मदद करता है।",
                "ಆಹಾರದ ಸಾಂದ್ರತೆಯನ್ನು ಹೆಚ್ಚಿಸಲು ನೆರವಾಗುತ್ತದೆ.",
                "A water-soluble carbohydrate thickener that produces a smooth, consistent texture.",
                "மென்மையான உணர்வை அளிக்கும் இயற்கை தாவர கார்போஹைட்ரேட்.",
                "మృదువైన ఆకృతిని అందించే మొక్కల ఆధారిత చిక్కదనం కారకం.",
                "एक घुलनशील गाढ़ा करने वाला एजेंट।",
                "ಮೃದುವಾದ ರಚನೆಯನ್ನು ನೀಡುವ ಗಟ್ಟಿಗಾರಕ.",
                "Safe, gluten-free, and suitable for vegetarian and vegan diets.",
                "சைவ மற்றும் குளூட்டன் அற்ற உணவுகளுக்கு ஏற்றது.",
                "శాకాహార మరియు గ్లూటెన్ రహిత ఆహారాలకు అనుకూలం.",
                "शाकाहारी और ग्लूटेन-मुक्त आहार के लिए उत्तम।",
                "ಸಸ್ಯಾಹಾರಿಗಳಿಗೆ ಸೂಕ್ತವಾದದ್ದು.",
                "Salad dressings, gluten-free bread, sauces, ice cream",
                "EFSA Journal, Codex Stan 192"),

            // 6. Potassium Sorbate (Preservative)
            createIngredient("Potassium Sorbate", "potassium-sorbate", "INS 202", "Preservative",
                "The potassium salt of sorbic acid used to inhibit mould and yeast proliferation.",
                Arrays.asList("E202", "INS 202", "Sorbate of potassium"),
                false, null,
                "Prevents spoilage by stopping mould and fungal spores from multiplying.",
                "பூஞ்சை மற்றும் ஈஸ்ட் வளர்ச்சியை தடுத்து உணவை பாதுகாக்கிறது.",
                "బూజు మరియు ఫంగస్ పెరగకుండా నిరోధిస్తుంది.",
                "फफूंद और यीस्ट को बढ़ने से रोककर भोजन को सुरक्षित रखता है।",
                "ಶಿಲೀಂಧ್ರ ಮತ್ತು ಈಸ್ಟ್ ಬೆಳೆಯದಂತೆ ತಡೆಯುತ್ತದೆ.",
                "An effective antifungal preservative that does not alter food flavour.",
                "உணவின் சுவையை மாற்றாமல் பாதுகாக்கும் பாதுகாப்புப் பொருள்.",
                "ఆహార రుచిని మార్చకుండా కాపాడే సంరక్షక పదార్థం.",
                "स्वाद बदले बिना भोजन को ताजा रखने वाला संरक्षक।",
                "ಆಹಾರದ ರುಚಿಯನ್ನು ಬದಲಾಯಿಸದ ಸಂರಕ್ಷಕ.",
                "Well tolerated within recommended daily intakes. Low allergenicity.",
                "பரிந்துரைக்கப்பட்ட வரம்பிற்குள் பாதுகாப்பானது.",
                "సిఫార్సు చేసిన పరిమితులలో సురక్షితమైనది.",
                "दैनिक सीमा के भीतर सुरक्षित।",
                "ಸೂಕ್ತ ಪ್ರಮಾಣದಲ್ಲಿ ಸುರಕ್ಷಿತ.",
                "Cheese, wine, dried meats, baked goods, yogurt drinks",
                "FSSAI Food Safety Standards, Codex General Standard"),

            // 7. Tartrazine (Food Colour)
            createIngredient("Tartrazine", "tartrazine", "INS 102", "Colour",
                "A synthetic lemon yellow azo dye used for colouring food and beverages.",
                Arrays.asList("E102", "INS 102", "FD&C Yellow 5", "Yellow 5", "CI 19140"),
                false, null,
                "Provides a vibrant yellow or golden hue to confectionery and drinks.",
                "உணவுப் பொருட்களுக்கு பிரகாசமான மஞ்சள் நிறத்தை அளிக்கிறது.",
                "ఆహారానికి ఆకర్షణీయమైన పసుపు రంగును ఇస్తుంది.",
                "भोजन और पेय पदार्थों को चमकीला पीला रंग प्रदान करता है।",
                "ಆಹಾರಕ್ಕೆ ಹಳದಿ ಬಣ್ಣವನ್ನು ನೀಡುತ್ತದೆ.",
                "A synthetic dye providing stable yellow coloration under heat and light.",
                "வெப்பத்திலும் மாறாத மஞ்சள் நிறத்தை வழங்கும் செயற்கை நிறமி.",
                "వేడికి స్థిరంగా ఉండే సింథటిక్ పసుపు రంగు.",
                "एक सिंथेटिक पीला खाद्य रंग।",
                "ಕೃತಕ ಹಳದಿ ಆಹಾರ ಬಣ್ಣ.",
                "May trigger intolerance or hyperactivity in sensitive children; requires declaration under FSSAI/EU.",
                "உணர்திறன் கொண்ட குழந்தைகளுக்கு எச்சரிக்கையுடன் பயன்படுத்த வேண்டும்.",
                "సున్నితత్వం ఉన్న పిల్లలలో అలెర్జీ లక్షణాలు కనిపించవచ్చు.",
                "संवेदनशील बच्चों में सतर्कता बरतें; पैकेट पर घोषणा अनिवार्य है।",
                "ಸೂಕ್ಷ್ಮ ಮಕ್ಕಳಿಗೆ ಎಚ್ಚರಿಕೆ ಅಗತ್ಯ.",
                "Custard powder, soft drinks, hard candy, chips, ice creams",
                "EFSA Scientific Opinion, FSSAI Labelling Regulations"),

            // 8. Sucralose (Artificial Sweetener)
            createIngredient("Sucralose", "sucralose", "INS 955", "Sweetener",
                "A zero-calorie artificial sweetener made by chlorinating sucrose, ~600x sweeter than sugar.",
                Arrays.asList("E955", "INS 955", "Splenda", "Sweetener (955)"),
                false, null,
                "Provides sweetness without contributing caloric energy or raising blood glucose.",
                "ரத்த சர்க்கரை அளவை உயர்த்தாமல் இனிப்பு சுவையை வழங்குகிறது.",
                "క్యాలరీలు లేకుండా చక్కెర కంటే 600 రెట్లు తీపిని అందిస్తుంది.",
                "कैलोरी और रक्त शर्करा बढ़ाए बिना मिठास प्रदान करता है।",
                "ಕ್ಯಾಲೋರಿಗಳಿಲ್ಲದೆ ಸಿಹಿ ರುಚಿಯನ್ನು ನೀಡುತ್ತದೆ.",
                "A heat-stable non-nutritive high-intensity sweetener suitable for cooking.",
                "சமைப்பதற்கும் வெப்பத்திற்கும் ஏற்ற கலோரி அற்ற இனிப்பூட்டி.",
                "వంటకు అనువైన జీరో-క్యాలరీ స్వీటెనర్.",
                "गर्मी में स्थिर रहने वाला शून्य-कैलोरी मिठास कारक।",
                "ಶಾಖ ನಿರೋಧಕ ಸಿಹಿಕಾರಕ.",
                "Does not promote tooth decay. Diabetic-friendly in moderation.",
                "நீரிழிவு நோயாளிகளுக்கு மிதமான அளவில் பாதுகாப்பானது.",
                "మధుమేహం ఉన్నవారికి మితంగా వాడటానికి సురక్షితం.",
                "मधुमेह रोगियों के लिए सीमित मात्रा में उपयुक्त।",
                "ಮಧುಮೇಹಿಗಳಿಗೆ ಮಿತ ಪ್ರಮಾಣದಲ್ಲಿ ಸೂಕ್ತ.",
                "Diet soda, protein powders, chewing gum, sugar-free baked items",
                "JECFA Toxicological Evaluation, US FDA"),

            // 9. Palm Oil (Fats & Oils)
            createIngredient("Palm Oil", "palm-oil", null, "Fat",
                "An edible vegetable oil derived from the mesocarp of the fruit of oil palms.",
                Arrays.asList("Palmolein", "Fractionated Palm Oil", "Refined Palm Oil", "Elaeis guineensis oil"),
                false, null,
                "Provides texture, crispness, stability, and high smoke point for frying.",
                "உணவுக்கு மொறுமொறுப்பையும் நீண்ட ஆயுளையும் வழங்குகிறது.",
                "ఆహారానికి కరకరలాడే స్వభావాన్ని మరియు స్థిరత్వాన్ని ఇస్తుంది.",
                "कुरकुरापन, संरचना और तलने के लिए उच्च तापमान स्थिरता देता है।",
                "ಕರಕುರು ರುಚಿ ಮತ್ತು ಬಾಳಿಕೆಯನ್ನು ಒದಗಿಸುತ್ತದೆ.",
                "A common vegetable fat rich in saturated palmitic acid and oleic acid.",
                "நிறைவுற்ற கொழுப்புகள் நிறைந்த பரவலான தாவர எண்ணெய்.",
                "సంతృప్త కొవ్వులతో కూడిన విస్తృతమైన వంట నూనె.",
                "संतृप्त वसा (सैचुरेटेड फैट) से युक्त वनस्पति तेल।",
                "ಸಂಪೃಕ್ತ ಕೊಬ್ಬಿನಾಂಶವಿರುವ ಸಾಮಾನ್ಯ ಖಾದ್ಯ ತೈಲ.",
                "High saturated fat content (~50%). Moderation recommended for heart health.",
                "அதிக நிறைவுற்ற கொழுப்பு உள்ளதால் மிதமாக உட்கொள்ளவும்.",
                "గుండె ఆరోగ్యం దృష్ట్యా మితంగా తీసుకోవడం మంచిది.",
                "हृदय स्वास्थ्य के लिए सीमित मात्रा में सेवन करने की सलाह दी जाती है।",
                "ಹೃದಯದ ಆರೋಗ್ಯಕ್ಕಾಗಿ ಮಿತವಾಗಿ ಬಳಸುವುದು ಸೂಕ್ತ.",
                "Biscuits, instant noodles, chips, packaged pastries, soaps",
                "FSSAI Edible Oils Standards, WHO Cardiovascular Guidelines"),

            // 10. Wheat Flour / Gluten (Allergen: Wheat)
            createIngredient("Wheat Flour", "wheat-flour", null, "Base Ingredient",
                "Powder made from the grinding of wheat grain; primary source of gluten in bakery products.",
                Arrays.asList("Atta", "Maida", "Refined Wheat Flour", "Gluten", "Wheat semolina"),
                true, "Wheat",
                "Forms the structural matrix, gluten network, and bulk in baked food products.",
                "ரொட்டி மற்றும் பிஸ்கட்டுகளின் அமைப்பை உருவாக்குகிறது.",
                "బేకరీ ఉత్పత్తుల ప్రాథమిక నిర్మాణం మరియు పిండి పదార్థం.",
                "बेकरी उत्पादों में मुख्य संरचना और लचीलापन प्रदान करता है।",
                "ಬೇಕರಿ ಉತ್ಪನ್ನಗಳ ಮುಖ್ಯ ಪದಾರ್ಥ.",
                "Cereal grain flour providing energy, starch, and gluten proteins.",
                "ஆற்றல் மற்றும் குளூட்டன் புரதங்களை வழங்கும் தானிய மாவு.",
                "శక్తిని మరియు ప్రోటీన్లను అందించే ధాన్యపు పిండి.",
                "ऊर्जा और प्रोटीन प्रदान करने वाला अनाज का आटा।",
                "ಶಕ್ತಿ ನೀಡುವ ಧಾನ್ಯದ ಹಿಟ್ಟು.",
                "Contains gluten; triggers severe immune reaction in individuals with Celiac disease or wheat allergy.",
                "குளூட்டன் உள்ளதால் சிலியாக் நோய் உள்ளவர்கள் தவிர்க்க வேண்டும்.",
                "గ్లూటెన్ ఉన్నందున సెలియాక్ వ్యాధి ఉన్నవారు నివారించాలి.",
                "ग्लूटेन युक्त; सीलिएक रोग और गेहूं की एलर्जी वाले लोग बचें।",
                "ಗ್ಲೂಟನ್ ಇರುವ ಕಾರಣ ಅಲರ್ಜಿ ಉಳ್ಳವರು ಎಚ್ಚರ ವಹಿಸಬೇಕು.",
                "Bread, biscuits, cakes, pasta, roti, noodles",
                "Codex Standard 152-1985, FSSAI Allergen Labelling Regulations"),

            // 11. Milk Solids (Allergen: Milk)
            createIngredient("Milk Solids", "milk-solids", null, "Dairy",
                "The dried protein, lactose, and mineral fraction of bovine milk.",
                Arrays.asList("Skimmed Milk Powder", "Whole Milk Powder", "Dairy Solids", "Milk Fat", "Whey Powder"),
                true, "Milk",
                "Enhances creaminess, browning (Maillard reaction), and protein content.",
                "கிரீமி சுவையையும் நிறத்தையும் அதிகரிக்க சேர்க்கப்படுகிறது.",
                "పాల రుచిని, మాంసకృత్తులను మరియు ఆకృతిని మెరుగుపరుస్తుంది.",
                "भोजन में मलाईदार स्वाद और प्रोटीन की मात्रा बढ़ाता है।",
                "ಹಾಲಿನ ರುಚಿ ಮತ್ತು ಪ್ರೋಟೀನ್ ಹೆಚ್ಚಿಸುತ್ತದೆ.",
                "Dried components of cows milk containing casein and whey proteins.",
                "பால் புரதங்கள் மற்றும் லாக்டோஸ் நிறைந்த பால் பொருள்.",
                "కేసిన్ మరియు పాల ప్రోటీన్లతో కూడిన పదార్థం.",
                "दूध के सूखे घटक जिनमें लैक्टोज और प्रोटीन होते हैं।",
                "ಹಾಲಿನ ಒಣ ಅಂಶಗಳು.",
                "Major allergen. Individuals with lactose intolerance or cow milk protein allergy must avoid.",
                "லாக்டோஸ் அல்லது பால் அலர்ஜி உள்ளவர்கள் தவிர்க்க வேண்டும்.",
                "లాక్టోస్ అసహనం లేదా పాల అలెర్జీ ఉన్నవారు నివారించాలి.",
                "लैक्टोज असहिष्णुता और दूध की एलर्जी वाले व्यक्तियों के लिए हानिकारक।",
                "ಲ್ಯಾಕ್ಟೋಸ್ ಅಲರ್ಜಿ ಇರುವವರು ದೂರವಿರಬೇಕು.",
                "Chocolates, biscuits, infant cereals, ice cream, dairy beverages",
                "Codex General Standard for Milk, FSSAI Dairy Regulations"),

            // 12. Peanut (Allergen: Peanut)
            createIngredient("Peanuts", "peanuts", null, "Nut/Legume",
                "Legume seeds known as groundnuts, rich in protein, oil, and micronutrients.",
                Arrays.asList("Groundnuts", "Peanut butter", "Roasted peanuts", "Peanut paste"),
                true, "Peanut",
                "Provides crunchy texture, rich roasted nut flavour, and vegetable protein.",
                "மொறுமொறுப்பான சுவையையும் புரதச்சத்தையும் வழங்குகிறது.",
                "కరకరలాడే రుచిని మరియు మొక్కల ప్రోటీన్ను అందిస్తుంది.",
                "कुरकुरा स्वाद और समृद्ध वनस्पति प्रोटीन प्रदान करता है।",
                "ಗರಿಗರಿ ರುಚಿ ಮತ್ತು ಪ್ರೋಟೀನ್ ನೀಡುತ್ತದೆ.",
                "A globally cultivated edible legume used whole, as paste, or oil.",
                "புரதம் நிறைந்த நிலக்கடலை பருப்பு.",
                "ప్రోటీన్లతో సమృద్ధిగా ఉండే వేరుశెనగ.",
                "प्रोटीन से भरपूर मूंगफली।",
                "ಕಡಲೆಕಾಯಿ ಬೀಜ.",
                "Major life-threatening anaphylactic allergen. Mandatory packaging declaration required.",
                "தீவிர அலர்ஜியை ஏற்படுத்தக்கூடிய முக்கிய பொருள்; எச்சரிக்கையுடன் தவிர்க்கவும்.",
                "తీవ్రమైన అలెర్జీని కలిగించే ప్రమాదముంది; తప్పనిసరిగా గమనించాలి.",
                "अत्यधिक गंभीर एलर्जी कारक; लेबल पर अनिवार्य चेतावनी जरूरी है।",
                "ತೀವ್ರ ಅಲರ್ಜಿ ಉಂಟುಮಾಡುವ ಪದಾರ್ಥ; ಎಚ್ಚರ ಅಗತ್ಯ.",
                "Peanut butter, energy bars, roasted snacks, confectionery",
                "Codex Guidelines on Labelling, FSSAI Food Safety regulations")
        );

        ingredientRepository.saveAll(seeds);
    }

    private Ingredient createIngredient(String name, String slug, String code, String category,
                                         String desc, List<String> synonyms, boolean isAllergen, String allergenType,
                                         String purpEn, String purpTa, String purpTe, String purpHi, String purpKn,
                                         String expEn, String expTa, String expTe, String expHi, String expKn,
                                         String precEn, String precTa, String precTe, String precHi, String precKn,
                                         String commonFoods, String sources) {
        Ingredient i = new Ingredient();
        i.setCanonicalName(name);
        i.setSlug(slug);
        i.setAdditiveCode(code);
        i.setCategory(category);
        i.setDescription(desc);
        i.setSynonyms(synonyms);
        i.setCommonAllergen(isAllergen);
        i.setAllergenType(allergenType);

        i.setPurposeEn(purpEn);
        i.setPurposeTa(purpTa);
        i.setPurposeTe(purpTe);
        i.setPurposeHi(purpHi);
        i.setPurposeKn(purpKn);

        i.setExplanationEn(expEn);
        i.setExplanationTa(expTa);
        i.setExplanationTe(expTe);
        i.setExplanationHi(expHi);
        i.setExplanationKn(expKn);

        i.setPrecautionsEn(precEn);
        i.setPrecautionsTa(precTa);
        i.setPrecautionsTe(precTe);
        i.setPrecautionsHi(precHi);
        i.setPrecautionsKn(precKn);

        i.setCommonFoods(commonFoods);
        i.setSources(sources);
        return i;
    }
}
