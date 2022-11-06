package core;

import java.util.SortedSet;
import java.util.TreeSet;

import javafx.scene.canvas.GraphicsContext;

public class BehaviorManager {
	private static BehaviorManager instance;
	
	private SortedSet<GameBehavior> s;
	
	public BehaviorManager() {
		s = new TreeSet<GameBehavior>();
	}
	
	public void addBehavior(GameBehavior e) {
		s.add(e);
	}
	
	public void update(float deltaTime, GraphicsContext gc) {
		for (GameBehavior e : s) {
			e.updateDeltaTime(deltaTime);
			e.updateGraphicsContext(gc);
			e.update();
		}
	}
	
	public static BehaviorManager getInstance() {
		if (instance == null) {
			instance = new BehaviorManager();
		}
		
		return instance;
	}
	
}
