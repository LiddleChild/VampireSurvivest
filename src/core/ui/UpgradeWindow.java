package core.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import core.sprite.Sprite;
import core.ui.components.Button;
import core.ui.components.ButtonEventHandler;
import core.ui.components.Label;
import core.ui.components.SubWindow;
import core.ui.components.UIComponent;
import core.world.Tile;
import javafx.scene.paint.Color;
import logic.GameLogic;
import logic.GameState;
import logic.GameStateEvent;
import logic.Window;
import util.ColorUtil;

public class UpgradeWindow extends UIComponent {
	private final int COIN_SIZE = 500, GRAVITY = Window.WINDOW_HEIGHT / 2;
	private final float COIN_FRAME_TIME = 0.15f;
	
	private Color backgroundColor;
	
	private Sprite coin;
	private ArrayList<Coin> coins;
	private float time = 0.f;
	
	private SubWindow window;
	private Label levelUp;
	private ArrayList<String> upgradeLists;
	private ArrayList<Button> items;
	
/*

TODO:
Upgradable
	- Max health
	- Movement speed
	- Weapon attack damage
	- Weapon attack speed
	- Weapon hit box
	
Sword LV8
	- Wave Force on attack
	
 */
	
	public UpgradeWindow() {
		backgroundColor = new Color(0, 0, 0, 0.5f);
		
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
		
		upgradeLists = new ArrayList<String>();
		upgradeLists.add("Max health");
		upgradeLists.add("Movement speed");
		upgradeLists.add("Weapon attack damage");
		upgradeLists.add("Weapon attack speed");
		upgradeLists.add("Weapon hit box");
		
		items = new ArrayList<Button>();
		GameLogic.getInstance().setOnGameStateChangeTo(GameState.UPGRADE, new GameStateEvent() {
			@Override
			public void onStateChange() {
				addItems();
			}
		});
	}
	
	@Override
	public void update(float deltaTime) {
		drawBackground(deltaTime);
		window.update(deltaTime);
		levelUp.update(deltaTime);

		for (int i = 0; i < 3; i++) {
			items.get(i).update(deltaTime);
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
	
	public void addItems() {
		Collections.shuffle(upgradeLists);
		items.clear();
		
		for (int i = 0; i < 3; i++) {
			Button btn = new Button(upgradeLists.get(i), Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + (i - 1) * 80, window.getBound().width - 10, 75);
			btn.getBound().setBackgroundColor(ColorUtil.parseRGB2Color(211, 191, 169));
			btn.getBound().setBorderColor(ColorUtil.parseRGB2Color(255, 204, 104));
			btn.getBound().setBorderSize(2);
			btn.getBound().setBorderRadius(8);
			btn.setOnClick(new ButtonEventHandler() {				
				@Override
				public void onClick() {
					GameLogic.getInstance().setGameState(GameState.PLAY);
					GameLogic.getInstance().setExp(0);
					GameLogic.getInstance().setMaxExp(GameLogic.getInstance().getMaxExp() * 2);
				}
			});
			
			items.add(btn);
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