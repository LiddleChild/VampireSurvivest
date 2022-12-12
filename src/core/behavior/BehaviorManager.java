package core.behavior;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import core.Camera;

public class BehaviorManager {	
	private static BehaviorManager instance;
	
	private ArrayList<GameBehavior> gameBehaviorLists;
	private Queue<GameBehavior> addQueues, removeQueues;
	
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
		this.gameBehaviorLists = new ArrayList<GameBehavior>();
		this.addQueues = new LinkedList<GameBehavior>();
		this.removeQueues = new LinkedList<GameBehavior>();
		
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
		if (!addQueues.isEmpty()) System.out.println("ADDING " + addQueues.size());
		while (!addQueues.isEmpty()) gameBehaviorLists.add(addQueues.poll());
		while (!removeQueues.isEmpty()) gameBehaviorLists.remove(removeQueues.poll());
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