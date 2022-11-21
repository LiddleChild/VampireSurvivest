package core.entity;

import core.InputHandler;
import core.Renderer;
import core.world.Tile;
import core.world.World;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import util.Vector2;

public class Player extends BaseEntity {
	
	private Vector2 direction;
	
	public Player(World world) {
		super("player", 3, world);
		
		direction = new Vector2(0.f, 0.f);
	}
	
	@Override
	public void update() {
		calculateDirection();
		
		Renderer.setFill(Color.LIGHTGREEN);
		Renderer.fillRect(position, Tile.SIZE, Tile.SIZE);
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
