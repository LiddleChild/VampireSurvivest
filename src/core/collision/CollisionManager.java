package core.collision;

import java.util.ArrayList;

import javafx.scene.shape.Rectangle;

public class CollisionManager {
	private static CollisionManager instance;
	
	private ArrayList<HittableObject> objects;

	public CollisionManager() {
		objects = new ArrayList<HittableObject>();
	}
	
	private boolean isIntersecting(HittableObject obj1, HittableObject obj2) {
		Rectangle rect1 = obj1.getHitBox();
		Rectangle rect2 = obj2.getHitBox();
		
		return rect1.intersects(rect2.getBoundsInLocal());
	}
	
	public ArrayList<HittableObject> isColliding(HittableObject obj) {
		ArrayList<HittableObject> hitObjects = new ArrayList<HittableObject>();
		
		for (HittableObject e : objects) {
			if (isIntersecting(e, obj)) {
				hitObjects.add(e);
				e.onHit(obj);
			}
		}
		
		return hitObjects;
	}
	
	public void add(HittableObject o) {
		objects.add(o);
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
