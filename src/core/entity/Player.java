package core.entity;

import java.awt.Rectangle;

import core.Renderer;
import core.collision.CollisionManager;
import core.inputHandler.KeyboardHandler;
import core.inputHandler.MouseHandler;
import core.item.Sword;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import core.world.Tile;
import core.world.World;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import logic.Window;
import util.math.Vector2f;

public class Player extends Entity {
	
	private Vector2f direction;
	private Vector2f middleScreen;
	
	private AnimatedSprite sprite;
	
	private Sword sword;
	
	private int blinkPeriod, maxBlink, blinkTime;
	
	public Player(World world) {
		super("player", world);
		
		sword = new Sword();
		
		middleScreen = new Vector2f(Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2);
		direction = new Vector2f(0.f, 0.f);

		blinkPeriod = 25;
		maxBlink = 6;
		blinkTime = 0;
		
		bound = new Rectangle(0, 0, 28, Tile.SIZE);
		
		sprite = new AnimatedSprite("player.png", 1, 5, 64, 128);
		sprite.setOffset(new Vector2f(0, -Tile.SIZE)
				.add(new Vector2f(
						(bound.width  - Tile.SIZE) / 2,
						(bound.height - Tile.SIZE) / 2)));
	}
	
	@Override
	public void update() {
		calculateDirection();
		sword.setPosition(position);
		
		if (MouseHandler.isMouseDown(MouseButton.PRIMARY)) {
			boolean[] hitDirs = middleScreen.getDirection(MouseHandler.getMousePosition());
			for (int i = 0; i < hitDirs.length; i++) {
				if (hitDirs[i]) {
					sword.attack(CollisionManager.getInstance().isColliding(this, sword.getHitboxes()[i]));

//					Renderer.setFill(new Color(1.f, 0.f, 0.f, 0.5f));
//					Renderer.fillRect(sword.getHitboxes()[i]);
					
					sword.setDirection(i);
				}
			}
		}
		
		if (blinkTime > 0) {
			if (blinkTime == maxBlink * blinkPeriod) blinkTime = 0;
			else blinkTime++;
		}

		if (direction.x < 0) sprite.setReverse(true);
		else if (direction.x > 0) sprite.setReverse(false);
		
		sprite.setState((direction.isZero()) ? State.IDLE : State.PLAY);
		sprite.update(deltaTime);
		
		sword.updateFX(deltaTime);
	}

	@Override
	public void render() {
		// Draw sword
		Renderer.drawSprite(sword.getSprite(),
				position.x + Tile.SIZE / 2.5f,
				position.y,
				Tile.SIZE, Tile.SIZE);
		
		// Draw player
		if (blinkTime % (2 * blinkPeriod) <= blinkPeriod) {
			sprite.render(position, Tile.SIZE, Tile.SIZE * 2, 0.f);
		}
		
		// Draw sword FX
		sword.renderFx();
		
		// Draw health bar
		super.drawHealthBar();
	}

	@Override
	protected void onTakingDamage() {
		blinkTime = 1;
	}

	@Override
	protected void onDeath() {
		
	}
	
	private void calculateDirection() {
		float dirX = 0;
		dirX += (KeyboardHandler.onKeyPressed(KeyCode.A)) ? -1.f : 0.f;
		dirX += (KeyboardHandler.onKeyPressed(KeyCode.D)) ?  1.f : 0.f;
		direction.x = dirX;

		float dirY = 0;
		dirY += (KeyboardHandler.onKeyPressed(KeyCode.W)) ? -1.f : 0.f;
		dirY += (KeyboardHandler.onKeyPressed(KeyCode.S)) ?  1.f : 0.f;
		direction.y = dirY;
		
		move(direction);
	}

}
