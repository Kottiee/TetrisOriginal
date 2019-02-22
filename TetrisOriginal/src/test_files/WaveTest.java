package test_files;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WaveTest {

	public static void main(String[] args)
    {
        Clip clip = null;
        int count = 5;      // (count+1)回 再生する
        AudioInputStream audioInputStream;
        try
        {   File soundFile = new File("C:\\Users\\school.6C-06\\Desktop\\Personal Temporaly\\tetris_Wavefile.wav");
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.loop(count);
        }
        catch (UnsupportedAudioFileException e)
        {   e.printStackTrace();  }
        catch (IOException e)
        {   e.printStackTrace();  }
        catch (LineUnavailableException e)
        {   e.printStackTrace();  }

        // 10秒経過したら終了する
        try
        {   Thread.sleep(1000000);  }
        catch (InterruptedException e)
        {  }
        clip.stop();
        System.exit(0);
    }

}
