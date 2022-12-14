package core.game.entity.enemy;

import java.awt.Rectangle;

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
 * Unread
 * - A normal enemy
 * - Attacks player if it touches
 * - Spawn coin on death
 * 
 */

public class Undead extends Entity implements HostileEntity {
	
	private AnimatedSprite sprite;
	
	public Undead(Vector2f spawn) {
		super("Undead");
		
		position = new Vector2f(spawn);
		bound = new Rectangle(0, 0, 20, Tile.SIZE);
		
		sprite = new AnimatedSprite("entity/undead.png", 1, 5, 64, 64);
		sprite.setOffset(new Vector2f(
						(bound.width  - Tile.SIZE) / 2,
						(bound.height - Tile.SIZE) / 2));
		
		setMaxHealth(25.f + (GameLogic.getInstance().getLevel() - 1) / 2 * 2 * 4.5f);
		setMovementSpeed(2.5f);
	}

	@Override
	public void update() {
		Vector2f playerPos = World.getInstance().getPlayer().getPosition();
		super.move(playerPos.subtract(position));
		
		if (CollisionManager.getInstance().isCollidingWithPlayer(this)) {
			super.attack(World.getInstance().getPlayer());
		}

		if (direction.x < 0) sprite.setReverse(true);
		else if (direction.x > 0) sprite.setReverse(false);

		if (sprite != null) {
			sprite.setState((direction.isZero()) ? State.IDLE : State.PLAY);
			sprite.update(deltaTime);
		}
	}

	@Override
	public void render() {
		if (sprite != null) sprite.render(position, Tile.SIZE, Tile.SIZE, 0.f);
		super.drawHealthBar();
	}

	@Override
	protected void onTakingDamage() {
		
	}

	@Override
	protected void onDeath() {
		super.delete();
		World.getInstance().removeEnemy(this);
		World.getInstance().spawnCoin(position.add(new Vector2f(0, Tile.SIZE / 2)));
	}

}
