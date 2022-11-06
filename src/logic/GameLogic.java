package logic;
import javafx.application.Application;
import javafx.stage.Stage;
import scene.GameScene;
import scene.MainMenuScene;

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
		GameScene gameScene = new GameScene();
		
		stage.setTitle("A Game Window");
		stage.setScene(gameScene.getScene());
//		stage.setScene(mainMenuScene.getScene());
		stage.setResizable(false);
		stage.show();
		
		
//		new AnimationTimer() {
//			
//			private long lastTime = System.nanoTime(), time;
//			private int fps = 0;
//			
//			@Override
//			public void handle(long now) {
//				time += (now - lastTime);
//				
//				if (time >= 1_000_000_000) {
//					time -= 1_000_000_000;
//					stage.setTitle(String.format("A Game Window | FPS: %d", fps));
//					fps = 0;
//				}
//				
//				lastTime = now;
//				fps++;
//			}
//		}.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
