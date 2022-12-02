package logic;
import javafx.application.Application;
import javafx.stage.Stage;
import scene.GameScene;

public class GameLogic extends Application {
	
	private static GameLogic instance;
	
	public static GameLogic getInstance() {
		if (instance == null) {
			instance = new GameLogic();
		}
		
		return instance;
	}

	@Override
	public void start(Stage stage) throws Exception {
//		MainMenuScene mainMenuScene = new MainMenuScene();
		GameScene gameScene = new GameScene(stage);
		
		stage.setTitle("A Game Window");
		stage.setScene(gameScene.getScene());
//		stage.setScene(mainMenuScene.getScene());
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
