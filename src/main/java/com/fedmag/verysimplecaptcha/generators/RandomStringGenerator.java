package com.fedmag.verysimplecaptcha.generators;

import java.util.Random;

/**
 * The {@code RandomStringGenerator} class provides methods to generate random strings. The class
 * includes static methods to generate alphanumeric strings of a specified length or using a custom
 * set of characters.
 *
 * <p>This class cannot be instantiated.
 */
public final class RandomStringGenerator {

  // Default set of alphanumeric characters
  private static final String ALPHANUMERIC_CHARACTERS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvz";

  /** Private constructor to prevent instantiation. */
  private RandomStringGenerator() {}

  /**
   * Generates a random alphanumeric string of the specified length.
   *
   * @param length the length of the random string to generate
   * @return a random alphanumeric string of the specified length
   * @throws IllegalArgumentException if the length is less than 1
   */
  public static String generate(int length) {
    if (length < 1) {
      throw new IllegalArgumentException(
          "The length of the generated string cannot be smaller than 1.");
    }
    StringBuilder randomString = new StringBuilder(length);
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(ALPHANUMERIC_CHARACTERS.length());
      char randomChar = ALPHANUMERIC_CHARACTERS.charAt(randomIndex);
      randomString.append(randomChar);
    }
    return randomString.toString();
  }

  /**
   * Generates a random string of the specified length using the given set of characters.
   *
   * @param length the length of the random string to generate
   * @param charsToUse the set of characters to use for generating the string
   * @return a random string of the specified length using the specified set of characters
   * @throws IllegalArgumentException if the length is less than 1
   */
  public static String generate(int length, String charsToUse) {
    if (length < 1) {
      throw new IllegalArgumentException(
          "The length of the generated string cannot be smaller than 1.");
    }
    StringBuilder randomString = new StringBuilder(length);
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(charsToUse.length());
      char randomChar = charsToUse.charAt(randomIndex);
      randomString.append(randomChar);
    }

    return randomString.toString();
  }
}
