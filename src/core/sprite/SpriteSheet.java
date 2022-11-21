package core.sprite;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import util.ColorUtil;

public class SpriteSheet {
	
	public static SpriteSheet tileset = new SpriteSheet("tilesheets.png", "colorsheets.png", 16);
	
	private int width, height, size;
	private Image image;
	private Map<Integer, Sprite> colorMaps = null;
	
	public SpriteSheet(String spriteSheetpath, int size) {
		this(spriteSheetpath, null, size);
	}
	
	public SpriteSheet(String spriteSheetpath, String colorSheetPath, int size) {
		this.image = SpriteLoader.load(spriteSheetpath);
		
		this.width = (int) image.getWidth();
		this.height = (int) image.getHeight();
		this.size = size;
		
		loadColorSheets(colorSheetPath);
	}
	
	private void loadColorSheets(String path) {
		if (path == null) return;
		
		Image colorsheet = SpriteLoader.load(path);

		int tileWidth = (width / size);
		int tileHeight = (height / size);

		this.colorMaps = new HashMap<Integer, Sprite>();
		
		PixelReader reader = colorsheet.getPixelReader();
		
		for (int x = 0; x < tileWidth; x++) {
			for (int y = 0; y < tileHeight; y++) {
				Color c = reader.getColor(x * size, y * size);
				
				colorMaps.put(ColorUtil.parseColor2Int(c), new Sprite(this, x, y));
			}
		}
	}
	
	public Image getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getSize() {
		return size;
	}

	public Map<Integer, Sprite> getColorMaps() {
		return colorMaps;
	}
	
}
