package scene;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class MainMenuScene extends BaseScene {
	
	public MainMenuScene(Stage stage) {
		super(stage);
		
		try {			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainMenuFXML.fxml"));
			loader.setController(this);
			loader.setRoot(getScene());
			loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
