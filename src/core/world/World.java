package core.world;

import core.GameBehavior;
import core.entity.Player;
import javafx.scene.paint.Color;

public class World extends GameBehavior {
	public static final int GRID_SIZE = 48;
	
	public World() {
		super();
	}
	
	@Override
	public void update() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if ((x + y) % 2 == 0) {					
					gc.setFill(Color.DARKGREY);
				} else {
					gc.setFill(Color.GREY);
				}
				
				gc.fillRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE);
			}
		}
	}

}
