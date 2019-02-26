package tetris_original;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds {
//
//	String bgmfile = "bin/tetris_original/tetris_Wavefile.wav";
//	String hitfile = "bin/tetris_original/hit.wav";
//	String filledfile = "bin/tetris_original/clearLine.wav";
//	String gameOverFile = "bin/tetris_original/gameOver.wav";
	String bgmfile = "/tetris_Wavefile.wav";
	String hitfile = "/hit.wav";
	String filledfile = "/clearLine.wav";
	String gameOverFile = "/gameOver.wav";
	SoundPlayer bgmSound;
	SoundPlayer hitSound;
	SoundPlayer filledSound;
	SoundPlayer gameOverSound;
	
	public Sounds(){
		try {
			hitSound = new SoundPlayer(hitfile);
			bgmSound = new SoundPlayer(bgmfile);
			filledSound = new SoundPlayer(filledfile);
			gameOverSound = new SoundPlayer(gameOverFile);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
}
