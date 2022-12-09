package logic;

import java.util.ArrayList;

import core.Renderer;
import core.inputHandler.KeyboardHandler;
import core.inputHandler.MouseHandler;
import core.world.World;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scene.BaseScene;
import scene.GameScene;
import scene.MainMenuScene;
import scene.StartingScene;

public class GameLogic {
	
	private Scene scene;
	private Stage stage;
	private ArrayList<BaseScene> sceneLists;
	private int currentScene;
	
	private GraphicsContext gc;
	
	private Font defaultFont;
	
	/*
	 * SINGLETON
	 */
	private static GameLogic instance;
	public static GameLogic getInstance() {
		if (instance == null) {
			instance = new GameLogic();
		}
		
		return instance;
	}
	
	public void initalize(Stage stage) {
		this.defaultFont = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ARCADEPI.TTF"), 20);
		
		this.stage = stage;
		initializeWindow();
		
		sceneLists = new ArrayList<BaseScene>();
		sceneLists.add(new StartingScene("starting", stage));
		sceneLists.add(new MainMenuScene("main_menu", stage));
		sceneLists.add(new GameScene("game", stage));
		setCurrentScene(0);
		
		// Initialize renderer
		Renderer.initialize(gc);

		initializeKeyboardHandler();
		initializeMouseHandler();
		
		// Initialize world
		new World();
		
		// Start game loops
		initializeGameLoop();
	}
	
	private void initializeWindow() {
		stage.setTitle("A Game Window");
		stage.setResizable(false);
		stage.show();
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

		Canvas canvas = new Canvas(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		Group root = new Group();
		root.getChildren().add(canvas);
		
		scene = new Scene(root);
		stage.setScene(scene);
	}
	
	// Initialize keyboard handler
	private void initializeKeyboardHandler() {
		KeyboardHandler.initialize();
		KeyboardHandler keyboardHandler = new KeyboardHandler();
		scene.setOnKeyPressed(keyboardHandler);
		scene.setOnKeyReleased(keyboardHandler);
	}
	
	// Initialize mouse handler
	private void initializeMouseHandler() {
		MouseHandler.initialize();
		MouseHandler mouseHandler = new MouseHandler();
		scene.setOnMousePressed(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);
		scene.setOnMouseMoved(mouseHandler);
	}
	
	private void initializeGameLoop() {
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
					
					stage.setTitle(String.format("A Game Window, %3d fps", fps));
					
					fps = 0;
				}
				
				// Clear screen
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
				
				// Call update
				sceneLists.get(currentScene).update(deltaTime);
				
				fps++;	
				lastTime = currentTime;
			}
			
		}.start();
	}
	
	/*
	 * GETTERS & SETTERS
	 */

	public GraphicsContext getGraphicsContext() {
		return gc;
	}
	
	public void setCurrentScene(int index) {
		currentScene = index;
	}
	
	public void nextScene() {
		currentScene++;
	}

	public Font getDefaultFont() {
		return defaultFont;
	}
}
