package core.entity;

import core.Camera;
import core.Renderer;
import core.world.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import util.InputHandler;
import util.math.Vector2;

public class Player extends BaseEntity {
	
	private Vector2 direction;
	
	public Player() {
		super("player", 1);
		
		direction = new Vector2(0.f, 0.f);
	}
	
	@Override
	public void update() {
		calculateDirection();
		
		Renderer.setFill(Color.LIGHTGREEN);
		Renderer.fillRect(position, Map.GRID_SIZE, Map.GRID_SIZE);
		
		Camera.getInstance().interpolate(position);
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
