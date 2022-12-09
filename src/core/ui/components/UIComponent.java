package core.ui.components;

import javafx.scene.canvas.GraphicsContext;
import logic.GameLogic;

public abstract class UIComponent {
	
	protected GraphicsContext gc;
	
	public UIComponent() {
		this.gc = GameLogic.getInstance().getGraphicsContext();
	}
	
	public abstract void update(float deltaTime);
	
}
