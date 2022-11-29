package core.sprite;

import core.Renderer;
import core.world.Tile;
import util.Vector2f;

public class AnimatedSprite {
	
	private int frameWidth, frameHeight;
	private int frame, frameCount;
	
	private int frameTime, time;
	
	private Sprite sprite;
	
	private boolean reverse;
	
	public AnimatedSprite(String path, int frameCount, int frameWidth, int frameHeight) {
		this.frameCount = frameCount;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		sprite = new Sprite(path);
				
		frame = 1;
		time = 0;
		frameTime = 25;
	}
	
	public void draw(Vector2f position, int w, int h) {
		if (time == frameTime) {
			time = 0;
			if (frame == frameCount - 1) {
				frame = 1;
			} else {
				frame++;
			}
//			frame = (frame + 1) % frameCount;
		} else {			
			time++;
		}

		int x = (int) position.x;
		int y = (int) position.y;
		
		if (reverse) {
			w = -w;
			x += Tile.SIZE;
		}
		
		Renderer.drawSprite(sprite, x, y - Tile.SIZE, w, h, frame * frameWidth, 0, frameWidth, frameHeight);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public void setReverse(boolean b) {
		reverse = b;
	}
	
}
