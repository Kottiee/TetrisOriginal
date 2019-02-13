package tetris_original;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Main {
	
	
	public void init() {
		JFrame frame = new JFrame();
		Board board = new Board();
		
		frame.setLocationRelativeTo(null);

		frame.add(board);
		//pack()メソッドが有効に働くためには対象コンポーネントが（この場合Boardクラスオブジェクト）
		//setPreferredSizeメソッドを使ってサイズ決定をしている必要がある。
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		frame.setVisible(true);

	}

	public static void main(String[] args) {
		Main game = new Main();
		game.init();


	}

}
