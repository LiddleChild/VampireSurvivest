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
	
	private ArrayList<GameBehavior> behaviorLists;
	private Queue<GameBehavior> addQueues, removeQueues;
	
	public BehaviorManager() {
		this.behaviorLists = new ArrayList<GameBehavior>();
		
		this.addQueues = new LinkedList<GameBehavior>();
		this.removeQueues = new LinkedList<GameBehavior>();
	}
	
	public void addBehavior(GameBehavior e) {
		if (state == State.RUNNING) {
			addQueues.add(e);
		} else {
			behaviorLists.add(e);
		}
	}
	
	public void removeBehavior(GameBehavior e) {
		if (state == State.RUNNING) {
			removeQueues.add(e);
		} else {
			behaviorLists.remove(e);
		}
	}
	
	public void update(float deltaTime) {
		state = State.RUNNING;
		
		behaviorLists.sort(null);
		
		// Call update to all behaviors
		for (GameBehavior e : behaviorLists) {
			e.updateDeltaTime(deltaTime);
			e.update();
		}
		
		// Camera update
		Camera.getInstance().update();
		
		// Add later to prevent iterator invalidation
		while (!addQueues.isEmpty()) behaviorLists.add(addQueues.poll());
		while (!removeQueues.isEmpty()) behaviorLists.remove(removeQueues.poll());
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