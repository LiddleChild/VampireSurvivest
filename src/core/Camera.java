package core;

import core.entity.BaseEntity;
import core.world.Tile;
import core.world.World;
import logic.Window;
import util.Vector2;

public class Camera {
	private static Camera instance;
	
	private Vector2 halfScreenOffset;

	private BaseEntity entity;
	private Vector2 position;
	
	private float interpThreshold = 0.05f;
	private float interpAmount = 0.045f;
	
	public Camera() {
		halfScreenOffset = new Vector2(
				Window.WINDOW_WIDTH - Tile.SIZE,
				Window.WINDOW_HEIGHT - Tile.SIZE)
				.multiply(0.5f);
		
		position = new Vector2(World.SPAWN_POINT);
	}
	
	public void update() {
		if (entity != null) {
			Vector2 diff = entity.getPosition()
					.subtract(position)
					.multiply(interpAmount);
			
			if (diff.getSize() < interpThreshold) return;
			
			position.addEqual(diff);
		}
	}
	
	/*
	 * GETTER & SETTER
	 */
	
	public void setPosition(Vector2 vec) {
		this.position = vec;
	}
	
	public Vector2 getPosition() {
		return position
				.multiply(-1)
				.add(halfScreenOffset);
	}
	
	public void setX(float x) {
		position.x = x;
	}
	
	public float getX() {
		return getPosition().x;
	}
	
	public void setY(float y) {
		position.y = y;
	}
	
	public float getY() {
		return getPosition().y;
	}
	
	/*
	 * SINGLETON
	 */
	public static Camera getInstance() {
		if (instance == null) {
			instance = new Camera();
		}
		
		return instance;
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public void setEntity(BaseEntity entity) {
		this.entity = entity;
	}
	
}
