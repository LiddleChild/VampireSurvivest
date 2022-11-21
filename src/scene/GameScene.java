package scene;

import core.InputHandler;
import core.Renderer;
import core.behavior.BehaviorManager;
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
		
		// Create canvas
		canvas = new Canvas(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		this.addComponent(canvas);

		// Grab graphics context
		gc = canvas.getGraphicsContext2D();
		
		Renderer.initialize(gc);
		World world = new World();
		
		// Initialize input handler
		InputHandler.initialize();
		InputHandler handler = new InputHandler();
		getScene().setOnKeyPressed(handler);
		getScene().setOnKeyReleased(handler);
		
		initGameLoop();
	}
	
	private void initGameLoop() {
		new AnimationTimer() {
			
			private long lastTime = System.nanoTime();
			
			private float time = 0.f;
			private int fps = 0;
			
			@Override
			public void handle(long currentTime) {	
				// Calculate delta time
				float deltaTime = (currentTime - lastTime) / 1000000000.f;
				
				time += deltaTime;
				if (time >= 1.f) {
					time -= 1.f;
					System.out.println(String.format("FPS: %d", fps));
					fps = 0;
				}
				
				// Clear screen
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
				
				// Call update
				BehaviorManager.getInstance().update(deltaTime);

//				for (int y = 0; y < 12; y++) {
//					for (int x = 0; x < 7; x++) {
//						gc.setFill(new Color(
//								40 * x / 255.0,
//								20 * y / 255.0,
//								1.0,
//								1.0));
//						gc.fillRect(x * 16, y * 16, 16, 16);
//					}
//				}
				
				fps++;	
				lastTime = currentTime;
			}
			
		}.start();
	}
	
}