package core;

public abstract class Entity extends GameBehavior {

	private String id;
	
	public Entity(String id) {
		this(id, 0);
	}
	
	public Entity(String id, int layer) {
		super(layer);
		this.id = id;
	}
	
	/*
	 * GETTER
	 */
	public String getID() {
		return id;
	}
	
}
