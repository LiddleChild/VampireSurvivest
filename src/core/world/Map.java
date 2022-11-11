package core.world;

import util.math.Vector2;

public class Map {
	private static Map instance;
	
	public static final int GRID_SIZE = 48;
	public static final int MAP_WIDTH = 10;
	public static final int MAP_HEIGHT = 10;
	public static final int SPAWN_POINT_X = MAP_WIDTH / 2 * GRID_SIZE;
	public static final int SPAWN_POINT_Y = MAP_HEIGHT / 2 * GRID_SIZE;
	
	public boolean[][] tileMaps;
	
	public Map() {
		tileMaps = new boolean[MAP_WIDTH][MAP_HEIGHT];

		tileMaps[2][2] = true;
		tileMaps[2][3] = true;
		tileMaps[3][3] = true;
	}
	
	public boolean getTile(float x, float y) {
		Vector2 coord = getCoord(x, y);
		return tileMaps[(int) coord.x][(int) coord.y];
	}
	
	public boolean getTile(Vector2 pos) {
		return getTile(pos.x, pos.y);
	}
	
	public Vector2 getCoord(float x, float y) {
		return new Vector2(
				(int) (x / GRID_SIZE),
				(int) (y / GRID_SIZE));
	}
	
	public Vector2 getCoord(Vector2 pos) {
		return getCoord(pos.x, pos.y);
	}
	
	public static Map getInstance() {
		if (instance == null) {
			instance = new Map();
		}
		
		return instance;
	}
	
}
