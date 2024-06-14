package com.fedmag.verysimplecaptcha.generators;

import com.fedmag.verysimplecaptcha.generators.filters.ImageFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public final class ImageGenerator {

  private static final ArrayList<ImageFilter> filters = new ArrayList<>();
  private static final ArrayList<AffineTransform> listOfStringTransformations = new ArrayList<>();
  // defaults
  private static Font font = new Font("Verdana", Font.BOLD, 28);
  private static Point startingPoint = new Point(10, 100);
  private static Color backgroundColor = new Color(29, 28, 26);
  private static Color fontColor = new Color(236, 239, 244);

  private ImageGenerator() {
  }

  public static void setStartingPoint(Point point) {
    startingPoint = point;
  }

  public static void addFontTransformation(AffineTransform transform) {
    listOfStringTransformations.add(transform);
  }

  public static void removeFontTransformation(AffineTransform transform) {
    listOfStringTransformations.remove(transform);
  }

  public static void removeAllFontTransformations() {
    listOfStringTransformations.clear();
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

  public static void setBackgroundColor(Color color) {
    backgroundColor = color;
  }

  public static void setFontColor(Color color) {
    fontColor = color;
  }

  public static BufferedImage fromString(String string, int width, int height) {
    BufferedImage bufferedImage = new BufferedImage(width, height, 1);
    Graphics2D g2 = prepareGraphics(bufferedImage.createGraphics(), width, height);

    // write on the image and apply filters
    applyStringTransformations(g2, string);
    bufferedImage = applyImageFilters(bufferedImage);
    removeAllImageFilters();
    g2.dispose();
    return bufferedImage;
  }

  private static Graphics2D prepareGraphics(Graphics2D g2, int width, int height) {
    g2.setColor(backgroundColor);
    g2.fillRect(0, 0, width, height);
    g2.setColor(fontColor);
    g2.setFont(ImageGenerator.font);
    return g2;
  }

  private static void applyStringTransformations(Graphics2D g2, String string) {
    if (listOfStringTransformations.isEmpty()) {
      // if the list is empty we just want to draw the string as it is
      g2.drawString(string, startingPoint.x, startingPoint.y);
      return;
    }
    // else apply all the transformations
    Font derived = Font.getFont(font.getAttributes());
    for (AffineTransform transform : listOfStringTransformations) {
      derived = derived.deriveFont(transform);
    }
    g2.setFont(derived);
    g2.drawString(string, startingPoint.x, startingPoint.y);
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
