package com.fedmag.verySimpleCaptcha.generators.filters;

import java.awt.image.BufferedImage;

public interface ImageFilter {

    BufferedImage apply(BufferedImage input);

}
