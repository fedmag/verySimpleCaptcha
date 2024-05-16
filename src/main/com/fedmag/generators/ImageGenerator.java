package com.fedmag.generators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Locale;

public class ImageGenerator {

    public static BufferedImage fromString(String string, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        Font font = new Font("Verdana", Font.BOLD, 28);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setFont(font);
        graphics.drawString(string, 20, 100);

        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        bufferedImage = addBlur(bufferedImage);
        return bufferedImage;
    }

    private static BufferedImage addBlur(BufferedImage bufferedImage) {
        Kernel kernel = new Kernel(3, 3, new float[] { 1f / 9f, 1f / 9f, 1f / 9f,
                1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f });
        BufferedImageOp op = new ConvolveOp(kernel);
        return op.filter(bufferedImage, null);
    }
}
