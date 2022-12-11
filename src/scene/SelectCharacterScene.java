package scene;

import java.util.ArrayList;

import core.sprite.Sprite;
import core.ui.components.Button;
import core.ui.components.ButtonEventHandler;
import core.ui.components.Image;
import core.ui.components.Label;
import core.ui.components.Position;
import core.ui.components.SubWindow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.PlayerCharacter;
import logic.Window;
import util.ColorUtil;

public class SelectCharacterScene extends BaseScene {

	private Sprite background;
	private Label title;
	private SubWindow window;
	private ArrayList<CharacterButton> characterButtonLists;

	public SelectCharacterScene(String ID, Stage stage) {
		super(ID, stage);

		background = new Sprite("mainmenu_background.jpg");
		
		int w = Window.WINDOW_WIDTH / 3;
		int h = Window.WINDOW_HEIGHT * 3 / 4;
		window = new SubWindow(Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2, w, h);
		window.setBackgroundColor(ColorUtil.parseRGB2Color(34, 34, 34));
		window.setBorderColor(ColorUtil.parseRGB2Color(255, 204, 104));
		window.setBorderSize(2);

		title = new Label("Character Selection", Window.WINDOW_WIDTH / 2, 130);
		title.setColor(Color.WHITE);
		title.setTextShadow(true);
		title.setShadowColor(Color.GREY);
		title.setShadowOffset(2);
		
		characterButtonLists = new ArrayList<CharacterButton>();
		characterButtonLists.add(new CharacterButton("Braves", "entity/braves_icon.png", PlayerCharacter.BRAVES,
				Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 - 50,
				w - 10, 100));
		
		characterButtonLists.add(new CharacterButton("Knight", "entity/knight_icon.png", PlayerCharacter.KNIGHT,
				Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + 55,
				w - 10, 100));
		
		characterButtonLists.add(new CharacterButton("Spellcaster", "entity/spellcaster_icon.png", PlayerCharacter.SPELLCASTER,
				Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + 160,
				w - 10, 100));
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void update(float deltaTime) {
		gc.drawImage(background.getImage(), 0, 0);

		window.update(deltaTime);
		title.update(deltaTime);
		
		for (CharacterButton btn : characterButtonLists) {
			btn.update(deltaTime);
		}
	}
	
}

class CharacterButton {
	
	public Button button;
	public Label label;
	private Image img;
	
	public CharacterButton(String name, String path, PlayerCharacter character, int x, int y, int w, int h) {
		button = new Button("", x, y, w, h);
		button.getBound().setBackgroundColor(Color.GREY);
		button.getBound().setBorderColor(ColorUtil.parseRGB2Color(255, 204, 104));
		button.getBound().setBorderSize(2);
		button.getBound().setBorderRadius(8);
		button.setOnClick(new ButtonEventHandler() {
			@Override
			public void onClick() {
				GameLogic.getInstance().setCharacter(character);
				GameLogic.getInstance().nextScene();
			}
		});
		
		label = new Label(name, x + w / 2 - 30, y);
		label.setFontSize(20);
		label.setColor(Color.WHITE);
		label.setPosition(Position.RIGHT);
		label.setShadowColor(Color.BLACK);
		label.setShadowOffset(2);
		label.setTextShadow(true);
		
		img = new Image(path, x - w / 2 + 50, y, 75, 75);
		img.getBound().setBackgroundColor(new Color(0, 0, 0, 0));  
		img.getBound().setBorderColor(Color.LIGHTGREY);
		img.getBound().setBorderSize(2);
	}
	
	public void update(float deltaTime) {
		button.update(deltaTime);
		label.update(deltaTime);
		img.update(deltaTime);
	}
	
}