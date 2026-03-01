package com.imageprocessor.imageprocessor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class ImageController {

    private final ImageProcessingService imageProcessingService;

    public ImageController(ImageProcessingService imageProcessingService) {
        this.imageProcessingService = imageProcessingService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/api/process")
    public ResponseEntity<byte[]> processImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "operations", required = false) List<String> operations,
            @RequestParam(value = "degrees", required = false) Integer degrees,
            @RequestParam(value = "width", required = false) Integer width,
            @RequestParam(value = "height", required = false) Integer height) {
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("Please choose an image.".getBytes(StandardCharsets.UTF_8));
        }
        if (operations == null || operations.isEmpty()) {
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("Please select at least one operation.".getBytes(StandardCharsets.UTF_8));
        }
        try {
            BufferedImage result = imageProcessingService.processImage(image, operations, degrees, width, height);
            byte[] png = ImageUtils.toPngBytes(result);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return ResponseEntity.ok().headers(headers).body(png);
        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("Image processing failed: " + msg).getBytes(StandardCharsets.UTF_8));
        }
    }
}