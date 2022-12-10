package core.ui;

import java.util.ArrayList;
import java.util.Random;

import core.sprite.Sprite;
import core.ui.components.Button;
import core.ui.components.Label;
import core.ui.components.SubWindow;
import core.ui.components.UIComponent;
import core.world.Tile;
import javafx.scene.paint.Color;
import logic.Window;
import util.ColorUtil;

public class UpgradeWindow extends UIComponent {
	private final int COIN_SIZE = 375, GRAVITY = Window.WINDOW_HEIGHT / 2;
	private final float COIN_FRAME_TIME = 0.15f;
	
	private Color backgroundColor;
	
	private Sprite coin;
	private ArrayList<Coin> coins;
	private float time = 0.f;
	
	private SubWindow window;
	private Label levelUp;
	private Button[] items;
	
	public UpgradeWindow() {
		backgroundColor = new Color(0, 0, 0, 0.25f);
		
		coin = new Sprite("coin.png");
		coins = new ArrayList<Coin>();
		for (int i = 0; i < COIN_SIZE; i++) coins.add(new Coin());
		
		int w = Window.WINDOW_WIDTH / 3;
		int h = Window.WINDOW_HEIGHT * 3 / 4;
		window = new SubWindow(Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2, w, h);
		window.setBackgroundColor(ColorUtil.parseRGB2Color(34, 34, 34));
		window.setBorderColor(ColorUtil.parseRGB2Color(255, 204, 104));
		window.setBorderSize(2);
		
		levelUp = new Label("Level Up!", Window.WINDOW_WIDTH / 2, 120);
		levelUp.setColor(Color.WHITE);
		
		items = new Button[3];
		for (int i = 0; i < 3; i++) {
			items[i] = new Button("Item " + i, Window.WINDOW_WIDTH / 2, 200 + i * 100, w - 80, 75);
			items[i].getBound().setBackgroundColor(Color.WHITE);
			items[i].getBound().setBorderSize(0);
		}
	}
	
	@Override
	public void update(float deltaTime) {
		drawBackground(deltaTime);
		window.update(deltaTime);
		levelUp.update(deltaTime);

		for (int i = 0; i < 3; i++) {
			items[i].update(deltaTime);
		}
	}
	
	private void drawBackground(float deltaTime) {
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		time += deltaTime;
		
		for (Coin c : coins) {
			c.y += GRAVITY * deltaTime;
			if (c.y > Window.WINDOW_HEIGHT)
				c.y -= Window.WINDOW_HEIGHT;

			gc.setGlobalAlpha(1.f - ((float) c.y / Window.WINDOW_HEIGHT) * 1.15f);
			gc.drawImage(coin.getImage(), c.frame * 64, 0, 64, 64, c.x, c.y, c.size, c.size);
			gc.setGlobalAlpha(1.f);
		}

		if (time >= COIN_FRAME_TIME) {
			time -= COIN_FRAME_TIME;
			for (Coin c : coins) {
				c.frame = (c.frame + 1) % 4;		
			}
		}
	}
}

/*
 * COIN DATACLASS
 */
class Coin {
	public int x, y, size, frame;
	
	public Coin() {
		Random rand = new Random();
		x = rand.nextInt(Window.WINDOW_WIDTH);
		y = -rand.nextInt(Window.WINDOW_HEIGHT * 2);
		size = rand.nextInt(Tile.SIZE / 4, Tile.SIZE);
		frame = rand.nextInt(0, 3);
	}
	
}