package scene;

import core.ui.components.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.Window;

public class StartingScene extends BaseScene {

	private Label title;
	
	private float time, waitTime;
	
	public StartingScene(String ID, Stage stage) {
		super(ID, stage);

		title = new Label("whatnameshouldiusewa studio",
				Window.WINDOW_WIDTH / 2,
				Window.WINDOW_HEIGHT / 2);
		title.setFontSize(25);
		title.setColor(Color.WHITE);
		
		time = 0.f;
		waitTime = 3.f;
	}
	
	@Override
	public void onLoadScene() {
		
	}

	@Override
	public void update(float deltaTime) {
		time += deltaTime;
		
		double a = (Math.tanh(4 * time - 2) + 1) / 2.0;
		
		title.setColor(new Color(a, a, a, 1.f));
		title.update(deltaTime);
		
		if (time >= waitTime) {
			GameLogic.getInstance().nextScene();
		}
	}

}
