package core.game.entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.Renderer;
import core.audio.AudioMedia;
import core.behavior.BehaviorManager;
import core.behavior.GameBehavior;
import core.collision.CollisionManager;
import core.game.effect.HealthEffect;
import core.game.world.Tile;
import core.game.world.World;
import javafx.scene.paint.Color;
import util.Time;
import util.math.Vector2f;

/*
 * 
 * Entity
 * - A base class for all entity
 * - move method
 * - check for collision with tiles
 * - draw health bar
 * - heal, takeDamage
 * - attack
 * 
 */

public abstract class Entity extends GameBehavior {

	private String id;
	
	protected Vector2f position;
	protected Vector2f direction;	
	protected float movementSpeed;

	protected float maxHealth, health;
	protected float attackDamage, attackCooldownTime, lastAttackTime;
	
	protected Rectangle bound;
	protected ArrayList<Entity> collidingEntity;
	
	private boolean[] movable = new boolean[4]; // T R B L
	
	public Entity(String id) {
		this(id, 3);
	}
	
	public Entity(String id, int layer) {
		super(layer);
		this.id = id;
		
		attackCooldownTime = 1.75f;
		lastAttackTime = 0.f;
		attackDamage = 8.f;
		
		CollisionManager.getInstance().add(this);
		
		movable = new boolean[] { true, true, true, true };
		
		setMovementSpeed(4.f);
		position = new Vector2f(World.getInstance().getSpawnPoint());
		direction = new Vector2f(0.f, 0.f);
		
		maxHealth = 100;
		health = maxHealth;
		bound = new Rectangle(0, 0, Tile.SIZE, Tile.SIZE);
	}
	
	/*
	 * EVENT
	 */
	protected abstract void onDeath();
	protected abstract void onTakingDamage();
	
	/*
	 * DEATH
	 * - delete this from CollisionManager and BehaviorManager
	 */
	@Override
	protected void delete() {
		super.delete();
		AudioMedia.BIG_HIT.play();
		CollisionManager.getInstance().remove(this);
	}
	
	/*
	 * DRAWING
	 */
	protected void drawBound() {
		Renderer.setFill(new Color(1, 0, 0, 0.5f));
		Renderer.fillRect(bound);
	}
	
	protected void drawHealthBar() {
		// Background
		Renderer.setFill(Color.GREY);
		Renderer.fillRectWithBound(
				position.x,
				position.y - Tile.SIZE / 2,
				Tile.SIZE,
				5);

		// Health
		Renderer.setFill(new Color(1 - health / maxHealth, health / maxHealth, 0, 1.f));
		Renderer.fillRect(
				position.x,
				position.y - Tile.SIZE / 2,
				Tile.SIZE * health / maxHealth,
				5);
	}
	
	/*
	 * HEALTH
	 */
	public void takeDamge(float damage) {
		AudioMedia.HIT.play();
		BehaviorManager.getInstance().addBehavior(new HealthEffect(position, -damage));
		
		this.setHealth(getHealth() - damage);
		onTakingDamage();
	}
	
	public void heal(float heal) {
		heal = (health + heal > maxHealth) ? maxHealth - health : heal;
		BehaviorManager.getInstance().addBehavior(new HealthEffect(position, heal));
		this.setHealth(getHealth() + heal);
	}

	/*
	 * ATTACK
	 */
	protected void attack(Entity e) {
		float currentTime = Time.getNanoSecond();
		
		if (currentTime - lastAttackTime >= attackCooldownTime) {	
			e.takeDamge(attackDamage);
			
			lastAttackTime = currentTime;
		}
	}
	
	/*
	 * MOVEMENT
	 */
	protected void move(Vector2f direction) {
		direction.normalize();
		this.direction = direction;
		
		checkCollision(direction);
		
		// TOP
		if (!movable[0] && direction.y < 0) {
			direction.y = 0;
			position.y = (float) Math.ceil(position.y);
		}
		
		// BELOW
		if (!movable[2] && direction.y > 0) {
			direction.y = 0;
			position.y = (float) Math.floor(position.y);
		}
		
		// LEFT
		if (!movable[3] && direction.x < 0) {
			direction.x = 0;
			position.x = (float) Math.ceil(position.x);
		}
		
		// RIGHT
		if (!movable[1] && direction.x > 0) {
			direction.x = 0;
			position.x = (float) Math.floor(position.x);
		}
		
		// Update position
		position.addEqual(direction.multiply(movementSpeed * deltaTime));
		
		bound.x = (int) position.x;
		bound.y = (int) position.y;
		
		super.setYPriority(bound.y);
	}
	
	private void checkCollision(Vector2f dv) {
		float leftX   =	(float) Math.floor(position.x / Tile.SIZE) * Tile.SIZE;
		float rightX  =	(float) Math.ceil (position.x / Tile.SIZE) * Tile.SIZE;
		float topY    =	(float) Math.floor(position.y / Tile.SIZE) * Tile.SIZE;
		float bottomY = (float) Math.ceil (position.y / Tile.SIZE) * Tile.SIZE;

		collidingEntity = CollisionManager.getInstance().isColliding(this);
		
		// TOP
		if (dv.y < 0) {
			movable[0] = !(World.getInstance().isPosSolidTile(leftX , bound.y + dv.y) ||
					World.getInstance().isPosSolidTile(rightX, bound.y + dv.y));
			
			collidingEntity.forEach((e) -> movable[0] &= !(position.isInNorth(e.getPosition())));
		}
		
		// BOTTOM
		if (dv.y > 0) {
			movable[2] = !(World.getInstance().isPosSolidTile(leftX , bound.y + dv.y + Tile.SIZE) ||
					World.getInstance().isPosSolidTile(rightX, bound.y + dv.y + Tile.SIZE));
			collidingEntity.forEach((e) -> movable[2] &= !(position.isInSouth(e.getPosition())));
		}

		// LEFT
		if (dv.x < 0) {
			movable[3] = !(World.getInstance().isPosSolidTile(bound.x + dv.x, topY) ||
					World.getInstance().isPosSolidTile(bound.x + dv.x, bottomY));
			collidingEntity.forEach((e) -> movable[3] &= !(position.isInWest(e.getPosition())));
		}

		// RIGHT
		if (dv.x > 0) {
			movable[1] = !(World.getInstance().isPosSolidTile(bound.x + dv.x + Tile.SIZE, topY) ||
					World.getInstance().isPosSolidTile(bound.x + dv.x + Tile.SIZE, bottomY));
			collidingEntity.forEach((e) -> movable[1] &= !(position.isInEast(e.getPosition())));
		}
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public String getID() {
		return id;
	}

	public Vector2f getPosition() {
		return position;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		if (health <= 0) {
			this.health = 0;
			onDeath();
			return;
		} else if (health > maxHealth) {
			this.health = maxHealth;
		} else {			
			this.health = health;
		}
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public void setMovementSpeed(float speed) {
		this.movementSpeed = speed * Tile.SIZE;
	}

	public float getMovementSpeed() {
		return movementSpeed / Tile.SIZE;
	}	
}
