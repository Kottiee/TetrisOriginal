package explain_draw;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Main {
//	private JLabel status;

//	public JLabel getStatusLabel() {
//		return status;
//
//	}
	public void init() {
		JFrame frame = new JFrame();
		Board board = new Board();
		
		frame.setSize(400,700);

		frame.add(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		frame.setVisible(true);
//		board.gameStart();

	}

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Main game = new Main();
		game.init();


	}

}
