package core.game.world;

import java.util.ArrayList;

import core.Camera;
import core.game.entity.Entity;
import core.game.entity.Player;
import core.game.entity.enemy.EnemySpawner;
import core.sprite.SpriteSheet;
import util.math.Vector2f;

/*
 * 
 * World
 * - Holds all information within this world
 * - Holds tile's lists, tile's collision
 * 
 */

public class World {
	private static World instance;

	public Vector2f spawnPoint;
	private int mapWidth, mapHeight, mapMaxLayer;
	private Tile[][][] tileMaps;
	private boolean[][] collisionMaps;
	
	private ArrayList<Coin> coins;
	private ArrayList<Entity> enemyLists;
	
	private Player player;
	
	private Thread enemySpawnerThread;
	private EnemySpawner enemySpawner;
	
	public void initialize() {
		WorldLoader.load(SpriteSheet.tileset,
				"world/map0.png",
				"world/map1.png",
				"world/map2.png",
				"world/map3.png");
		
		tileMaps = WorldLoader.getTileMaps();
		collisionMaps = WorldLoader.getCollisionMaps();
		spawnPoint = WorldLoader.getSpawnPoint();
		
		mapWidth = tileMaps.length;
		mapHeight = tileMaps[0].length;
		mapMaxLayer = tileMaps[0][0].length;
		
		if (spawnPoint == null) {			
			spawnPoint = new Vector2f(
					mapWidth / 2 * Tile.SIZE,
					mapHeight / 2 * Tile.SIZE);
		}
		
		coins = new ArrayList<Coin>();
		enemyLists = new ArrayList<Entity>();
		
		player = new Player();
		Camera.getInstance().setEntity(player);
		
		if (enemySpawnerThread != null) {
			enemySpawner.stop();
			enemySpawnerThread.interrupt();
		}
		
		enemySpawner = new EnemySpawner();
		enemySpawnerThread = new Thread(enemySpawner, "enemy_spawner_thread");
		enemySpawnerThread.start();
	}
	
	public static World getInstance() {
		if (instance == null) {
			instance = new World();
		}
		
		return instance;
	}
	
	public void spawnCoin(Vector2f pos) {
		coins.add(new Coin(pos));
	}
	
	public void addEnemy(Entity e) {
		enemyLists.add(e);
	}
	
	public void removeEnemy(Entity e) {
		enemyLists.remove(enemyLists.indexOf(e));
	}
	
	/*
	 * isPosSolidTile
	 */
	public boolean isPosSolidTile(float x, float y) {
		Vector2f coord = posToCoord(x, y);
		
		return isCoordSolidTile((int) coord.x, (int) coord.y);
	}
	
	public boolean isCoordSolidTile(int x, int y) {
		if (x < 0 || x >= tileMaps.length ||
				y < 0 || y >= tileMaps[0].length)
			return false;
		
		return collisionMaps[x][y];
	}
	
	/*
	 * coordinates and positions convertion
	 */
	public Vector2f posToCoord(float x, float y) {
		return new Vector2f(
				(int) (x / Tile.SIZE),
				(int) (y / Tile.SIZE));
	}
	
	public Vector2f pos2Coord(Vector2f pos) {
		return posToCoord(pos.x, pos.y);
	}
	public Vector2f coordToPos(int x, int y) {
		return new Vector2f(x * Tile.SIZE, y * Tile.SIZE);
	}
	
	/*
	 * getTile
	 */
	public Tile getTile(float x, float y, int layer) {
		int ix = (int) x;
		int iy = (int) y;
		int iLayer = (int) layer;
		
		if (ix < 0 || ix >= mapWidth ||
				iy < 0 || iy >= mapHeight ||
				iLayer < 0 || iLayer >= mapMaxLayer)
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

	public ArrayList<Coin> getCoins() {
		return coins;
	}

	public Vector2f getSpawnPoint() {
		return spawnPoint;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public int getMapMaxLayer() {
		return mapMaxLayer;
	}
}
