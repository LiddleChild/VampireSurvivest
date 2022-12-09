package core.ui.components;

import java.awt.Rectangle;

import core.inputHandler.MouseHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class Button extends UIComponent {

	private Label label;
	private Rectangle bound;
	private Color backgroundColor, hoverBackgroundColor;

	private boolean isClick;
	private ButtonEventHandler event;
	
	public Button(String text, int x, int y, int w, int h) {
		label = new Label(text, x, y);
		bound = new Rectangle(x - w / 2, y - h / 2, w, h);
		
		isClick = false;
		backgroundColor = Color.WHITE;
		hoverBackgroundColor = Color.GREY;
	}

	@Override
	public void update(float deltaTime) {
		if (!MouseHandler.isMouseDown(MouseButton.PRIMARY)) {
			isClick = false;
		}
		
		if (bound.contains(MouseHandler.getMousePosition().x, MouseHandler.getMousePosition().y)) {
			if (event != null && !isClick && MouseHandler.isMouseDown(MouseButton.PRIMARY)) {
				isClick = true;
				event.onClick();
			}
			
			gc.setFill(hoverBackgroundColor);
		} else {			
			gc.setFill(backgroundColor);
		}
		
		gc.fillRect(bound.x, bound.y, bound.width, bound.height);
		
		label.update(deltaTime);
	}

	
	/*
	 * GETTERS & SETTERS
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setHoverBackgroundColor(Color hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}

	public void setOnClick(ButtonEventHandler event) {
		this.event = event;
	}
}
