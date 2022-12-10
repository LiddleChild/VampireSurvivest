package core.ui.components;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.GameLogic;

public class Label extends UIComponent {
	private Position pos;
	private Font font;
	private Color color;
	private String text;
	private int x, y;
	
	private Color shadowColor;
	private boolean isTextShadow;
	private int shadowOffset;
	
	private Text fxText;
	
	public Label(String text, int x, int y) {
		this.fxText = new Text(text);
		this.x = x;
		this.y = y;

		setText(text);
		setTextShadow(false);
		setPosition(Position.CENTER);
		setFont(GameLogic.getInstance().getDefaultFont());		
		setColor(Color.BLACK);
		setShadowColor(Color.WHITE);
		setShadowOffset(3);
	}
	
	@Override
	public void update(float deltaTime) {
		int xx = x, yy = y + getTextHeight() / 2;
		switch (pos) {
		case CENTER:
			xx -= getTextWidth() / 2;
			break;
			
		case RIGHT:
			xx -= getTextWidth();
			
		default:
			break;
		}
		
		gc.setFont(font);
		
		if (isTextShadow) {
			gc.setFill(shadowColor);
			gc.fillText(text, xx + shadowOffset, yy + shadowOffset);
		}
		
		gc.setFill(color);
		gc.fillText(text, xx, yy);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public void setFont(Font font) {
		this.font = font;
		this.fxText.setFont(font);
	}
	
	public void setFontSize(double size) {
		setFont(Font.font(font.getFamily(), size));
	}
	
	public int getTextWidth() {
		return (int) fxText.getBoundsInLocal().getWidth();
	}
	
	public int getTextHeight() {
		return (int) fxText.getBoundsInLocal().getHeight();
	}

	public void setPosition(Position pos) {
		this.pos = pos;
	}

	public void setTextShadow(boolean isTextShadow) {
		this.isTextShadow = isTextShadow;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setShadowColor(Color shadowColor) {
		this.shadowColor = shadowColor;
	}
	
	public void setText(String text) {
		this.text = text;
		this.fxText.setText(text);
	}

	public void setShadowOffset(int shadowOffset) {
		this.shadowOffset = shadowOffset;
	}
}
