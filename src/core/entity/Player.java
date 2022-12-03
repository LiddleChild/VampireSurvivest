package core.entity;

import core.Renderer;
import core.collision.CollisionManager;
import core.inputHandler.KeyboardHandler;
import core.inputHandler.MouseHandler;
import core.item.Sword;
import core.sprite.AnimatedSprite;
import core.sprite.AnimationState.State;
import core.world.Tile;
import core.world.World;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import logic.Window;
import util.Vector2f;

public class Player extends Entity {
	
	private Vector2f direction;
	private Vector2f middleScreen;
	
	private AnimatedSprite playerSprite;
//	private AnimatedSprite hitFxSprite;
	
	private Sword sword;
	
	public Player(World world) {
		super("player", world);
		
		sword = new Sword();
		
		middleScreen = new Vector2f(Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2);
		direction = new Vector2f(0.f, 0.f);
		
		playerSprite = new AnimatedSprite("player.png", 1, 5, 64, 128);
		playerSprite.setOffset(new Vector2f(0, -Tile.SIZE));
		
//		hitFxSprite = new AnimatedSprite("hit_animation.png", 1, 5, 35, 32);
//		hitFxSprite.setFrameTime(0.1f);
//		hitFxSprite.setState(State.IDLE);
//		hitFxSprite.setStateIntervals(State.IDLE, State.IDLE, -1, -1);
//		hitFxSprite.setStateIntervals(State.PLAY, State.IDLE, 0, 5);
	}
	
	@Override
	public void update() {
		calculateDirection();
		sword.setPosition(position);
		
		if (direction.x < 0) {
			playerSprite.setReverse(true);
		} else if (direction.x > 0) {
			playerSprite.setReverse(false);
		}
		
		playerSprite.setState((direction.isZero()) ? State.IDLE : State.PLAY);
		playerSprite.draw(position, Tile.SIZE, Tile.SIZE * 2, deltaTime, 0.f);
		
		super.drawHealthBar();
		
		if (MouseHandler.isMouseDown(MouseButton.PRIMARY)) {
			boolean[] hitDirs = middleScreen.getDirection(MouseHandler.getMousePosition());
			for (int i = 0; i < hitDirs.length; i++) {
				if (hitDirs[i]) {
					sword.attack(CollisionManager.getInstance().isColliding(this, sword.getHitboxes()[i]));
					
					Renderer.setFill(new Color(1.f, 0.f, 0.f, 0.5f));
					Renderer.fillRect(sword.getHitboxes()[i]);
				}
			}
		}
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
