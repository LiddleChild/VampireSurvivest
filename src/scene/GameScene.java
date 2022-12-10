package scene;

import core.behavior.BehaviorManager;
import core.ui.UpgradeWindow;
import core.ui.components.Label;
import core.ui.components.Position;
import core.ui.components.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.GameState;
import logic.Window;
import util.ColorUtil;

/*
 * 
 * TODO:
 * - separate update method into: update(float), render(float) to implement pausing
 * - Upgrade UI on level up
 * - REFAC: GameLogic
 * 
 */

public class GameScene extends BaseScene {

	private Color backgroundColor = new Color(43.0 / 255.0, 41.0 / 255.0, 41.0 / 255.0, 1.0);
	private ProgressBar expBar;
	private Label level;
	
	private UpgradeWindow upgrade;
	
	public GameScene(String ID, Stage stage) {
		super(ID, stage);
		
		expBar = new ProgressBar(0, 0, Window.WINDOW_WIDTH, 25, 100);
		expBar.setForegroundColor(ColorUtil.parseRGB2Color(86, 152, 204));
		expBar.setBackgroundColor(ColorUtil.parseRGB2Color(34, 34, 34));
		expBar.setBorderSize(2);
		expBar.setMaxProgress(GameLogic.getInstance().getMaxExp());
		expBar.setProgress(GameLogic.getInstance().getExp());
		
		level = new Label("Level 1", Window.WINDOW_WIDTH - 10, 10);
		level.setColor(Color.WHITE);
		level.setFontSize(15);
		level.setPosition(Position.RIGHT);
		level.setShadowColor(Color.BLACK);
		level.setTextShadow(true);
		level.setShadowOffset(2);
		
		upgrade = new UpgradeWindow();
	}

	@Override
	public void update(float deltaTime) {
		// Clear screen
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		// Call update
		if (GameLogic.getInstance().getGameState() == GameState.PLAY)
			BehaviorManager.getInstance().update(deltaTime);
		
		BehaviorManager.getInstance().render(deltaTime);
		
		/*
		 * UI
		 */
		if (GameLogic.getInstance().getGameState() == GameState.UPGRADE) {
			upgrade.update(deltaTime);
		}
		
		expBar.setMaxProgress(GameLogic.getInstance().getMaxExp());
		expBar.setProgress(GameLogic.getInstance().getExp());
		expBar.update(deltaTime);
		
		level.setText(String.format("Level %d", GameLogic.getInstance().getLevel()));
		level.update(deltaTime);
	}
	
}