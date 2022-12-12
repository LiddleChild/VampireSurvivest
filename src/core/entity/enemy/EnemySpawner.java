package core.entity.enemy;

import java.util.Random;

import core.Renderer;
import core.world.Tile;
import core.world.World;
import logic.GameLogic;
import logic.GameState;
import util.Time;

public class EnemySpawner implements Runnable {
	
	private Random random;
	
	private final float nextSpawnTime;
	private float time;
	
	private boolean running;
	
	public EnemySpawner() {
		random = new Random();
		
		nextSpawnTime = 3.f;
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
		int count = 3;
		float spawnRate = 0.1f / (World.getInstance().getMapWidth() * World.getInstance().getMapHeight());
		
		while (count > 0) {
			for (int x = 0; x < World.getInstance().getMapWidth(); x++) {
				for (int y = 0; y < World.getInstance().getMapHeight(); y++) {
					if (random.nextFloat() < spawnRate &&
							!World.getInstance().isCoordSolidTile(x, y) &&
							!Renderer.checkInsideWindow(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE)) {
						
						int rand = random.nextInt(100);
						if (rand <= 25) {							
							World.getInstance().addEnemy(new Imp(World.getInstance().coord2Pos(x, y)));
						} else {
							World.getInstance().addEnemy(new Enemy(World.getInstance().coord2Pos(x, y)));
						}
						count--;
					}
				}
			}
		}
	}
	
	public void stop() {
		running = false;
	}
}
