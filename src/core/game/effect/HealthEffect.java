package core.game.effect;

import core.Renderer;
import core.behavior.BehaviorManager;
import core.behavior.GameBehavior;
import javafx.scene.paint.Color;
import util.math.Vector2f;

/*
 * 
 * HealthEffect
 * - A text effect whenever healed or damaged
 * 
 */

public class HealthEffect extends GameBehavior {

	private Vector2f position;
	private float amount;
	private float time, maxTime;
	
	public HealthEffect(Vector2f position, float amount) {
		super(99);
		this.position = new Vector2f(position);
		this.amount = amount;
		
		maxTime = 1.5f;
		time = 0.f;
		if (amount == 0) time = maxTime;
	}
	
	@Override
	public void update() {
		time += deltaTime;
		position.addEqual(new Vector2f(0, -4).multiply(deltaTime));
		
		if (time >= maxTime) {
			BehaviorManager.getInstance().removeBehavior(this);
		}
	}

	@Override
	public void render() {
		Renderer.setFontSize(12);
		if (amount < 0) Renderer.setFill(Color.BLACK);
		else Renderer.setFill(Color.WHITE);
		Renderer.drawString(String.valueOf((int) Math.abs(amount)), position.x, position.y);

		Renderer.setFontSize(10);
		if (amount < 0) Renderer.setFill(Color.RED);
		else Renderer.setFill(Color.LIGHTGREEN);
		Renderer.drawString(String.valueOf((int) Math.abs(amount)), position.x, position.y);
	}
}
