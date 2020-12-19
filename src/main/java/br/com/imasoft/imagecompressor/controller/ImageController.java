package br.com.imasoft.imagecompressor.controller;

import br.com.imasoft.imagecompressor.entity.Image;
import br.com.imasoft.imagecompressor.service.ImageService;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/compress")
    public ResponseEntity<Image> compress(@RequestParam("file") MultipartFile file) {
        try {
            Image image = Image.of(file.getOriginalFilename(), new Binary(file.getBytes()));
            Image imageCompressed = this.imageService.compress(image);
            return ResponseEntity.ok(imageCompressed);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
