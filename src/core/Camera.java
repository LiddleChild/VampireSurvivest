package core;

import core.world.World;
import logic.Window;

public class Camera {
	private static Camera instance;

	private float x, y;
	
	private float interpAmount = 0.05f;
	
	public Camera() {
		x = World.getSpawnPointX();
		y = World.getSpawnPointY();
	}
	
	public void interpolate(float x, float y, float deltaTime) {
		this.x += interpAmount * (x - this.x);
		this.y += interpAmount * (y - this.y);
	}
	
	/*
	 * GETTER & SETTER
	 */
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getX() {
		return -x + ((Window.WINDOW_WIDTH - World.GRID_SIZE) / 2);
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getY() {
		return -y + ((Window.WINDOW_HEIGHT - World.GRID_SIZE) / 2);
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
