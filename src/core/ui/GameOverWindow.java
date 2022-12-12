package core.ui;

import core.ui.components.UIComponent;
import javafx.scene.paint.Color;
import logic.Window;

public class GameOverWindow extends UIComponent {

	private Color backgroundColor = new Color(1, 0.1f, 0.1f, 0.5f);
	
	@Override
	public void update(float deltaTime) {
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
	}

}
