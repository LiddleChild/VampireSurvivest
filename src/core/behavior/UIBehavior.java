package core.behavior;

import javafx.scene.canvas.GraphicsContext;
import scene.GameScene;

public abstract class UIBehavior {
	
	protected float deltaTime;
	protected GraphicsContext gc;
	
	public UIBehavior() {
		BehaviorManager.getInstance().addUIBehavior(this);
		this.gc = GameScene.getInstance().getGraphicsContext();
	}
	
	public abstract void update();
	
	public void updateDeltaTime(float dt) {
		this.deltaTime = dt;
	}
	
	protected void delete() {
//		BehaviorManager.getInstance().removeBehavior(this);
	}
	
}
