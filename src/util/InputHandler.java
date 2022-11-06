package util;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler implements EventHandler<KeyEvent> {

	@Override
	public void handle(KeyEvent e) {
		
	}
	
	public static boolean onKeyPressed(KeyCode k) {
		return false;
	}
	
	public static boolean onKeyReleased() {
		return false;
	}
	
}
