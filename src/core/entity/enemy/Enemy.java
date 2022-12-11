package core.entity.enemy;

import java.awt.Rectangle;

import core.collision.CollisionManager;
import core.entity.Entity;
import core.entity.HostileEntity;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import core.world.Tile;
import core.world.World;
import util.math.Vector2f;

public class Enemy extends Entity implements HostileEntity {
	
	private AnimatedSprite sprite;
	
	public Enemy(Vector2f spawn) {
		super("enemy");
		
		position = new Vector2f(spawn);
		bound = new Rectangle(0, 0, 20, Tile.SIZE);
		
		sprite = new AnimatedSprite("entity/undead.png", 1, 5, 64, 64);
		sprite.setOffset(new Vector2f(
						(bound.width  - Tile.SIZE) / 2,
						(bound.height - Tile.SIZE) / 2));
		
		setMaxHealth(25.f);
		setMovementSpeed(1.5f);
	}
	
	@Override
	public void init() {
		
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

		sprite.setState((direction.isZero()) ? State.IDLE : State.PLAY);
		sprite.update(deltaTime);
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
		World.getInstance().removeEnemy(this);
		super.delete();
		World.getInstance().spawnCoin(position.add(new Vector2f(0, Tile.SIZE / 2)));
	}

}
