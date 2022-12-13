package core.collision;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.game.entity.Entity;
import core.game.world.World;

/*
 * 
 * CollisionManager
 * - Manages all Entity collisions
 * 
 */

public class CollisionManager {
	private static CollisionManager instance;
	
	enum State { IDLE, RUNNING }
	private State state = State.IDLE;
	
	private ArrayList<Entity> entityLists, addQueues, removeQueues;

	public void initialize() {
		entityLists = new ArrayList<Entity>();
		addQueues = new ArrayList<Entity>();
		removeQueues = new ArrayList<Entity>();
	}
	
	public void reset() {
		entityLists.clear();
		addQueues.clear();
		removeQueues.clear();
	}
	
	/*
	 * isColliding
	 */
	public ArrayList<Entity> isColliding(Entity entity) {
		return isColliding(entity.getBound());
	}
	
	public ArrayList<Entity> isColliding(Rectangle rect) {
		state = State.RUNNING;
		
		ArrayList<Entity> collidedEntity = new ArrayList<Entity>();
		
		for (Entity other : entityLists) {
			if (rect.intersects(other.getBound())) {
				collidedEntity.add(other);
			}
		}
		
		// Prevent iterator invalidation
		entityLists.addAll(addQueues);
		addQueues.clear();
		
		entityLists.removeAll(removeQueues);
		removeQueues.clear();
		
		state = State.IDLE;
		
		return collidedEntity;
	}
	
	public boolean isCollidingWithPlayer(Entity entity) {
		return World.getInstance().getPlayer().getBound().intersects(entity.getBound());
	}
	
	public void add(Entity e) {
		if (state == State.RUNNING) {
			addQueues.add(e);
		} else {
			entityLists.add(e);
		}
	}
	
	public void remove(Entity e) {
		if (state == State.RUNNING) {
			removeQueues.add(e);
		} else {
			entityLists.remove(e);
		}
	}
	
	/*
	 * SINGLETON
	 */
	public static CollisionManager getInstance() {
		if (instance == null) {
			instance = new CollisionManager();
		}
		
		return instance;
	}
	
}
