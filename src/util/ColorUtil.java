package util;

import javafx.scene.paint.Color;

/*
 * 
 * ColorUtil
 * - Color parsing
 * 
 */

public class ColorUtil {
	
	/*
	 * OVERLOADING parseColorToInt
	 */
	
	// RGB
	public static int parseColorToInt(int r, int g, int b) {
		return 0xff << 32 | r << 16 | g << 8 | b;
	}

	// RGBA
	public static int parseColorToInt(int r, int g, int b, int a) {
		return a << 32 | r << 16 | g << 8 | b;
	}
	
	// Color
	public static int parseColorToInt(Color c) {
		int a = (int) (c.getOpacity() * 255);
		int r = (int) (c.getRed() * 255);
		int g = (int) (c.getGreen() * 255);
		int b = (int) (c.getBlue() * 255);
		
		return parseColorToInt(r, g, b, a);
	}
	
	public static Color parseRGBToColor(int r, int g, int b) {
		return new Color(r / 255.f, g / 255.f, b / 255.f, 1.f);
	}
	
}
