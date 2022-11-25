package core.entity;

import java.util.ArrayList;

import core.InputHandler;
import core.Renderer;
import core.sprite.Sprite;
import core.world.Tile;
import core.world.World;
import javafx.scene.input.KeyCode;
import util.Vector2;

public class Player extends BaseEntity {
	
	private Vector2 direction;
	
	private Sprite playerSprite;
	
	public Player(World world) {
		super("player", world);
		
		direction = new Vector2(0.f, 0.f);
		
		playerSprite = new Sprite("player.png");
	}
	
	@Override
	public void update() {
		calculateDirection();
		
//		Renderer.setFill(Color.LIGHTGREEN);
//		Renderer.fillRect(position, Tile.SIZE, Tile.SIZE);
		
		Renderer.drawSprite(playerSprite, position.subtract(new Vector2(0, Tile.SIZE)), Tile.SIZE, Tile.SIZE * 2, 0, 0, 64, 128);
		
		super.drawHealthBar();
	}

	@Override
	public void onHit(ArrayList<BaseEntity> entities) {
		
	}
	
	private void calculateDirection() {
		float dirX = 0;
		dirX += (InputHandler.onKeyPressed(KeyCode.A)) ? -1.f : 0.f;
		dirX += (InputHandler.onKeyPressed(KeyCode.D)) ?  1.f : 0.f;
		direction.x = dirX;

		float dirY = 0;
		dirY += (InputHandler.onKeyPressed(KeyCode.W)) ? -1.f : 0.f;
		dirY += (InputHandler.onKeyPressed(KeyCode.S)) ?  1.f : 0.f;
		direction.y = dirY;
		
		move(direction);
	}

}
