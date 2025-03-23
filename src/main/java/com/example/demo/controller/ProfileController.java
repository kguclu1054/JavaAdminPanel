package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Profile;
import com.example.demo.repository.ProfileRepository;

@RestController
@RequestMapping("/profile")
@CrossOrigin("*")
public class ProfileController {
    
    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/description")
    public ResponseEntity<String> getProfileDescription() {
        Profile profile = profileRepository.findById(1).orElse(null);
        return ResponseEntity.ok(profile != null ? profile.getDescription() : "");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody Profile profile) {
        Profile existingProfile = profileRepository.findById(1).orElse(new Profile(1, ""));
        existingProfile.setDescription(profile.getDescription());
        profileRepository.save(existingProfile);
        return ResponseEntity.ok("Profil güncellendi!");
    }
}



