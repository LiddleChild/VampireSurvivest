package core.behavior;

public abstract class GameBehavior implements Comparable<GameBehavior> {
	
	private int layerPriority, yPriority;

	protected float deltaTime;
	
	/*
	 * CONSTRUCTOR
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
	
	public void updateDeltaTime(float dt) {
		this.deltaTime = dt;
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
