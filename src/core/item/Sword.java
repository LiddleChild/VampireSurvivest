package core.item;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.entity.Entity;
import core.sprite.Sprite;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import core.world.Tile;
import util.Time;
import util.Vector2f;

public class Sword {

	private Sprite sprite;
	private AnimatedSprite hitFxSprite;
	private Rectangle[] hitboxes;
	private int direction;
	private float attackDamage, attackCooldownTime, lastAttackTime;
	
	public Sword() {
		sprite = new Sprite("short_sword.png");
		
		hitboxes = new Rectangle[] {
				new Rectangle(			0, -Tile.SIZE, Tile.SIZE, Tile.SIZE),
				new Rectangle(  Tile.SIZE, 			0, Tile.SIZE, Tile.SIZE),
				new Rectangle(			0,  Tile.SIZE, Tile.SIZE, Tile.SIZE),
				new Rectangle( -Tile.SIZE, 			0, Tile.SIZE, Tile.SIZE)
		};

		direction = 0;
		
		attackCooldownTime = 0.6f;
		lastAttackTime = 0.f;
		attackDamage = 35.f;

		hitFxSprite = new AnimatedSprite("hit_animation.png", 1, 5, 35, 32);
		hitFxSprite.setFrameTime(0.05f);
		hitFxSprite.setState(State.IDLE);
		hitFxSprite.setStateIntervals(State.IDLE, State.IDLE, -1, -1);
		hitFxSprite.setStateIntervals(State.PLAY, State.IDLE, 0, 5);
	}
	
	public void drawFx(float deltaTime) {
		hitFxSprite.draw(hitboxes[direction].x, hitboxes[direction].y, Tile.SIZE, Tile.SIZE, deltaTime, 90.f * direction);
		
//		for (Rectangle r : hitboxes) {
//			Renderer.setFill(new Color(1, 0, 0, 0.5f));
//			Renderer.fillRect(r);
//		}
	}
	
	public void attack(ArrayList<Entity> entityLists) {
		float currentTime = Time.getNanoSecond();
		
		if (currentTime - lastAttackTime >= attackCooldownTime) {
			entityLists.forEach((e) -> e.takeDamge(attackDamage));
			hitFxSprite.setState(State.PLAY);
			
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}
