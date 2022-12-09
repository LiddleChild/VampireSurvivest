package scene;

import core.behavior.BehaviorManager;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Window;

public class GameScene extends BaseScene {

	private Color backgroundColor = new Color(43.0 / 255.0, 41.0 / 255.0, 41.0 / 255.0, 1.0);
	
	public GameScene(String ID, Stage stage) {
		super(ID, stage);
	}

	@Override
	public void update(float deltaTime) {
		// Clear screen
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		// Call update
		BehaviorManager.getInstance().update(deltaTime);
	}
	
}