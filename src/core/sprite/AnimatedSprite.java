package core.sprite;

import java.util.HashMap;
import java.util.Map;

import core.Renderer;
import core.world.Tile;
import javafx.util.Pair;
import util.Vector2f;

public class AnimatedSprite {
	
	public static enum State { IDLE, PLAY }
	
	private int frameWidth, frameHeight;
	private int frame, frameCol;
	
	private float frameTime, time;
	
	private Sprite sprite;
	
	private boolean reverse;
	
	private Vector2f offset;
	
	private State state;
	private Map<State, Pair<Integer, Integer>> intervals;
	
	public AnimatedSprite(String path, int frameRow, int frameCol, int frameWidth, int frameHeight) {
		this.frameCol = frameCol;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		sprite = new Sprite(path);
				
		frame = 1;
		time = 0;
		frameTime = 0.1f;
		
		offset = new Vector2f(0.f, 0.f);
		
		state = State.IDLE;
		
		intervals = new HashMap<State, Pair<Integer,Integer>>();
		setStateIntervals(State.IDLE, 0, 1);
		setStateIntervals(State.PLAY, 1, frameCol * frameRow);
	}
	
	public void setStateIntervals(State state, int start, int end) {
		intervals.put(state, new Pair<Integer, Integer>(start, end));
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void draw(Vector2f position, int w, int h, float deltaTime, float angle) {
		time += deltaTime;
		if (time >= frameTime) {
			time -= frameTime;
			if (frame >= intervals.get(state).getValue() - 1) {
				frame = intervals.get(state).getKey();
			} else {
				frame++;
			}
		}
		
		int x = (int) (position.x);
		int y = (int) (position.y);
		
		if (reverse) {
			w = -w;
			x += Tile.SIZE;
		}
		
		Renderer.drawSprite(sprite,
				x, y,
				w, h,
				(frame % frameCol) * frameWidth,
				(frame / frameCol) * frameHeight,
				frameWidth, frameHeight,
				offset.x, offset.y,
				1.f,
				angle);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public void setReverse(boolean b) {
		reverse = b;
	}
	
	public void setOffset(Vector2f vec) {
		offset = vec;
	}
	
	public void setFrameTime(float frameTime) {
		this.frameTime = frameTime;
	}
	
}
