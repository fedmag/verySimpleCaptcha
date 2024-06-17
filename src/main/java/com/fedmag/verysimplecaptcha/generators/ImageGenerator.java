package com.fedmag.verysimplecaptcha.generators;

import com.fedmag.verysimplecaptcha.generators.filters.ImageFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The {@code ImageGenerator} class provides methods to generate images with text and various
 * customizations. The class supports setting font styles, colors, transformations, and applying
 * image filters.
 *
 * <p>This class cannot be instantiated.
 */
public final class ImageGenerator {

  private static final ArrayList<ImageFilter> filters = new ArrayList<>();
  private static final ArrayList<AffineTransform> listOfStringTransformations = new ArrayList<>();
  // Default settings
  private static Font font = new Font("Verdana", Font.BOLD, 28);
  private static Point startingPoint = new Point(10, 100);
  private static Color backgroundColor = new Color(29, 28, 26);
  private static Color fontColor = new Color(236, 239, 244);

  /** Private constructor to prevent instantiation. */
  private ImageGenerator() {}

  /**
   * Sets the starting point for drawing the text.
   *
   * @param point the starting point
   */
  public static void setStartingPoint(Point point) {
    startingPoint = point;
  }

  /**
   * Adds a font transformation to be applied to the text.
   *
   * @param transform the affine transform to be added
   */
  public static void addFontTransformation(AffineTransform transform) {
    listOfStringTransformations.add(transform);
  }

  /**
   * Removes a specific font transformation.
   *
   * @param transform the affine transform to be removed
   */
  public static void removeFontTransformation(AffineTransform transform) {
    listOfStringTransformations.remove(transform);
  }

  /** Removes all font transformations. */
  public static void removeAllFontTransformations() {
    listOfStringTransformations.clear();
  }

  /**
   * Sets the font for the text.
   *
   * @param font the font to be set
   */
  public static void setFont(Font font) {
    ImageGenerator.font = font;
  }

  /**
   * Adds an image filter to be applied to the generated image.
   *
   * @param filter the image filter to be added
   */
  public static void addImageFilter(ImageFilter filter) {
    filters.add(filter);
  }

  /**
   * Removes a specific image filter.
   *
   * @param filter the image filter to be removed
   */
  public static void removeImageFilter(ImageFilter filter) {
    filters.remove(filter);
  }

  /** Removes all image filters. */
  public static void removeAllImageFilters() {
    filters.clear();
  }

  /**
   * Sets the background color for the image.
   *
   * @param color the background color to be set
   */
  public static void setBackgroundColor(Color color) {
    backgroundColor = color;
  }

  /**
   * Sets the font color for the text.
   *
   * @param color the font color to be set
   */
  public static void setFontColor(Color color) {
    fontColor = color;
  }

  /**
   * Generates an image from the given string with specified width and height.
   *
   * @param string the string to be drawn on the image
   * @param width the width of the image
   * @param height the height of the image
   * @return the generated image as a {@code BufferedImage}
   */
  public static BufferedImage fromString(String string, int width, int height) {
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = prepareGraphics(bufferedImage.createGraphics(), width, height);

    // Draw the string and apply filters
    applyStringTransformations(g2, string);
    bufferedImage = applyImageFilters(bufferedImage);
    removeAllImageFilters();
    g2.dispose();
    return bufferedImage;
  }

  /**
   * Prepares the graphics context with the specified dimensions.
   *
   * @param g2 the graphics context
   * @param width the width of the image
   * @param height the height of the image
   * @return the prepared graphics context
   */
  private static Graphics2D prepareGraphics(Graphics2D g2, int width, int height) {
    g2.setColor(backgroundColor);
    g2.fillRect(0, 0, width, height);
    g2.setColor(fontColor);
    g2.setFont(ImageGenerator.font);
    return g2;
  }

  /**
   * Applies the string transformations and draws the string on the graphics context.
   *
   * @param g2 the graphics context
   * @param string the string to be drawn
   */
  private static void applyStringTransformations(Graphics2D g2, String string) {
    if (listOfStringTransformations.isEmpty()) {
      // Draw the string as it is if no transformations are specified
      g2.drawString(string, startingPoint.x, startingPoint.y);
      return;
    }
    // Apply all transformations and draw the string
    Font derived = font.deriveFont(font.getAttributes());
    for (AffineTransform transform : listOfStringTransformations) {
      derived = derived.deriveFont(transform);
    }
    g2.setFont(derived);
    g2.drawString(string, startingPoint.x, startingPoint.y);
  }

  /**
   * Applies all image filters to the given image.
   *
   * @param bufferedImage the image to which filters will be applied
   * @return the filtered image
   */
  private static BufferedImage applyImageFilters(BufferedImage bufferedImage) {
    for (ImageFilter filter : filters) {
      bufferedImage = filter.apply(bufferedImage);
    }
    return bufferedImage;
  }
}
