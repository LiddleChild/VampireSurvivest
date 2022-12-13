package core.ui;

import core.ui.component.Button;
import core.ui.component.Label;
import core.ui.component.UIComponent;
import javafx.scene.paint.Color;
import logic.GameLogic;
import logic.GameState;
import logic.Window;
import util.ColorUtil;

/*
 * 
 * PauseWindow
 * - A pause window overlay
 * - Back to game button
 * - Quit to main menu button
 * 
 */

public class PauseWindow extends UIComponent {
	
	private Color backgroundColor;
	private Label text;
	private Button quit, back;
	
	public PauseWindow() {
		backgroundColor = new Color(0, 0, 0, 0.5f);
		
		text = new Label("PAUSE",
				Window.WINDOW_WIDTH / 2,
				Window.WINDOW_HEIGHT / 2 - 100);
		text.setColor(Color.WHITE);
		text.setFontSize(50);
		text.setTextShadow(true);
		text.setShadowColor(Color.BLACK);

		back = new Button("BACK", Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + 100, 180, 50);
		back.getBound().setBackgroundColor(ColorUtil.parseRGBToColor(34, 34, 34));
		back.getBound().setBorderColor(ColorUtil.parseRGBToColor(255, 204, 104));
		back.getBound().setBorderSize(2);
		back.getLabel().setColor(Color.WHITE);
		back.setOnClick(() -> {
			GameLogic.getInstance().setGameState(GameState.PLAY);
		});

		quit = new Button("QUIT", Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + 160, 180, 50);
		quit.getBound().setBackgroundColor(ColorUtil.parseRGBToColor(34, 34, 34));
		quit.getBound().setBorderColor(ColorUtil.parseRGBToColor(255, 204, 104));
		quit.getBound().setBorderSize(2);
		quit.getLabel().setColor(Color.WHITE);
		quit.setOnClick(() -> {
			GameLogic.getInstance().initializeGameState();
			GameLogic.getInstance().setCurrentScene(1);
		});
	}
	
	@Override
	public void update(float deltaTime) {
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		text.update(deltaTime);
		quit.update(deltaTime);
		back.update(deltaTime);
	}

}
