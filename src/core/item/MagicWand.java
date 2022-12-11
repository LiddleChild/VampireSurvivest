package core.item;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.Renderer;
import core.collision.CollisionManager;
import core.collision.Hitbox;
import core.entity.Entity;
import core.entity.HostileEntity;
import core.sprite.Sprite;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import core.world.Tile;
import core.world.World;
import util.math.Vector2f;

public class MagicWand implements Item {

	private Sprite sprite;
	private AnimatedSprite hitFxSprite;
	private Hitbox[] hitboxes;
	
	private Vector2f position;
	private int direction;
	
	private float attackDamage, attackCooldownTime;
	private float attackTime;
	
	private ArrayList<ForceWave> forceWaveLists;
	
	public MagicWand() {
		sprite = new Sprite("magic_wand.png");
		
		hitboxes = new Hitbox[] {
				new Hitbox(0, 0, 0, 0),
				new Hitbox( Tile.SIZE, -Tile.SIZE, Tile.SIZE * 2, Tile.SIZE * 2),
				new Hitbox(0, 0, 0, 0),
				new Hitbox(-Tile.SIZE * 2, -Tile.SIZE, Tile.SIZE * 2, Tile.SIZE * 2)
		};
		
		forceWaveLists = new ArrayList<ForceWave>();
		
		position = new Vector2f();
		direction = 1;
		
		attackCooldownTime = 2.f;
		attackTime = 0.f;
		attackDamage = 10.f;

		hitFxSprite = new AnimatedSprite("fx/hit_animation.png", 1, 5, 128, 228);
		hitFxSprite.setFrameTime(0.03f);
		hitFxSprite.setState(State.IDLE);
		hitFxSprite.setStateIntervals(State.IDLE, State.IDLE, -1, -1);
		hitFxSprite.setStateIntervals(State.PLAY, State.IDLE, 0, 5);
	}
	
	@Override
	public void update(float deltaTime) {
		attackTime += deltaTime;
		
		ArrayList<ForceWave> deleteLists = new ArrayList<ForceWave>();
		
		hitFxSprite.update(deltaTime);
		for (ForceWave fw : forceWaveLists) {
			fw.update(deltaTime);

			ArrayList<Entity> entityLists = CollisionManager.getInstance().isColliding(fw.getBound());
			for (Entity e : entityLists) {
				if (e instanceof HostileEntity) {					
					e.takeDamge(attackDamage);
					deleteLists.add(fw);
				}
			}
			
			if (!Renderer.checkInsideWindow(
					fw.getBound().x,
					fw.getBound().y,
					fw.getBound().width,
					fw.getBound().height)) {
				deleteLists.add(fw);
			}
		}

		forceWaveLists.removeAll(deleteLists);
	}
	
	@Override
	public void render() {
		Renderer.setRenderOffset(16, -Tile.SIZE + 5);
		Renderer.drawSprite(sprite,
				position.x,
				position.y,
				Tile.SIZE, Tile.SIZE * 2);
		
		hitFxSprite.setReverse(direction == 3);
		hitFxSprite.render(
				hitboxes[direction].getBound().x,
				hitboxes[direction].getBound().y,
				hitboxes[direction].getBound().width,
				hitboxes[direction].getBound().height,
				0.f);
		for (ForceWave fw : forceWaveLists) {
			fw.render();
		}
	}
	
	@Override
	public void attack() {
		Entity temp = null;
		Vector2f dist = new Vector2f(1000, 1000);
		for (Entity e : World.getInstance().getEnemyLists()) {
			Vector2f d = e.getPosition().subtract(position);
			
			if (d.getSize() <= 5.f * Tile.SIZE && d.compareTo(dist) < 0) {
				dist = d;
				temp = e;
			}
		}
		
		if (temp != null && attackTime >= attackCooldownTime) {
			attackTime = 0.f;
			forceWaveLists.add(new ForceWave(position, (float) (Math.atan2(dist.y, dist.x) / Math.PI * 180.f)));
		}
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	@Override
	public float getAttackDamage() {
		return attackDamage;
	}
	
	@Override
	public float getAttackCooldownTime() {
		return attackCooldownTime;
	}
	
	@Override
	public void setPosition(Vector2f position) {
		for (int i = 0; i < hitboxes.length; i++) {
			hitboxes[i].setPosition(position);
		}
		
		this.position = position;
	}

	@Override
	public void setDirection(Vector2f direction) {
		if (direction.x < 0) this.direction = 3;
		else if (direction.x > 0) this.direction = 1;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Hitbox[] getHitboxes() {
		return hitboxes;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}

class ForceWave {
	private static final float MOVEMENT_SPEED = 5.f * Tile.SIZE;

	private AnimatedSprite sprite;
	private Rectangle bound;
	
	private Vector2f position, direction;
	private float angle;
	
	public ForceWave(Vector2f position, float angle) {
		this.position = new Vector2f(position);
		this.angle = angle;
		
		direction = new Vector2f(this.angle);
		bound = new Rectangle((int) position.x, (int) position.y, Tile.SIZE, Tile.SIZE);
		
		sprite = new AnimatedSprite("fx/wind_projectile_purple.png", 2, 3, 32, 32);
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
		sprite.render(position, Tile.SIZE, Tile.SIZE, angle);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public Rectangle getBound() {
		return bound;
	}
	
}
