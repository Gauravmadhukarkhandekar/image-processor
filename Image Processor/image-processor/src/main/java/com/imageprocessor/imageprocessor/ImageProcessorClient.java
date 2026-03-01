package com.imageprocessor.imageprocessor;

import java.awt.image.BufferedImage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Optional RMI client for remote image processing.
 * The main application is the web app (ImageController + index.html).
 */
public class ImageProcessorClient {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Object service = registry.lookup("ImageProcessorService");
            // Use service when RMI server is running and ImageProcessorService interface is defined
            @SuppressWarnings("unused")
            BufferedImage image = loadImage("path/to/your/image.jpg");
            // BufferedImage flippedImage = ((ImageProcessorService) service).flipHorizontal(image);
            // saveImage(flippedImage, "path/to/your/flipped_image.jpg");
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
