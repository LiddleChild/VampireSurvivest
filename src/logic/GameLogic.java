package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.Renderer;
import core.inputHandler.KeyboardHandler;
import core.inputHandler.MouseHandler;
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
import scene.SelectCharacterScene;
import scene.StartingScene;

public class GameLogic {
	
	private Scene scene;
	private Stage stage;
	private ArrayList<BaseScene> sceneLists;
	private int currentScene;
	
	private GraphicsContext gc;
	private Font defaultFont;
	
	/*
	 * GAAME STATE
	 */
	private GameState gameState;
	private Map<GameState, GameStateEvent> gameStateEventMaps;
	private int maxExp, exp, level;
	private PlayerCharacter character;
	
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
		maxExp = 100;
		exp = 0;
		level = 1;
		
		character = PlayerCharacter.BRAVES;
		
		gameStateEventMaps = new HashMap<GameState, GameStateEvent>();
		gameState = GameState.PLAY;
		
		this.defaultFont = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ARCADEPI.TTF"), 20);
		
		this.stage = stage;
		initializeWindow();
		
		sceneLists = new ArrayList<BaseScene>();
		sceneLists.add(new StartingScene("starting", stage));
		sceneLists.add(new MainMenuScene("main_menu", stage));
		sceneLists.add(new SelectCharacterScene("select_character", stage));
		sceneLists.add(new GameScene("game", stage));
		setCurrentScene(1);
		
		// Initialize renderer
		Renderer.initialize(gc);

		initializeKeyboardHandler();
		initializeMouseHandler();
		
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
            	setGameState(GameState.EXIT);
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
		scene.setOnMouseClicked(mouseHandler);
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
				
				if (gameState != GameState.UPGRADE && exp >= maxExp) {
					level++;
					
					setGameState(GameState.UPGRADE);
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
		sceneLists.get(currentScene).init();
	}
	
	public void nextScene() {
		setCurrentScene(currentScene + 1);
	}

	public Font getDefaultFont() {
		return defaultFont;
	}

	public int getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
		
		if (gameStateEventMaps.get(gameState) != null) {
			gameStateEventMaps.get(gameState).onStateChange();
		}
	}
	
	public void setOnGameStateChangeTo(GameState gameState, GameStateEvent e) {
		gameStateEventMaps.put(gameState, e);
	}

	public PlayerCharacter getCharacter() {
		return character;
	}

	public void setCharacter(PlayerCharacter character) {
		this.character = character;
	}
}
