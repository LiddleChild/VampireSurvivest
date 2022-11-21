package core.behavior;

public abstract class GameBehavior implements Comparable<GameBehavior> {
	
	private Integer layer;

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
	
	@Override
	public int compareTo(GameBehavior other) {
		int comp = layer.compareTo(other.layer);
		
		return (comp != 0) ? comp : Integer.compare(hashCode(), other.hashCode());
	}
	
}
