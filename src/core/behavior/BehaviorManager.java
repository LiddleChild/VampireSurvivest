package core.behavior;

import java.util.ArrayList;

import core.Camera;

/*
 * 
 * BehaviorManager
 * - Manages all GameBehaviors
 * - Update and render all GameBehaviors
 * - ddQueues and removeQueues are used to prevent iterator invalidation
 * 
 */

public class BehaviorManager {	
	private static BehaviorManager instance;
	
	private ArrayList<GameBehavior> gameBehaviorLists, addQueues, removeQueues;
	
	public void initialize() {
		gameBehaviorLists = new ArrayList<GameBehavior>();
		addQueues = new ArrayList<GameBehavior>();
		removeQueues = new ArrayList<GameBehavior>();
	}
	
	public void reset() {
		gameBehaviorLists.clear();
		addQueues.clear();
		removeQueues.clear();
	}
	
	public void update(float deltaTime) {
		// Sort behaviors by y-position then layer priority
		gameBehaviorLists.sort(null);
		
		// Update GameBehaviors
		for (GameBehavior e : gameBehaviorLists) {
			e.updateDeltaTime(deltaTime);
			e.update();
		}
		
		// Update Camera
		Camera.getInstance().update();
		
		// Add or remove queued element
		modifyBehaviorLists();
	}
	
	public void addBehavior(GameBehavior e) {
		addQueues.add(e);
	}
	
	public void removeBehavior(GameBehavior e) {
		removeQueues.add(e);
	}
	
	public void modifyBehaviorLists() {
		// Add later to prevent iterator invalidation
		gameBehaviorLists.addAll(addQueues);
		addQueues.clear();
		
		// Remove ...
		gameBehaviorLists.removeAll(removeQueues);
		removeQueues.clear();
	}
	
	public void render(float deltaTime) {
		for (GameBehavior e : gameBehaviorLists) {
			e.render();
		}
	}
	
	/*
	 * SINGLETON
	 */
	public static BehaviorManager getInstance() {
		if (instance == null) {
			instance = new BehaviorManager();
		}
		
		return instance;
	}
}