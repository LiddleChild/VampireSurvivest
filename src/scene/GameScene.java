package scene;

import core.behavior.BehaviorManager;
import core.ui.components.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.Window;
import util.ColorUtil;

public class GameScene extends BaseScene {

	private Color backgroundColor = new Color(43.0 / 255.0, 41.0 / 255.0, 41.0 / 255.0, 1.0);
	private ProgressBar expBar;
	
	public GameScene(String ID, Stage stage) {
		super(ID, stage);
		
		expBar = new ProgressBar(0, 0, Window.WINDOW_WIDTH, 20, 100);
		expBar.setForegroundColor(ColorUtil.parseRGB2Color(86, 152, 204));
		expBar.setBackgroundColor(ColorUtil.parseRGB2Color(34, 34, 34));
		expBar.setBorderSize(2);
		expBar.setMaxProgress(GameLogic.getInstance().getMaxExp());
		expBar.setProgress(GameLogic.getInstance().getExp());
	}

	@Override
	public void update(float deltaTime) {
		// Clear screen
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		// Call update
		BehaviorManager.getInstance().update(deltaTime);
		
		// Update experience bar
		expBar.setTargetProgress(GameLogic.getInstance().getExp());
		expBar.update(deltaTime);
	}
	
}