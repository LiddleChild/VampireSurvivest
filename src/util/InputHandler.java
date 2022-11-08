package util;

import java.util.Set;
import java.util.TreeSet;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler implements EventHandler<KeyEvent> {
	
	private static Set<KeyCode> keydowns;
	
	public static void initialize() {
		keydowns = new TreeSet<KeyCode>();
	}

	@Override
	public void handle(KeyEvent e) {
		if (e.getEventType() == KeyEvent.KEY_PRESSED) {
			keydowns.add(e.getCode());
		} else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
			keydowns.remove(e.getCode());
		}
	}
	
	public static boolean onKeyPressed(KeyCode k) {
		return keydowns.contains(k);
	}
	
}
