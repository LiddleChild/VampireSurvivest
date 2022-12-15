package core.sprite.animation;

import java.util.HashMap;
import java.util.Map;

import core.Renderer;
import core.sprite.Sprite;
import core.sprite.animation.AnimationState.State;
import util.math.Vector2f;

/*
 * 
 * AnimatedSprite
 * - Plays animation sprite
 * - Uses state for animation management
 * 
 */

public class AnimatedSprite {
	
	private int frameWidth, frameHeight;
	private int frame, frameCol;
	
	private float timePerFrame, time;
	
	private Sprite sprite;
	
	private boolean reverse = false;
	
	private Vector2f offset;
	
	private State currentState;
	private Map<State, AnimationState> animationState;
	
	private Runnable event;
	
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
		timePerFrame = 0.1f;
		
		offset = new Vector2f(0.f, 0.f);
		
		setCurrentState(State.IDLE);
		
		animationState = new HashMap<State, AnimationState>();
		setStateData(State.IDLE, State.IDLE, 0, 1);
		setStateData(State.PLAY, State.IDLE, 1, frameCount);
	}
	
	public void setStateData(State state, State nextState, int start, int end) {
		animationState.put(state, new AnimationState(state, nextState, start, end));
	}
	
	public void update(float deltaTime) {
		time += deltaTime;

		if (time >= timePerFrame) {
			time -= timePerFrame;
			if (frame >= animationState.get(currentState).getEndFrame() - 1) {
				if (event != null) event.run();

				currentState = animationState.get(currentState).getNextStage();
				frame = animationState.get(currentState).getStartFrame();
			} else {
				frame++;
			}
		}
	}

	public void render(Vector2f position, int w, int h, float angle) {
		render((int) position.x, (int) position.y, w, h, angle);
	}
	
	public void render(int x, int y, int w, int h, float angle) {
		if (reverse) {
			x += w;
			w = -w;
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
	
	public Vector2f getOffset() {
		return offset;
	}

	public void setTimePerFrame(float timePerFrame) {
		this.timePerFrame = timePerFrame;
	}
	
	public int getWidth() {
		return sprite.getWidth();
	}
	
	public int getHeight() {
		return sprite.getHeight();
	}
	
	public void setEventHandler(Runnable event) {
		this.event = event;
	}
	
	public void setCurrentState(State state) {	
		this.currentState = state;
	}
	
}
