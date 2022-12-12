package core.behavior;

import java.util.ArrayList;

import core.Camera;

public class BehaviorManager {	
	private static BehaviorManager instance;
	
	private ArrayList<GameBehavior> gameBehaviorLists, addQueues, removeQueues;
	
	/*
	 * GAME BEHAVIOR
	 */
	public void addBehavior(GameBehavior e) {
		addQueues.add(e);
	}
	
	public void removeBehavior(GameBehavior e) {
		removeQueues.add(e);
	}
	
	public void initialize() {
		gameBehaviorLists = new ArrayList<GameBehavior>();
		addQueues = new ArrayList<GameBehavior>();
		removeQueues = new ArrayList<GameBehavior>();
		
		modifyBehaviorLists();
	}
	
	public void reset() {
		gameBehaviorLists.clear();
		addQueues.clear();
		removeQueues.clear();

		modifyBehaviorLists();
	}
	
	public void update(float deltaTime) {
		// Sort behaviors by y-position and layer priority
		gameBehaviorLists.sort(null);
		
		// Call update
		for (GameBehavior e : gameBehaviorLists) {
			e.updateDeltaTime(deltaTime);
			e.update();
		}
		
		// Camera update
		Camera.getInstance().update();
		
		modifyBehaviorLists();
	}
	
	private void modifyBehaviorLists() {
		// Add later to prevent iterator invalidation
		gameBehaviorLists.addAll(addQueues);
		addQueues.clear();
		
		gameBehaviorLists.removeAll(removeQueues);
		removeQueues.clear();
	}
	
	public void render(float deltaTime) {
		// Call render
		for (GameBehavior e : gameBehaviorLists) {
			e.render();
		}
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