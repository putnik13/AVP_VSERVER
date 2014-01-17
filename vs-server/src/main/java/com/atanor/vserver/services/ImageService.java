package com.atanor.vserver.services;

import java.awt.image.BufferedImage;

public interface ImageService {

	boolean isSimilar(BufferedImage image1, BufferedImage image2);
}
