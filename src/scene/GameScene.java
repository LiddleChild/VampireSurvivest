package scene;

import core.Camera;
import core.audio.AudioMedia;
import core.behavior.BehaviorManager;
import core.collision.CollisionManager;
import core.game.world.World;
import core.inputHandler.KeyboardHandler;
import core.ui.GameOverWindow;
import core.ui.PauseWindow;
import core.ui.StatusWindow;
import core.ui.UpgradeWindow;
import core.ui.component.Label;
import core.ui.component.Position;
import core.ui.component.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.GameState;
import logic.Window;
import util.ColorUtil;
import util.Time;

/*
 * 
 * GameScene
 * - plays Battle Background Music
 * - update and render current GameState
 * - update and render all GameBehavior
 * - initialize BehaviorManager, CollisionManager, World, Camera
 * 
 */

public class GameScene extends BaseScene {

	private Color backgroundColor;
	private ProgressBar expBar;
	private Label level, timer;
	private float lastTime;
	
	private UpgradeWindow upgradeWindow;
	private StatusWindow statusWindow;
	private GameOverWindow gameOverWindow;
	private PauseWindow pauseWindow;
	
	public GameScene(String ID, Stage stage) {
		super(ID, stage);
		
		backgroundColor = ColorUtil.parseRGBToColor(43, 41, 41);
		
		expBar = new ProgressBar(0, 0, Window.WINDOW_WIDTH, 30, 100);
		expBar.setForegroundColor(ColorUtil.parseRGBToColor(86, 152, 204));
		expBar.setBackgroundColor(ColorUtil.parseRGBToColor(34, 34, 34));
		expBar.setBorderSize(2);
		expBar.setMaxProgress(GameLogic.getInstance().getMaxExperience());
		expBar.setProgress(GameLogic.getInstance().getExperience());
		
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
		/*
		 * UPDATE
		 */
		
		// Level
		if (GameLogic.getInstance().getGameState() != GameState.UPGRADE &&
				GameLogic.getInstance().getExperience() >= GameLogic.getInstance().getMaxExperience()) {
			
			GameLogic.getInstance().incrementLevel();
			GameLogic.getInstance().setGameState(GameState.UPGRADE);
		}
		
		// STATE:PLAY
		if (GameLogic.getInstance().getGameState() == GameState.PLAY) {
			// Update
			BehaviorManager.getInstance().update(deltaTime);
			
			// Increment counter
			float currentTime = Time.getNanoSecond();
			if (currentTime - lastTime >= 1.f) {
				lastTime = currentTime;
				GameLogic.getInstance().setTimeCounter(GameLogic.getInstance().getTimeCounter() + 1);
			}
		}
		
		/*
		 * RENDER
		 */
		// Clear screen
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		// Render
		BehaviorManager.getInstance().render(deltaTime);
		
		// STATE:UPGRADE
		if (GameLogic.getInstance().getGameState() == GameState.UPGRADE) {
			upgradeWindow.update(deltaTime);
			statusWindow.update(deltaTime);
		}
		
		// STATE:PAUSE
		if (KeyboardHandler.isKeyPressed(KeyCode.ESCAPE)) {
			if (GameLogic.getInstance().getGameState() == GameState.PLAY)
				GameLogic.getInstance().setGameState(GameState.PAUSE);

			else if (GameLogic.getInstance().getGameState() == GameState.PAUSE)
				GameLogic.getInstance().setGameState(GameState.PLAY);
		}
		
		if (GameLogic.getInstance().getGameState() == GameState.PAUSE) {
			pauseWindow.update(deltaTime);
			statusWindow.update(deltaTime);
		}
		
		// Render UI
		expBar.setMaxProgress(GameLogic.getInstance().getMaxExperience());
		expBar.setProgress(GameLogic.getInstance().getExperience());
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