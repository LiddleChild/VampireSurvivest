package scene;

import core.Renderer;
import core.behavior.BehaviorManager;
import core.entity.Player;
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
		
		canvas = new Canvas(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		this.addComponent(canvas);
		
		Renderer.init(gc);
		World world = new World();
		Player player = new Player();

		getScene().setOnKeyPressed(player);
		getScene().setOnKeyReleased(player);
		
		initGameLoop();
	}
	
	private void initGameLoop() {
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
