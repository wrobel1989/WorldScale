package testjavafx;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player {

	private boolean isplaying;

	private Clip clip;
	private AudioInputStream soundIn;
	
	public Player(String wavname){
		this.isplaying = false;
		File soundFile = new File(wavname);
        soundIn=null;
		try {
			soundIn = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
         AudioFormat format = soundIn.getFormat();
         DataLine.Info info = new DataLine.Info(Clip.class, format);

         clip = null;
		try {
			clip = (Clip)AudioSystem.getLine(info);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
         try {
			clip.open(soundIn);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
         clip.stop();
	   
	}
	
	public void SetPlaying(boolean isplaying_){
		this.isplaying = isplaying_;
    	if(this.isplaying){
    		this.clip.loop(-1);
    	}else{
    		this.clip.stop();
    	}	
	};
	
	
}

