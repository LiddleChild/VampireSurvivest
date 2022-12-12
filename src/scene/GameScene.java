package scene;

import core.behavior.BehaviorManager;
import core.ui.GameOverWindow;
import core.ui.StatusWindow;
import core.ui.UpgradeWindow;
import core.ui.components.Label;
import core.ui.components.Position;
import core.ui.components.ProgressBar;
import core.world.World;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.GameState;
import logic.Window;
import util.ColorUtil;

public class GameScene extends BaseScene {

	private Color backgroundColor = ColorUtil.parseRGB2Color(43, 41, 41);
	private ProgressBar expBar;
	private Label level;
	
	private UpgradeWindow upgradeWindow;
	private StatusWindow statusWindow;
	private GameOverWindow gameOverWindow;
	
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
		
		upgradeWindow = new UpgradeWindow();
		statusWindow = new StatusWindow();
		gameOverWindow = new GameOverWindow();
	}
	
	@Override
	public void init() {
		// Initialize world
		new World();
		
		// Call init
		BehaviorManager.getInstance().init();
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
		
		// UPGRADE
		if (GameLogic.getInstance().getGameState() == GameState.UPGRADE) {
			upgradeWindow.update(deltaTime);
			statusWindow.update(deltaTime);
		}
		
		expBar.setMaxProgress(GameLogic.getInstance().getMaxExp());
		expBar.setProgress(GameLogic.getInstance().getExp());
		expBar.update(deltaTime);
		
		level.setText(String.format("Level %d", GameLogic.getInstance().getLevel()));
		level.update(deltaTime);

		// GAME OVER
		if (GameLogic.getInstance().getGameState() == GameState.GAME_OVER) {
			gameOverWindow.update(deltaTime);
		}
	}
	
}