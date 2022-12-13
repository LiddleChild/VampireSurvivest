package core.ui.component;

import core.audio.AudioMedia;
import core.inputHandler.MouseHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

/*
 * 
 * Button
 * - A button UI
 * 
 */

public class Button extends UIComponent {

	private Label label, rightHoverCursor, leftHoverCursor;
	private SubWindow bound;
	private Color hoverColor;
	private boolean isHover;

	private Runnable event;
	
	public Button(String text, int x, int y, int w, int h) {
		label = new Label(text, x, y);
		bound = new SubWindow(x, y, w, h);

		rightHoverCursor = new Label("<", x + w / 2 + 20, y);
		rightHoverCursor.setPosition(Position.LEFT);
		rightHoverCursor.setFontSize(h * 0.8f);
		rightHoverCursor.setColor(hoverColor);
		rightHoverCursor.setTextShadow(true);
		rightHoverCursor.setShadowOffset(2);
		rightHoverCursor.setShadowColor(Color.BLACK);
		
		leftHoverCursor = new Label(">", x - w / 2 - 20, y);
		leftHoverCursor.setPosition(Position.RIGHT);
		leftHoverCursor.setFontSize(h * 0.8f);
		leftHoverCursor.setColor(hoverColor);
		leftHoverCursor.setTextShadow(true);
		leftHoverCursor.setShadowOffset(2);
		leftHoverCursor.setShadowColor(Color.BLACK);
		
		hoverColor = Color.WHITE;
		isHover = false;
	}

	@Override
	public void update(float deltaTime) {
		if (bound.getBound().contains(MouseHandler.getMousePosition().x, MouseHandler.getMousePosition().y)) {
			if (event != null && MouseHandler.isMouseDown(MouseButton.PRIMARY)) {
				event.run();
				AudioMedia.CONFIRM.play();
			}
			
			if (!isHover) {
				isHover = true;
				AudioMedia.SELECT.play();
			}

			leftHoverCursor.setColor(hoverColor);
			leftHoverCursor.update(deltaTime);
			
			rightHoverCursor.setColor(hoverColor);
			rightHoverCursor.update(deltaTime);
		} else {
			isHover = false;
		}
		
		bound.update(deltaTime);
		label.update(deltaTime);
	}

	/*
	 * GETTERS & SETTERS
	 */
	public void setOnClick(Runnable event) {
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
