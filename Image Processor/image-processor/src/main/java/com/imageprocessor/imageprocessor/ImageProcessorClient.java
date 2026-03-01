package com.imageprocessor.imageprocessor.rpc;

import java.awt.image.BufferedImage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ImageProcessorClient {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ImageProcessorService imageProcessorService = (ImageProcessorService) registry.lookup("ImageProcessorService");

            // Load image (replace with your image loading logic)
            BufferedImage image = loadImage("path/to/your/image.jpg");

            // Example: Flip Horizontal
            BufferedImage flippedImage = imageProcessorService.flipHorizontal(image);

            // Save or display the processed image
            // (replace with your image saving/displaying logic)
            saveImage(flippedImage, "path/to/your/flipped_image.jpg");
            displayImage(flippedImage, "Flipped Image");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Placeholder methods, replace with your actual implementations
    private static BufferedImage loadImage(String filePath) {
        // Implement image loading logic
        return null;
    }

    private static void saveImage(BufferedImage image, String filePath) {
        // Implement image saving logic
    }

    private static void displayImage(BufferedImage image, String title) {
        // Implement image displaying logic
    }
}
