package core.game.item;

import java.util.ArrayList;

import core.Renderer;
import core.audio.AudioMedia;
import core.collision.CollisionManager;
import core.collision.Hitbox;
import core.game.entity.Entity;
import core.game.entity.HostileEntity;
import core.game.world.Tile;
import core.sprite.Sprite;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import util.math.Vector2f;

/*
 * 
 * ShortSword
 * - A sword full of agility but not power
 * 
 */

public class ShortSword extends Item {
	
	private Sprite sprite;
	private AnimatedSprite hitFxSprite;
	private Hitbox[] hitboxes;
	
	private Vector2f position;
	private int direction;
	
	public ShortSword() {
		super("Short Sword", "item/short_sword.png", 25.f, 1.f);
		sprite = new Sprite(spritePath);
		
		hitboxes = new Hitbox[] {
				new Hitbox( Tile.SIZE, 0, Tile.SIZE, Tile.SIZE),
				new Hitbox(-Tile.SIZE, 0, Tile.SIZE, Tile.SIZE)
		};
		
		position = new Vector2f();
		direction = 0;

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
		
		hitFxSprite.setReverse(direction == 1);
		hitFxSprite.render(
				hitboxes[direction].getBound().x,
				hitboxes[direction].getBound().y,
				hitboxes[direction].getBound().width,
				hitboxes[direction].getBound().height,
				0.f);
	}

	@Override
	public void reset() {
		
	}
	
	@Override
	public void attack() {
		attackDamage = baseAttackDamage + 2.5f * (level - 1);
		attackCooldownTime = baseAttackCooldownTime - 0.05f * (level - 1);
		for (int i = 0; i < hitboxes.length; i++) {
			hitboxes[i].setSize(6 * (level - 1));
		}
		
		if (attackTime >= attackCooldownTime) {
			attackTime -= attackCooldownTime;
			
			ArrayList<Entity> entityLists = CollisionManager.getInstance().isColliding(hitboxes[direction].getBound());
			entityLists.forEach((e) -> {
				if (e instanceof HostileEntity) e.takeDamge(attackDamage);
			});
			
			hitFxSprite.setState(State.PLAY);
			AudioMedia.SWING1.play();
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
		if (direction.x < 0) this.direction = 1;
		else if (direction.x > 0) this.direction = 0;
	}
}
