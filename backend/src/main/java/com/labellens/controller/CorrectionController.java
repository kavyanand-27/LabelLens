package com.labellens.controller;

import com.labellens.model.Correction;
import com.labellens.repository.CorrectionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/corrections")
public class CorrectionController {

    private final CorrectionRepository correctionRepository;

    public CorrectionController(CorrectionRepository correctionRepository) {
        this.correctionRepository = correctionRepository;
    }

    @PostMapping
    public ResponseEntity<?> submitCorrection(@RequestBody Correction correction) {
        if (correction.getTermName() == null || correction.getSuggestedCorrection() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Term name and suggested correction are required."));
        }
        Correction saved = correctionRepository.save(correction);
        return ResponseEntity.ok(Map.of("message", "Correction submitted for administrative review.", "id", saved.getId()));
    }
}
