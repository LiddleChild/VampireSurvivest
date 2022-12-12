package scene;

import core.Camera;
import core.audio.AudioMedia;
import core.behavior.BehaviorManager;
import core.collision.CollisionManager;
import core.inputHandler.KeyboardHandler;
import core.ui.GameOverWindow;
import core.ui.PauseWindow;
import core.ui.StatusWindow;
import core.ui.UpgradeWindow;
import core.ui.components.Label;
import core.ui.components.Position;
import core.ui.components.ProgressBar;
import core.world.World;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.GameState;
import logic.Window;
import util.ColorUtil;
import util.Time;

public class GameScene extends BaseScene {

	private Color backgroundColor = ColorUtil.parseRGB2Color(43, 41, 41);
	private ProgressBar expBar;
	private Label level, timer;
	private float lastTime;
	
	private UpgradeWindow upgradeWindow;
	private StatusWindow statusWindow;
	private GameOverWindow gameOverWindow;
	private PauseWindow pauseWindow;
	
	public GameScene(String ID, Stage stage) {
		super(ID, stage);
		
		expBar = new ProgressBar(0, 0, Window.WINDOW_WIDTH, 30, 100);
		expBar.setForegroundColor(ColorUtil.parseRGB2Color(86, 152, 204));
		expBar.setBackgroundColor(ColorUtil.parseRGB2Color(34, 34, 34));
		expBar.setBorderSize(2);
		expBar.setMaxProgress(GameLogic.getInstance().getMaxExp());
		expBar.setProgress(GameLogic.getInstance().getExp());
		
		level = new Label("Level 1", Window.WINDOW_WIDTH - 10, 15);
		level.setColor(Color.WHITE);
		level.setFontSize(16);
		level.setPosition(Position.RIGHT);
		level.setShadowColor(Color.BLACK);
		level.setTextShadow(true);
		level.setShadowOffset(2);
		
		GameLogic.getInstance().setTimeCounter(0);
		lastTime = Time.getNanoSecond();
		
		timer = new Label("", Window.WINDOW_WIDTH / 2, 15);
		timer.setColor(Color.WHITE);
		level.setFontSize(14);
		timer.setShadowColor(Color.BLACK);
		timer.setTextShadow(true);
		
		upgradeWindow = new UpgradeWindow();
		statusWindow = new StatusWindow();
		gameOverWindow = new GameOverWindow();
		pauseWindow = new PauseWindow();
	}
	
	@Override
	public void onLoadScene() {
		BehaviorManager.getInstance().initialize();
		CollisionManager.getInstance().initialize();
		World.getInstance().initialize();
		Camera.getInstance().initialize();

		AudioMedia.BGM.stop();

		AudioMedia.BGM_BATTLE.play();
		AudioMedia.BGM_BATTLE.setRepeat(true);
		AudioMedia.BGM_BATTLE.setVolume(0.25f);
	}

	@Override
	public void update(float deltaTime) {
		// Clear screen
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		// PLAY
		if (GameLogic.getInstance().getGameState() == GameState.PLAY) {
			BehaviorManager.getInstance().update(deltaTime);
			
			float currentTime = Time.getNanoSecond();
			
			if (currentTime - lastTime >= 1.f) {
				lastTime = currentTime;
				
				//Update
				GameLogic.getInstance().setTimeCounter(GameLogic.getInstance().getTimeCounter() + 1);
			}
		}
		
		// Render
		BehaviorManager.getInstance().render(deltaTime);
		
		// UPGRADE
		if (GameLogic.getInstance().getGameState() == GameState.UPGRADE) {
			upgradeWindow.update(deltaTime);
			statusWindow.update(deltaTime);
		}
		
		if (KeyboardHandler.isKeyPressed(KeyCode.ESCAPE)) {
			if (GameLogic.getInstance().getGameState() == GameState.PLAY)
				GameLogic.getInstance().setGameState(GameState.PAUSE);

			else if (GameLogic.getInstance().getGameState() == GameState.PAUSE)
				GameLogic.getInstance().setGameState(GameState.PLAY);
		}
		
		// PAUSE
		if (GameLogic.getInstance().getGameState() == GameState.PAUSE) {
			pauseWindow.update(deltaTime);
			statusWindow.update(deltaTime);
		}
		
		expBar.setMaxProgress(GameLogic.getInstance().getMaxExp());
		expBar.setProgress(GameLogic.getInstance().getExp());
		expBar.update(deltaTime);
		
		level.setText(String.format("Level %d", GameLogic.getInstance().getLevel()));
		level.update(deltaTime);
		
		timer.setText(GameLogic.getInstance().timeCounterToString());
		timer.update(deltaTime);

		// GAME OVER
		if (GameLogic.getInstance().getGameState() == GameState.GAME_OVER) {
			gameOverWindow.update(deltaTime);
		}
	}
	
}