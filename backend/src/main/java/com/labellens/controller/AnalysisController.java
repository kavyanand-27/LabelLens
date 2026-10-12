package com.labellens.controller;

import com.labellens.dto.AnalysisResponseDto;
import com.labellens.model.Analysis;
import com.labellens.model.User;
import com.labellens.repository.AnalysisRepository;
import com.labellens.repository.UserRepository;
import com.labellens.service.AnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/analyses")
public class AnalysisController {

    private final AnalysisService analysisService;
    private final AnalysisRepository analysisRepository;
    private final UserRepository userRepository;

    public AnalysisController(AnalysisService analysisService,
                              AnalysisRepository analysisRepository,
                              UserRepository userRepository) {
        this.analysisService = analysisService;
        this.analysisRepository = analysisRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/text")
    public ResponseEntity<AnalysisResponseDto> analyzeText(@RequestBody Map<String, Object> req,
                                                           Authentication authentication) {
        String rawText = (String) req.get("rawText");
        String productName = (String) req.get("productName");
        String brand = (String) req.get("brand");
        String inputType = (String) req.getOrDefault("inputType", "MANUAL");
        String lang = (String) req.getOrDefault("lang", "en");

        @SuppressWarnings("unchecked")
        List<String> explicitAllergenPrefs = (List<String>) req.get("allergens");

        User user = null;
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            user = userRepository.findByEmail(authentication.getName()).orElse(null);
        }

        AnalysisResponseDto result = analysisService.analyzeText(
                rawText, productName, brand, inputType, lang, user, explicitAllergenPrefs
        );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized access."));
        }
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "User not found."));
        }

        List<Analysis> history = analysisRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnalysisById(@PathVariable Long id) {
        return analysisRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
