package core.game.item;

import java.util.ArrayList;

import core.Camera;
import core.Renderer;
import core.audio.AudioMedia;
import core.collision.CollisionManager;
import core.game.effect.GreenForceWave;
import core.game.entity.Entity;
import core.game.entity.HostileEntity;
import core.game.world.Tile;
import core.game.world.World;
import core.sprite.Sprite;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import util.Vector2f;

/*
 * 
 * MagicWand
 * - Create a ForceWave to the nearest enemy
 * 
 */

public class MagicWand extends Item {
	
	private Sprite sprite;
	private AnimatedSprite hitFxSprite;
	
	private Vector2f position;
	private ArrayList<GreenForceWave> forceWaveLists;
	
	private final float BASE_ATTACK_RANGE;;
	private float attackRange;
	
	public MagicWand() {
		super("Magic Wand", "item/magic_wand.png", 10.f, 1.25f);
		sprite = new Sprite(SPRITE_PATH);
	
		BASE_ATTACK_RANGE = 5.f;
		forceWaveLists = new ArrayList<GreenForceWave>();
		
		position = new Vector2f();

		hitFxSprite = new AnimatedSprite("fx/hit_animation.png", 1, 5, 128, 228);
		hitFxSprite.setTimePerFrame(0.03f);
		hitFxSprite.setCurrentState(State.IDLE);
		hitFxSprite.setStateData(State.IDLE, State.IDLE, -1, -1);
		hitFxSprite.setStateData(State.PLAY, State.IDLE, 0, 5);
	}
	
	@Override
	public void update(float deltaTime) {
		attackTime += deltaTime;
		
		ArrayList<GreenForceWave> deleteLists = new ArrayList<GreenForceWave>();
		
		hitFxSprite.update(deltaTime);
		for (GreenForceWave fw : forceWaveLists) {
			fw.update(deltaTime);

			// Check for hit entity and damage it
			ArrayList<Entity> entityLists = CollisionManager.getInstance().isColliding(fw.getBound());
			for (Entity e : entityLists) {
				if (e instanceof HostileEntity) {					
					e.takeDamge(attackDamage);
					deleteLists.add(fw);
				}
			}
			
			// Delete force wave if it's outside rendering window
			if (!Camera.getInstance().isInCameraView(
					fw.getBound().x,
					fw.getBound().y,
					fw.getBound().width,
					fw.getBound().height)) {
				deleteLists.add(fw);
			}
		}

		forceWaveLists.removeAll(deleteLists);
	}
	
	@Override
	public void render() {
		Renderer.setRenderOffset(16, -Tile.SIZE + 5);
		Renderer.drawSprite(sprite,
				position.x,
				position.y,
				Tile.SIZE, Tile.SIZE * 2);
		
		for (GreenForceWave fw : forceWaveLists) {
			fw.render();
		}
	}

	@Override
	public void reset() {
		super.reset();
		forceWaveLists.clear();
	}
	
	@Override
	public void attack() {
		attackRange = BASE_ATTACK_RANGE + 0.5f * (level - 1);
		attackDamage = BASE_ATTACK_DAMAGE + 2.5f * (level - 1);
		attackCooldownTime = BASE_ATTACK_COOLDOWN_TIME - 0.1f * (level - 1);
		int waveSize = Tile.SIZE + (level - 1) * 4;
		
		ArrayList<Entity> temp = new ArrayList<Entity>();
		for (Entity e : World.getInstance().getEnemyLists()) {
			Vector2f d = e.getPosition().subtract(position);
			
			if (d.getSize() <= attackRange * Tile.SIZE) {
				temp.add(e);
			}
		}
		
		if (temp.size() > 0 && attackTime >= attackCooldownTime) {
			attackTime = 0.f;
			AudioMedia.FORCE_WAVE.play();
			
			int size = Math.min((level + 2) / 3, temp.size());
			for (int i = 0; i < size; i++) {
				Vector2f dist = temp.get(i).getPosition().subtract(position);
				forceWaveLists.add(
						new GreenForceWave(position, waveSize, (float) (Math.atan2(dist.y, dist.x) / Math.PI * 180.f)));
			}
		}
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	@Override
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	@Override
	public void setDirection(Vector2f direction) {
		
	}

}