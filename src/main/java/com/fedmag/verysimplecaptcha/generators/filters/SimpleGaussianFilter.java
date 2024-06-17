package com.fedmag.verysimplecaptcha.generators.filters;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;

/**
 * The {@code SimpleGaussianFilter} class provides a simple implementation of a Gaussian filter to
 * apply a blurring effect to an image. This filter uses a convolution operation with a square
 * matrix.
 */
public class SimpleGaussianFilter implements ImageFilter {

  private int matrixSize = 7;

  /**
   * Constructs a {@code SimpleGaussianFilter} with the specified matrix size.
   *
   * @param matrixSize the size of the convolution matrix
   */
  public SimpleGaussianFilter(int matrixSize) {
    this.matrixSize = matrixSize;
  }

  /** Constructs a {@code SimpleGaussianFilter} with the default matrix size of 7. */
  public SimpleGaussianFilter() {}

  /**
   * Applies the Gaussian filter to the given image.
   *
   * @param input the input image to which the filter will be applied
   * @return a new {@code BufferedImage} with the filter applied
   */
  @Override
  public BufferedImage apply(BufferedImage input) {
    int numberOfCells = matrixSize * matrixSize;
    float[] matrix = new float[numberOfCells];

    Arrays.fill(matrix, 1.0f / (float) numberOfCells);
    BufferedImageOp op =
        new ConvolveOp(new Kernel(matrixSize, matrixSize, matrix), ConvolveOp.EDGE_NO_OP, null);
    return op.filter(input, null);
  }
}
