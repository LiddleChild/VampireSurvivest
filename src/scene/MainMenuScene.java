package scene;

import core.sprite.Sprite;
import core.ui.components.Button;
import core.ui.components.ButtonEventHandler;
import core.ui.components.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.Window;
import util.ColorUtil;

public class MainMenuScene extends BaseScene {

	private Label title;
	private Sprite background;
	private Button startButton;
	
	public MainMenuScene(String ID, Stage stage) {
		super(ID, stage);
		
		title = new Label("VAMPIRE SURVIVORS",
				Window.WINDOW_WIDTH / 2,
				Window.WINDOW_HEIGHT / 2 - 100);
		title.setFontSize(45);
		title.setColor(ColorUtil.parseRGB2Color(243, 186, 10));
		title.setShadowColor(Color.WHITE);
		title.setTextShadow(true);
		
		startButton = new Button("START",
				Window.WINDOW_WIDTH / 2,
				Window.WINDOW_HEIGHT / 2 + 100,
				150,
				50);
		startButton.setOnClick(new ButtonEventHandler() {
			@Override
			public void onClick() {
				GameLogic.getInstance().nextScene();
			}
		});
		
		this.background = new Sprite("mainmenu_background.jpg");
	}

	@Override
	public void update(float deltaTime) {
		gc.drawImage(background.getImage(), 0, 0);
		
		title.update(deltaTime);
		startButton.update(deltaTime);
	}
	
}
