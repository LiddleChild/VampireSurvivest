package core.behavior;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameBehavior implements Comparable<GameBehavior> {
	
	private Integer layer;

	protected GraphicsContext gc;
	protected float deltaTime;
	
	/*
	 * CONSTRUCTOR
	 */
	
	public GameBehavior() {
		this(0);
	}
	
	public GameBehavior(int layer) {
		this.layer = layer;
		
		BehaviorManager.getInstance().addBehavior(this);
	}
	
	public abstract void update();
	
	public void updateDeltaTime(float dt) {
		this.deltaTime = dt;
	}
	
	public void updateGraphicsContext(GraphicsContext gc) {
		this.gc = gc;
	}
	
	@Override
	public int compareTo(GameBehavior other) {
		return layer.compareTo(other.layer);
	}
	
}
