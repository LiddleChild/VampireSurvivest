package core.entity;

import core.behavior.GameBehavior;

public abstract class BaseEntity extends GameBehavior {

	private String id;
	
	public BaseEntity(String id) {
		this(id, 0);
	}
	
	public BaseEntity(String id, int layer) {
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
