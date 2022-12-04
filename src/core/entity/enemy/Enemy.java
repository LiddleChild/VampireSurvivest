package core.entity.enemy;

import java.awt.Rectangle;

import core.collision.CollisionManager;
import core.entity.Entity;
import core.sprite.AnimatedSprite;
import core.sprite.AnimationState.State;
import core.world.Tile;
import core.world.World;
import util.Vector2f;

public class Enemy extends Entity {
	
	private AnimatedSprite enemySprite;
	
	public Enemy(World world, Vector2f spawn) {
		super("enemy", world);
		
		super.speed = Tile.SIZE * .75f;
		
		position = new Vector2f(spawn);
		
		bound = new Rectangle(0, 0, 20, Tile.SIZE);
		
		enemySprite = new AnimatedSprite("undead.png", 1, 5, 64, 64);
		enemySprite.setOffset(new Vector2f(
						(bound.width  - Tile.SIZE) / 2,
						(bound.height - Tile.SIZE) / 2));
	}

	@Override
	public void update() {
		Vector2f playerPos = World.getPlayer().getPosition();
		super.move(playerPos.subtract(position));
		
		if (CollisionManager.getInstance().isCollidingWithPlayer(this)) {
			super.attack(World.getPlayer());
		}

		if (direction.x < 0) {
			enemySprite.setReverse(true);
		} else if (direction.x > 0) {
			enemySprite.setReverse(false);
		}

		enemySprite.setState((direction.isZero()) ? State.IDLE : State.PLAY);
		enemySprite.draw(position, Tile.SIZE, Tile.SIZE, deltaTime, 0.f);
		
//		Renderer.setFill(Color.DARKRED);
//		Renderer.fillRect(position, Tile.SIZE, Tile.SIZE);
		
		super.drawHealthBar();
	}

	@Override
	protected void onTakingDamage() {
		
	}

	@Override
	protected void onDeath() {
		super.delete();
	}

}
