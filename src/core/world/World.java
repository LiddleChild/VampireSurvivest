package core.world;

import java.util.ArrayList;

import core.Camera;
import core.entity.Entity;
import core.entity.Player;
import core.entity.enemy.EnemySpawner;
import core.sprite.SpriteSheet;
import util.math.Vector2f;

public class World {
	private static World instance;
	
	public final int MAP_WIDTH, MAP_HEIGHT, MAP_MAX_LAYER;
	public Vector2f SPAWN_POINT;

	private Player player;

	private Tile[][][] tileMaps;
	private boolean[][] collisionMaps;
	
	private ArrayList<Coin> coins;
	private ArrayList<Entity> enemyLists;
	
	public World() {
		super();
		
		instance = this;
		
		WorldLoader.load(SpriteSheet.tileset,
				"world/map0.png",
				"world/map1.png",
				"world/map2.png",
				"world/map3.png");
		
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
		
		player = new Player();
		Camera.getInstance().setEntity(player);
		
		new Thread(new EnemySpawner(this), "enemy_spawner_thread").start();
		
		coins = new ArrayList<Coin>();
		enemyLists = new ArrayList<Entity>();

		spawnCoin(SPAWN_POINT.add(32.f));
		spawnCoin(SPAWN_POINT.add(32.f));
		spawnCoin(SPAWN_POINT.add(32.f));
		spawnCoin(SPAWN_POINT.add(32.f));
	}
	
	public void spawnCoin(Vector2f pos) {
		coins.add(new Coin(pos));
	}
	
	public void addEnemy(Entity e) {
		enemyLists.add(e);
	}
	
	public void removeEnemy(Entity e) {
		enemyLists.remove(e);
	}
	
	public static World getInstance() {
		return instance;
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
	public Player getPlayer() {
		return player;
	}

	public ArrayList<Entity> getEnemyLists() {
		return enemyLists;
	}
}
