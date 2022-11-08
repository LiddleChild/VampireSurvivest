package core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import logic.Window;

public class Renderer {
	
	private static GraphicsContext gc;
	
	public static void initialize(GraphicsContext gc) {
		Renderer.gc = gc;
	}
	
	public static void setFill(Paint p) {
		gc.setFill(p);
	}
	
	public static void fillRect(float x, float y, float w, float h) {
		float sx = x + Camera.getInstance().getX();
		float sy = y + Camera.getInstance().getY();
		
		if (sx > -w && sx < Window.WINDOW_WIDTH &&
				sy > -h && sy < Window.WINDOW_HEIGHT) {
			
			gc.fillRect(sx, sy, w, h);
		}		
	}
	
}
