package core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import logic.Window;
import util.math.Vector2;

public class Renderer {
	
	private static GraphicsContext gc;
	private static Paint paint;
	
	public static void initialize(GraphicsContext gc) {
		Renderer.gc = gc;
	}
	
	public static void setFill(Paint p) {
		paint = p;
	}
	
	/*
	 * Overloading
	 */
	
	public static void fillRect(Vector2 pos, float w, float h) {
		fillRect(pos.x, pos.y, w, h);
	}
	
	public static void fillRect(float x, float y, float w, float h) {
		float sx = x + Camera.getInstance().getX();
		float sy = y + Camera.getInstance().getY();
		
		if (sx > -w && sx < Window.WINDOW_WIDTH &&
				sy > -h && sy < Window.WINDOW_HEIGHT) {
			
			if (paint != null) {				
				gc.setFill(paint);
			}
			
			gc.fillRect(sx, sy, w, h);
		}		
	}
	
}
