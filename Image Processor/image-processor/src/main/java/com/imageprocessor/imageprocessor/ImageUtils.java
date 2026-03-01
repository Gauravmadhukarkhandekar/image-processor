package com.imageprocessor.imageprocessor;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public static BufferedImage multipartToImage(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            return ImageIO.read(inputStream);
        }
    }

    public static BufferedImage flipHorizontal(BufferedImage image) {
        // Implement flip horizontal logic
        return image; // Placeholder, replace with actual implementation
    }

    public static BufferedImage flipVertical(BufferedImage image) {
        // Implement flip vertical logic
        return image; // Placeholder, replace with actual implementation
    }

    public static BufferedImage rotate(BufferedImage image, double degrees) {
        // Implement rotate logic
        return image; // Placeholder, replace with actual implementation
    }
}
