package core.collision;

import java.awt.Rectangle;

import util.math.Vector2f;

public class Hitbox {
	
	private int ox, oy;
	private Rectangle bound;
	
	public Hitbox(int x, int y, int w, int h) {
		this.ox = x;
		this.oy = y;
		bound = new Rectangle(ox, oy, w, h);
	}
	
	public void setPosition(Vector2f vec) {
		bound.x = ox + (int) vec.x;
		bound.y = oy + (int) vec.y;
	}

	public Rectangle getBound() {
		return bound;
	}
	
}
