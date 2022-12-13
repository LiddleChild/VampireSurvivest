package core.inputHandler;

import java.util.Set;
import java.util.TreeSet;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/*
 * 
 * KeyboardHandler
 * - Handle keyboard inputs
 * 
 */

public class KeyboardHandler implements EventHandler<KeyEvent> {
	
	private static Set<KeyCode> keydowns, lastKeydowns;
	
	public static void initialize() {
		keydowns = new TreeSet<KeyCode>();
		lastKeydowns = new TreeSet<KeyCode>();
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
	
	public static boolean isKeyPressed(KeyCode k) {
		if (keydowns.contains(k)) {
			if (lastKeydowns.contains(k)) return false;
			
			lastKeydowns.add(k);
			return true;
		}
		
		lastKeydowns.remove(k);
		return false;
	}
	
}
