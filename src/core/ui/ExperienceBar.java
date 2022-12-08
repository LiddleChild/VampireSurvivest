package core.ui;

import core.behavior.UIBehavior;
import javafx.scene.paint.Color;
import logic.Window;
import util.ColorUtil;

public class ExperienceBar extends UIBehavior {
	
	private int maxExp;
	private int exp;

	public ExperienceBar() {
		maxExp = 100;
		exp = 50;
	}
	
	@Override
	public void update() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, 15);
		
		gc.setFill(ColorUtil.parseRGB2Color(218, 78, 56));
		gc.fillRect(0, 0, Window.WINDOW_WIDTH * ((float) exp / maxExp), 15);
	}
	
}
