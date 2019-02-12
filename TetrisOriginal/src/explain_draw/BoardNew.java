package explain_draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

// Using Swing's components and containers
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardNew extends JFrame{
	 static int board_W = 300;
	 static int board_H = 660;
	 int WIDTH = 12;
	 int HEIGHT = 22;
	 int blockPx = 30;
	 int curX = 5;
	 int curY = 5;
	 private Canvas canvas;

	 int[][] board = new int[WIDTH][HEIGHT];
	 int[][] piece = {{ 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 }};

	public BoardNew() {

		setSize(board_W,board_H);

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(board_W,board_H));
		add(canvas);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
	}

	private class Canvas extends JPanel{
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.blue);


			g.setColor(Color.yellow);
			g.fillRect(curX*blockPx, curY*blockPx, blockPx, blockPx);

		}
		public void paintBlocks(Graphics g) {
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					if (board[y][x] == 1) {
						g.fillRect((x * blockPx) + 1, (y * blockPx) + 1, blockPx - 2, blockPx - 2);
					}
				}
			}
		}
	}


	public static  void main(String [] args) {
		BoardNew board = new BoardNew();


	}


}
