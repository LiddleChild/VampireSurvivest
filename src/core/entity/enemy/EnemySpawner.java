package core.entity.enemy;

import java.util.Random;

import core.Renderer;
import core.world.Tile;
import core.world.World;
import logic.GameLogic;
import logic.GameState;
import util.Time;

public class EnemySpawner implements Runnable {
	
	private World world;
	
	private Random random;
	
	private float nextSpawnTime = 0.f;
	private float time = 0.f;
	
	public EnemySpawner(World world) {
		super();
		
		this.world = world;
		this.random = new Random();
	}

	@Override
	public void run() {
		float lastTime = Time.getNanoSecond();
		while (GameLogic.getInstance().getGameState() != GameState.EXIT) {
			float currentTime = Time.getNanoSecond();
			float deltaTime = currentTime - lastTime;
			
			if (time >= nextSpawnTime) {
				time = 0.f;
				nextSpawnTime = random.nextFloat(3.f, 5.f);
				
				spawnEnemy();
			} else {
				time += deltaTime;
			}
			
			lastTime = currentTime;
		}
	}
	
	private void spawnEnemy() {
		int count = 3;
		float spawnRate = 0.1f / (World.getInstance().MAP_WIDTH * World.getInstance().MAP_HEIGHT);
		
		while (count > 0) {
			for (int x = 0; x < World.getInstance().MAP_WIDTH; x++) {
				for (int y = 0; y < World.getInstance().MAP_HEIGHT; y++) {
					if (random.nextFloat() < spawnRate &&
							!world.isCoordSolidTile(x, y) &&
							!Renderer.checkInsideWindow(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE)) {
						
						int rand = random.nextInt(100);
						if (rand <= 25) {							
							world.addEnemy(new Imp(world.coord2Pos(x, y)));
						} else {
							world.addEnemy(new Enemy(world.coord2Pos(x, y)));
						}
						count--;
					}
				}
			}
		}
	}
}
