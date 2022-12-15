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
import util.Vector2f;

/*
 * 
 * RustySword
 * - A sword with great power which costs agility
 * 
 */

public class RustySword extends Item {

	private Sprite sprite;
	private AnimatedSprite hitFxSprite;
	private Hitbox[] hitboxes;
	
	private Vector2f position;
	private int direction;
	
	public RustySword() {
		super("Rusty Sword", "item/rusty_sword.png", 25.f, 1.5f);
		sprite = new Sprite(SPRITE_PATH);
		
		hitboxes = new Hitbox[] {
				new Hitbox( Tile.SIZE, -Tile.SIZE, Tile.SIZE * 2, Tile.SIZE * 2),
				new Hitbox(-Tile.SIZE * 2, -Tile.SIZE, Tile.SIZE * 2, Tile.SIZE * 2)
		};
		
		position = new Vector2f();
		direction = 0;

		hitFxSprite = new AnimatedSprite("fx/hit_animation.png", 1, 5, 128, 228);
		hitFxSprite.setTimePerFrame(0.025f);
		hitFxSprite.setCurrentState(State.IDLE);
		hitFxSprite.setStateData(State.IDLE, State.IDLE, -1, -1);
		hitFxSprite.setStateData(State.PLAY, State.IDLE, 0, 5);
	}
	
	@Override
	public void update(float deltaTime) {
		hitFxSprite.update(deltaTime);

		attackTime += deltaTime;
	}
	
	@Override
	public void render() {
		Renderer.setRenderOffset(16, -Tile.SIZE - 5);
		Renderer.drawSprite(sprite,
				position.x,
				position.y,
				Tile.SIZE, Tile.SIZE * 2);
		
		hitFxSprite.setReverse(direction == 1);
		hitFxSprite.render(
				hitboxes[direction].getBound().x,
				hitboxes[direction].getBound().y,
				hitboxes[direction].getBound().width,
				hitboxes[direction].getBound().height,
				0.f);
	}
	
	@Override
	public void attack() {
		attackDamage = BASE_ATTACK_DAMAGE + 3f * (level - 1);
		attackCooldownTime = BASE_ATTACK_COOLDOWN_TIME - 0.1f * (level - 1);
		for (int i = 0; i < hitboxes.length; i++) {
			hitboxes[i].setSize(4 * (level - 1));
		}
		
		if (attackTime >= attackCooldownTime) {
			attackTime -= attackCooldownTime;

			ArrayList<Entity> entityLists = CollisionManager.getInstance().isColliding(hitboxes[direction].getBound());
			entityLists.forEach((e) -> {
				if (e instanceof HostileEntity) e.takeDamge(attackDamage);
			});
			
			hitFxSprite.setCurrentState(State.PLAY);
			AudioMedia.SWING2.play();
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
