package net.mbadelek.universe.utils;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Player {

	private Clip clip;
	private AudioInputStream soundIn;

	public Player(String wavname) throws Exception {//music
		File soundFile = new File(wavname);
		soundIn = AudioSystem.getAudioInputStream(soundFile);
		clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, soundIn.getFormat()));
		clip.open(soundIn);
	}

	public void setPlaying(boolean isPlaying) {
		if (isPlaying)
			this.clip.loop(-1);
		else
			this.clip.stop();
	}
}
