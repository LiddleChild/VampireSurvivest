package core;

import java.awt.Rectangle;

import core.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import logic.GameLogic;
import util.math.Vector2f;

/*
 * 
 * Renderer
 * - 
 * 
 */

public class Renderer {
	
	private static GraphicsContext gc;
	
	private static Paint paint;
	
	private static Vector2f offset;
	private static float alpha, rotation;
	private static Font font;
	
	public static void initialize(GraphicsContext gc) {
		 Renderer.gc = gc;
		 Renderer.offset = new Vector2f();
		 Renderer.alpha = 1.f;
		 Renderer.rotation = 0.f;
		 Renderer.font = GameLogic.getInstance().getDefaultFont();
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
	
	public static void setFont(Font font) {
		Renderer.font = font;
	}
	
	public static void setFontSize(int fontSize) {
		Renderer.font = Font.font(font.getFamily(), fontSize);
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
		Vector2f t = Camera.getInstance().translateToCameraPosition(x, y);
		
		if (Camera.getInstance().isInCameraView(x, y, w, h)) {
			gc.fillRect(t.x, t.y, w, h);
		}
	}
	
	public static void fillRectWithBound(float x, float y, float w, float h) {
		Vector2f t = Camera.getInstance().translateToCameraPosition(x, y);
		
		if (Camera.getInstance().isInCameraView(x, y, w, h)) {
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
		Vector2f t = Camera.getInstance().translateToCameraPosition(x, y);
		float ox = offset.x, oy = offset.y;
		
		if (Camera.getInstance().isInCameraView(x, y, w, h)) {
			if (alpha < 1.f) gc.setGlobalAlpha(alpha);
			
			if (rotation != 0.f) {
				gc.save();
				gc.translate(t.x + ox + w / 2, t.y + oy + h / 2);
				gc.rotate(rotation);
				
				gc.drawImage(sprite.getImage(), dx, dy, dw, dh, ox - (w / 2 + ox), oy - (h / 2 + oy), w, h);
				
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
	
	/*
	 * DrawString
	 */
	public static void drawString(String text, float x, float y) {
		Vector2f t = Camera.getInstance().translateToCameraPosition(x, y);
		
		gc.setFont(font);
		gc.fillText(text, t.x, t.y);
	}
	
}
