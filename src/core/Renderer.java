package core;

public class Renderer {
	private static Renderer instance;
	
	private Camera camera;
	
	public Renderer(Camera camera) {
		this.camera = camera;
	}
	
	public static Renderer getInstance() {
		return instance;
	}
	
	
	
}
