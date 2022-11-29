package core.world;

import core.Camera;
import core.behavior.GameBehavior;
import core.entity.Player;
import core.entity.enemy.EnemySpawner;
import core.sprite.SpriteSheet;
import util.Vector2f;

public class World extends GameBehavior {
	public static int MAP_WIDTH;
	public static int MAP_HEIGHT;
	public static int MAP_MAX_LAYER;
	public static Vector2f SPAWN_POINT;

	private Tile[][][] tileMaps;
	private boolean[][] collisionMaps;
	
	private static Player player;
	private EnemySpawner enemySpawner;
	
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
			SPAWN_POINT = new Vector2f(
					MAP_WIDTH / 2 * Tile.SIZE,
					MAP_HEIGHT / 2 * Tile.SIZE);
		}
		
		player = new Player(this);
		Camera.getInstance().setEntity(player);
		
		enemySpawner = new EnemySpawner(this);
	}

	@Override
	public void update() {
		if (getTile(pos2Coord(player.getPosition()), 2).isEmptyTile()) return;
		
		for (int x = 0; x < MAP_WIDTH; x++) {
			for (int y = 0; y < MAP_HEIGHT; y++) {
				for (int i = 2; i < MAP_MAX_LAYER; i++) {
					Tile t = getTile(x, y, i);
					if (t == null ||
							player.getPosition()
							.subtract(new Vector2f(x * Tile.SIZE, y * Tile.SIZE)).getSize() > 5.f * Tile.SIZE
							) continue;
					
					t.setTransparent();
				}
			}
		}
	}
	
	/*
	 * IS SOLID TILE
	 */
	public boolean isPosSolidTile(float x, float y) {
		Vector2f coord = pos2Coord(x, y);
		
		return isCoordSolidTile((int) coord.x, (int) coord.y);
	}
	
	public boolean isCoordSolidTile(int x, int y) {
		if (x < 0 || x >= tileMaps.length ||
				y < 0 || y >= tileMaps[0].length)
			return false;
		
		return collisionMaps[x][y];
	}
	
	/*
	 * COORD, POS CONVERSION
	 */
	public Vector2f pos2Coord(float x, float y) {
		return new Vector2f(
				(int) (x / Tile.SIZE),
				(int) (y / Tile.SIZE));
	}
	
	public Vector2f pos2Coord(Vector2f pos) {
		return pos2Coord(pos.x, pos.y);
	}
	public Vector2f coord2Pos(int x, int y) {
		return new Vector2f(x * Tile.SIZE, y * Tile.SIZE);
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
	
	public Tile getTile(Vector2f pos, int layer) {
		return getTile(pos.x, pos.y, layer);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public static Player getPlayer() {
		return player;
	}
}
