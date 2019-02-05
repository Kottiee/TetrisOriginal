package tetris_original;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class DropTest extends JPanel implements KeyListener {

	enum Tetrominos {noshape, zshape, sshape, lineshape, squareshape, tshape,
			lshape, mirrorl}
	private Tetrominos shapeName;


	/** One Piece composition as Coords 4x2 aray*/
	int[][] piece = { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } };
	/** Wxh unit board grid*/
	int[][] board;
	Timer timer;
	ScheduleTask task;

	private int WIDTHpx = 300;
	private int HEIGHTpx = 600;
	private int WIDTH = 10;
	private int HEIGHT = 20;
	private int blockWPx = 0;
	private int blockHPx = 0;

	/**Pieceの現在位置X*/
	private int curX;
	private int curY;

	private boolean isStarted = false;
	private boolean isPaused = false;



	//Constructor
	public DropTest() {
		setFocusable(true);
		blockWPx = WIDTHpx/WIDTH;
		blockHPx = HEIGHTpx/HEIGHT;
		board = new int[HEIGHT][WIDTH];
		newPiece();
	


		System.out.println(blockWPx);
		addKeyListener(this);
		timer = new Timer();
		task = new ScheduleTask();

		timer.scheduleAtFixedRate(task, 300, 300);


	}
	public int getWp() {
		return WIDTHpx;
	}
	public int getHp() {
		return HEIGHTpx;
	}

	//return current Piece max width (はみ出さないように
	public int maxPX() {
		int max = piece[0][0];
		for(int i=0; i<4; i++) {
			max = Math.min(max, piece[i][0]);
		}
		return max;
	}
	//return max y coord of Piece(下端の検出）
	public int minPY() {
		int min = piece[0][1];
		for(int i=0; i<4; i++) {
			min = Math.min(min, piece[i][1]);
		}
		min = Math.abs(min);
		return min+1;
	}

	//create new Piece .board[][]のIndexはそれぞれx,y座標を表しており、piece[][]のIndexもそれぞれx,y座標を表しているのでこのようになる。
	public void newPiece() {
		curX = WIDTH/2;
		curY = 0;
	}

	//Rotation

	//tryMove and move
	public void tryMove(int x, int y) {
//		System.out.println("curx"+curX);
//		System.out.println("cury"+curY);
		
		if (!(0>(curX+maxPX()+x)||(curX+maxPX()+x)>=WIDTH-1)) {
			
			if (!(HEIGHT<=curY+minPY()+y)) {
				curY += y;
				curX += x;
			}
			else {
				dropped();
			}
		}

		for (int i = 0; i<4; i++) {
			if(board[curY+piece[i][1]+2][curX+piece[i][0]] == 1) {
				dropped();
				
			}
		}
		
		
		repaint();

	}
	//dropping
	public void dropping() {
		tryMove(0,1);
	}
	//dropped
	public void dropped() {
		for(int i=0; i<4; i++) {
			board[curY+piece[i][1]+1][curX+piece[i][0]] = 1;
		}
		
		//debug
//		for(int[] n:board) {
//
//			for(int i:n) {
//				System.out.print(i);
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//		
		newPiece();
		
	}

	//TimerTask run method Override

	//Paint method　
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTHpx, HEIGHTpx);
		g.setColor(Color.black);

		//Draw pieces on board(piled ones)
		for(int y=0; y<board.length; y++) {
			for(int x=0; x<board[y].length; x++) {
				if(board[y][x]==1) {
					g.fillRect((x*blockWPx)+1, (y*blockHPx)+1, blockWPx-2, blockHPx-2);
				}
			}
		}
		//Draw current moving pice
		for(int i = 0; i<4; i++) {
			int x = curX + piece[i][0];
			int y = curY + piece[i][1];
//			System.out.println(x + " " + y);
			g.fillRect((x*blockWPx)+1, (y*blockHPx)+1, blockWPx-2, blockHPx-2);
		}
	}
	//

	//keylisten and move
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch(keycode) {
		case(KeyEvent.VK_A):
			tryMove(-1,0);
			break;

		case(KeyEvent.VK_S):
			tryMove(0,1);
			break;

		case(KeyEvent.VK_D):
			tryMove(1,0);
			break;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		DropTest test = new DropTest();
		frame.setSize(test.getWp(),test.getHp());
		frame.add(test);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	private class ScheduleTask extends TimerTask{
		@Override
		public void run() {
			dropping();


//			System.out.println("timer test");
		}
	}


}
