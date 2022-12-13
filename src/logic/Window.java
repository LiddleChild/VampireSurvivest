package logic;

import core.inputHandler.KeyboardHandler;
import core.inputHandler.MouseHandler;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
 * 
 * Window:
 * - create JavaFX window
 * - create Canvas
 * - get GraphicsContext from Canvas
 * - initialize Keyboard and Mouse input handler
 * 
 */

public class Window {
	
	public static final int WINDOW_WIDTH = 1024;
	public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
	
	private Stage stage;
	private Scene scene;
	private GraphicsContext gc;
	
	public Window(Stage stage) {
		this.stage = stage;
		
		initializeWindow();
		initializeKeyboardHandler();
		initializeMouseHandler();
	}
	
	private void initializeWindow() {
		stage.setTitle("Vampire Survivest");
		stage.setResizable(false);
		stage.show();
		
		// Set STATE:Exit on close application
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
            	GameLogic.getInstance().setGameState(GameState.EXIT);
            }
        });

		Canvas canvas = new Canvas(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		Group root = new Group();
		root.getChildren().add(canvas);
		
		scene = new Scene(root);
		stage.setScene(scene);
	}
	
	private void initializeKeyboardHandler() {
		KeyboardHandler.initialize();
		KeyboardHandler keyboardHandler = new KeyboardHandler();
		scene.setOnKeyPressed(keyboardHandler);
		scene.setOnKeyReleased(keyboardHandler);
	}
	
	private void initializeMouseHandler() {
		MouseHandler.initialize();
		MouseHandler mouseHandler = new MouseHandler();
		scene.setOnMouseClicked(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);
		scene.setOnMouseMoved(mouseHandler);
	}

	/*
	 * GETTERS & SETTERS
	 */
	public void setTitle(String text) {
		stage.setTitle(text);
	}
	
	public Stage getStage() {
		return stage;
	}

	public Scene getScene() {
		return scene;
	}

	public GraphicsContext getGraphicsContext() {
		return gc;
	}
	
}
