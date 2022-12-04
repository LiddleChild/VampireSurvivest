package core;

import java.awt.Rectangle;

import core.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import logic.Window;
import util.Mathf;
import util.Vector2f;

public class Renderer {
	private static GraphicsContext gc;
	
	private static Paint paint;
	
	private static Vector2f offset;
	private static float alpha, rotation;
	
	public static void initialize(GraphicsContext gc) {
		 Renderer.gc = gc;
		 Renderer.offset = new Vector2f();
		 Renderer.alpha = 1.f;
		 Renderer.rotation = 0.f;
	}
	
	public static Vector2f translateCamera(float x, float y) {
		return Mathf.round(new Vector2f(x, y)
				.add(Camera.getInstance().getPosition()));
	}
	
	public static boolean checkInsideWindow(float x, float y, float w, float h) {
		Vector2f t = translateCamera(x, y);
		
		return (t.x > -w && t.x < Window.WINDOW_WIDTH &&
				t.y > -h && t.y < Window.WINDOW_HEIGHT);
	}
	
	/*
	 * CONFIGURATION
	 */
	public static void setRenderOffset(float x, float y) {
		offset.x = x;
		offset.y = y;
	}
	
	public static void setRenderOpacity(float alpha) {
		Renderer.alpha = alpha;
	}
	
	public static void setRenderRotation(float rotation) {
		Renderer.rotation = rotation;
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
	public static void fillRect(Rectangle rect) {
		fillRect(rect.x, rect.y, rect.width, rect.height);
	}
	
	public static void fillRect(Vector2f pos, float w, float h) {
		fillRect(pos.x, pos.y, w, h);
	}
	
	public static void fillRect(float x, float y, float w, float h) {
		Vector2f t = translateCamera(x, y);
		
		if (checkInsideWindow(x, y, w, h)) {
			gc.fillRect(t.x, t.y, w, h);
		}
	}
	
	public static void fillRectWithBound(float x, float y, float w, float h) {
		Vector2f t = translateCamera(x, y);
		
		if (checkInsideWindow(x, y, w, h)) {
			gc.fillRect(t.x, t.y, w, h);
			
			gc.setFill(Color.WHITE);
			gc.fillRect(t.x - 1, t.y - 1, w + 2, h + 2);
		}
	}
	
	/*
	 * DrawSprite
	 */
	public static void drawSprite(Sprite sprite, float x, float y, float w, float h) {
		drawSprite(sprite, x, y, w, h, 0, 0, sprite.getWidth(), sprite.getHeight());
	}
	
	public static void drawSprite(Sprite sprite, float x, float y, float w, float h, float dx, float dy, float dw, float dh) {
		Vector2f t = translateCamera(x, y);
		float ox = offset.x, oy = offset.y;
		
		if (checkInsideWindow(x, y, w, h)) {
			if (alpha < 1.f) gc.setGlobalAlpha(alpha);
			
			if (rotation != 0.f) {
				gc.save();
				gc.translate(t.x + (w + ox) / 2, t.y + (h + oy) / 2);
				gc.rotate(rotation);
				
				gc.drawImage(sprite.getImage(), dx, dy, dw, dh, ox - (w + ox) / 2, oy - (h + oy) / 2, w, h);
				
				gc.restore();
			} else {
				gc.drawImage(sprite.getImage(), dx, dy, dw, dh, t.x + ox, t.y + oy, w, h);
			}
			
			if (alpha < 1.f) gc.setGlobalAlpha(1.f);
		}
		
		alpha = 1.f;
		rotation = 0.f;
		offset.setZero();
	}
	
}
