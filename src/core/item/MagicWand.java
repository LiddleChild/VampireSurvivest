package core.item;

import java.util.ArrayList;

import core.Renderer;
import core.audio.AudioMedia;
import core.collision.CollisionManager;
import core.effect.GreenForceWave;
import core.entity.Entity;
import core.entity.HostileEntity;
import core.sprite.Sprite;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import core.world.Tile;
import core.world.World;
import util.math.Vector2f;

public class MagicWand extends Item {
	
	private Sprite sprite;
	private AnimatedSprite hitFxSprite;
	
	private Vector2f position;
	private ArrayList<GreenForceWave> forceWaveLists;
	
	private float baseAttackRange, attackRange;
	
	public MagicWand() {
		super("Magic Wand", "item/magic_wand.png", 10.f, 1.25f);
		sprite = new Sprite(spritePath);
	
		baseAttackRange = 5.f;
		forceWaveLists = new ArrayList<GreenForceWave>();
		
		position = new Vector2f();

		hitFxSprite = new AnimatedSprite("fx/hit_animation.png", 1, 5, 128, 228);
		hitFxSprite.setFrameTime(0.03f);
		hitFxSprite.setState(State.IDLE);
		hitFxSprite.setStateIntervals(State.IDLE, State.IDLE, -1, -1);
		hitFxSprite.setStateIntervals(State.PLAY, State.IDLE, 0, 5);
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
			if (!Renderer.checkInsideWindow(
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
		forceWaveLists.clear();
	}
	
	@Override
	public void attack() {
		attackRange = baseAttackRange + 0.5f * (level - 1);
		attackDamage = baseAttackDamage + 2.5f * (level - 1);
		attackCooldownTime = baseAttackCooldownTime - 0.1f * (level - 1);
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
			
			int size = Math.min((level + 1) / 3, temp.size());
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