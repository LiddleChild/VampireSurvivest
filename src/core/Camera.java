package core;

import logic.Window;

public class Camera {
	private static Camera instance;

	private float offsetX, offsetY;
	
	private float interpAmount = 0.05f;
	
	public Camera() {
		offsetX = 0;
		offsetY = 0;
	}
	
	public void interpolate(float x, float y, float deltaTime) {
		offsetX += interpAmount * (x - offsetX);
		offsetY += interpAmount * (y - offsetY);
	}
	
	public void setOffsetX(float x) {
		offsetX = x;
	}
	
	public float getOffsetX() {
		return -offsetX + (Window.WINDOW_WIDTH / 2);
	}
	
	public void setOffsetY(float y) {
		offsetY = y;
	}
	
	public float getOffsetY() {
		return -offsetY + (Window.WINDOW_HEIGHT / 2);
	}
	
	public static Camera getInstance() {
		if (instance == null) {
			instance = new Camera();
		}
		
		return instance;
	}
	
}
