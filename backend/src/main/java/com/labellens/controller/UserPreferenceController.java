package com.labellens.controller;

import com.labellens.model.User;
import com.labellens.model.UserPreference;
import com.labellens.repository.UserPreferenceRepository;
import com.labellens.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/preferences")
public class UserPreferenceController {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserRepository userRepository;

    public UserPreferenceController(UserPreferenceRepository userPreferenceRepository,
                                    UserRepository userRepository) {
        this.userPreferenceRepository = userPreferenceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getPreferences(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user == null) return ResponseEntity.status(401).build();

        List<UserPreference> prefs = userPreferenceRepository.findByUserId(user.getId());
        return ResponseEntity.ok(prefs);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<?> updatePreferences(@RequestBody List<Map<String, String>> prefsPayload,
                                              Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user == null) return ResponseEntity.status(401).build();

        userPreferenceRepository.deleteByUserId(user.getId());

        for (Map<String, String> item : prefsPayload) {
            String type = item.get("preferenceType");
            String val = item.get("preferenceValue");
            if (type != null && val != null) {
                UserPreference up = new UserPreference();
                up.setUser(user);
                up.setPreferenceType(type);
                up.setPreferenceValue(val);
                userPreferenceRepository.save(up);
            }
        }

        return ResponseEntity.ok(Map.of("message", "Preferences saved successfully."));
    }
}
