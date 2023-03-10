package core.game.world;

import java.awt.Rectangle;

import core.audio.AudioMedia;
import core.behavior.GameBehavior;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import logic.GameLogic;
import util.Vector2f;

/*
 * 
 * Coin
 * - Add experience on collect
 * - Goes to player in a certain range
 * 
 */

public class Coin extends GameBehavior {

	private Vector2f position;
	private Rectangle bound;
	
	private AnimatedSprite sprite;
	
	private float time;
	
	public Coin(Vector2f position) {
		super(3);
		this.position = position;

		sprite = new AnimatedSprite("coin.png", 1, 4, 64, 64);
		sprite.setTimePerFrame(0.125f);
		sprite.setStateData(State.IDLE, State.IDLE, 0, 3);
		
		bound = new Rectangle((int) position.x, (int) position.y, 16, 16);
		time = 0.f;
	}
	
	@Override
	public void update() {
		// Fancy equation for range calculation
		double radius = 2 * Math.tanh(0.5 * GameLogic.getInstance().getLevel() - 2) + 2;
		
		Vector2f direction = World.getInstance().getPlayer().getPosition().subtract(position);
		if (direction.getSize() <= radius * Tile.SIZE) {
			position.addEqual(direction.normalize().multiply(Tile.SIZE * (float) radius * deltaTime));
			bound.x = (int) position.x;
			bound.y = (int) position.y;
		}
		
		if (bound.intersects(World.getInstance().getPlayer().getBound())) {
			AudioMedia.COLLECT.play();
			World.getInstance().getPlayer().heal(2.f);
			GameLogic.getInstance().setExperience(GameLogic.getInstance().getExperience() + 25);
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
