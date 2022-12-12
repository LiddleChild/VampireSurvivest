package core.ui;

import core.audio.AudioMedia;
import core.ui.components.Button;
import core.ui.components.ButtonEventHandler;
import core.ui.components.Label;
import core.ui.components.UIComponent;
import javafx.scene.paint.Color;
import logic.GameLogic;
import logic.GameState;
import logic.Window;
import util.ColorUtil;

public class GameOverWindow extends UIComponent {
	
	private Color backgroundColor = new Color(1, 0.2f, 0.2f, 0.65f);
	private Label text;
	private Button back;
	
	public GameOverWindow() {
		text = new Label("GAME OVER",
				Window.WINDOW_WIDTH / 2,
				Window.WINDOW_HEIGHT / 2 - 100);
		text.setColor(ColorUtil.parseRGB2Color(243, 186, 10));
		text.setFontSize(50);
		text.setTextShadow(true);
		text.setShadowColor(Color.BLACK);

		
		back = new Button("QUIT", Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + 100, 180, 50);
		back.getBound().setBackgroundColor(ColorUtil.parseRGB2Color(34, 34, 34));
		back.getBound().setBorderColor(ColorUtil.parseRGB2Color(255, 204, 104));
		back.getBound().setBorderSize(2);
		back.getLabel().setColor(Color.WHITE);
		back.setOnClick(new ButtonEventHandler() {
			@Override
			public void onClick() {
				GameLogic.getInstance().initializeGameState();
				GameLogic.getInstance().setCurrentScene(1);
			}
		});
		
		GameLogic.getInstance().setOnGameStateChangeTo(GameState.GAME_OVER, () -> AudioMedia.GAME_OVER.play());
	}
	
	@Override
	public void update(float deltaTime) {
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		text.update(deltaTime);
		back.update(deltaTime);
	}

}
