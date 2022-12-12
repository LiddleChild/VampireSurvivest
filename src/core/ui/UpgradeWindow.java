package core.ui;

import java.util.ArrayList;
import java.util.Random;

import core.audio.AudioMedia;
import core.sprite.Sprite;
import core.ui.components.Button;
import core.ui.components.Image;
import core.ui.components.Label;
import core.ui.components.Position;
import core.ui.components.SubWindow;
import core.ui.components.UIComponent;
import core.world.Tile;
import core.world.World;
import javafx.scene.paint.Color;
import logic.GameLogic;
import logic.GameState;
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
	private ArrayList<UpgradeItem> upgradeItemLists;
	
	public UpgradeWindow() {
		backgroundColor = new Color(0, 0, 0, 0.5f);
		
		coin = new Sprite("coin.png");
		coins = new ArrayList<Coin>();
		for (int i = 0; i < COIN_SIZE; i++) coins.add(new Coin());
		
		int w = Window.WINDOW_WIDTH / 3;
		int h = Window.WINDOW_HEIGHT * 3 / 4;
		window = new SubWindow(Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2, w, h);
		window.setBackgroundColor(ColorUtil.parseRGB2Color(43, 41, 41));
		window.setBorderColor(ColorUtil.parseRGB2Color(255, 204, 104));
		window.setBorderSize(2);
		
		levelUp = new Label("Level Up!", Window.WINDOW_WIDTH / 2, 120);
		levelUp.setColor(Color.WHITE);
		
		upgradeItemLists = new ArrayList<UpgradeItem>();
		
		GameLogic.getInstance().setOnGameStateChangeTo(GameState.UPGRADE, () -> {
			addItems();
			AudioMedia.LEVEL_UP.play();
		});
	}
	
	@Override
	public void update(float deltaTime) {
		drawBackground(deltaTime);
		window.update(deltaTime);
		levelUp.update(deltaTime);

		for (int i = 0; i < upgradeItemLists.size(); i++) {
			upgradeItemLists.get(i).update(deltaTime);
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
		ArrayList<ItemData> itemLists = new ArrayList<ItemData>();
		itemLists.add(new ItemData(
				"Health Potion",
				"Increases max health",
				"item/potion_health.png",
				() -> {
					World.getInstance().getPlayer().setMaxHealth(World.getInstance().getPlayer().getMaxHealth() + 5.f);
					World.getInstance().getPlayer().heal(World.getInstance().getPlayer().getMaxHealth());
				}
			));
		
		itemLists.add(new ItemData(
				"Swiftness Potion",
				"Increases movement speed",
				"item/potion_swiftness.png",
				() -> {
					World.getInstance().getPlayer().setMovementSpeed(World.getInstance().getPlayer().getMovementSpeed() + 0.25f);
				}
			));
		
		if (World.getInstance().getPlayer().getItem().getLevel() < 8)
			itemLists.add(new ItemData(
				World.getInstance().getPlayer().getItem().getName(),
				"Upgrade to the next level",
				World.getInstance().getPlayer().getItem().getSpritePath(),
				() -> {
					World.getInstance().getPlayer().getItem().increaseLevel();
				}
			));
		
		upgradeItemLists.clear();
		
		for (int i = 0; i < itemLists.size(); i++) {
			ItemData itemData = itemLists.get(i);
			UpgradeItem itm = new UpgradeItem(itemData,
					Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + (i - 1) * 80,
					window.getBound().width - 10, 75);
			
			upgradeItemLists.add(itm);
		}
	}
}

/*
 * Coin DATACLASS
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

/*
 * Item DATACLASS
 */
interface ItemAction {
	void action();
}
class ItemData {
	public String name, description, path;
	public ItemAction action;
	public ItemData(String name, String description, String path, ItemAction action) {
		this.name = name;
		this.description = description;
		this.path = path;
		this.action = action;
	}
}

/*
 * UpgradeButton UI
 */
class UpgradeItem {
	
	public Button button;
	public Label label, label2;
	private Image img;
	
	public UpgradeItem(ItemData item, int x, int y, int w, int h) {
		button = new Button("", x, y, w, h);
		button.getBound().setBackgroundColor(ColorUtil.parseRGB2Color(59, 51, 50));
		button.getBound().setBorderColor(ColorUtil.parseRGB2Color(255, 204, 104));
		button.getBound().setBorderSize(2);
		button.getBound().setBorderRadius(8);
		button.setOnClick(() -> {
			item.action.action();
			GameLogic.getInstance().nextLevel();
		});
		
		label = new Label(item.name, x - w / 2 + 75, y - h / 6);
		label.setFontSize(20);
		label.setColor(Color.WHITE);
		label.setPosition(Position.LEFT);
		label.setShadowColor(Color.BLACK);
		label.setShadowOffset(2);

		label2 = new Label(item.description, x - w / 2 + 75, y + h / 6);
		label2.setFontSize(14);
		label2.setColor(Color.WHITE);
		label2.setPosition(Position.LEFT);
		label2.setShadowColor(Color.BLACK);
		label2.setShadowOffset(2);
		
		img = new Image(item.path, x - w / 2 + 35, y, 55);
		img.getBound().setBackgroundColor(new Color(0, 0, 0, 0));  
		img.getBound().setBorderColor(Color.LIGHTGREY);
		img.getBound().setBorderSize(2);
	}
	
	public void update(float deltaTime) {
		button.update(deltaTime);
		label.update(deltaTime);
		label2.update(deltaTime);
		img.update(deltaTime);
	}
	
}