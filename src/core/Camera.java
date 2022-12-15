package core;

import core.game.entity.Entity;
import core.game.world.Tile;
import core.game.world.World;
import logic.Window;
import util.math.Vector2f;

public class Camera {
	private static Camera instance;
	
	private Vector2f halfScreenOffset;

	private Entity entity;
	private Vector2f position;
	
	private float interpolationThreshold = 0.05f;
	private float interpolationAmount = 0.045f;
	
	public void initialize() {
		halfScreenOffset = new Vector2f(
			Window.WINDOW_WIDTH - Tile.SIZE,
			Window.WINDOW_HEIGHT - Tile.SIZE)
			.multiply(0.5f);
	
		position = new Vector2f(World.getInstance().getSpawnPoint());
	}
	
	public void update() {
		if (entity != null) {
			Vector2f diff = entity.getPosition()
					.subtract(position)
					.multiply(interpolationAmount);
			
			if (diff.getSize() < interpolationThreshold) return;
			
			position.addEqual(diff);
		}
	}

	public Vector2f translateToCameraPosition(float x, float y) {
		return new Vector2f(x, y).add(Camera.getInstance().getPosition()).round();
	}
	
	public boolean isInCameraView(float x, float y, float w, float h) {
		Vector2f t = translateToCameraPosition(x, y);
		
		return (t.x > -w && t.x < Window.WINDOW_WIDTH &&
				t.y > -h && t.y < Window.WINDOW_HEIGHT);
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
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
}
