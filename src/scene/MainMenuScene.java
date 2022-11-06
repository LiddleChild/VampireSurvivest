package scene;

import javafx.fxml.FXMLLoader;

public class MainMenuScene extends BaseScene {
	
	public MainMenuScene() {
		super();
		
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
