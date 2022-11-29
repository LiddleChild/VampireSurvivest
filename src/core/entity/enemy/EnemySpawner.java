package core.entity.enemy;

import java.util.ArrayList;
import java.util.Random;

import core.Camera;
import core.Renderer;
import core.behavior.GameBehavior;
import core.world.Tile;
import core.world.World;

public class EnemySpawner extends GameBehavior {
	private World world;
	
	private Random random;
	
	private float nextSpawnTime = 0.f;
	private float time = 0.f;
	
	private ArrayList<Enemy> enemyLists;
	
	public EnemySpawner(World world) {
		super();
		
		this.world = world;
		this.random = new Random();
		
		enemyLists = new ArrayList<Enemy>();
	}
	
	@Override
	public void update() {
		if (time >= nextSpawnTime) {
			time = 0.f;
			nextSpawnTime = random.nextFloat(3.f, 5.f);
			
//			enemyLists.add(new Enemy(world, World.SPAWN_POINT));
			spawnEnemy();
		} else {
			time += deltaTime;
		}
	}
	
	private void spawnEnemy() {
		int count = 5;
		float spawnRate = 0.1f / (World.MAP_WIDTH * World.MAP_HEIGHT);
		
		while (count > 0) {
			for (int x = 0; x < World.MAP_WIDTH; x++) {
				for (int y = 0; y < World.MAP_HEIGHT; y++) {
					if (random.nextFloat() < spawnRate &&
							!world.isCoordSolidTile(x, y) &&
							!Renderer.checkInsideWindow(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE)) {
						
						enemyLists.add(new Enemy(world, world.coord2Pos(x, y)));
						count--;
					}
				}
			}
		}
	}
	
}
