package tetris_original;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements KeyListener {



	/** Wxh unit board grid*/
	int[][] board;
	Timer timer;
	ScheduleTask task;

	private JLabel status;

	private int board_W = 300;
	private int board_H = 600;
	private int WIDTH = 10;
	private int HEIGHT = 20;
	private int blockPx = 0;


	Shape curPiece;
	/**Pieceの現在位置X*/
	private int curX;
	private int curY;

	private boolean isStarted = false;
	private boolean isPaused = false;

//////////////////////////////////////////////////////////

	public Board() {
		setFocusable(true);
		blockPx = board_W / WIDTH;
		board = new int[HEIGHT][WIDTH];
		newPiece();
		status = new JLabel("test");
		add(status, new BorderLayout().CENTER);

		//debug
		System.out.println(blockPx);

		addKeyListener(this);
		timer = new Timer();
		task = new ScheduleTask();

		timer.scheduleAtFixedRate(task, 300, 300);

	}



	/** WindowのサイズをDimension型で返す */
	public Dimension getWHSize() {
		return new Dimension(board_W,board_H);
	}


	//return current Piece max width (はみ出さないように
//	public int maxPX() {
//		int max = piece[0][0];
//		for (int i = 0; i < 4; i++) {
//			max = Math.min(max, piece[i][0]);
//		}
//		return max;
//	}
//
//	//return max y coord of Piece(下端の検出）
//	public int minPY() {
//		int min = piece[0][1];
//		for (int i = 0; i < 4; i++) {
//			min = Math.min(min, piece[i][1]);
//		}
//		min = Math.abs(min);
//		return min + 1;
//	}

	//create new Piece .board[][]のIndexはそれぞれx,y座標を表しており、piece[][]のIndexもそれぞれx,y座標を表しているのでこのようになる。
	public void newPiece() {
		curPiece = new Shape();
		curX = WIDTH / 2;
		curY = 0;
	}

	//Rotation


	/** 指定した方向に移動できるかどうかだけ検出
	 * @param x
	 * @param y
	 */
	public void tryMove(int x, int y) {
		//			System.out.println("curx"+curX);
		//			System.out.println("cury"+curY);

		curX += x;
		curY += y;
//		if (!(0 > (curX + maxPX() + x) || (curX + maxPX() + x) >= WIDTH - 1)) {
//
//			if (!(HEIGHT <= curY + minPY() + y)) {
//				curY += y;
//				curX += x;
//			} else {
//				dropped();
//			}
//		}
//
//		for (int i = 0; i < 4; i++) {
//			if (board[curY + piece[i][1] + 2][curX + piece[i][0]] == 1) {
//				dropped();
//
//			}
//		}

		repaint();

	}

	//dropping
	/** 自動で落ちる動作
	 *
//	 */
//	public void dropping() {
//		tryMove(0, 1);
//	}

	//dropped
	public void dropped() {
		for (int i = 0; i < 4; i++) {
			board[curY + curPiece.getPiece()[i][1] + 1][curX + curPiece.getPiece()[i][0]] = 1;
		}
		newPiece();

	}

	//Paint method　
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, board_W, board_H);


		//Draw Grid lines for DEBUG
		for(int i=0; i<WIDTH; i++) {
			g.setColor(Color.blue);
			g.drawLine(i*blockPx, 0, i*blockPx, HEIGHT*blockPx);
		}
		for(int i=0; i<HEIGHT; i++) {
			g.setColor(Color.blue);
			g.drawLine(0, i*blockPx, WIDTH*blockPx, i*blockPx);
		}

		g.setColor(Color.red);
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				g.drawString(x+","+y, x*blockPx, y*blockPx+20);
			}
		}


//		//Draw Dropped Pieces
//		for (int y = 0; y < board.length; y++) {
//			for (int x = 0; x < board[y].length; x++) {
//				if (board[y][x] == 1) {
//					g.fillRect((x * blockPx) + 1, (y * blockPx) + 1, blockPx - 2, blockPx - 2);
//				}
//			}
//		}
		g.setColor(Color.black);
		//Draw current moving piece
		for (int i = 0; i < 4; i++) {
			int x = curX + curPiece.getPiece()[i][0];
			int y = curY + curPiece.getPiece()[i][1];
			//				System.out.println(x + " " + y);
			g.fillRect((x * blockPx) + 1, (y * blockPx) + 1, blockPx - 2, blockPx - 2);
		}
	}
	//

	//keylisten and move
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch (keycode) {
		case (KeyEvent.VK_A):
			tryMove(-1, 0);
			break;

		case (KeyEvent.VK_S):
			tryMove(0, 1);
			break;

		case (KeyEvent.VK_D):
			tryMove(1, 0);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private class ScheduleTask extends TimerTask{
		@Override
		public void run() {
//			dropping();


//			System.out.println("timer test");
		}
	}

}
