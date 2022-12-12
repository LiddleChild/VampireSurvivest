package scene;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import logic.GameLogic;

public abstract class BaseScene {
	
	protected Stage stage;
	protected GraphicsContext gc;
	
	private String ID;
	
	public BaseScene(String ID, Stage stage) {
		this.ID = ID;
		this.stage = stage;
		this.gc = GameLogic.getInstance().getGraphicsContext();
	}
	
	public abstract void onLoadScene();
	public abstract void update(float deltaTime);
	
	/*
	 * GETTERS & SETTERS
	 */
	public String getID() {
		return ID;
	}
	
}
