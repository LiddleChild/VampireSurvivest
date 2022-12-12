package core.item;

import java.util.ArrayList;

import core.Renderer;
import core.collision.CollisionManager;
import core.collision.Hitbox;
import core.entity.Entity;
import core.sprite.Sprite;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import core.world.Tile;
import util.math.Vector2f;

public class ShortSword extends Item {
	
	private Sprite sprite;
	private AnimatedSprite hitFxSprite;
	private Hitbox[] hitboxes;
	
	private Vector2f position;
	private int direction;
	
	public ShortSword() {
		super("Short Sword", "item/short_sword.png", 25.f, 1.5f);
		sprite = new Sprite(spritePath);
		
		hitboxes = new Hitbox[] {
				new Hitbox(0, 0, 0, 0),
				new Hitbox( Tile.SIZE, 0, Tile.SIZE, Tile.SIZE),
				new Hitbox(0, 0, 0, 0),
				new Hitbox(-Tile.SIZE, 0, Tile.SIZE, Tile.SIZE)
		};
		
		position = new Vector2f();
		direction = 1;

		hitFxSprite = new AnimatedSprite("fx/hit_animation.png", 1, 5, 128, 228);
		hitFxSprite.setFrameTime(0.025f);
		hitFxSprite.setState(State.IDLE);
		hitFxSprite.setStateIntervals(State.IDLE, State.IDLE, -1, -1);
		hitFxSprite.setStateIntervals(State.PLAY, State.IDLE, 0, 5);
	}
	
	@Override
	public void update(float deltaTime) {
		hitFxSprite.update(deltaTime);

		attackTime += deltaTime;
	}
	
	@Override
	public void render() {
		Renderer.setRenderOffset(12, -8);
		Renderer.drawSprite(sprite,
				position.x,
				position.y,
				Tile.SIZE, Tile.SIZE);
		
		hitFxSprite.setReverse(direction == 3);
		hitFxSprite.render(
				hitboxes[direction].getBound().x,
				hitboxes[direction].getBound().y,
				hitboxes[direction].getBound().width,
				hitboxes[direction].getBound().height,
				0.f);
	}
	
	@Override
	public void attack() {
		if (attackTime >= attackCooldownTime) {
			attackTime -= attackCooldownTime;
			
			ArrayList<Entity> entityLists = CollisionManager.getInstance().isColliding(hitboxes[direction].getBound());
			entityLists.forEach((e) -> e.takeDamge(attackDamage));
			
			hitFxSprite.setState(State.PLAY);
		}
	}
	
	/*
	 * GETTERS & SETTERS
	 */	
	@Override
	public void setPosition(Vector2f position) {
		for (int i = 0; i < hitboxes.length; i++) {
			hitboxes[i].setPosition(position);
		}
		
		this.position = position;
	}

	@Override
	public void setDirection(Vector2f direction) {
		if (direction.x < 0) this.direction = 3;
		else if (direction.x > 0) this.direction = 1;
	}
}
