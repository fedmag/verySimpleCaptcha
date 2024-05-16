package com.fedmag.verySimpleCaptcha.generators.filters;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;

public class SimpleGaussianFilter implements ImageFilter{

    private int matrixSize = 7;

    public SimpleGaussianFilter(int matrixSize) {
        this.matrixSize = matrixSize;
    }

    public SimpleGaussianFilter() {
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        int numberOfCells = matrixSize * matrixSize;
        float[] matrix = new float[numberOfCells];

        Arrays.fill(matrix, 1.0f / (float) numberOfCells);
        BufferedImageOp op = new ConvolveOp( new Kernel(matrixSize, matrixSize, matrix), ConvolveOp.EDGE_NO_OP, null );
        return op.filter(input, new BufferedImage(input.getWidth(), input.getHeight(), input.getType()));
    }
}
