package sound_test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MidiTest {

	public static void main(String[] args) {

		//Sequencer Initialization
		Sequencer sequencer = null;
		try {
			//Sequencerを取得
			sequencer = MidiSystem.getSequencer();
			//無限Loopを設定
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

			sequencer.open(); //Whats that for?


			String filePath = "C:\\Users\\school.6C-06\\Desktop\\"
					+ "Personal Temporaly\\Tetris_Audio\\tetris_MIDIfile.mid";

			System.out.println("DEBUG try plying");
			play(filePath, sequencer);



			//catch stop key
			while(true) {
				System.out.println("input stop if you want to stop playing");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String input = in.readLine();
				if(input.equals("stop")) {
					stop(sequencer);
					break;

				}
			}


		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void play(String name, Sequencer player) {
		try {

			//ファイルストリームObjectを作成し、Sequence変数でStream音源ファイルを取得する
			FileInputStream in = new FileInputStream(name);
			Sequence sequence = MidiSystem.getSequence(in);

			in.close();//Streamのクローズ

			player.setSequence(sequence);	//Sequencer に sequence 変数を渡してセットする
			player.start();  //Sequencerをスタートする
			System.out.println("DEBUG music started");

		} catch (InvalidMidiDataException | IOException e) {
			e.printStackTrace();
		}

	}

	//Sequencerオブジェクトを渡してStopメソッドで再生を止める
	public static void stop(Sequencer player) {
		player.stop();
		player.close(); //Sequencerの解放　これをしないとプログラムが終わらない
	}

}
