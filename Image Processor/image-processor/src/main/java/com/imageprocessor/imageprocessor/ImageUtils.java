package com.imageprocessor.imageprocessor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    /** Reads image and normalizes to TYPE_INT_ARGB so all operations work reliably. */
    public static BufferedImage multipartToImage(org.springframework.web.multipart.MultipartFile file) throws IOException {
        BufferedImage read;
        try (InputStream inputStream = file.getInputStream()) {
            read = ImageIO.read(inputStream);
        }
        if (read == null) {
            throw new IOException("Unsupported or invalid image format");
        }
        return normalizeToARGB(read);
    }

    /** Converts any BufferedImage to TYPE_INT_ARGB for consistent processing. */
    public static BufferedImage normalizeToARGB(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_INT_ARGB) return image;
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return out;
    }

    public static BufferedImage flipHorizontal(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(image, w, 0, -w, h, null);
        g.dispose();
        return out;
    }

    public static BufferedImage flipVertical(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(image, 0, h, w, -h, null);
        g.dispose();
        return out;
    }

    public static BufferedImage rotate(BufferedImage image, double degrees) {
        double rad = Math.toRadians(degrees);
        int w = image.getWidth();
        int h = image.getHeight();
        AffineTransform at = AffineTransform.getRotateInstance(rad, w / 2.0, h / 2.0);
        Rectangle2D bounds = at.createTransformedShape(new Rectangle(0, 0, w, h)).getBounds2D();
        int nw = (int) Math.ceil(bounds.getWidth());
        int nh = (int) Math.ceil(bounds.getHeight());
        AffineTransform drawAt = AffineTransform.getTranslateInstance(-bounds.getX(), -bounds.getY());
        drawAt.rotate(rad, w / 2.0, h / 2.0);
        BufferedImage out = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(image, drawAt, null);
        g.dispose();
        return out;
    }

    public static BufferedImage convertToGrayscale(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                int a = (rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                int gray = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                out.setRGB(x, y, (a << 24) | (gray << 16) | (gray << 8) | gray);
            }
        }
        return out;
    }

    public static BufferedImage resize(BufferedImage image, int targetWidth, int targetHeight) {
        if (targetWidth <= 0 || targetHeight <= 0) return image;
        Image scaled = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage out = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(scaled, 0, 0, null);
        g.dispose();
        return out;
    }

    public static BufferedImage generateThumbnail(BufferedImage image, int maxSize) {
        int w = image.getWidth();
        int h = image.getHeight();
        if (w <= maxSize && h <= maxSize) return image;
        double scale = Math.min((double) maxSize / w, (double) maxSize / h);
        int tw = (int) Math.round(w * scale);
        int th = (int) Math.round(h * scale);
        return resize(image, tw, th);
    }

    public static byte[] toPngBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
