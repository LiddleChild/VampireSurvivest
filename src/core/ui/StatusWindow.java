package core.ui;

import core.ui.components.Label;
import core.ui.components.Position;
import core.ui.components.SubWindow;
import core.ui.components.UIComponent;
import core.world.Tile;
import core.world.World;
import javafx.scene.paint.Color;
import logic.Window;

public class StatusWindow extends UIComponent {

	private SubWindow window;
	
	private Label playerTitle, playerStat;
	
	public StatusWindow() {
		window = new SubWindow(100, Window.WINDOW_HEIGHT / 2, 200, 300);
		
		playerTitle = new Label("Player Status", 10, 150);
		playerTitle.setColor(Color.WHITE);
		playerTitle.setFontSize(16);
		playerTitle.setPosition(Position.LEFT);

		playerStat = new Label("", 10, 170);
		playerStat.setColor(Color.WHITE);
		playerStat.setFontSize(12);
		playerStat.setPosition(Position.LEFT);
	}
	
	@Override
	public void update(float deltaTime) {
		playerStat.setText(String.format("Movement Speed: %.1f\nMax Health: %.1f",
				World.getPlayer().getMovementSpeed() / Tile.SIZE, World.getPlayer().getMaxHealth()));
		
		window.update(deltaTime);
		
		playerTitle.update(deltaTime);
		playerStat.update(deltaTime);
	}

}
