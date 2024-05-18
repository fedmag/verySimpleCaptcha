package com.fedmag.verysimplecaptcha;

import com.fedmag.verysimplecaptcha.generators.filters.SimpleGaussianFilter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Captcha captcha = new Captcha.Builder()
                .width(220)
                .height(220)
                .numberOfChars(8)
                .startingPoint(new Point(10, 220 / 2))
                .rotate(true)
                .addImageFilter(new SimpleGaussianFilter())
                .build();
        System.out.println("Generated string: " + captcha.getToken());
        try {
            ImageIO.write(captcha.getImage(), "jpg", new File("test.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Encoded string: " + captcha.getImageAsBase64EncododedString());
    }
}
