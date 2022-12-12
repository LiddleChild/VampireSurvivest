package core.collision;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import core.entity.Entity;
import core.world.World;

public class CollisionManager {
	private static CollisionManager instance;
	
	enum State { IDLE, RUNNING }
	private State state = State.IDLE;
	
	private ArrayList<Entity> entityLists;
	private Queue<Entity> addQueues, removeQueues;

	public void initialize() {
		entityLists = new ArrayList<Entity>();
		this.addQueues = new LinkedList<Entity>();
		this.removeQueues = new LinkedList<Entity>();
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
		while (!addQueues.isEmpty()) entityLists.add(addQueues.poll());
		while (!removeQueues.isEmpty()) entityLists.remove(removeQueues.poll());
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
