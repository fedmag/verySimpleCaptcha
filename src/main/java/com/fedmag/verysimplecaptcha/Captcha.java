package com.fedmag.verysimplecaptcha;

import com.fedmag.verysimplecaptcha.generators.ImageGenerator;
import com.fedmag.verysimplecaptcha.generators.RandomStringGenerator;
import com.fedmag.verysimplecaptcha.generators.filters.ImageFilter;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import javax.imageio.ImageIO;

/**
 * The {@code Captcha} class represents a CAPTCHA generator that produces an image and its
 * corresponding value. It allows various customizations such as character set, image dimensions,
 * colors, and image filters.
 *
 * <p>This class is designed using the Builder pattern to facilitate customization.
 */
public class Captcha {

  private final String trueValue;
  private final BufferedImage image;

  /**
   * Constructs a {@code Captcha} object based on the specified {@code Builder}.
   *
   * @param builder the builder containing the customization options
   */
  private Captcha(Builder builder) {
    this.trueValue =
        builder.charsToUse == null
            ? RandomStringGenerator.generate(builder.numbOfChars)
            : RandomStringGenerator.generate(builder.numbOfChars, builder.charsToUse);

    if (builder.rotateString) {
      generateRotation();
    }
    if (builder.statrtingPoint != null) {
      ImageGenerator.setStartingPoint(builder.statrtingPoint);
    }
    if (builder.backgroundColor != null) {
      ImageGenerator.setBackgroundColor(builder.backgroundColor);
    }
    if (builder.fontColor != null) {
      ImageGenerator.setFontColor(builder.fontColor);
    }

    for (ImageFilter filter : builder.filters) {
      ImageGenerator.addImageFilter(filter);
    }
    this.image = ImageGenerator.fromString(this.trueValue, builder.imageWidth, builder.imageHeight);
  }

  /** Generates a random rotation for the CAPTCHA text. */
  private void generateRotation() {
    double randomRotation = Math.random();
    if (randomRotation < 0.5d) {
      randomRotation *= -1;
    }
    randomRotation = randomRotation * 30;
    ImageGenerator.addFontTransformation(
        AffineTransform.getRotateInstance(Math.toRadians(randomRotation)));
  }

  /**
   * Returns the CAPTCHA token (the true value).
   *
   * @return the CAPTCHA token
   */
  public String getToken() {
    return trueValue;
  }

  /**
   * Returns the CAPTCHA image.
   *
   * @return the CAPTCHA image
   */
  public BufferedImage getImage() {
    return image;
  }

  /**
   * Returns the CAPTCHA image as a Base64 encoded string.
   *
   * @return the CAPTCHA image as a Base64 encoded string
   * @throws IOException if an error occurs during encoding
   */
  public String getImageAsBase64EncododedString() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(this.getImage(), "jpeg", baos);
    return Base64.getEncoder().encodeToString(baos.toByteArray());
  }

  /**
   * The {@code Builder} class is used to build {@code Captcha} instances with various customization
   * options.
   */
  public static class Builder {

    private final ArrayList<ImageFilter> filters = new ArrayList<>();
    private int numbOfChars = 5;
    private int imageWidth = 200;
    private int imageHeight = 100;
    private String charsToUse;
    private boolean rotateString = false;
    private Point statrtingPoint;
    private Color backgroundColor;
    private Color fontColor;

    /**
     * Builds and returns a {@code Captcha} instance.
     *
     * @return a new {@code Captcha} instance
     */
    public Captcha build() {
      assert !charsToUse.isBlank();
      return new Captcha(this);
    }

    /**
     * Sets the number of characters for the CAPTCHA.
     *
     * @param numbOfChars the number of characters
     * @return the builder instance
     * @throws IllegalArgumentException if the number of characters is less than 5 or greater than
     *     10
     */
    public Builder numberOfChars(int numbOfChars) {
      if (numbOfChars < 0 || numbOfChars > 10) {
        throw new IllegalArgumentException("Supported number of chars goes from 5 to 10");
      }
      this.numbOfChars = numbOfChars;
      return this;
    }

    /**
     * Excludes numbers from the character set.
     *
     * @return the builder instance
     */
    public Builder excludeNumbers() {
      charsToUse = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv";
      return this;
    }

    /**
     * Sets the width of the CAPTCHA image.
     *
     * @param imageWidth the width of the image
     * @return the builder instance
     */
    public Builder width(int imageWidth) {
      this.imageWidth = imageWidth;
      return this;
    }

    /**
     * Sets the height of the CAPTCHA image.
     *
     * @param imageHeight the height of the image
     * @return the builder instance
     */
    public Builder height(int imageHeight) {
      this.imageHeight = imageHeight;
      return this;
    }

    /**
     * Excludes letters from the character set.
     *
     * @return the builder instance
     */
    public Builder excludeLetters() {
      charsToUse = "0123456789";
      return this;
    }

    /**
     * Adds an image filter to the CAPTCHA.
     *
     * @param filter the image filter to add
     * @return the builder instance
     */
    public Builder addImageFilter(ImageFilter filter) {
      this.filters.add(filter);
      return this;
    }

    /**
     * Enables or disables rotation of the CAPTCHA text.
     *
     * @param rotate whether to rotate the text
     * @return the builder instance
     */
    public Builder rotate(boolean rotate) {
      this.rotateString = rotate;
      return this;
    }

    /**
     * Sets the starting point for the CAPTCHA text.
     *
     * @param point the starting point
     * @return the builder instance
     */
    public Builder startingPoint(Point point) {
      this.statrtingPoint = point;
      return this;
    }

    /**
     * Sets the background color of the CAPTCHA image.
     *
     * @param color the background color
     * @return the builder instance
     */
    public Builder backgroundColor(Color color) {
      this.backgroundColor = color;
      return this;
    }

    /**
     * Sets the font color of the CAPTCHA text.
     *
     * @param color the font color
     * @return the builder instance
     */
    public Builder fontColor(Color color) {
      this.fontColor = color;
      return this;
    }
  }
}
