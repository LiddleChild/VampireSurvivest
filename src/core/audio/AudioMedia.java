package core.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/*
 * 
 * AudioMedia:
 * - Load audio sources
 * - A collection of all AudioMedia
 * 
 */

public class AudioMedia {

	public static final AudioMedia SELECT = new AudioMedia("audio/select.wav");
	public static final AudioMedia CONFIRM = new AudioMedia("audio/confirm.wav");
	
	public static final AudioMedia SWING1 = new AudioMedia("audio/swing_1.wav");
	public static final AudioMedia SWING2 = new AudioMedia("audio/swing_2.wav");
	public static final AudioMedia HIT = new AudioMedia("audio/hit.wav");
	public static final AudioMedia BIG_HIT = new AudioMedia("audio/big_hit.wav");
	public static final AudioMedia EXPLOSION_TICK = new AudioMedia("audio/08_Step_rock_02.wav");
	public static final AudioMedia EXPLOSION = new AudioMedia("audio/25_Wind_01.wav");
	public static final AudioMedia FORCE_WAVE = new AudioMedia("audio/04_Fire_explosion_04_medium.wav");

	public static final AudioMedia COLLECT = new AudioMedia("audio/collect.wav");
	public static final AudioMedia LEVEL_UP = new AudioMedia("audio/level_up.wav");

	public static final AudioMedia GAME_OVER = new AudioMedia("audio/88_Teleport_02.wav");

	public static final AudioMedia BGM = new AudioMedia("audio/Goblins_Den_(Regular).wav");
	public static final AudioMedia BGM_BATTLE = new AudioMedia("audio/Goblins_Dance_(Battle).wav");
	
	private MediaPlayer player;
	
	public AudioMedia(String path) {
		Media media = new Media(ClassLoader.getSystemResource(path).toExternalForm());
		player = new MediaPlayer(media);
		player.setVolume(0.3f);
	}
	
	public void play() {
        player.seek(Duration.ZERO);
		player.play();
	}
	
	public void stop() {
		player.stop();
	}
	
	public void setVolume(float amount) {
		player.setVolume(amount);
	}
	
	public void setRepeat(boolean repeat) {
		if (repeat) {			
			player.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					player.seek(Duration.ZERO);
					player.play();
				}
			});
		} else {
			player.setOnEndOfMedia(null);
		}
	}
}
