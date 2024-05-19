package com.fedmag.verysimplecaptcha.generators;

import com.fedmag.verysimplecaptcha.generators.filters.ImageFilter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageGenerator {

  // defaults
  private static Font font = new Font("Verdana", Font.BOLD, 28);
  private static final ArrayList<ImageFilter> filters = new ArrayList<>();
  private static final ArrayList<AffineTransform> listOfStringTransformations = new ArrayList<>();
  private static Point startingPoint = new Point(10, 100);

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

  public static BufferedImage fromString(String string, int width, int height) {
    BufferedImage bufferedImage = new BufferedImage(width, height, 1);
    Graphics2D g2 = bufferedImage.createGraphics();
    g2.setFont(ImageGenerator.font);

    applyStringTransformations(g2, string);
    bufferedImage = applyImageFilters(bufferedImage);
    removeAllImageFilters(); // FIXME if this is not there every call to captcha adds a filter XD
    g2.dispose();
    return bufferedImage;
  }

  private static void applyStringTransformations(Graphics2D g2, String string) {
    if (listOfStringTransformations.isEmpty()) {
      // if the list is empty we just want to draw the string as it is
      g2.drawString(string, startingPoint.x, startingPoint.y);
    } else {
      // else apply all the transformations
      Font derived = Font.getFont(font.getAttributes());
      for (AffineTransform transform : listOfStringTransformations) {
        derived = derived.deriveFont(transform);
      }
      g2.setFont(derived);
      g2.drawString(string, startingPoint.x, startingPoint.y);
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
