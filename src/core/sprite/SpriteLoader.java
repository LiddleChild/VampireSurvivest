package core.sprite;

import javafx.scene.image.Image;

/*
 * 
 * SpriteLoader
 * - Loads image from system resources
 * 
 */

public class SpriteLoader {
	public static Image load(String src) {
		String path = ClassLoader.getSystemResource(src).toString();
		return new Image(path);
	}
}
