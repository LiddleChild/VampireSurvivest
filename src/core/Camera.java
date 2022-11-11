package core;

import core.world.Map;
import logic.Window;
import util.math.Vector2;

public class Camera {
	private static Camera instance;

	private Vector2 position;
	
	private float interpAmount = 0.05f;
	
	public Camera() {
		position = new Vector2(
				Map.SPAWN_POINT_X,
				Map.SPAWN_POINT_Y);
	}
	
	public void interpolate(Vector2 vec) {
		position.addEqual(vec
				.substract(position)
				.multiply(interpAmount));
	}
	
	/*
	 * GETTER & SETTER
	 */
	
	public void setPosition(Vector2 vec) {
		this.position = vec;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public void setX(float x) {
		position.x = x;
	}
	
	public float getX() {
		return -position.x + ((Window.WINDOW_WIDTH - Map.GRID_SIZE) / 2);
	}
	
	public void setY(float y) {
		position.y = y;
	}
	
	public float getY() {
		return -position.y + ((Window.WINDOW_HEIGHT - Map.GRID_SIZE) / 2);
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
	
}
