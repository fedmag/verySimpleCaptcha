package com.fedmag.verySimpleCaptcha.generators;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;

public class ImageGenerator {

    public static BufferedImage fromString(String string, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        Font font = new Font("Verdana", Font.BOLD, 28);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setFont(font);
        graphics.drawString(string, 20, 100);

        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        return addBlur(bufferedImage, 7); // TODO do we want this to be customizable???
    }

    private static BufferedImage addBlur(BufferedImage image, int matrixSize) {
        int numberOfCells = matrixSize * matrixSize;
        float[] matrix = new float[numberOfCells];

        Arrays.fill(matrix, 1.0f / (float) numberOfCells);
        BufferedImageOp op = new ConvolveOp( new Kernel(matrixSize, matrixSize, matrix), ConvolveOp.EDGE_NO_OP, null );
        return op.filter(image, new BufferedImage(image.getWidth(), image.getHeight(), image.getType()));
    }
}
