package core.collision;

import java.awt.Rectangle;

import util.math.Vector2f;

public class Hitbox {
	
	private int ox, oy, ow, oh;
	private Rectangle bound;
	
	public Hitbox(int x, int y, int w, int h) {
		this.ox = x;
		this.oy = y;
		this.ow = w;
		this.oh = h;
		bound = new Rectangle(ox, oy, ow, oh);
	}
	
	public void setPosition(Vector2f vec) {
		bound.x = ox + (int) vec.x - (bound.width - ow) / 2;
		bound.y = oy + (int) vec.y - (bound.height - oh) / 2;
	}
	
	public void setSize(int s) {
		bound.width = ow + s;
		bound.height = oh + s;
	}

	public Rectangle getBound() {
		return bound;
	}
	
}
