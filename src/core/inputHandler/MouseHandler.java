package core.inputHandler;

import java.util.Set;
import java.util.TreeSet;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import util.math.Vector2f;

public class MouseHandler implements EventHandler<MouseEvent> {
	
	private static Set<String> mousedowns;
	private static Vector2f mousePosition;
	private static boolean lastMouseDown;
	
	public static void initialize() {
		mousePosition = new Vector2f(0, 0);
		mousedowns = new TreeSet<String>();
		lastMouseDown = false;
	}

	@Override
	public void handle(MouseEvent e) {
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mousedowns.add(e.getButton().name());
		} else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mousedowns.remove(e.getButton().name());
		} else if (e.getEventType() == MouseEvent.MOUSE_MOVED) {
			mousePosition.x = (float) e.getX();
			mousePosition.y = (float) e.getY();
		}
	}
	
	public static boolean isMouseDown(MouseButton mb) {
		if (mousedowns.contains(mb.name())) {
			if (lastMouseDown) return false;
			
			lastMouseDown = true;
			return true;
		}
		
		lastMouseDown = false;
		return false;
	}

	public static Vector2f getMousePosition() {
		return mousePosition;
	}
	
}
