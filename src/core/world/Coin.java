package core.world;

import java.awt.Rectangle;

import core.behavior.GameBehavior;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import logic.GameLogic;
import util.math.Vector2f;

public class Coin extends GameBehavior {

	private Vector2f position;
	private Rectangle bound;
	
	private AnimatedSprite sprite;
	
	private float time;
	
	public Coin(Vector2f position) {
		super(3);
		this.position = position;

		sprite = new AnimatedSprite("coin.png", 1, 4, 64, 64);
		sprite.setFrameTime(0.125f);
		sprite.setStateIntervals(State.IDLE, State.IDLE, 0, 3);
		
		bound = new Rectangle((int) position.x, (int) position.y, 16, 16);
		time = 0.f;
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void update() {
		if (bound.intersects(World.getInstance().getPlayer().getBound())) {
			GameLogic.getInstance().setExp(GameLogic.getInstance().getExp() + 25);
			super.delete();
		}
		
		time += deltaTime;
		sprite.update(deltaTime);
	}
	
	@Override
	public void render() {
		sprite.render(
				(int) position.x,
				(int) (position.y + Math.sin(time * 3.f) * Tile.SIZE / 8),
				Tile.SIZE / 2,
				Tile.SIZE / 2,
				0.f);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public Rectangle getBound() {
		return bound;
	}

}
