package com.fedmag.verySimpleCaptcha;

import com.fedmag.verySimpleCaptcha.generators.ImageGenerator;
import com.fedmag.verySimpleCaptcha.generators.RandomStringGenerator;
import com.fedmag.verySimpleCaptcha.generators.filters.ImageFilter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Captcha {

    private final String trueValue;
    private final BufferedImage image;

    public String getToken() {
        return trueValue;
    }

    public BufferedImage getImage() {
        return image;
    }

    private Captcha(Builder builder) {
        this.trueValue = builder.charsToUse == null ? RandomStringGenerator.generate(builder.numbOfChars) : RandomStringGenerator.generate(builder.numbOfChars, builder.charsToUse);
        this.image = ImageGenerator.fromString(this.trueValue, builder.imageWidth, builder.imageHeight);
    }

    public static class Builder {

        private int numbOfChars = 5;
        private int imageWidth = 200;
        private int imageHeight = 100;
        private String charsToUse;
        ArrayList<Character> availableChars = new ArrayList<>();
        ArrayList<ImageFilter> filters = new ArrayList<>();

        public Captcha build() {
            assert !availableChars.isEmpty();
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

        public Builder addFilter(ImageFilter filter) {
            this.filters.add(filter);
            return this;
        }
    }

    /**
     * This takes the image, writes the bytes to an output stream, obtains a byte array from the stream and encodes those bytes so that they can be sent as string.
     * In order to send the captcha to the FE, the session created in the BE can be used to store the correct answer for that user:
     *
     * Specifically the session works based on cookies:
     *      By default session tracking happens by cookies.
     *      WebServer sends the session id to the browser in the form of cookie.
     *      And, the browser send the cookie having session id for the subsequent requests.
     *
     * How does the browser identifies which cookies to send for a link/request? It is based on the these parameters. If the request matches these paramters the browser sends that particular cookie:
     *
     *     Domain: The domain name to which the request is made. Verify in your case if the domain name is same for two instances
     *     Path: If the path name is same. Web Server send the context root as the path , requests under same context root share cookies.
     *     Secure: Server sends if the given cookie is secure or not. Meaning, if the cookie can be sent on non-secure channel.

    public String generateCaptchaImage(String token) {
        BufferedImage image = cage.drawImage(token);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate captcha image", e);
        }
    }
     */

}
