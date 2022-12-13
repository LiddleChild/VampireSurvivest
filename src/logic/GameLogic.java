package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.Renderer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import scene.BaseScene;
import scene.GameScene;
import scene.MainMenuScene;
import scene.SelectCharacterScene;
import scene.StartingScene;

/*
 * 
 * GameLogic
 * - holds GameState & GameScene
 * - initializes Game Loop
 * - holds player Experiences & Level
 * - holds Time Counter
 * 
 */

public class GameLogic {
	
	private Window window;
	private GraphicsContext gc;
	
	private ArrayList<BaseScene> sceneLists;
	private int currentScene;
	
	private Font defaultFont;
	
	/*
	 * GAME STATE
	 */
	private GameState gameState;
	private Map<GameState, Runnable> gameStateEvents;
	
	private float maxExperience, experience;
	private int level;
	
	private int timeCounter;
	
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
		gameStateEvents = new HashMap<GameState, Runnable>();
		
		this.defaultFont = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ARCADEPI.TTF"), 20);
		
		window = new Window(stage);
		gc = window.getGraphicsContext();
		
		sceneLists = new ArrayList<BaseScene>();
		sceneLists.add(new StartingScene("starting", stage));
		sceneLists.add(new MainMenuScene("main_menu", stage));
		sceneLists.add(new SelectCharacterScene("select_character", stage));
		sceneLists.add(new GameScene("game", stage));

		Renderer.initialize(gc);
		initializeGameState();
		initializeGameLoop();
	}
	
	public void initializeGameState() {
		setMaxExperience(100.f);
		setExperience(0.f);
		level = 1;
		
		timeCounter = 0;

		setGameState(GameState.PLAY);
		setOnGameStateChangeTo(GameState.EXIT, () -> {
            Platform.exit();
            System.exit(0);
		});
		
		setCurrentScene(0);
		
		character = PlayerCharacter.BRAVES;
	}
	
	private void initializeGameLoop() {
		new AnimationTimer() {
			
			private long lastTime = System.nanoTime();
			
			private float time = 0.f;
			private int fps = 0;
			
			@Override
			public void handle(long currentTime) {	
				// Calculate time between frames
				// This will be frequently used
				float deltaTime = (currentTime - lastTime) / 1000000000.f;
				
				// FPS Counter
				time += deltaTime;
				if (time >= 1.f) {
					time -= 1.f;
					window.setTitle(String.format("Vampire Survivest | %3d fps", fps));
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
	
	// Scene
	public void setCurrentScene(int index) {
		currentScene = index;
		sceneLists.get(currentScene).onLoadScene();
	}
	
	public void nextScene() {
		setCurrentScene(currentScene + 1);
	}
	
	// Font
	public Font getDefaultFont() {
		return defaultFont;
	}

	// Experience & Level
	public float getMaxExperience() {
		return maxExperience;
	}

	public void setMaxExperience(float maxExperience) {
		this.maxExperience = maxExperience;
	}

	public float getExperience() {
		return experience;
	}

	public void setExperience(float experience) {
		this.experience = experience;
	}
	
	public void nextLevel() {
		setExperience(getExperience() - getMaxExperience());
		setMaxExperience(maxExperience * 1.75f);
		setGameState(GameState.PLAY);
	}
	
	public void incrementLevel() {
		level++;
	}

	public int getLevel() {
		return level;
	}

	// GameState
	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
		if (gameStateEvents.get(gameState) != null) {
			gameStateEvents.get(gameState).run();
		}
	}
	
	public void setOnGameStateChangeTo(GameState gameState, Runnable e) {
		gameStateEvents.put(gameState, e);
	}

	// PlayerCharcter
	public PlayerCharacter getCharacter() {
		return character;
	}

	public void setCharacter(PlayerCharacter character) {
		this.character = character;
	}

	// Counter
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
