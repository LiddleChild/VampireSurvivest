package core.item;

import util.math.Vector2f;

public interface Item {

	void update(float deltaTime);
	void render();

	void attack();
	
	/*
	 * GETTERS & SETTERS
	 */
	float getAttackDamage();
	float getAttackCooldownTime();
	void setPosition(Vector2f position);
	void setDirection(Vector2f direction);
	
}
