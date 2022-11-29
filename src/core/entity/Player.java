package core.entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import core.Renderer;
import core.collision.CollisionManager;
import core.inputHandler.KeyboardHandler;
import core.inputHandler.MouseHandler;
import core.sprite.AnimatedSprite;
import core.sprite.Sprite;
import core.world.Tile;
import core.world.World;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import logic.Window;
import util.Vector2f;

public class Player extends Entity {
	
	private Vector2f direction;
	private Vector2f middleScreen;
	
//	private Sprite playerSprite;
	private AnimatedSprite playerSprite;
	
	private Rectangle[] hitboxes;
	
	public Player(World world) {
		super("player", world);
		
		middleScreen = new Vector2f(Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2);
		direction = new Vector2f(0.f, 0.f);
		
		hitboxes = new Rectangle[] {
				new Rectangle((int) position.x, (int) position.y - Tile.SIZE, Tile.SIZE, Tile.SIZE),
				new Rectangle((int) position.x + Tile.SIZE, (int) position.y, Tile.SIZE, Tile.SIZE),
				new Rectangle((int) position.x, (int) position.y + Tile.SIZE, Tile.SIZE, Tile.SIZE),
				new Rectangle((int) position.x - Tile.SIZE, (int) position.y, Tile.SIZE, Tile.SIZE)
		};
		
		playerSprite = new AnimatedSprite("player.png", 5, 64, 128);
//		playerSprite = new Sprite("player.png");
		
		attackDamage = 25.f;
		attackCooldownTime = 0.5f;
	}
	
	@Override
	public void update() {
		calculateDirection();
		updateHitboxes();
		
//		Renderer.setFill(Color.LIGHTGREEN);
//		Renderer.fillRect(position, Tile.SIZE, Tile.SIZE);
		
//		Renderer.drawSprite(playerSprite, position.subtract(new Vector2f(0, Tile.SIZE)), Tile.SIZE, Tile.SIZE * 2, 0, 0, 64, 128);
		
		if (direction.x < 0) {
			playerSprite.setReverse(true);
		} else if (direction.x > 0) {
			playerSprite.setReverse(false);
		}
		
		playerSprite.draw(position, Tile.SIZE, Tile.SIZE * 2);
		
		super.drawHealthBar();
		attackTime += deltaTime;
		
		if (MouseHandler.onKeyPressed(MouseButton.PRIMARY)) {
			boolean[] hitDirs = middleScreen.getDirection(MouseHandler.getMousePosition());
			for (int i = 0; i < hitDirs.length; i++) {
				if (hitDirs[i]) {
					ArrayList<Entity> hits = CollisionManager.getInstance().isColliding(this, hitboxes[i]);
					for (Entity e : hits) attack(e);
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
	
	private void updateHitboxes() {
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
