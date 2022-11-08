package core.world;

import core.Renderer;
import core.behavior.GameBehavior;
import javafx.scene.paint.Color;

public class World extends GameBehavior {
	public static final int GRID_SIZE = 48;
	public static final int WORLD_WIDTH = 15;
	public static final int WORLD_HEIGHT = 15;
	
	private static final int SPAWN_POINT_X = 7;
	private static final int SPAWN_POINT_Y = 7;
	
	public World() {
		super();
	}
	
	@Override
	public void update() {
		for (int x = 0; x < WORLD_WIDTH; x++) {
			for (int y = 0; y < WORLD_HEIGHT; y++) {
				if ((x + y) % 2 == 0) {					
					Renderer.setFill(Color.DARKGREY);
				} else {
					Renderer.setFill(Color.GREY);
				}
				
				Renderer.fillRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE);
			}
		}
	}

	public static int getSpawnPointX() {
		return SPAWN_POINT_X * GRID_SIZE;
	}

	public static int getSpawnPointY() {
		return SPAWN_POINT_Y * GRID_SIZE;
	}

}
