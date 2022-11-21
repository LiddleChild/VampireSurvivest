package core.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Sprite implements Comparable<Sprite> {
	
	private int x, y, size;
	private Image image;
	private SpriteSheet sheet;
	
	public Sprite(SpriteSheet sheet, int x, int y) {
		this.size = sheet.getSize();
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		
		load();
	}
	
	private void load() {
		PixelReader reader = sheet.getImage().getPixelReader();
		image = new WritableImage(reader, x, y, size, size);
	}
	
	public Image getImage() { 
		return image;
	}

	@Override
	public int compareTo(Sprite o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}
	
}
