package com.example.demo.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.service.ImageService;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*") 
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file, 
            @RequestParam("description") String description) {
        try {
            Image savedImage = imageService.saveImage(file, description);
            return ResponseEntity.ok("Resim başarıyla yüklendi! ID: " + savedImage.getId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Hata oluştu: " + e.getMessage());
        }
    }
    
    
    @GetMapping
    public ResponseEntity<List<Map<String, String>>> getImages() {
        List<Map<String, String>> images = imageRepository.findAll().stream().map(image -> {
            Map<String, String> imageData = new HashMap<>();
            imageData.put("id", String.valueOf(image.getId()));
            imageData.put("name", image.getName());
            imageData.put("description", image.getDescription());
            imageData.put("type", image.getType());

            
            String base64Image = Base64.getEncoder().encodeToString(image.getData());
            imageData.put("data", "data:" + image.getType() + ";base64," + base64Image);

            return imageData;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(images);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) {
        try {
            Image updatedImage = imageService.updateImage(id, file, description);
            if (updatedImage != null) {
                return ResponseEntity.ok("Resim başarıyla güncellendi!");
            } else {
                return ResponseEntity.status(404).body("Resim bulunamadı!");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Hata oluştu: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        boolean deleted = imageService.deleteImage(id);
        if (deleted) {
            return ResponseEntity.ok("Resim başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Resim bulunamadı!");
        }
    }


}


