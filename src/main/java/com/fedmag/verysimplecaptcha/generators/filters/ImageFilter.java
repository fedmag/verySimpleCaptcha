package com.fedmag.verysimplecaptcha.generators.filters;

import java.awt.image.BufferedImage;

public interface ImageFilter {

    BufferedImage apply(BufferedImage input);

}
