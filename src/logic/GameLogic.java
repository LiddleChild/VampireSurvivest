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
	 * GAME STATE
	 */
	private GameState gameState;
	private Map<GameState, Runnable> gameStateEventMaps;
	private float maxExp, exp;
	private int level, timeCounter;
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
		gameStateEventMaps = new HashMap<GameState, Runnable>();
		
		this.defaultFont = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ARCADEPI.TTF"), 20);
		
		this.stage = stage;
		initializeWindow();
		
		sceneLists = new ArrayList<BaseScene>();
		sceneLists.add(new StartingScene("starting", stage));
		sceneLists.add(new MainMenuScene("main_menu", stage));
		sceneLists.add(new SelectCharacterScene("select_character", stage));
		sceneLists.add(new GameScene("game", stage));
		
		// Initialize game state
		initializeGameState();
		
		// Initialize renderer
		Renderer.initialize(gc);

		// Initialize handler
		initializeKeyboardHandler();
		initializeMouseHandler();
		
		// Start game loops
		initializeGameLoop();
	}
	
	public void initializeGameState() {
		maxExp = 100;
		exp = 0;
		level = 1;
		
		timeCounter = 0;

		gameState = GameState.PLAY;
		setCurrentScene(0);
		
		character = PlayerCharacter.BRAVES;
	}
	
	private void initializeWindow() {
		stage.setTitle("Vampire Survivest");
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
	
	private void initializeGameLoop() {
		new AnimationTimer() {
			
			private long lastTime = System.nanoTime();
			
			private float time = 0.f;
			private int fps = 0;
			
			@Override
			public void handle(long currentTime) {	
				// Calculate delta time
				float deltaTime = (currentTime - lastTime) / 1000000000.f;
				
				// FPS Counter
				time += deltaTime;
				if (time >= 1.f) {
					time -= 1.f;
					stage.setTitle(String.format("Vampire Survivest, %3d fps", fps));
					fps = 0;
				}
				
				// Update
				update(deltaTime);
				
				fps++;	
				lastTime = currentTime;
			}
		}.start();
	}
	
	private void update(float deltaTime) {
		if (gameState != GameState.UPGRADE && exp >= maxExp) {
			level++;
			setGameState(GameState.UPGRADE);
		}
		
		// Clear screen
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		
		// Call update
		sceneLists.get(currentScene).update(deltaTime);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public GraphicsContext getGraphicsContext() {
		return gc;
	}
	
	public void setCurrentScene(int index) {
		currentScene = index;
		sceneLists.get(currentScene).onLoadScene();
	}
	
	public void nextScene() {
		setCurrentScene(currentScene + 1);
	}

	public Font getDefaultFont() {
		return defaultFont;
	}

	public float getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(float maxExp) {
		this.maxExp = maxExp;
	}

	public float getExp() {
		return exp;
	}

	public void setExp(float exp) {
		this.exp = exp;
	}
	
	public void nextLevel() {
		GameLogic.getInstance().setExp(GameLogic.getInstance().getExp() - GameLogic.getInstance().getMaxExp());
		GameLogic.getInstance().setMaxExp(GameLogic.getInstance().getMaxExp() * 1.75f);
		GameLogic.getInstance().setGameState(GameState.PLAY);
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
			gameStateEventMaps.get(gameState).run();
		}
	}
	
	public void setOnGameStateChangeTo(GameState gameState, Runnable e) {
		gameStateEventMaps.put(gameState, e);
	}

	public PlayerCharacter getCharacter() {
		return character;
	}

	public void setCharacter(PlayerCharacter character) {
		this.character = character;
	}

	public int getTimeCounter() {
		return timeCounter;
	}

	public void setTimeCounter(int timeCounter) {
		this.timeCounter = timeCounter;
	}
	
	public String timeCounterToString() {
		return String.format("%02d:%02d", timeCounter / 60, timeCounter % 60);
	}
}
