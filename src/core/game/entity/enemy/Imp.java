package core.game.entity.enemy;

import java.awt.Rectangle;

import core.audio.AudioMedia;
import core.collision.CollisionManager;
import core.game.entity.Entity;
import core.game.entity.HostileEntity;
import core.game.world.Tile;
import core.game.world.World;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import logic.GameLogic;
import util.math.Vector2f;

/*
 * 
 * Imp
 * - A explosive enemy
 * - Triggers fuse when player is in a certain range
 * - Only deal damage to player with explosion
 * 
 */

public class Imp extends Entity implements HostileEntity {
	
	private AnimatedSprite sprite, explosionSprite;
	
	private float baseDetonateTime, detonateTime, time;
	private float baseEplosionRange, explosionRange;
	private boolean isDetonated;
	
	private float blinkTime, blinkStartFrequency;
	private boolean showBlink, lastShowBlink;

	public Imp(Vector2f spawn) {
		super("Imp");

		position = new Vector2f(spawn);
		bound = new Rectangle(0, 0, 20, Tile.SIZE);
		
		sprite = new AnimatedSprite("entity/imp.png", 1, 5, 64, 64);
		sprite.setOffset(new Vector2f(
						(bound.width  - Tile.SIZE) / 2,
						(bound.height - Tile.SIZE) / 2));
		
		explosionSprite = new AnimatedSprite("fx/explosion.png", 8, 10, 100, 100);
		explosionSprite.setFrameTime(0.015f);
		explosionSprite.setStateIntervals(State.IDLE, State.IDLE, -1, -1);
		explosionSprite.setStateIntervals(State.PLAY, State.IDLE, 0, 81);
		explosionSprite.setEventHandler(() -> {
			World.getInstance().removeEnemy(this);
			super.delete();
		});
		
		setMaxHealth(25.f);
		setMovementSpeed(3.5f);
		
		isDetonated = false;
		baseDetonateTime = 2.f;
		time = 0.f;
		
		blinkTime = 0.f;
		showBlink = true;
		blinkStartFrequency = 0.1f;
		
		baseEplosionRange = 3.f;
	}

	@Override
	public void update() {
		explosionRange = baseEplosionRange + GameLogic.getInstance().getLevel() * 0.25f;
		detonateTime = baseDetonateTime - GameLogic.getInstance().getLevel() * 0.075f;
		
		if (!isDetonated) {			
			Vector2f playerPos = World.getInstance().getPlayer().getPosition();
			super.move(playerPos.subtract(position));
		
			if (World.getInstance().getPlayer().getPosition().subtract(position).getSize() <= explosionRange * Tile.SIZE) {
				time += deltaTime;
				blinkTime += deltaTime;
				
				if (blinkTime >= blinkStartFrequency / time) {
					blinkTime -= blinkStartFrequency / time;
					showBlink = !showBlink;
				}
				
				if (time >= detonateTime) {
					isDetonated = true;
					
					AudioMedia.EXPLOSION.play();
					explosionSprite.setState(State.PLAY);
					World.getInstance().getPlayer().takeDamge(12.f);
					
					CollisionManager.getInstance().remove(this);
				}
			} else {
				time = 0.f;
				showBlink = true;
			}
			
			if (!lastShowBlink && showBlink) AudioMedia.EXPLOSION_TICK.play();
			lastShowBlink = showBlink;

			if (direction.x < 0) sprite.setReverse(true);
			else if (direction.x > 0) sprite.setReverse(false);
			
			if (sprite != null) {				
				sprite.setState((direction.isZero()) ? State.IDLE : State.PLAY);
				sprite.update(deltaTime);
			}
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
		World.getInstance().removeEnemy(this);
		super.delete();
		World.getInstance().spawnCoin(position.add(new Vector2f(0, Tile.SIZE / 2)));
	}

	@Override
	protected void onTakingDamage() {
		
	}
	
}
