package core.item;

import util.math.Vector2f;

public abstract class Item {

	protected final String name, spritePath;
	protected final float baseAttackDamage;
	
	protected float attackDamage, attackCooldownTime, attackTime;
	protected Vector2f position, direction;
	protected int level;
	
	public Item(String name, String spritePath, float baseAttackDamage, float attackCooldownTime) {
		this.name = name;
		this.spritePath = spritePath;
		this.baseAttackDamage = baseAttackDamage;
		this.attackDamage = baseAttackDamage;
		this.attackCooldownTime = attackCooldownTime;
		this.level = 1;
		this.attackTime = 1.f;
	}
	
	public abstract void update(float deltaTime);
	public abstract void render();
	public abstract void attack();
	
	public abstract void setPosition(Vector2f position);
	public abstract void setDirection(Vector2f direction);
	
	/*
	 * GETTERS & SETTERS
	 */
	public float getAttackDamage() {
		return attackDamage;
	}

	public float getAttackCooldownTime() {
		return attackCooldownTime;
	}

	public int getLevel() {
		return level;
	}
	
	public void increaseLevel() {
		level++;
	}
	
	public String getSpritePath() {
		return spritePath;
	}
	
	public String getName() {
		return String.format("%s LV %d", name, level);
	}
	
}
