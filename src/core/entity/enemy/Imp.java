package core.entity.enemy;

import java.awt.Rectangle;

import core.collision.CollisionManager;
import core.entity.Entity;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimatedSpriteEvent;
import core.sprite.animation.AnimationState.State;
import core.world.Tile;
import core.world.World;
import util.math.Vector2f;

public class Imp extends Entity {
	
	private AnimatedSprite sprite, explosionSprite;
	
	private float detonateTime, time, explosionRange;
	private boolean isDetonated;
	
	private float blinkTime;
	private boolean showBlink;

	public Imp(World world, Vector2f spawn) {
		super("Bomber", world);

		super.speed = Tile.SIZE * 0.75f;
		
		position = new Vector2f(spawn);
		
		bound = new Rectangle(0, 0, 20, Tile.SIZE);
		
		sprite = new AnimatedSprite("imp.png", 1, 5, 64, 64);
		sprite.setOffset(new Vector2f(
						(bound.width  - Tile.SIZE) / 2,
						(bound.height - Tile.SIZE) / 2));
		
		explosionSprite = new AnimatedSprite("explosion.png", 8, 10, 100, 100);
		explosionSprite.setFrameTime(0.015f);
		explosionSprite.setStateIntervals(State.IDLE, State.IDLE, -1, -1);
		explosionSprite.setStateIntervals(State.PLAY, State.IDLE, 0, 81);
		explosionSprite.setEventHandler(new AnimatedSpriteEvent() {
			@Override
			public void onEnd() {
				delete();
			}
		});
		
		isDetonated = false;
		detonateTime = 3.f;
		time = 0.f;
		
		blinkTime = 0.f;
		showBlink = true;
		
		explosionRange = 5.f;
	}

	@Override
	public void update() {
		if (!isDetonated) {			
			Vector2f playerPos = World.getPlayer().getPosition();
			super.move(playerPos.subtract(position));
		
			if (World.getPlayer().getPosition().subtract(position).getSize() <= explosionRange * Tile.SIZE) {
				time += deltaTime;
				blinkTime += deltaTime;
				
				if (blinkTime >= 0.2f / time) {
					blinkTime -= 0.2f / time;
					showBlink = !showBlink;
				}
				
				if (time >= detonateTime) {
					isDetonated = true;
					explosionSprite.setState(State.PLAY);
					World.getPlayer().knockback(World.getPlayer().getPosition().subtract(position), 4.f);
					World.getPlayer().takeDamge(12.f);
					CollisionManager.getInstance().remove(this);
				}
			} else {
				time = 0.f;
				showBlink = true;
			}

			if (direction.x < 0) sprite.setReverse(true);
			else if (direction.x > 0) sprite.setReverse(false);
			
			sprite.setState((direction.isZero()) ? State.IDLE : State.PLAY);
			sprite.update(deltaTime);
		} else {
			explosionSprite.update(deltaTime);
		}
	}

	@Override
	public void render() {
		if (!isDetonated) {
			// Draw imp
			if (showBlink) {			
				sprite.render(position, Tile.SIZE, Tile.SIZE, 0.f);
			}

			// Draw health bar
			super.drawHealthBar();
		} else {
			// Draw explosion
			explosionSprite.render(position.subtract((explosionRange - 1.f) * Tile.SIZE / 2.f),
					(int) (explosionRange * Tile.SIZE),
					(int) (explosionRange * Tile.SIZE),
					0.f);
		}
	}

	@Override
	protected void onDeath() {
		super.delete();
		world.spawnCoin(position.add(new Vector2f(0, Tile.SIZE / 2)));
	}

	@Override
	protected void onTakingDamage() {
		
	}
	
}
