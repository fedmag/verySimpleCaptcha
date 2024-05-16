package com.fedmag.verySimpleCaptcha.generators;

import com.fedmag.verySimpleCaptcha.generators.filters.ImageFilter;
import com.fedmag.verySimpleCaptcha.generators.filters.SimpleGaussianFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageGenerator {

    private static Font font = new Font("Verdana", Font.BOLD, 28);
    private static final ArrayList<ImageFilter> filters = new ArrayList<>();

    static {
        filters.add(new SimpleGaussianFilter());
    }

    public static void setFont(Font font) {
        ImageGenerator.font = font;
    }

    public static void addFilter(ImageFilter filter) {
        filters.add(filter);
    }

    // TODO here we might provide the filters as parameter from the captcha call. Do we actually want it tho?
    //  we could also do like for the Font, provide a default list of filters, and allow the user to modify it
    public static BufferedImage fromString(String string, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setFont(ImageGenerator.font);
        graphics.drawString(string, 20, 100);

        for (ImageFilter filter : filters) {
            bufferedImage = filter.apply(bufferedImage);
        }

        return bufferedImage; // TODO do we want this to be customizable???
    }

}
