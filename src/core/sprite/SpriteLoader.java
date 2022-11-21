package core.sprite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class SpriteLoader {
	
	public static Image load(String src) {
		FileInputStream in = null;
		
		try {
			in = new FileInputStream(new File(String.format("./res/%s", src)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		
		}
		return new Image(in);
	}
	
}
