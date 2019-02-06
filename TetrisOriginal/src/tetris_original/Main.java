package tetris_original;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		JFrame frame = new JFrame();
		Board board = new Board();
		frame.add(board);
		frame.setSize(board.getWHSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		frame.setVisible(true);


	}

}
