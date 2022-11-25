package core.collision;

import java.util.ArrayList;

import core.entity.BaseEntity;

public class CollisionManager {
	private static CollisionManager instance;
	
	private ArrayList<BaseEntity> entityLists;

	public CollisionManager() {
		entityLists = new ArrayList<BaseEntity>();
	}
	
	public ArrayList<BaseEntity> isColliding(BaseEntity entity) {
		ArrayList<BaseEntity> collidedEntity = new ArrayList<BaseEntity>();
		
		for (BaseEntity other : entityLists) {
			if (other != entity &&
					entity.getBound().intersects(other.getBound())) {
				
				collidedEntity.add(other);
			}
		}
		
		return collidedEntity;
	}
	
	public void add(BaseEntity o) {
		entityLists.add(o);
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
