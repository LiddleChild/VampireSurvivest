package core.ui;

import core.ui.components.Label;
import core.ui.components.Position;
import core.ui.components.SubWindow;
import core.ui.components.UIComponent;
import core.world.World;
import javafx.scene.paint.Color;
import logic.Window;
import util.ColorUtil;

public class StatusWindow extends UIComponent {

	private SubWindow window;

	private Label playerTitle, itemTitle;
	
	private StatItem movementSpeed, maxHealth, attackDamage, attackSpeed;
	
	public StatusWindow() {
		window = new SubWindow(100, Window.WINDOW_HEIGHT / 2, 200, 150);
		
		playerTitle = new Label("Player Status", 15, Window.WINDOW_HEIGHT / 2 - 55);
		playerTitle.setColor(Color.WHITE);
		playerTitle.setFontSize(16);
		playerTitle.setPosition(Position.LEFT);
		
		movementSpeed = new StatItem(15, Window.WINDOW_HEIGHT / 2 - 35);
		maxHealth = new StatItem(15, Window.WINDOW_HEIGHT / 2 - 20);
		
		itemTitle = new Label("Item Status", 15, Window.WINDOW_HEIGHT / 2 + 15);
		itemTitle.setColor(Color.WHITE);
		itemTitle.setFontSize(16);
		itemTitle.setPosition(Position.LEFT);

		attackDamage = new StatItem(15, Window.WINDOW_HEIGHT / 2 + 35);
		attackSpeed = new StatItem(15, Window.WINDOW_HEIGHT / 2 + 50);
	}
	
	@Override
	public void update(float deltaTime) {
		window.update(deltaTime);
		
		playerTitle.update(deltaTime);
		movementSpeed.update("Movement Speed", World.getInstance().getPlayer().getMovementSpeed(), deltaTime);
		maxHealth.update("Max Health", World.getInstance().getPlayer().getMaxHealth(), deltaTime);
		
		itemTitle.update(deltaTime);
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
		item2.setColor(ColorUtil.parseRGB2Color(255, 204, 104));
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