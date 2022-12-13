package core.behavior;

/*
 * 
 * GameBehavior
 * - A base class for all game objects which provides
 *		update, render, delete method
 * 
 */

public abstract class GameBehavior implements Comparable<GameBehavior> {
	
	protected float deltaTime;
	private int layerPriority, yPriority;
	
	/*
	 * OVERLOADING Constructor
	 */
	public GameBehavior() {
		this(0);
	}
	
	public GameBehavior(int layerPriority) {
		this.layerPriority = layerPriority;
		this.yPriority = 0;
		
		BehaviorManager.getInstance().addBehavior(this);
	}
	
	public abstract void update();
	public abstract void render();
	
	public void updateDeltaTime(float dt) {
		this.deltaTime = dt;
	}
	
	public int getLayerPriority() {
		return layerPriority;
	}

	public int getYPriority() {
		return yPriority;
	}

	public void setYPriority(int yPriority) {
		this.yPriority = yPriority;
	}
	
	protected void delete() {
		BehaviorManager.getInstance().removeBehavior(this);
	}

	@Override
	public int compareTo(GameBehavior other) {
		int comp = Integer.compare(layerPriority, other.layerPriority);
		
		return (comp != 0) ? comp : Integer.compare(yPriority, other.yPriority);
	}
	
}
