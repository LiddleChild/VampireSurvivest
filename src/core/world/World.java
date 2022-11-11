package core.world;

import core.Renderer;
import core.behavior.GameBehavior;
import javafx.scene.paint.Color;

public class World extends GameBehavior {
	
	public World() {
		super();
	}
	
	@Override
	public void update() {
		for (int x = 0; x < Map.MAP_WIDTH; x++) {
			for (int y = 0; y < Map.MAP_HEIGHT; y++) {
				if (Map.getInstance().tileMaps[x][y]) {
					Renderer.setFill(Color.DARKRED);
				}
				else if ((x + y) % 2 == 0) {					
					Renderer.setFill(Color.DARKGREY);
				} else {
					Renderer.setFill(Color.GREY);
				}
				
				Renderer.fillRect(x * Map.GRID_SIZE, y * Map.GRID_SIZE, Map.GRID_SIZE, Map.GRID_SIZE);
			}
		}
	}

}
