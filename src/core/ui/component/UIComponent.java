package core.ui.component;

import javafx.scene.canvas.GraphicsContext;
import logic.GameLogic;

/*
 * 
 * UIComponent
 * - A base class for all UI involving class
 * - Provides GraphicsContext, update method
 * 
 */

public abstract class UIComponent {
	
	protected GraphicsContext gc;
	
	public UIComponent() {
		this.gc = GameLogic.getInstance().getGraphicsContext();
	}
	
	public abstract void update(float deltaTime);
	
}
