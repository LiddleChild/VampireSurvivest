package core.world;

import core.Camera;
import core.behavior.GameBehavior;
import core.entity.Enemy;
import core.entity.Player;
import core.sprite.SpriteSheet;
import util.Vector2;

public class World extends GameBehavior {
	public static int MAP_WIDTH;
	public static int MAP_HEIGHT;
	public static int MAP_MAX_LAYER;
	public static Vector2 SPAWN_POINT;

	private Tile[][][] tileMaps;
	private boolean[][] collisionMaps;
	
	private Player player;
	private Enemy enemy;
	
	public World() {
		super();
		WorldLoader.load(SpriteSheet.tileset,
				"map0.png",
				"map1.png",
				"map2.png",
				"map3.png");
		
		tileMaps = WorldLoader.getTileMaps();
		collisionMaps = WorldLoader.getCollisionMaps();
		SPAWN_POINT = WorldLoader.getSpawnPoint();
		
		MAP_WIDTH = tileMaps.length;
		MAP_HEIGHT = tileMaps[0].length;
		MAP_MAX_LAYER = tileMaps[0][0].length;
		
		if (SPAWN_POINT == null) {			
			SPAWN_POINT = new Vector2(
					MAP_WIDTH / 2 * Tile.SIZE,
					MAP_HEIGHT / 2 * Tile.SIZE);
		}
		
		player = new Player(this);
		Camera.getInstance().setEntity(player);
		
		enemy = new Enemy(this);
	}

	@Override
	public void update() {
		if (getTile(getCoord(player.getPosition()), 2).isEmptyTile()) return;
		
		for (int x = 0; x < MAP_WIDTH; x++) {
			for (int y = 0; y < MAP_HEIGHT; y++) {
				for (int i = 2; i < MAP_MAX_LAYER; i++) {
					Tile t = getTile(x, y, i);
					if (t == null ||
							player.getPosition()
							.subtract(new Vector2(x * Tile.SIZE, y * Tile.SIZE)).getSize() > 5.f * Tile.SIZE
							) continue;
					
					t.setTransparent();
				}
			}
		}
	}
	
	/*
	 * IS SOLID TILE
	 */
	public boolean isSolidTile(float x, float y) {
		Vector2 coord = getCoord(x, y);

		int ix = (int) coord.x;
		int iy = (int) coord.y;

		if (ix < 0 || ix >= tileMaps.length ||
				iy < 0 || iy >= tileMaps[0].length)
			return false;
		
		return collisionMaps[ix][iy];
	}
	
	public boolean isSolidTile(Vector2 pos) {
		return isSolidTile(pos.x, pos.y);
	}
	
	/*
	 * GET COORD
	 */
	public Vector2 getCoord(float x, float y) {
		return new Vector2(
				(int) (x / Tile.SIZE),
				(int) (y / Tile.SIZE));
	}
	
	public Vector2 getCoord(Vector2 pos) {
		return getCoord(pos.x, pos.y);
	}
	
	/*
	 * GET TILE
	 */
	public Tile getTile(float x, float y, int layer) {
		int ix = (int) x;
		int iy = (int) y;
		int iLayer = (int) layer;
		
		if (ix < 0 || ix >= MAP_WIDTH ||
				iy < 0 || iy >= MAP_HEIGHT ||
				iLayer < 0 || iLayer >= MAP_MAX_LAYER)
			return null;
		
		return tileMaps[ix][iy][iLayer];
	}
	
	public Tile getTile(Vector2 pos, int layer) {
		return getTile(pos.x, pos.y, layer);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public Player getPlayer() {
		return player;
	}
}
