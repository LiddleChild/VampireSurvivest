package core.sprite.animation;

import java.util.HashMap;
import java.util.Map;

import core.Renderer;
import core.sprite.Sprite;
import core.sprite.animation.AnimationState.State;
import core.world.Tile;
import util.math.Vector2f;

public class AnimatedSprite {
	
	private int frameWidth, frameHeight;
	private int frame, frameCol;
	
	private float frameTime, time;
	
	private Sprite sprite;
	
	private boolean reverse = false;
	
	private Vector2f offset;
	
	private State state;
	private Map<State, AnimationState> intervals;
	
	private AnimatedSpriteEvent event;
	
	public AnimatedSprite(String path, int frameRow, int frameCol, int frameWidth, int frameHeight) {
		this(path, frameRow, frameCol, frameWidth, frameHeight, frameRow * frameCol);
	}
	
	public AnimatedSprite(String path, int frameRow, int frameCol, int frameWidth, int frameHeight, int frameCount) {
		this.frameCol = frameCol;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		sprite = new Sprite(path);
				
		frame = 1;
		time = 0;
		frameTime = 0.1f;
		
		offset = new Vector2f(0.f, 0.f);
		
		state = State.IDLE;
		
		intervals = new HashMap<State, AnimationState>();
		setStateIntervals(State.IDLE, State.IDLE, 0, 1);
		setStateIntervals(State.PLAY, State.IDLE, 1, frameCount);
	}
	
	public void setStateIntervals(State state, State nextState, int start, int end) {
		intervals.put(state, new AnimationState(state, nextState, start, end));
	}
	
	public void setState(State state) {	
		this.state = state;
	}

	public void draw(Vector2f position, int w, int h, float deltaTime, float angle) {
		draw((int) position.x, (int) position.y, w, h, deltaTime, angle);
	}
	
	public void draw(int x, int y, int w, int h, float deltaTime, float angle) {
		time += deltaTime;
		if (time >= frameTime) {
			time -= frameTime;
			if (frame >= intervals.get(state).getEndFrame() - 1) {
				if (event != null) event.onEnd();

				state = intervals.get(state).getNextStage();
				frame = intervals.get(state).getStartFrame();
			} else {
				frame++;
			}
		}
		
		if (reverse) {
			w = -w;
			x += Tile.SIZE;
		}
		
		Renderer.setRenderOffset(offset.x, offset.y);
		Renderer.setRenderRotation(angle);
		Renderer.drawSprite(sprite,
				x, y,
				w, h,
				(frame % frameCol) * frameWidth,
				(frame / frameCol) * frameHeight,
				frameWidth, frameHeight);
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
	
	public int getWidth() {
		return sprite.getWidth();
	}
	
	public int getHeight() {
		return sprite.getHeight();
	}
	
	public void setEventHandler(AnimatedSpriteEvent event) {
		this.event = event;
	}
	
}
