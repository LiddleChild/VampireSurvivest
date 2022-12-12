package core.item.fx;

import java.awt.Rectangle;

import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import core.world.Tile;
import util.math.Vector2f;

public class GreenForceWave {
	private static final float MOVEMENT_SPEED = 6.f * Tile.SIZE;

	private AnimatedSprite sprite;
	private Rectangle bound;
	
	private Vector2f position, direction;
	private int size;
	private float angle;
	
	public GreenForceWave(Vector2f position, int size, float angle) {
		this.position = new Vector2f(position);
		this.angle = angle;
		this.size = size;
		
		direction = new Vector2f(this.angle);
		bound = new Rectangle((int) position.x, (int) position.y, size, size);
		
		sprite = new AnimatedSprite("fx/wind_projectile_green.png", 2, 3, 32, 32);
		sprite.setStateIntervals(State.IDLE, State.IDLE, 0, 5);
	}
	
	public void update(float deltaTime) {
		Vector2f amount = direction.multiply(deltaTime * MOVEMENT_SPEED);
		position.addEqual(amount);
		bound.x = (int) position.x;
		bound.y = (int) position.y;
		
		sprite.update(deltaTime);
	}
	
	public void render() {
		sprite.render(position, size, size, angle);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public Rectangle getBound() {
		return bound;
	}
	
}
