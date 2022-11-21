package core.entity;

import core.behavior.GameBehavior;
import core.world.Tile;
import core.world.World;
import util.Vector2;

public abstract class BaseEntity extends GameBehavior {

	private String id;
	
	protected Vector2 position;
	protected float speed;
	protected World world;
	
	private boolean[] movable = new boolean[4]; // T R B L
	
	public BaseEntity(String id, World world) {
		this(id, 1, world);

		movable[0] = true;
		movable[1] = true;
		movable[2] = true;
		movable[3] = true;
	}
	
	public BaseEntity(String id, int layer, World world) {
		super(layer);
		this.id = id;
		this.world = world;

		speed = Tile.SIZE * 4.f;
		
		position = new Vector2(World.SPAWN_POINT);
	}
	
	protected void move(Vector2 direction) {
		checkCollision(direction);
		direction.normalize();
		
		if (!movable[0] && direction.y < 0) {
			direction.y = 0;
			position.y = (float) Math.ceil(position.y);
		}
		
		if (!movable[2] && direction.y > 0) {
			direction.y = 0;
			position.y = (float) Math.floor(position.y);
		}
		
		if (!movable[3] && direction.x < 0) {
			direction.x = 0;
			position.x = (float) Math.ceil(position.x);
		}
		
		if (!movable[1] && direction.x > 0) {
			direction.x = 0;
			position.x = (float) Math.floor(position.x);
		}
		
		position.addEqual(direction.multiply(speed * deltaTime));
	}
	
	protected void checkCollision(Vector2 dv) {
		float leftX = (float) Math.floor(position.x / Tile.SIZE) * Tile.SIZE;
		float rightX = (float) Math.ceil(position.x / Tile.SIZE) * Tile.SIZE;
		float topY = (float) Math.floor(position.y / Tile.SIZE) * Tile.SIZE;
		float bottomY = (float) Math.ceil(position.y / Tile.SIZE) * Tile.SIZE;
		
		// TOP
		movable[0] = (dv.y < 0) &&
				!(world.isSolidTile(leftX , position.y + dv.y) ||
				world.isSolidTile(rightX, position.y + dv.y));
		
		// BOTTOM
		movable[2] = (dv.y > 0) &&
				!(world.isSolidTile(leftX , position.y + Tile.SIZE + dv.y) ||
				world.isSolidTile(rightX, position.y + Tile.SIZE + dv.y));

		// LEFT
		movable[3] = (dv.x < 0) &&
				!(world.isSolidTile(position.x + dv.x, topY) ||
				world.isSolidTile(position.x + dv.x, bottomY));

		// RIGHT
		movable[1] = (dv.x > 0) &&
				!(world.isSolidTile(position.x + Tile.SIZE + dv.x, topY) ||
				world.isSolidTile(position.x + Tile.SIZE + dv.x, bottomY));
	}
	
	/*
	 * GETTER
	 */
	public String getID() {
		return id;
	}

	public Vector2 getPosition() {
		return position;
	}
	
}
