package core.sprite;

public class AnimationState {
	public static enum State { IDLE, PLAY }
	
	private State state;
	private State nextStage;
	private int startFrame, endFrame;
	
	public AnimationState(State state, State nextStage, int startFrame, int endFrame) {
		this.state = state;
		this.nextStage = nextStage;
		this.startFrame = startFrame;
		this.endFrame = endFrame;
	}

	public State getState() {
		return state;
	}

	public State getNextStage() {
		return nextStage;
	}

	public int getStartFrame() {
		return startFrame;
	}

	public int getEndFrame() {
		return endFrame;
	}
}
