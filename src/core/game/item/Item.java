package core.game.item;

import util.math.Vector2f;

/*
 * 
 * Item
 * - A base class for all items
 * - Provides a general information about that item
 * 
 */

public abstract class Item {

	protected final String name, spritePath;
	protected final float baseAttackDamage, baseAttackCooldownTime;
	
	protected float attackDamage, attackCooldownTime, attackTime;
	protected Vector2f position, direction;
	protected int level;
	
	public Item(String name, String spritePath, float baseAttackDamage, float baseAttackCooldownTime) {
		this.name = name;
		this.spritePath = spritePath;
		this.baseAttackDamage = baseAttackDamage;
		this.attackDamage = baseAttackDamage;
		this.baseAttackCooldownTime = baseAttackCooldownTime;
		this.attackCooldownTime = baseAttackCooldownTime;
		this.level = 1;
		this.attackTime = 1.f;
	}
	
	public void reset() {
		this.level = 1;
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
		return String.format("%s", name, level);
	}
	
}
