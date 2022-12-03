package core.inputHandler;

import java.util.Set;
import java.util.TreeSet;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import util.Vector2f;

public class MouseHandler implements EventHandler<MouseEvent> {
	
	private static Set<String> keydowns;

	private static Vector2f mousePosition;
	
	public static void initialize() {
		mousePosition = new Vector2f(0, 0);
		keydowns = new TreeSet<String>();
	}

	@Override
	public void handle(MouseEvent e) {
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			keydowns.add(e.getButton().name());
		} else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			keydowns.remove(e.getButton().name());
		} else if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
			mousePosition.x = (float) e.getX();
			mousePosition.y = (float) e.getY();
		}
	}
	
	public static boolean isMouseDown(MouseButton mb) {
		return keydowns.contains(mb.name());
	}

	public static Vector2f getMousePosition() {
		return mousePosition;
	}
	
}
