package scene;

import java.io.IOException;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Window;

public abstract class BaseScene {
	
	protected Stage stage;
	
	private Scene scene;
	private Group root;
	
	public BaseScene(Stage stage) {
		this.stage = stage;
		
		root = new Group();
		scene = new Scene(root, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
	}
	
	protected void addComponent(Node n) {
		root.getChildren().add(n);
	}
	
	protected void loadScene(String path) throws IOException {
		
	}
	
	/*
	 * GETTER
	 */
	public Scene getScene() {
		return scene;
	}
	
}
