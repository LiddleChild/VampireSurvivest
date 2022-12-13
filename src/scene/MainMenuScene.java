package scene;

import core.audio.AudioMedia;
import core.sprite.Sprite;
import core.ui.component.Button;
import core.ui.component.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.GameState;
import logic.Window;
import util.ColorUtil;

/*
 * 
 * MainMenuScene
 * - plays background music
 * - start button
 * 
 */

public class MainMenuScene extends BaseScene {

	private Label title;
	private Sprite background;
	private Button startButton, exitButton;
	
	public MainMenuScene(String ID, Stage stage) {
		super(ID, stage);
		
		title = new Label("VAMPIRE SURVIVEST",
				Window.WINDOW_WIDTH / 2,
				Window.WINDOW_HEIGHT / 2 - 100);
		title.setFontSize(45);
		title.setColor(ColorUtil.parseRGBToColor(243, 186, 10));
		title.setShadowColor(Color.WHITE);
		title.setTextShadow(true);
		
		startButton = new Button("START", Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + 100, 180, 50);
		startButton.getBound().setBackgroundColor(ColorUtil.parseRGBToColor(34, 34, 34));
		startButton.getBound().setBorderColor(ColorUtil.parseRGBToColor(255, 204, 104));
		startButton.getBound().setBorderSize(2);
		startButton.getLabel().setColor(Color.WHITE);
		startButton.setOnClick(() -> GameLogic.getInstance().nextScene());

		exitButton = new Button("EXIT", Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + 160, 180, 50);
		exitButton.getBound().setBackgroundColor(ColorUtil.parseRGBToColor(34, 34, 34));
		exitButton.getBound().setBorderColor(ColorUtil.parseRGBToColor(255, 204, 104));
		exitButton.getBound().setBorderSize(2);
		exitButton.getLabel().setColor(Color.WHITE);
		exitButton.setOnClick(() -> GameLogic.getInstance().setGameState(GameState.EXIT));
		
		background = new Sprite("mainmenu_background.jpg");
	}
	
	@Override
	public void onLoadScene() {
		AudioMedia.BGM.play();
		AudioMedia.BGM.setRepeat(true);
		AudioMedia.BGM.setVolume(0.25f);
	}

	@Override
	public void update(float deltaTime) {
		gc.drawImage(background.getImage(), 0, 0);
		
		title.update(deltaTime);
		startButton.update(deltaTime);
		exitButton.update(deltaTime);
	}
	
}
