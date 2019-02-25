package tetris_original;


import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 

import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

/**このクラスのインスタンスはそれぞれ一つのWavファイルの制御（再生、停止など）を担当する。
 * Fileの数だけプレイヤーをつくることになるが、それが良いことなのかはちょっとわからない。
 */
public class SoundPlayer
{ 

	// to store current position 
	private Long currentFrame; 
	private Clip clip; 
	
	// current status of clip 
	private String status = " "; 
	
	private AudioInputStream audioInputStream; 
	private String filePath; 
	
	
	/**コンストラクタ。Fileパスを渡してSoundPlayerオブジェクトを生成する。
	 */
	public SoundPlayer(String filePath) 
		throws UnsupportedAudioFileException, 
		IOException, LineUnavailableException 
	{ 
		// create AudioInputStream object 
		this.filePath = filePath;
		audioInputStream = 
				AudioSystem.getAudioInputStream(new File(this.filePath).getAbsoluteFile()); 
		
		// create clip reference 
		clip = AudioSystem.getClip(); 
		clip.open(audioInputStream);

		
	} 

	
	// Method to loop play the clip 
	public void play() 
	{ 
		clip.loop(Clip.LOOP_CONTINUOUSLY); 

		status = "play"; 
		System.out.println(status);
	}
	//Method to play the audio one time
	public void playOnce() {
		clip.setMicrosecondPosition(0);
		clip.start();
	}
	
	
	// Method to pause the audio 
	public void pause() 
	{ 
		if (status.equals("paused")) 
		{ 
			System.out.println("audio is already paused"); 
			return; 
		} 
		this.currentFrame = 
		this.clip.getMicrosecondPosition(); 
		clip.stop(); 
		status = "paused"; 
	} 
	
	// Method to resume the audio 
	public void resumeAudio()
	{ 
		if (status.equals("play")) 
		{ 
			System.out.println("Audio is already "+ 
			"being played"); 
			return; 
		} 
		clip.close(); 
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame); 
		this.play(); 
	} 
	
	// Method to restart the audio 
	public void restart() throws IOException, LineUnavailableException, 
											UnsupportedAudioFileException 
	{ 
		clip.stop(); 
		clip.close(); 
		resetAudioStream(); 
		currentFrame = 0L; 
		clip.setMicrosecondPosition(0); 
		this.play(); 
	} 
	
	// Method to stop the audio 
	//クリップは一度CloseするとAudioInputStreamを再生成しなければ開けない
	public void stop()
	{ 
		currentFrame = 0L; 
		clip.stop(); 
		clip.close(); 
	} 
	
	// Method to jump over a specific part 
	public void jump(long c) throws UnsupportedAudioFileException, IOException, 
														LineUnavailableException 
	{ 
		if (c > 0 && c < clip.getMicrosecondLength()) 
		{ 
			clip.stop(); 
			clip.close(); 
			resetAudioStream(); 
			currentFrame = c; 
			clip.setMicrosecondPosition(c); 
			this.play(); 
		} 
	} 
	
	// Method to reset audio stream 
	public void resetAudioStream() 
	{ 
		try {
			audioInputStream = AudioSystem.getAudioInputStream( 
			new File(filePath).getAbsoluteFile()); 
			clip.open(audioInputStream); 
			//loopメソッドの呼び出しは playメソッドに任せる
//			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} 
		
	}
		

} 

