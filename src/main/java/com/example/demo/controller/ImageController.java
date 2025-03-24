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
    public ResponseEntity<Map<String, Object>> uploadImage(
            @RequestParam("file") MultipartFile file, 
            @RequestParam("description") String description,
            @RequestParam(value = "link", required = false) String link){
        try {
            Image savedImage = imageService.saveImage(file, description, link);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Resim başarıyla yüklendi!");
            response.put("id", savedImage.getId());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Hata oluştu: " + e.getMessage()));
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
            imageData.put("link", image.getLink());

            String base64Image = Base64.getEncoder().encodeToString(image.getData());
            imageData.put("data", "data:" + image.getType() + ";base64," + base64Image);

            return imageData;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(images);
    }

    
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateImage(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file, // Dosya zorunlu değil
            @RequestParam("description") String description) {
        try {
            Optional<Image> optionalImage = imageRepository.findById(id);
            if (optionalImage.isPresent()) {
                Image image = optionalImage.get();
                image.setDescription(description);

                if (file != null && !file.isEmpty()) {
                    image.setData(file.getBytes());
                    image.setName(file.getOriginalFilename());
                    image.setType(file.getContentType());
                }

                imageRepository.save(image);

                // Güncellenen veriyi JSON formatında geri döndür
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Resim başarıyla güncellendi!");
                response.put("id", image.getId());
                response.put("name", image.getName());
                response.put("description", image.getDescription());
                response.put("type", image.getType());

                // Base64 kodlanmış veri ekleme
                String base64Image = Base64.getEncoder().encodeToString(image.getData());
                response.put("data", "data:" + image.getType() + ";base64," + base64Image);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body(Map.of("success", false, "message", "Resim bulunamadı!"));
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Hata oluştu: " + e.getMessage()));
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


