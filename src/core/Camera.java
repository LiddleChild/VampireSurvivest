package core;

import core.world.World;
import logic.Window;

public class Camera {
	
	private float x, y;
	
	public Camera(World world) {
		
	}
	
	public float getOffsetX() {
		return x + (Window.WINDOW_WIDTH / 2);
	}
	
	public float getOffsetY() {
		return y + (Window.WINDOW_HEIGHT / 2);
	}
	
}
