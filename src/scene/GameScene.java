package scene;

import core.behavior.BehaviorManager;
import core.ui.components.Label;
import core.ui.components.Position;
import core.ui.components.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.Window;
import util.ColorUtil;

/*
 * 
 * TODO:
 * - separate update method into: update(float), render(float) to implement pausing
 * - Upgrade UI on level up
 * 
 */

public class GameScene extends BaseScene {

	private Color backgroundColor = new Color(43.0 / 255.0, 41.0 / 255.0, 41.0 / 255.0, 1.0);
	private ProgressBar expBar;
	private Label level;
	
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
	}

	@Override
	public void update(float deltaTime) {
		switch (GameLogic.getInstance().getGameState()) {
		case PLAY: play(deltaTime); break;
		case PAUSE: pause(deltaTime); break;
		}
	}
	
	private void play(float deltaTime) {
		// Clear screen
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		// Call update
		BehaviorManager.getInstance().update(deltaTime);
		
		// Update experience bar
		expBar.setMaxProgress(GameLogic.getInstance().getMaxExp());
		expBar.setProgress(GameLogic.getInstance().getExp());
		expBar.update(deltaTime);
		
		level.setText(String.format("Level %d", GameLogic.getInstance().getLevel()));
		level.update(deltaTime);
	}
	
	private void pause(float deltaTime) {
		
	}
	
}