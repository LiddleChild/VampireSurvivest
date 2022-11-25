package core.entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.Renderer;
import core.behavior.GameBehavior;
import core.collision.CollisionManager;
import core.world.Tile;
import core.world.World;
import javafx.scene.paint.Color;
import util.Vector2;

public abstract class BaseEntity extends GameBehavior {

	private String id;
	
	protected Vector2 position;
	protected Vector2 direction;
	
	protected float speed;
	protected World world;
	
	protected int health;
	protected Rectangle bound;
	
	private boolean[] movable = new boolean[4]; // T R B L
	
	public BaseEntity(String id, World world) {
		this(id, 3, world);
	}
	
	public BaseEntity(String id, int layer, World world) {
		super(layer);
		this.id = id;
		this.world = world;
		
		CollisionManager.getInstance().add(this);
		
		// Movement
		for (int i = 0; i < 4; i++)
			movable[i] = true;
		
		speed = Tile.SIZE * 4.f;
		position = new Vector2(World.SPAWN_POINT);
		direction = new Vector2(0.f, 0.f);
		
		health = 100;
		bound = new Rectangle(0, 0, Tile.SIZE, Tile.SIZE);
	}
	
	public abstract void onHit(ArrayList<BaseEntity> entities);
	
	protected void drawHealthBar() {
		Renderer.setFill(new Color(1, 0, 0, .5f));
		Renderer.fillRect(position, Tile.SIZE, Tile.SIZE);
		
		// Background
		Renderer.setFill(Color.GREY);
		Renderer.fillRect(
				position.x,
				position.y - Tile.SIZE / 2,
				Tile.SIZE,
				5);

		// Health
		Renderer.setFill(Color.GREEN);
		Renderer.fillRect(
				position.x,
				position.y - Tile.SIZE / 2,
				Tile.SIZE * health / 100.f,
				5);
	}
	
	protected void move(Vector2 direction) {
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
		position.addEqual(direction.multiply(speed * deltaTime));
		
		bound.x = (int) position.x;
		bound.y = (int) position.y;
	}
	
	private void checkCollision(Vector2 dv) {
		float leftX =	(float) Math.floor(position.x / Tile.SIZE) * Tile.SIZE;
		float rightX =	(float) Math.ceil (position.x / Tile.SIZE) * Tile.SIZE;
		float topY =	(float) Math.floor(position.y / Tile.SIZE) * Tile.SIZE;
		float bottomY = (float) Math.ceil (position.y / Tile.SIZE) * Tile.SIZE;
		
		ArrayList<BaseEntity> entityLists = CollisionManager.getInstance().isColliding(this);
		
		Vector2 futurePosition = position.add(dv);
		
		// TOP
		if (dv.y < 0) {
			movable[0] = !(world.isSolidTile(leftX , futurePosition.y) ||
					world.isSolidTile(rightX, position.y + dv.y));
			
			for (BaseEntity e : entityLists)
				movable[0] &= !(futurePosition.isInNorth(e.getPosition()));
		}
		
		// BOTTOM
		if (dv.y > 0) {
			movable[2] = !(world.isSolidTile(leftX , futurePosition.y + Tile.SIZE) ||
					world.isSolidTile(rightX, position.y + Tile.SIZE + dv.y));
			
			for (BaseEntity e : entityLists)
				movable[2] &= !(futurePosition.isInSouth(e.getPosition()));
		}

		// LEFT
		if (dv.x < 0) {
			movable[3] = !(world.isSolidTile(futurePosition.x, topY) ||
					world.isSolidTile(position.x + dv.x, bottomY));
			
			for (BaseEntity e : entityLists)
				movable[3] &= !(futurePosition.isInWest(e.getPosition()));
		}

		// RIGHT
		if (dv.x > 0) {
			movable[1] = !(world.isSolidTile(futurePosition.x + Tile.SIZE, topY) ||
					world.isSolidTile(position.x + Tile.SIZE + dv.x, bottomY));
			
			for (BaseEntity e : entityLists)
				movable[1] &= !(futurePosition.isInEast(e.getPosition()));
		}
	}
	
	/*
	 * GETTER
	 */
	public String getID() {
		return id;
	}

	public Vector2 getPosition() {
		return position;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}
	
}
