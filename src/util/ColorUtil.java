package util;

import javafx.scene.paint.Color;

public class ColorUtil {
	
	/*
	 * COLOR PARSING OVERLOADING
	 */
	public static int parseColor2Int(int r, int g, int b) {
		return 0xff << 32 | r << 16 | g << 8 | b;
	}

	public static int parseColor2Int(int a, int r, int g, int b) {
		return a << 32 | r << 16 | g << 8 | b;
	}
	
	public static int parseColor2Int(Color c) {
		int a = (int) (c.getOpacity() * 255);
		int r = (int) (c.getRed() * 255);
		int g = (int) (c.getGreen() * 255);
		int b = (int) (c.getBlue() * 255);
		
		return parseColor2Int(a, r, g, b);
	}
	
}
