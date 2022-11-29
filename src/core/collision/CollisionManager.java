package core.collision;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.entity.Entity;
import core.world.World;

public class CollisionManager {
	private static CollisionManager instance;
	
	private ArrayList<Entity> entityLists;

	public CollisionManager() {
		entityLists = new ArrayList<Entity>();
	}
	
	/*
	 * isColliding
	 */
	public ArrayList<Entity> isColliding(Entity entity) {
		return isColliding(entity, entity.getBound());
	}
	
	public ArrayList<Entity> isColliding(Entity entity, Rectangle rect) {
		ArrayList<Entity> collidedEntity = new ArrayList<Entity>();
		
		for (Entity other : entityLists) {
			if (other != entity &&
					rect.intersects(other.getBound())) {
				
				collidedEntity.add(other);
			}
		}
		
		return collidedEntity;
	}
	
	public boolean isCollidingWithPlayer(Entity entity) {
		return World.getPlayer().getBound().intersects(entity.getBound());
	}
	
	public void add(Entity o) {
		entityLists.add(o);
	}
	
	public void remove(Entity o) {
		entityLists.remove(o);
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
