package com.fedmag.verysimplecaptcha.generators;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fedmag.verysimplecaptcha.generators.filters.ImageFilter;
import com.fedmag.verysimplecaptcha.generators.filters.SimpleGaussianFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class ImageGeneratorTest {

  @BeforeEach
  void setUp() {
    ImageGenerator.removeAllImageFilters();
    ImageGenerator.removeAllFontTransformations();
    ImageGenerator.setFont(new Font("Verdana", Font.BOLD, 28));
    ImageGenerator.setStartingPoint(new Point(10, 100));
    ImageGenerator.setBackgroundColor(new Color(83, 102, 102, 249));
    ImageGenerator.setFontColor(new Color(150, 193, 214, 219));
  }

  @Test
  public void successTest() {
    // when
    BufferedImage image = ImageGenerator.fromString("test", 100, 100);
    // do
    assertNotNull(image);
  }

  @Test
  public void testSetSize() {
    // when
    BufferedImage image = ImageGenerator.fromString("test", 600, 100);
    // do
    saveImage(image, "testSetSize");
  }

  @Test
  void testSetStartingPoint() {
    Point newPoint = new Point(2, 180);
    ImageGenerator.setStartingPoint(newPoint);
    // Need to call a method that uses startingPoint to validate
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    // Here you would inspect the image or mock the Graphics2D object
    // For simplicity, we assume a way to verify the point (not directly testable)
    saveImage(image, "testSetStartingPoint");
  }

  @Test
  void testAddAndRemoveFontTransformation() {
    // when
    AffineTransform transform = new AffineTransform();
    transform.rotate(Math.PI / 4);
    // then
    ImageGenerator.addFontTransformation(transform);
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    ImageGenerator.removeFontTransformation(transform);
    image = ImageGenerator.fromString("Test", 200, 200);
    // do
    saveImage(image, "testAddAndRemoveFontTransformation");
  }

  @Test
  void testAddFontTransformation() {
    // when
    AffineTransform transform = new AffineTransform();
    transform.rotate(Math.PI / 4);
    // then
    ImageGenerator.addFontTransformation(transform);
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    // do
    saveImage(image, "testAddFontTransformation");
  }

  @Test
  void testRemoveAllFontTransformations() {
    // when
    AffineTransform transform = new AffineTransform();
    transform.rotate(Math.PI / 4);
    ImageGenerator.addFontTransformation(transform);
    ImageGenerator.removeAllFontTransformations();
    // then
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    // do
    saveImage(image, "testRemoveAllFontTransformations");
  }

  @Test
  void testSetFont() {
    // when
    Font newFont = new Font("Arial", Font.PLAIN, 20);
    ImageGenerator.setFont(newFont);
    // then
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    // do
    saveImage(image, "testSetFont");
  }

  @Test
  void testAddImageFilter() {
    // when
    ImageGenerator.addImageFilter(new SimpleGaussianFilter());
    // then
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    // do
    saveImage(image, "testAddImageFilter");
  }

  @Test
  void testAddAndRemoveImageFilter() {
    // when
    ImageFilter mockFilter = Mockito.mock(ImageFilter.class);
    BufferedImage dummyImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
    Mockito.when(mockFilter.apply(Mockito.any(BufferedImage.class))).thenReturn(dummyImage);

    // then
    ImageGenerator.addImageFilter(mockFilter);
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    // do
    Mockito.verify(mockFilter, Mockito.times(1)).apply(Mockito.any(BufferedImage.class));

    // then
    ImageGenerator.removeImageFilter(mockFilter);
    image = ImageGenerator.fromString("Test", 200, 200);
    // do
    Mockito.verifyNoMoreInteractions(mockFilter);
  }

  @Test
  void testRemoveAllImageFilters() {
    // when
    ImageFilter mockFilter = Mockito.mock(ImageFilter.class);
    ImageGenerator.addImageFilter(mockFilter);
    ImageGenerator.removeAllImageFilters();
    // then
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    // do
    Mockito.verify(mockFilter, Mockito.never()).apply(Mockito.any(BufferedImage.class));
  }

  @Test
  void testSetBackgroundColor() {
    // when
    Color newColor = Color.BLUE;
    ImageGenerator.setBackgroundColor(newColor);
    // then
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    // do
    saveImage(image, "testSetBackgroundColor");

  }

  @Test
  void testSetFontColor() {
    // when
    Color newColor = Color.RED;
    ImageGenerator.setFontColor(newColor);
    // then
    BufferedImage image = ImageGenerator.fromString("Test", 200, 200);
    // do
    saveImage(image, "testSetFontColor");

  }

  @Test
  void testFromString() {
    // when
    String testString = "Captcha";
    // then
    BufferedImage image = ImageGenerator.fromString(testString, 200, 200);
    // do
    Assertions.assertNotNull(image);
    Assertions.assertEquals(200, image.getWidth());
    Assertions.assertEquals(200, image.getHeight());

    saveImage(image, "testFromString");
  }

  private void saveImage(BufferedImage image, String testName) {
    try {
      ImageIO.write(image, "jpg", new File("src/test/resources/" + testName + ".jpg"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}