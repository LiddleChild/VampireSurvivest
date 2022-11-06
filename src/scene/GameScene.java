package scene;

import core.BehaviorManager;
import core.Camera;
import core.Renderer;
import core.world.World;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.Window;

public class GameScene extends BaseScene {
	
	private Canvas canvas;
	private GraphicsContext gc;
	
	public GameScene() {
		super();

		World world = new World();
		Camera camera = new Camera(world);
		Renderer renderer = new Renderer(camera);
		
		canvas = new Canvas(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		this.addComponent(canvas);
		
		new AnimationTimer() {
			
			private long lastTime = System.nanoTime();
			
			@Override
			public void handle(long currentTime) {
				// Calculate delta time
				float deltaTime = (currentTime - lastTime) / 1000000000.f;
				
				// Clear screen
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
				
				// Call update
				BehaviorManager.getInstance().update(deltaTime, gc);
				
				lastTime = currentTime;
			}
			
		}.start();
	}
	
}
