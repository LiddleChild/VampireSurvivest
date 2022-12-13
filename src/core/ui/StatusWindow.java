package core.ui;

import core.game.world.World;
import core.ui.component.Label;
import core.ui.component.Position;
import core.ui.component.SubWindow;
import core.ui.component.UIComponent;
import javafx.scene.paint.Color;
import logic.Window;
import util.ColorUtil;

/*
 * 
 * StatusWindow
 * - Show player statuss
 * - Show item statuss
 * 
 */

public class StatusWindow extends UIComponent {

	private SubWindow window;

	private Label playerTitle, itemTitle;
	
	private StatItem movementSpeed, maxHealth, attackDamage, attackSpeed, weaponLevel;
	
	public StatusWindow() {
		window = new SubWindow(100, Window.WINDOW_HEIGHT / 2, 200, 160);
		
		playerTitle = new Label("Player Status", 15, Window.WINDOW_HEIGHT / 2 - 60);
		playerTitle.setColor(Color.WHITE);
		playerTitle.setFontSize(16);
		playerTitle.setPosition(Position.LEFT);
		
		movementSpeed = new StatItem(15, Window.WINDOW_HEIGHT / 2 - 40);
		maxHealth = new StatItem(15, Window.WINDOW_HEIGHT / 2 - 25);
		
		itemTitle = new Label("Item Status", 15, Window.WINDOW_HEIGHT / 2 + 10);
		itemTitle.setColor(Color.WHITE);
		itemTitle.setFontSize(16);
		itemTitle.setPosition(Position.LEFT);

		weaponLevel = new StatItem(15, Window.WINDOW_HEIGHT / 2 + 30);
		attackDamage = new StatItem(15, Window.WINDOW_HEIGHT / 2 + 45);
		attackSpeed = new StatItem(15, Window.WINDOW_HEIGHT / 2 + 60);
	}
	
	@Override
	public void update(float deltaTime) {
		window.update(deltaTime);
		
		playerTitle.update(deltaTime);
		movementSpeed.update("Movement Speed", World.getInstance().getPlayer().getMovementSpeed(), deltaTime);
		maxHealth.update("Max Health", World.getInstance().getPlayer().getMaxHealth(), deltaTime);
		
		itemTitle.update(deltaTime);
		weaponLevel.update("Weapon level", World.getInstance().getPlayer().getItem().getLevel(), deltaTime);
		attackDamage.update("Attack damage", World.getInstance().getPlayer().getItem().getAttackDamage(), deltaTime);
		attackSpeed.update("Attack speed", 1.f / World.getInstance().getPlayer().getItem().getAttackCooldownTime(), deltaTime);
	}
}

class StatItem {
	private Label item, item2;
	
	public StatItem(int x, int y) {
		item = new Label("", x, y);
		item.setColor(Color.WHITE);
		item.setFontSize(12);
		item.setPosition(Position.LEFT);

		item2 = new Label("", x + 175, y);
		item2.setColor(ColorUtil.parseRGBToColor(255, 204, 104));
		item2.setFontSize(12);
		item2.setPosition(Position.RIGHT);
	}
	
	public void update(String name, float amount, float deltaTime) {
		item.setText(String.format("%s", name));
		item.update(deltaTime);

		item2.setText(String.format("%.1f", amount));
		item2.update(deltaTime);
	}
}