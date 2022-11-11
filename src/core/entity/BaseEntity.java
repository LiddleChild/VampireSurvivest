package core.entity;

import core.behavior.GameBehavior;
import core.world.Map;
import util.math.Vector2;

public abstract class BaseEntity extends GameBehavior {

	private String id;
	
	protected Vector2 position;
	protected float speed;
	
	private boolean[] movable = new boolean[4]; // T R B L
	
	public BaseEntity(String id) {
		this(id, 1);

		movable[0] = true;
		movable[1] = true;
		movable[2] = true;
		movable[3] = true;
	}
	
	public BaseEntity(String id, int layer) {
		super(layer);
		this.id = id;

		speed = Map.GRID_SIZE * 4.f;
		
		position = new Vector2(
				Map.SPAWN_POINT_X,
				Map.SPAWN_POINT_Y);
	}
	
	protected void move(Vector2 direction) {
		checkCollision(direction);
		direction.normalize();
		
		if (!movable[0] && direction.y < 0) direction.y = 0;
		if (!movable[2] && direction.y > 0) direction.y = 0;
		if (!movable[3] && direction.x < 0) direction.x = 0;
		if (!movable[1] && direction.x > 0) direction.x = 0;
		
		position.addEqual(direction.multiply(speed * deltaTime));
	}
	
	protected void checkCollision(Vector2 dv) {
		float leftX = (float) Math.floor(position.x / Map.GRID_SIZE) * Map.GRID_SIZE;
		float rightX = (float) Math.ceil(position.x / Map.GRID_SIZE) * Map.GRID_SIZE;
		float topY = (float) Math.floor(position.y / Map.GRID_SIZE) * Map.GRID_SIZE;
		float bottomY = (float) Math.ceil(position.y / Map.GRID_SIZE) * Map.GRID_SIZE;
		
		// TOP
		movable[0] = (dv.y < 0) &&
				!(Map.getInstance().getTile(leftX , position.y + dv.y) ||
				Map.getInstance().getTile(rightX, position.y + dv.y));
		
		// BOTTOM
		movable[2] = (dv.y > 0) &&
				!(Map.getInstance().getTile(leftX , position.y + Map.GRID_SIZE + dv.y) ||
				Map.getInstance().getTile(rightX, position.y + Map.GRID_SIZE + dv.y));

		// LEFT
		movable[3] = (dv.x < 0) &&
				!(Map.getInstance().getTile(position.x + dv.x, topY) ||
				Map.getInstance().getTile(position.x + dv.x, bottomY));

		// RIGHT
		movable[1] = (dv.x > 0) &&
				!(Map.getInstance().getTile(position.x + Map.GRID_SIZE + dv.x, topY) ||
				Map.getInstance().getTile(position.x + Map.GRID_SIZE + dv.x, bottomY));
	}
	
	/*
	 * GETTER
	 */
	public String getID() {
		return id;
	}
	
}
