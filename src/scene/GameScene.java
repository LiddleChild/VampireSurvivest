package scene;

import core.Renderer;
import core.behavior.BehaviorManager;
import core.inputHandler.KeyboardHandler;
import core.inputHandler.MouseHandler;
import core.ui.ExperienceBar;
import core.world.World;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Window;

public class GameScene extends BaseScene {
	private static GameScene instance;
	
	private Canvas canvas;
	private GraphicsContext gc;
	
	public GameScene(Stage stage) {
		super(stage);
		
		instance = this;
		
		// Create canvas
		canvas = new Canvas(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		this.addComponent(canvas);

		// Grab graphics context
		gc = canvas.getGraphicsContext2D();
		
		// Initialize renderer
		Renderer.initialize(gc);
		
		// Initialize world
		World world = new World();

		// Initialize keyboard handler
		KeyboardHandler.initialize();
		KeyboardHandler keyboardHandler = new KeyboardHandler();
		getScene().setOnKeyPressed(keyboardHandler);
		getScene().setOnKeyReleased(keyboardHandler);

		// Initialize mouse handler
		MouseHandler.initialize();
		MouseHandler mouseHandler = new MouseHandler();
		getScene().setOnMousePressed(mouseHandler);
		getScene().setOnMouseReleased(mouseHandler);
		getScene().setOnMouseMoved(mouseHandler);
		
		// Initialize UI
		ExperienceBar expBar = new ExperienceBar();
		
		// Start game loops
		initGameLoop();
	}
	
	private void initGameLoop() {
		new AnimationTimer() {
			
			private long lastTime = System.nanoTime();
			
			private float time = 0.f;
			private int fps = 0;
			
			private Color backgroundColor = new Color(43.0 / 255.0, 41.0 / 255.0, 41.0 / 255.0, 1.0);
			
			@Override
			public void handle(long currentTime) {	
				// Calculate delta time
				float deltaTime = (currentTime - lastTime) / 1000000000.f;
				
				time += deltaTime;
				if (time >= 1.f) {
					time -= 1.f;
					
					stage.setTitle(String.format("A Game Window, %3d fps", fps));
					
					fps = 0;
				}
				
				// Clear screen
				gc.setFill(backgroundColor);
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
	
	/*
	 * SINGLETON
	 */
	public static GameScene getInstance() {
		return instance;
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public GraphicsContext getGraphicsContext() {
		return gc;
	}
	
}