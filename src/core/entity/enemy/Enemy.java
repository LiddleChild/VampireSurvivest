package core.entity.enemy;

import core.Renderer;
import core.behavior.BehaviorManager;
import core.collision.CollisionManager;
import core.entity.Entity;
import core.world.Tile;
import core.world.World;
import javafx.scene.paint.Color;
import util.Vector2f;

public class Enemy extends Entity {
	
	private float attackCooldown = 1.5f;
	private float attackTime = 0.f;
	
	public Enemy(World world, Vector2f spawn) {
		super("enemy", world);
		
		super.speed = Tile.SIZE * .5f;
		
		position = new Vector2f(spawn);
//		position.subtractEqual(new Vector2(0, Tile.SIZE * 3));
	}

	@Override
	public void update() {
		Vector2f playerPos = World.getPlayer().getPosition();
		super.move(playerPos.subtract(position));
		
		attackTime += deltaTime;
		
		if (attackTime >= attackCooldown) {
			if (CollisionManager.getInstance().isCollidingWithPlayer(this)) {
				World.getPlayer().setHealth(World.getPlayer().getHealth() - 10);
				
				attackTime = 0.f;
			}
		}
		
		Renderer.setFill(Color.DARKRED);
		Renderer.fillRect(position, Tile.SIZE, Tile.SIZE);
		
		super.drawHealthBar();
	}

	@Override
	protected void onDeath() {
		CollisionManager.getInstance().remove(this);
		BehaviorManager.getInstance().removeBehavior(this);
	}

}
