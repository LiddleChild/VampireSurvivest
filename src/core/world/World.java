package core.world;

import core.Camera;
import core.behavior.GameBehavior;
import core.entity.Player;
import core.sprite.SpriteSheet;
import util.Vector2;

public class World extends GameBehavior {
	public static int MAP_WIDTH;
	public static int MAP_HEIGHT;
	public static Vector2 SPAWN_POINT;

	private Tile[][][] tileMaps;
	private boolean[][] collisionMaps;
	
	private Player player;
	
	public World() {
		super();
		WorldLoader.load(SpriteSheet.tileset, "map0.png", "map1.png", "map2.png", "map3.png");
		
		tileMaps = WorldLoader.getTileMaps();
		collisionMaps = WorldLoader.getCollisionMaps();
		SPAWN_POINT = WorldLoader.getSpawnPoint();
		
		MAP_WIDTH = tileMaps.length;
		MAP_HEIGHT = tileMaps[0].length;
		
		if (SPAWN_POINT == null) {			
			SPAWN_POINT = new Vector2(
					MAP_WIDTH / 2 * Tile.SIZE,
					MAP_HEIGHT / 2 * Tile.SIZE);
		}
		
		player = new Player(this);
		Camera.getInstance().setEntity(player);
	}

	@Override
	public void update() {
		
	}
	
	public boolean isSolidTile(float x, float y) {
		Vector2 coord = getCoord(x, y);
		
		return collisionMaps[(int) coord.x][(int) coord.y];
	}
	
	public boolean isSolidTile(Vector2 pos) {
		return isSolidTile(pos.x, pos.y);
	}
	
	public Vector2 getCoord(float x, float y) {
		return new Vector2(
				(int) (x / Tile.SIZE),
				(int) (y / Tile.SIZE));
	}
	
	public Vector2 getCoord(Vector2 pos) {
		return getCoord(pos.x, pos.y);
	}
	
	public Tile getTile(float x, float y, int layer) {
		return tileMaps[(int) x][(int) y][layer];
	}
	
	public Tile getTile(Vector2 pos, int layer) {
		return getTile(pos.x, pos.y, layer);
	}

}
