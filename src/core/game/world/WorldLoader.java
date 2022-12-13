package core.game.world;

import java.util.Map;

import core.sprite.Sprite;
import core.sprite.SpriteLoader;
import core.sprite.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import util.ColorUtil;
import util.math.Vector2f;

/*
 * 
 * WorldLoader
 * - Loads all tiles
 * 
 */

public class WorldLoader {
	
	private static Tile[][][] tileMaps;
	private static boolean[][] collisionMaps;
	
	private static Vector2f spawnPoint;
	
	public static void load(SpriteSheet tileset, String... paths) {
		Image maps[] = new Image[paths.length];
		for (int i = 0; i < maps.length; i++) {
			maps[i] = SpriteLoader.load(paths[i]);
		}

		int width = (int) maps[0].getWidth();
		int height = (int) maps[0].getHeight();

		Map<Integer, Sprite> colorMaps = tileset.getColorMaps();
		
		tileMaps = new Tile[width][height][maps.length];
		collisionMaps = new boolean[width][height];
		
		// Coordinate
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				// Layer
				for (int i = 0; i < maps.length; i++) {
					Color c = maps[i].getPixelReader().getColor(x, y);
					Sprite s = colorMaps.get(ColorUtil.parseColorToInt(c));
					
					// A color 255, 255, 0 in map data indicates spawn point
					if (ColorUtil.parseColorToInt(c) == ColorUtil.parseColorToInt(255, 255, 0)) {
						spawnPoint = new Vector2f(x * Tile.SIZE, y * Tile.SIZE);
					}
					
					// Set collision maps from first layer
					if (i == 0) {		
						collisionMaps[x][y] = isSolid(c);
					}
					
					int order = i + ((i >= 2) ? 2 : 0);
					tileMaps[x][y][i] = new Tile(s, x, y, order);
				}
			}
		}
	}
	
	// Empty pixel -> solid block
	private static boolean isSolid(Color c) {
		return (ColorUtil.parseColorToInt(c) == ColorUtil.parseColorToInt(0, 0, 0, 0));
	}

	/*
	 * GETTERS & SETTERS
	 */
	public static Tile[][][] getTileMaps() {
		return tileMaps;
	}

	public static boolean[][] getCollisionMaps() {
		return collisionMaps;
	}

	public static Vector2f getSpawnPoint() {
		return spawnPoint;
	}
	
}
