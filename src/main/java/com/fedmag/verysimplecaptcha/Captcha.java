package com.fedmag.verysimplecaptcha;

import com.fedmag.verysimplecaptcha.generators.ImageGenerator;
import com.fedmag.verysimplecaptcha.generators.RandomStringGenerator;
import com.fedmag.verysimplecaptcha.generators.filters.ImageFilter;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import javax.imageio.ImageIO;

public class Captcha {

  private final String trueValue;
  private final BufferedImage image;

  private Captcha(Builder builder) {
    this.trueValue =
        builder.charsToUse == null ? RandomStringGenerator.generate(builder.numbOfChars)
            : RandomStringGenerator.generate(builder.numbOfChars, builder.charsToUse);

    if (builder.rotateString) {
      double randomRotation = Math.random();
      if (randomRotation < 0.5d) {
        randomRotation *= -1;
      }
      randomRotation = randomRotation * 30;
      System.out.println("Random rotation:  " + randomRotation);
      ImageGenerator.addFontTransformation(
          AffineTransform.getRotateInstance(Math.toRadians(randomRotation)));
    }
    if (builder.statrtingPoint != null) {
      ImageGenerator.setStartingPoint(builder.statrtingPoint);
    }
    for (ImageFilter filter : builder.filters) {
      ImageGenerator.addImageFilter(filter);
    }
    this.image = ImageGenerator.fromString(this.trueValue, builder.imageWidth, builder.imageHeight);
  }

  public String getToken() {
    return trueValue;
  }

  public BufferedImage getImage() {
    return image;
  }

  public String getImageAsBase64EncododedString() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(this.getImage(), "jpeg", baos);
    return Base64.getEncoder().encodeToString(baos.toByteArray());
  }

  public static class Builder {

    private final ArrayList<ImageFilter> filters = new ArrayList<>();
    private int numbOfChars = 5;
    private int imageWidth = 200;
    private int imageHeight = 100;
    private String charsToUse;
    private boolean rotateString = false;
    private Point statrtingPoint;

    public Captcha build() {
      assert !charsToUse.isBlank();
      return new Captcha(this);
    }

    public Builder numberOfChars(int numbOfChars) {
      if (numbOfChars < 0 || numbOfChars > 10) {
        throw new IllegalArgumentException("Supported number of chars goes from 5 to 10");
      }
      this.numbOfChars = numbOfChars;
      return this;
    }

    public Builder excludeNumbers() {
      charsToUse = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv";
      return this;
    }

    public Builder width(int imageWidth) {
      this.imageWidth = imageWidth;
      return this;
    }

    public Builder height(int imageHeight) {
      this.imageHeight = imageHeight;
      return this;
    }


    public Builder excludeLetters() {
      charsToUse = "0123456789";
      return this;
    }

    public Builder addImageFilter(ImageFilter filter) {
      this.filters.add(filter);
      return this;
    }

    public Builder rotate(boolean rotate) {
      this.rotateString = rotate;
      return this;
    }

    public Builder startingPoint(Point point) {
      this.statrtingPoint = point;
      return this;
    }
  }
}
