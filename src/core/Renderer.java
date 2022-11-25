package core;

import core.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import logic.Window;
import util.Mathf;
import util.Vector2;

public class Renderer {
	private static GraphicsContext gc;
	
	private static Paint paint;
	
	public static void initialize(GraphicsContext gc) {
		 Renderer.gc = gc;
	}
	
	private static Vector2 cameraTranslate(float x, float y) {
		return Mathf.round(new Vector2(x, y)
				.add(Camera.getInstance().getPosition()));
	}
	
	private static boolean checkInsideWindow(float x, float y, float w, float h) {
		Vector2 t = cameraTranslate(x, y);
		
		return (t.x > -w && t.x < Window.WINDOW_WIDTH &&
				t.y > -h && t.y < Window.WINDOW_HEIGHT);
	}
	
	/*
	 * SetFill
	 */
	public static void setFill(Paint p) {
		if (p != paint) {
			gc.setFill(p);
		}
	}
	
	/*
	 * FillRect
	 */
	public static void fillRect(Vector2 pos, float w, float h) {
		fillRect(pos.x, pos.y, w, h);
	}
	
	public static void fillRect(float x, float y, float w, float h) {
		Vector2 t = cameraTranslate(x, y);
		
		if (checkInsideWindow(x, y, w, h)) {
			gc.fillRect(t.x, t.y, w, h);
		}
	}
	
	/*
	 * DrawSprite
	 */
	public static void drawSprite(Sprite sprite, Vector2 pos, float w, float h, float dx, float dy, float dw, float dh) {
		Vector2 t = cameraTranslate(pos.x, pos.y);
		
		if (checkInsideWindow(pos.x, pos.y, w, h)) {
			gc.drawImage(sprite.getImage(), dx, dy, dw, dh, t.x, t.y, w, h);
		}
	}
	
	public static void drawSprite(Sprite sprite, Vector2 pos, float w, float h) {
		drawSprite(sprite, pos.x, pos.y, w, h, 1.f);
	}
	
	public static void drawSprite(Sprite sprite, float x, float y, float w, float h, float alpha) {
		Vector2 t = cameraTranslate(x, y);
		
		if (checkInsideWindow(x, y, w, h)) {
			if (alpha < 1.f) gc.setGlobalAlpha(alpha);
			
			gc.drawImage(sprite.getImage(), t.x, t.y, w, h);
			
			if (alpha < 1.f) gc.setGlobalAlpha(1.f);
		}
	}
	
}
