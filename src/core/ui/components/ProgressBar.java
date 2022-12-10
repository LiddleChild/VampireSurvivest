package core.ui.components;

import java.awt.Rectangle;

import javafx.scene.paint.Color;

public class ProgressBar extends UIComponent {

	private float maxProgress, progress;
	private Rectangle bound;
	private Color backgroundColor, foregroundColor;
	private boolean isReverse;
	
	private Color borderColor;
	private int borderSize, borderRadius;
	
	public ProgressBar(int x, int y, int w, int h, float maxProgress) {
		this.maxProgress = maxProgress;
		
		progress = maxProgress;
		bound = new Rectangle(x, y, w, h);
		backgroundColor = Color.WHITE;
		foregroundColor = Color.BLACK;
		isReverse = false;
		
		borderSize = 0;
		borderColor = Color.WHITE;
		borderRadius = 8;
	}
	
	@Override
	public void update(float deltaTime) {
		gc.setFill(backgroundColor);
		gc.fillRect(
				bound.x + borderSize,
				bound.y + borderSize,
				bound.width - borderSize * 2,
				bound.height - borderSize * 2);
		
		float xx, yy, ww, hh;
		gc.setFill(foregroundColor);
		if (isReverse) {
			xx = bound.x + (progress / maxProgress) * bound.width + borderSize;
			yy = bound.y + borderSize;
			ww = bound.width * (1.f - progress / maxProgress) - borderSize * 2;
			hh = bound.height - borderSize * 2;
		} else {
			xx = bound.x + borderSize;
			yy = bound.y + borderSize;
			ww = bound.width * (progress / maxProgress) - borderSize * 2;
			hh = bound.height - borderSize * 2;
		}
		gc.fillRect(xx, yy, ww, hh);
		
		gc.setStroke(borderColor);
		gc.setLineWidth(borderSize);
		gc.strokeRoundRect(
				bound.x + borderSize / 2,
				bound.y + borderSize / 2,
				bound.width - borderSize,
				bound.height - borderSize,
				borderRadius, borderRadius);
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

	public void setProgress(float progress) {
		this.progress = (progress > maxProgress) ? maxProgress : progress;
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

	public void setBorderRadius(int borderRadius) {
		this.borderRadius = borderRadius;
	}
}
