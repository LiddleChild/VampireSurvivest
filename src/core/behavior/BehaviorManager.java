package core.behavior;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import core.Camera;

public class BehaviorManager {
	enum State {
		INITIALIZING,
		RUNNING
	}
	
	private static BehaviorManager instance;
	
	private State state = State.INITIALIZING;
	
	private ArrayList<GameBehavior> gameBehaviorLists;
	private Queue<GameBehavior> addQueues, removeQueues;
	
	public BehaviorManager() {
		this.gameBehaviorLists = new ArrayList<GameBehavior>();
		
		this.addQueues = new LinkedList<GameBehavior>();
		this.removeQueues = new LinkedList<GameBehavior>();
	}
	
	/*
	 * GAME BEHAVIOR
	 */
	public void addBehavior(GameBehavior e) {
		if (state == State.RUNNING) {
			addQueues.add(e);
		} else {
			gameBehaviorLists.add(e);
		}
	}
	
	public void removeBehavior(GameBehavior e) {
		if (state == State.RUNNING) {
			removeQueues.add(e);
		} else {
			gameBehaviorLists.remove(e);
		}
	}
	
	public void update(float deltaTime) {
		state = State.RUNNING;
		
		// Sort behaviors by y-position and layer priority
		gameBehaviorLists.sort(null);
		
		// Call update
		for (GameBehavior e : gameBehaviorLists) {
			e.updateDeltaTime(deltaTime);
			e.update();
		}
		
		// Camera update
		Camera.getInstance().update();
		
		// Add later to prevent iterator invalidation
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