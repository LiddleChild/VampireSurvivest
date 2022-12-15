package core.collision;

import java.awt.Rectangle;

import util.math.Vector2f;

/*
 * 
 * HitboriginalX
 * - A rectangle hitboriginalX
 * 
 */

public class Hitbox {
	
	private int originalX, originalY, originalWidth, originalHeight;
	private Rectangle bound;
	
	public Hitbox(int x, int y, int w, int h) {
		this.originalX = x;
		this.originalY = y;
		this.originalWidth = w;
		this.originalHeight = h;
		bound = new Rectangle(originalX, originalY, originalWidth, originalHeight);
	}
	
	public void setPosition(Vector2f vec) {
		bound.x = originalX + (int) vec.x - (bound.width - originalWidth) / 2;
		bound.y = originalY + (int) vec.y - (bound.height - originalHeight) / 2;
	}
	
	public void setSize(int s) {
		bound.width = originalWidth + s;
		bound.height = originalHeight + s;
	}

	public Rectangle getBound() {
		return bound;
	}
	
}
