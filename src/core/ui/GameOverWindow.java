package core.ui;

import core.audio.AudioMedia;
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
 * GameOverWindow
 * - Render game over overlay
 * - A button back to main menu
 * 
 */

public class GameOverWindow extends UIComponent {
	
	private Color backgroundColor;
	private Label text;
	private Button quit;
	
	public GameOverWindow() {
		backgroundColor = new Color(1, 0.2f, 0.2f, 0.65f);
				
		text = new Label("GAME OVER",
				Window.WINDOW_WIDTH / 2,
				Window.WINDOW_HEIGHT / 2 - 100);
		text.setColor(ColorUtil.parseRGBToColor(243, 186, 10));
		text.setFontSize(50);
		text.setTextShadow(true);
		text.setShadowColor(Color.BLACK);

		quit = new Button("QUIT", Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + 100, 180, 50);
		quit.getBound().setBackgroundColor(ColorUtil.parseRGBToColor(34, 34, 34));
		quit.getBound().setBorderColor(ColorUtil.parseRGBToColor(255, 204, 104));
		quit.getBound().setBorderSize(2);
		quit.getLabel().setColor(Color.WHITE);
		quit.setOnClick(() -> {
			GameLogic.getInstance().initializeGameState();
			GameLogic.getInstance().setCurrentScene(1);
		});
		
		GameLogic.getInstance().setOnGameStateChangeTo(GameState.GAME_OVER, () -> {
			AudioMedia.BGM_BATTLE.stop();
			
			AudioMedia.GAME_OVER.play();
		});
	}
	
	@Override
	public void update(float deltaTime) {
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		text.update(deltaTime);
		quit.update(deltaTime);
	}

}
