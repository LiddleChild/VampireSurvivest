package core.ui.components;

import javafx.scene.canvas.GraphicsContext;

public abstract class UIComponent {
	
	private GraphicsContext gc;
	
	public UIComponent() {
		
	}
	
	public abstract void update(float deltaTime);
	
}
