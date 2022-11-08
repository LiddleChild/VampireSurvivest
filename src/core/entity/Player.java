package core.entity;

import core.Camera;
import core.Renderer;
import core.world.World;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import util.InputHandler;
import util.math.Mathf;

public class Player extends BaseEntity {

	private float x, y;
	private float dirX = 0, dirY = 0;
	private float speed = World.GRID_SIZE * 4.f;
	
	public Player() {
		super("player", 1);

		x = World.getSpawnPointX();
		y = World.getSpawnPointY();
	}
	
	@Override
	public void update() {
		movement();
		
		float[] unitDir = Mathf.unitVector(dirX, dirY);
		
		x += unitDir[0] * speed * deltaTime;
		y += unitDir[1] * speed * deltaTime;
		
		Renderer.setFill(Color.LIGHTGREEN);
		Renderer.fillRect(x, y, World.GRID_SIZE, World.GRID_SIZE);
		
		Camera.getInstance().interpolate(x, y, deltaTime);
	}
	
	private void movement() {
		dirX = 0;
		dirX += (InputHandler.onKeyPressed(KeyCode.A)) ? -1.f : 0.f;
		dirX += (InputHandler.onKeyPressed(KeyCode.D)) ?  1.f : 0.f;

		dirY = 0;
		dirY += (InputHandler.onKeyPressed(KeyCode.W)) ? -1.f : 0.f;
		dirY += (InputHandler.onKeyPressed(KeyCode.S)) ?  1.f : 0.f;
	}

}
