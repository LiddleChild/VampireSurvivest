package core.behavior;

import java.util.SortedSet;
import java.util.TreeSet;

import core.Camera;

public class BehaviorManager {
	private static BehaviorManager instance;
	
	private SortedSet<GameBehavior> s;
	
	public BehaviorManager() {
		this.s = new TreeSet<GameBehavior>();
	}
	
	public void addBehavior(GameBehavior e) {
		s.add(e);
	}
	
	public void update(float deltaTime) {
		// Call update to all behaviors
		for (GameBehavior e : s) {
			e.updateDeltaTime(deltaTime);
			e.update();
		}
		
		// Camera update
		Camera.getInstance().update();
	}
	
	/*
	 * Singleton pattern
	 */
	public static BehaviorManager getInstance() {
		if (instance == null) {
			instance = new BehaviorManager();
		}
		
		return instance;
	}
	
}
