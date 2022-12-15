package core.game.entity;

import java.awt.Rectangle;

import core.game.item.Item;
import core.game.world.Tile;
import core.inputHandler.KeyboardHandler;
import core.sprite.animation.AnimatedSprite;
import core.sprite.animation.AnimationState.State;
import javafx.scene.input.KeyCode;
import logic.GameLogic;
import logic.GameState;
import logic.PlayerCharacter;
import util.Vector2f;

/*
 * 
 * Player
 * - initialize with selected PlayerCharacter
 * - move with KeyboardInput
 * 
 */

public class Player extends Entity {
	
	private Vector2f direction;
	private AnimatedSprite sprite;
	
	private Item item;
	
	private int blinkPeriod, maxBlink, blinkTime;
	
	public Player() {
		super("player");
		
		PlayerCharacter c = GameLogic.getInstance().getCharacter();
		item = c.getItem();
		item.reset();
		setMovementSpeed(c.getMovementSpeed());
		
		direction = new Vector2f(0.f, 0.f);

		blinkPeriod = 25;
		maxBlink = 6;
		blinkTime = 0;
		
		bound = new Rectangle(0, 0, 28, Tile.SIZE);
		
		sprite = new AnimatedSprite(c.getSpritePath(), 1, 5, 64, 128);
		sprite.setOffset(new Vector2f(0, -Tile.SIZE)
				.add(new Vector2f(
						(bound.width  - Tile.SIZE) / 2,
						(bound.height - Tile.SIZE) / 2)));
	}
	
	@Override
	public void update() {
		calculateDirection();
		
		item.setPosition(position);
		item.setDirection(direction);
		item.attack();
		
		if (blinkTime > 0) {
			if (blinkTime == maxBlink * blinkPeriod) blinkTime = 0;
			else blinkTime++;
		}

		if (direction.x < 0) sprite.setReverse(true);
		else if (direction.x > 0) sprite.setReverse(false);
		
		sprite.setCurrentState((direction.isZero()) ? State.IDLE : State.PLAY);
		sprite.update(deltaTime);
		
		item.update(deltaTime);
	}

	@Override
	public void render() {
		// Draw item
		item.render();
		
		// Draw player
		if (blinkTime % (2 * blinkPeriod) <= blinkPeriod) {
			sprite.render(position, Tile.SIZE, Tile.SIZE * 2, 0.f);
		}
	
		// Draw health bar
		super.drawHealthBar();
	}

	@Override
	protected void onTakingDamage() {
		blinkTime = 1;
	}

	@Override
	protected void onDeath() {
		GameLogic.getInstance().setGameState(GameState.GAME_OVER);
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
	
	/*
	 * GETTERS & SETTERS
	 */
	public Item getItem() {
		return item;
	}

}
