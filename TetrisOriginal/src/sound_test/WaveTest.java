package sound_test;


import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 

import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

public class WaveTest
{ 
	
	//アクセス修飾子がPrivateでないと再生するクリップが不安定になる。
	// to store current position 
	Long currentFrame; 
	Clip clip; 
	
	// current status of clip 
	String status; 
	
	AudioInputStream audioInputStream; 
	String filePath; 

	// constructor to initialize streams and clip 
	public WaveTest(String path) 
		throws UnsupportedAudioFileException, 
		IOException, LineUnavailableException 
	{ 
		// create AudioInputStream object 
		filePath = path;
		
		audioInputStream = 
				AudioSystem.getAudioInputStream(new File(this.filePath).getAbsoluteFile()); 
		
		// create clip reference 
		clip = AudioSystem.getClip(); 
		
		// open audioInputStream to the clip 
		clip.open(audioInputStream); 
		
		
		
	} 

	
	
	// Work as the user enters his choice 
	
	public void gotoChoice(int c) 
			throws IOException, LineUnavailableException, UnsupportedAudioFileException 
	{ 
		switch (c) 
		{ 
			case 1: 
				pause(); 
				break; 
			case 2: 
				resumeAudio(); 
				break; 
			case 3: 
				restart(); 
				break; 
			case 4: 
				stop(); 
				break; 
			case 5: 
				System.out.println("Enter time (" + 0 + 
				", " + clip.getMicrosecondLength() + ")"); 
				Scanner sc = new Scanner(System.in); 
				long c1 = sc.nextLong(); 
				jump(c1); 
				break; 
			case 6:
				play();
				break;
			case 7:
				playAtOnce();
				break;
				
				
	
		} 
	
	} 
	
	// Method to play the audio 
	public void play() 
	{ 
		
		//
		this.clip.loop(Clip.LOOP_CONTINUOUSLY); 
		//start the clip 
		this.clip.start(); 
		
		this.status = "play"; 
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
		this.clip.stop(); 
		this.status = "paused"; 
	} 
	
	// Method to resume the audio 
	public void resumeAudio() throws UnsupportedAudioFileException, 
								IOException, LineUnavailableException 
	{ 
		if (status.equals("play")) 
		{ 
			System.out.println("Audio is already "+ 
			"being played"); 
			return; 
		} 
		this.clip.close(); 
		this.resetAudioStream(); 
		this.clip.setMicrosecondPosition(currentFrame); 
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
	public void stop() throws UnsupportedAudioFileException, 
	IOException, LineUnavailableException 
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
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, 
											LineUnavailableException 
	{ 
		audioInputStream = AudioSystem.getAudioInputStream( 
		new File(filePath).getAbsoluteFile()); 
		clip.open(audioInputStream); 
		
		//ループ再生はPlayメソッドに任せる
//		clip.loop(Clip.LOOP_CONTINUOUSLY); 
	} 
	//setMicrosecondPositionでStreamを巻き戻すのがポイント
	public void playAtOnce() {
		clip.setMicrosecondPosition(0);
		clip.start();
	}

} 
