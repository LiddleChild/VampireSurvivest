package core;

import core.entity.Entity;
import core.world.Tile;
import core.world.World;
import logic.Window;
import util.math.Vector2f;

public class Camera {
	private static Camera instance;
	
	private Vector2f halfScreenOffset;

	private Entity entity;
	private Vector2f position;
	
	private float interpThreshold = 0.05f;
	private float interpAmount = 0.045f;
	
	public Camera() {
		halfScreenOffset = new Vector2f(
				Window.WINDOW_WIDTH - Tile.SIZE,
				Window.WINDOW_HEIGHT - Tile.SIZE)
				.multiply(0.5f);
		
		position = new Vector2f(World.getInstance().SPAWN_POINT);
	}
	
	public void update() {
		if (entity != null) {
			Vector2f diff = entity.getPosition()
					.subtract(position)
					.multiply(interpAmount);
			
			if (diff.getSize() < interpThreshold) return;
			
			position.addEqual(diff);
		}
	}
	
	/*
	 * GETTER & SETTER
	 */
	
	public void setPosition(Vector2f vec) {
		this.position = vec;
	}
	
	public Vector2f getPosition() {
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
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
}
