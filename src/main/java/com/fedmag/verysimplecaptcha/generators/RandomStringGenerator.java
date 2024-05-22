package com.fedmag.verysimplecaptcha.generators;

import java.util.Random;

public final class RandomStringGenerator {

  private final static String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvz";

  private RandomStringGenerator() {
  }

  public static String generate(int length) {
    if (length < 1) {
      throw new IllegalArgumentException(
          "The length of the generated string cannot be smaller than 1.");
    }
    StringBuilder randomString = new StringBuilder(length);
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(alphanumericCharacters.length());
      char randomChar = alphanumericCharacters.charAt(randomIndex);
      randomString.append(randomChar);
    }

    return randomString.toString();
  }

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
