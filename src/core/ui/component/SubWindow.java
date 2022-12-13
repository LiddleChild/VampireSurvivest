package core.ui.component;

import java.awt.Rectangle;

import javafx.scene.paint.Color;

/*
 * 
 * SubWindow
 * - Rounded corner blank rectangle
 * 
 */

public class SubWindow extends UIComponent {
	
	private Rectangle bound;

	private Color borderColor, backgroundColor;
	private int borderSize, borderRadius;
	
	public SubWindow(int x, int y, int w, int h) {
		bound = new Rectangle(x - w / 2, y - h / 2, w, h);
		
		backgroundColor = Color.BLACK;
		borderColor = Color.WHITE;
		borderSize = 0;
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
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}

	public void setBorderRadius(int borderRadius) {
		this.borderRadius = borderRadius;
	}

	public Rectangle getBound() {
		return bound;
	}
	
}
