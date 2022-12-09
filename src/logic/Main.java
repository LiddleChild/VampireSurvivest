package logic;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		GameLogic.getInstance().initalize(stage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
