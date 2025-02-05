package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	Clip clip;
	URL sourdUrl[] = new URL[30];
	
	public Sound() {
		sourdUrl[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
		sourdUrl[1] = getClass().getResource("/sound/coin.wav");
		sourdUrl[2] = getClass().getResource("/sound/powerup.wav");
		sourdUrl[3] = getClass().getResource("/sound/unlock.wav");
		sourdUrl[4] = getClass().getResource("/sound/fanfare.wav");
		
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(sourdUrl[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}

}
