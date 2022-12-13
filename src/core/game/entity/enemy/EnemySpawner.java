package core.game.entity.enemy;

import java.util.Random;

import core.game.world.Tile;
import core.game.world.World;
import logic.GameLogic;
import logic.GameState;
import util.Time;
import util.math.Vector2f;

/*
 * 
 * EnemySpawner
 * - Spawn enemy with some chances
 * 
 */

public class EnemySpawner implements Runnable {
	
	private Random random;
	
	private final float nextSpawnTime;
	private float time;
	
	private boolean running;
	
	public EnemySpawner() {
		random = new Random();
		
		nextSpawnTime = 5.f;
		time = nextSpawnTime;
	}

	@Override
	public void run() {
		float lastTime = Time.getNanoSecond();
		
		running = true;
		
		while (running) {
			if (GameLogic.getInstance().getGameState() != GameState.PLAY) continue;
			
			float currentTime = Time.getNanoSecond();
			float deltaTime = currentTime - lastTime;
			
			if (time >= nextSpawnTime) {
				time = 0.f;
				
				spawnEnemy();
			} else {
				time += deltaTime;
			}
			
			lastTime = currentTime;
		}
	}
	
	private void spawnEnemy() {
		// enemy count +2 every 1 min
		int enemyCount = 3 + GameLogic.getInstance().getTimeCounter() / 60 * 2;
		float spawnRate = 1.f / (World.getInstance().getMapWidth() * World.getInstance().getMapHeight());
		
		while (enemyCount > 0) {
			for (int x = 0; x < World.getInstance().getMapWidth(); x++) {
				for (int y = 0; y < World.getInstance().getMapHeight(); y++) {
					float range = new Vector2f(x * Tile.SIZE, y * Tile.SIZE).subtract(World.getInstance().getPlayer().getPosition()).getSize();
					if (random.nextFloat() < spawnRate &&
							range >= 16.f * Tile.SIZE &&
							range <= 32.f * Tile.SIZE &&
							!World.getInstance().isCoordSolidTile(x, y)) {
												
						float rand = random.nextFloat();
						if (rand <= 0.2) {
							World.getInstance().addEnemy(new Imp(World.getInstance().coordToPos(x, y)));
						} else {
							World.getInstance().addEnemy(new Undead(World.getInstance().coordToPos(x, y)));
						}
						
						enemyCount--;
					}
				}
			}
		}
	}
	
	public void stop() {
		running = false;
	}
}
