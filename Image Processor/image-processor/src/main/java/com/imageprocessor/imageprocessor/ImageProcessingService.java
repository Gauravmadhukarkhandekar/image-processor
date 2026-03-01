package com.imageprocessor.imageprocessor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageProcessingService {

    public BufferedImage processImage(MultipartFile file, String operation) throws IOException {
        BufferedImage image = ImageUtils.multipartToImage(file);
        
        // Perform image processing based on the specified operation
        if ("flipHorizontal".equals(operation)) {
            image = ImageUtils.flipHorizontal(image);
        } else if ("flipVertical".equals(operation)) {
            image = ImageUtils.flipVertical(image);
        } else if ("rotate".equals(operation)) {
            image = ImageUtils.rotate(image, 90);
        }
        // Add more cases for other operations
        
        return image;
    }
}
