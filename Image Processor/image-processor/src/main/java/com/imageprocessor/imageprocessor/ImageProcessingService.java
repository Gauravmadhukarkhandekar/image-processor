package com.imageprocessor.imageprocessor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Service
public class ImageProcessingService {

    private static final int DEFAULT_THUMBNAIL_SIZE = 150;
    private static final double DEFAULT_ROTATE_DEGREES = 90;

    public BufferedImage processImage(MultipartFile file, List<String> operations,
                                      Integer degrees, Integer width, Integer height) throws IOException {
        BufferedImage image = ImageUtils.multipartToImage(file);

        double rotateDegrees = degrees != null ? degrees : DEFAULT_ROTATE_DEGREES;
        int targetWidth = width != null && width > 0 ? width : 0;
        int targetHeight = height != null && height > 0 ? height : 0;

        for (String op : operations) {
            switch (op) {
                case "flipHorizontal" -> image = ImageUtils.flipHorizontal(image);
                case "flipVertical" -> image = ImageUtils.flipVertical(image);
                case "rotate" -> image = ImageUtils.rotate(image, rotateDegrees);
                case "rotateLeft" -> image = ImageUtils.rotate(image, -90);
                case "rotateRight" -> image = ImageUtils.rotate(image, 90);
                case "convertToGrayscale" -> image = ImageUtils.convertToGrayscale(image);
                case "resize" -> {
                    if (targetWidth > 0 && targetHeight > 0) {
                        image = ImageUtils.resize(image, targetWidth, targetHeight);
                    }
                }
                case "generateThumbnail" -> image = ImageUtils.generateThumbnail(image, DEFAULT_THUMBNAIL_SIZE);
                default -> { /* ignore unknown */ }
            }
        }

        return image;
    }
}
