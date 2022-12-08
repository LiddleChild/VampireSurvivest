package core.entity.enemy;

import java.util.ArrayList;
import java.util.Random;

import core.Renderer;
import core.behavior.GameBehavior;
import core.entity.Entity;
import core.world.Tile;
import core.world.World;

public class EnemySpawner extends GameBehavior {
	private World world;
	
	private Random random;
	
	private float nextSpawnTime = 0.f;
	private float time = 0.f;
	
	private ArrayList<Entity> enemyLists;
	
	public EnemySpawner(World world) {
		super();
		
		this.world = world;
		this.random = new Random();
		
		enemyLists = new ArrayList<Entity>();
	}
	
	@Override
	public void update() {
		if (time >= nextSpawnTime) {
			time = 0.f;
			nextSpawnTime = random.nextFloat(3.f, 5.f);
			
			spawnEnemy();
		} else {
			time += deltaTime;
		}
	}
	
	private void spawnEnemy() {
		int count = 3;
		float spawnRate = 0.1f / (World.MAP_WIDTH * World.MAP_HEIGHT);
		
		while (count > 0) {
			for (int x = 0; x < World.MAP_WIDTH; x++) {
				for (int y = 0; y < World.MAP_HEIGHT; y++) {
					if (random.nextFloat() < spawnRate &&
							!world.isCoordSolidTile(x, y) &&
							!Renderer.checkInsideWindow(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE)) {
						
						int rand = random.nextInt(100);
						if (rand <= 25) {							
							enemyLists.add(new Imp(world, world.coord2Pos(x, y)));
						} else {
							enemyLists.add(new Enemy(world, world.coord2Pos(x, y)));
						}
						count--;
					}
				}
			}
		}
	}
	
}
