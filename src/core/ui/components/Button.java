package core.ui.components;

import core.inputHandler.MouseHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class Button extends UIComponent {

	private Label label, rightHoverCursor, leftHoverCursor;
	private SubWindow bound;
	private Color hoverColor;

	private boolean isClick;
	private ButtonEventHandler event;
	
	public Button(String text, int x, int y, int w, int h) {
		label = new Label(text, x, y);
		bound = new SubWindow(x, y, w, h);

		rightHoverCursor = new Label("<", x + w / 2 + 20, y);
		rightHoverCursor.setPosition(Position.LEFT);
		rightHoverCursor.setFontSize(h * 0.8f);
		rightHoverCursor.setColor(hoverColor);
		rightHoverCursor.setTextShadow(true);
		rightHoverCursor.setShadowColor(Color.BLACK);
		
		leftHoverCursor = new Label(">", x - w / 2 - 20, y);
		leftHoverCursor.setPosition(Position.RIGHT);
		leftHoverCursor.setFontSize(h * 0.8f);
		leftHoverCursor.setColor(hoverColor);
		leftHoverCursor.setTextShadow(true);
		leftHoverCursor.setShadowColor(Color.BLACK);
		
		isClick = false;
		hoverColor = Color.WHITE;
	}

	@Override
	public void update(float deltaTime) {
		if (!MouseHandler.isMouseDown(MouseButton.PRIMARY)) {
			isClick = false;
		}
		
		if (bound.getBound().contains(MouseHandler.getMousePosition().x, MouseHandler.getMousePosition().y)) {
			if (event != null && !isClick && MouseHandler.isMouseDown(MouseButton.PRIMARY)) {
				isClick = true;
				event.onClick();
			}

			leftHoverCursor.setColor(hoverColor);
			leftHoverCursor.update(deltaTime);
			
			rightHoverCursor.setColor(hoverColor);
			rightHoverCursor.update(deltaTime);
		}
		
		bound.update(deltaTime);
		label.update(deltaTime);
	}

	/*
	 * GETTERS & SETTERS
	 */

	public void setOnClick(ButtonEventHandler event) {
		this.event = event;
	}

	public void setHoverColor(Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	public SubWindow getBound() {
		return bound;
	}

	public Label getLabel() {
		return label;
	}
}
