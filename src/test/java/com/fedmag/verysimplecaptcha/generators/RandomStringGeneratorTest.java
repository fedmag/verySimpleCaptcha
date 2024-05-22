package com.fedmag.verysimplecaptcha.generators;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomStringGeneratorTest {

  @Test
  void generateInvalidNumbsOfChars() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> RandomStringGenerator.generate(0));
  }

  @Test
  void generateValidVariableLength() {
    // when
    Integer stringLength = 5;
    String generated = RandomStringGenerator.generate(stringLength);
    // do
    Assertions.assertNotNull(generated);
    Assertions.assertEquals(stringLength, generated.length());
  }

  @Test
  void generateOnlyNumbers() {
    // when
    String generated = RandomStringGenerator.generate(5, "0123456789");
    System.out.println(generated);
    // then
    Integer integer = Integer.valueOf(generated);
    // do
    Assertions.assertNotNull(integer);
  }

  @Test
  void generateOnlyUppercaseLetters() {
    // when
    String generated = RandomStringGenerator.generate(5, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    System.out.println(generated);
    // do
    Assertions.assertTrue(StringUtils.isAllUpperCase(generated));
  }

  @Test
  void generateOnlyLowercaseLetters() {
    // when
    String generated = RandomStringGenerator.generate(5, "abcdefghijklmnopqrstuvz");
    System.out.println(generated);
    // do
    Assertions.assertTrue(StringUtils.isAllLowerCase(generated));

  }
}