package core.behavior;

import javafx.scene.canvas.GraphicsContext;
import logic.GameLogic;

public abstract class UIBehavior {
	
	protected float deltaTime;
	protected GraphicsContext gc;
	
	public UIBehavior() {
		this.gc = GameLogic.getInstance().getGraphicsContext();
	}
	
	public abstract void update();
	
	public void updateDeltaTime(float dt) {
		this.deltaTime = dt;
	}
	
	protected void delete() {
//		BehaviorManager.getInstance().removeBehavior(this);
	}
	
}
