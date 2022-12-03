package core.item;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.entity.Entity;
import core.sprite.Sprite;
import core.world.Tile;
import util.Time;
import util.Vector2f;

public class Sword {

	private Sprite sprite;
	private Rectangle[] hitboxes;
	private float attackDamage, attackCooldownTime, lastAttackTime;
	
	public Sword() {
		sprite = new Sprite("short_sword.png");
		
		hitboxes = new Rectangle[] {
				new Rectangle(			0, -Tile.SIZE, Tile.SIZE, Tile.SIZE),
				new Rectangle(  Tile.SIZE, 			0, Tile.SIZE, Tile.SIZE),
				new Rectangle(			0,  Tile.SIZE, Tile.SIZE, Tile.SIZE),
				new Rectangle( -Tile.SIZE, 			0, Tile.SIZE, Tile.SIZE)
		};

		
		attackCooldownTime = 0.5f;
		lastAttackTime = 0.f;
		attackDamage = 50.f;
	}
	
	public void attack(ArrayList<Entity> entityLists) {
		float currentTime = Time.getNanoSecond();
		
		if (currentTime - lastAttackTime >= attackCooldownTime) {
			
			for (Entity e : entityLists) {				
				e.setHealth(e.getHealth() - attackDamage);
			}
			
			lastAttackTime = currentTime;
		}
	}
	
	float getAttackDamage() {
		return attackDamage;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public Rectangle[] getHitboxes() {
		return hitboxes;
	}

	public void setPosition(Vector2f position) {
		hitboxes[0].x = (int) position.x;
		hitboxes[0].y = (int) position.y - Tile.SIZE;
		
		hitboxes[1].x = (int) position.x + Tile.SIZE;
		hitboxes[1].y = (int) position.y;
		
		hitboxes[2].x = (int) position.x;
		hitboxes[2].y = (int) position.y + Tile.SIZE;
		
		hitboxes[3].x = (int) position.x - Tile.SIZE;
		hitboxes[3].y = (int) position.y;
	}
	
}
