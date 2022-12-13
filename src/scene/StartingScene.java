package scene;

import core.ui.component.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.Window;

/*
 * 
 * StartingScene
 * - show game studio's name
 * 
 */

public class StartingScene extends BaseScene {

	private Label title;
	
	private float time, maxTime;
	
	public StartingScene(String ID, Stage stage) {
		super(ID, stage);

		title = new Label("whatnameshouldiusewa studio",
				Window.WINDOW_WIDTH / 2,
				Window.WINDOW_HEIGHT / 2);
		title.setFontSize(25);
		title.setColor(Color.WHITE);
		
		time = 0.f;
		maxTime = 3.f;
	}
	
	@Override
	public void onLoadScene() {
		
	}

	@Override
	public void update(float deltaTime) {
		time += deltaTime;
		
		// Fancy equation for text opacity calculation
		double alpha = (Math.tanh(4 * time - 2) + 1) / 2.0;
		
		title.setColor(new Color(1, 1, 1, alpha));
		title.update(deltaTime);
		
		if (time >= maxTime) {
			GameLogic.getInstance().nextScene();
		}
	}

}
