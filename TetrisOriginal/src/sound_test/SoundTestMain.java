package sound_test;

import java.util.Scanner;

public class SoundTestMain {
	
	static String bgmPath = "bin/sound_test/tetris_Wavefile.wav"; 
	static String hitEffectPath = "bin/sound_test/hit.wav";
	
	public static void main(String[] args) 
	{ 
		try
		{ 
			WaveTest audioPlayer = 
							new WaveTest(SoundTestMain.bgmPath); 
			WaveTest hitEffect = 
							new WaveTest(SoundTestMain.hitEffectPath);
			
//			audioPlayer.play(); 
			Scanner sc = new Scanner(System.in); 
			
			while (true) 
			{ 
				System.out.println("1. pause"); 
				System.out.println("2. resume"); 
				System.out.println("3. restart"); 
				System.out.println("4. stop"); 
				System.out.println("5. Jump to specific time"); 
				System.out.println("6. play");
				System.out.println("7. play at once");
				int c = sc.nextInt();
				
				//効果音の再生と、BGMの制御の判別
				if(c==7) {
					hitEffect.gotoChoice(c);
				}
				else {
					audioPlayer.gotoChoice(c); 
					if (c == 4) 
					break; 
				}
			} 
			sc.close(); 
		} 
		
		catch (Exception ex) 
		{ 
			System.out.println("Error with playing sound."); 
			ex.printStackTrace(); 
		
		} 
	} 

}
