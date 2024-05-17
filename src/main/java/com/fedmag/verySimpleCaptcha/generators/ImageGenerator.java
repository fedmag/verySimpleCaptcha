package com.fedmag.verySimpleCaptcha.generators;

import com.fedmag.verySimpleCaptcha.generators.filters.ImageFilter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageGenerator {

    private static Font font = new Font("Verdana", Font.BOLD, 28);
    private static final ArrayList<ImageFilter> filters = new ArrayList<>();
    private static final ArrayList<AffineTransform> stringTransformations = new ArrayList<>();

    public static void addFontTransformation(AffineTransform transform) {
        stringTransformations.add(transform);
    }

    public static void removeFontTransformation(AffineTransform transform) {
        stringTransformations.remove(transform);
    }

    public static void removeAllFontTransformations() {
        stringTransformations.clear();
    }

    public static void setFont(Font font) {
        ImageGenerator.font = font;
    }

    public static void addImageFilter(ImageFilter filter) {
        filters.add(filter);
    }

    public static void removeImageFilter(ImageFilter filter) {
        filters.remove(filter);
    }

    public static void removeAllImageFilters() {
        filters.clear();
    }

    public static BufferedImage fromString(String string, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setFont(ImageGenerator.font);

        applyStringTransformations(g2, string);
        bufferedImage = applyImageFilters(bufferedImage);
        g2.dispose();
        return bufferedImage;
    }

    private static void applyStringTransformations(Graphics2D g2, String string) {
        if (stringTransformations.isEmpty()) {
            g2.drawString(string, 20, 100);
        } else {
            Font derived = Font.getFont(font.getAttributes());
            for (AffineTransform transform : stringTransformations) {
                derived = derived.deriveFont(transform);
            }
            g2.setFont(derived);
            g2.drawString(string, 20, 100);
        }
    }

    private static BufferedImage applyImageFilters(BufferedImage bufferedImage) {
        if (filters.isEmpty()) {
            return bufferedImage;
        }
        for (ImageFilter filter : filters) {
            bufferedImage = filter.apply(bufferedImage);
        }
        return bufferedImage;
    }

}
