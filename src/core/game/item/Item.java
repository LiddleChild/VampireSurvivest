package core.game.item;

import util.Vector2f;

/*
 * 
 * Item
 * - A base class for all items
 * - Provides a general information about that item
 * 
 */

public abstract class Item {

	protected final String NAME, SPRITE_PATH;
	protected final float BASE_ATTACK_DAMAGE, BASE_ATTACK_COOLDOWN_TIME;
	
	protected float attackDamage, attackCooldownTime, attackTime;
	protected Vector2f position, direction;
	protected int level;
	
	public Item(String NAME, String SPRITE_PATH, float BASE_ATTACK_DAMAGE, float BASE_ATTACK_COOLDOWN_TIME) {
		this.NAME = NAME;
		this.SPRITE_PATH = SPRITE_PATH;
		this.BASE_ATTACK_DAMAGE = BASE_ATTACK_DAMAGE;
		this.attackDamage = BASE_ATTACK_DAMAGE;
		this.BASE_ATTACK_COOLDOWN_TIME = BASE_ATTACK_COOLDOWN_TIME;
		this.attackCooldownTime = BASE_ATTACK_COOLDOWN_TIME;
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
		return SPRITE_PATH;
	}
	
	public String getName() {
		return String.format("%s", NAME, level);
	}
	
}
