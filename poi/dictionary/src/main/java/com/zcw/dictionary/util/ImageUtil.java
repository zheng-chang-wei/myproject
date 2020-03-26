package com.zcw.dictionary.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static int[] getImageWH(String imagePath, int width_Max) {
		double radio = 1;
		BufferedImage image = readImage(imagePath);
		int targetW = image.getWidth();
		int targetH = image.getHeight();
		if (targetW > width_Max) {
			radio = (double) width_Max / targetW;
			targetW = width_Max;
			targetH = (int) (targetH * radio);
		}
		return new int[] { targetW, targetH };
	}

	public static BufferedImage readImage(final String imagePath) {
		final File file = new File(imagePath);
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (final IOException e) {
		}
		return image;
	}
}
