package core.entity;

import core.Camera;
import core.Renderer;
import core.world.World;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import util.math.Mathf;

public class Player extends BaseEntity implements EventHandler<KeyEvent> {

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
		float[] unitDir = Mathf.unitVector(dirX, dirY);
		
		x += unitDir[0] * speed * deltaTime;
		y += unitDir[1] * speed * deltaTime;
		
		Renderer.setFill(Color.LIGHTGREEN);
		Renderer.fillRect(x, y, World.GRID_SIZE, World.GRID_SIZE);
		
		Camera.getInstance().interpolate(x, y, deltaTime);
	}

	@Override
	public void handle(KeyEvent e) {
		if (e.getEventType() == KeyEvent.KEY_PRESSED) {
			switch (e.getCode()) {
			case W:
				dirY = -1;
				break;
			case S:
				dirY = 1;
				break;
			case A:
				dirX = -1;
				break;
			case D:
				dirX = 1;
				break;
			default:
				break;
			}
		} else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
			switch (e.getCode()) {
			case W:
				dirY = 0;
				break;
			case S:
				dirY = 0;
				break;
			case A:
				dirX = 0;
				break;
			case D:
				dirX = 0;
				break;
			default:
				break;
			}
		}
	}

}
