package core.ui.components;

import java.awt.Rectangle;

import javafx.scene.paint.Color;

public class ProgressBar extends UIComponent {

	private float maxProgress, progress, targetProgress;
	private float interpAmount;
	private Rectangle bound;
	private Color backgroundColor, foregroundColor;
	private boolean isReverse;
	
	private Color borderColor;
	private int borderSize;
	
	public ProgressBar(int x, int y, int w, int h, float maxProgress) {
		this.maxProgress = maxProgress;
		
		progress = maxProgress;
		bound = new Rectangle(x, y, w, h);
		backgroundColor = Color.WHITE;
		foregroundColor = Color.BLACK;
		isReverse = false;
		
		borderSize = 0;
		borderColor = Color.WHITE;
		
		interpAmount = 0.99f;
	}
	
	@Override
	public void update(float deltaTime) {
		progress += (targetProgress - progress) * interpAmount * deltaTime;
		
		gc.setFill(borderColor);
		gc.fillRect(bound.x, bound.y, bound.width, bound.height);
		
		gc.setFill(backgroundColor);
		gc.fillRect(
				bound.x + borderSize,
				bound.y + borderSize,
				bound.width - borderSize * 2,
				bound.height - borderSize * 2);
		
		gc.setFill(foregroundColor);
		if (isReverse) {
			float xx = bound.x + (progress / maxProgress) * bound.width + borderSize;
			float yy = bound.y + borderSize;
			float ww = bound.width * (1.f - progress / maxProgress) - borderSize * 2;
			float hh = bound.height - borderSize * 2;
			
			gc.fillRect(xx, yy, ww, hh);
		} else {
			float xx = bound.x + borderSize;
			float yy = bound.y + borderSize;
			float ww = bound.width * (progress / maxProgress) - borderSize * 2;
			float hh = bound.height - borderSize * 2;

			gc.fillRect(xx, yy, ww, hh);
		}
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	
	public void setMaxProgress(float maxProgress) {
		this.maxProgress = maxProgress;
	}

	public float getProgress() {
		return progress;
	}

	public void setTargetProgress(float targetProgress) {
		this.targetProgress = targetProgress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}

	public void setReverse(boolean isReverse) {
		this.isReverse = isReverse;
	}

	public void setInterpAmount(float interpAmount) {
		this.interpAmount = interpAmount;
	}
}
