package tetris_original;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements KeyListener {



	/** Wxh unit board grid*/
	int[][] board;
	Timer timer;
	ScheduleTask task;

	public JLabel status;
	private Main parent;

	private int board_W = 300;
	private int board_H = 600;
	private int WIDTH = 10;
	private int HEIGHT = 20;
	private int blockPx = 0;

	/**全ての回転の形を格納するリスト*/
	private ArrayList<int[][]> allRotations =new  ArrayList<int[][]>();
	/**0ー３まで*/
	private int curRotation;

	private Shape shape;
	private int[][] curPiece;
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

//		status = new JLabel("test");
//		add(status,new BorderLayout().SOUTH);

		//Pieceを初期化
		newPiece();

		addKeyListener(this);
		timer = new Timer();
		task = new ScheduleTask();
		timer.scheduleAtFixedRate(task, 300, 300);

	}



	/** WindowのサイズをDimension型で返す */
	public Dimension getWHSize() {
		return new Dimension(board_W,board_H);
	}

	//create new Piece .board[][]のIndexはそれぞれx,y座標を表しており、piece[][]のIndexもそれぞれx,y座標を表しているのでこのようになる。
	public void newPiece() {
		shape = new Shape();

		//動作するピースの二次元配列をShapeオブジェクトから取得
		curPiece = shape.getPiece();

		setAllVal(curPiece);

		//DEBUG DEBUG
//		for(int[][] i: allRotations) {
//			for(int[] n: i) {
//				for(int x: n)
//				System.out.print(x+",");
//			}
//			System.out.print("..");
//		}
//		System.out.println("");

		curRotation = 0;

		curX = WIDTH / 2;
		curY = 2;
		System.out.println(curPiece + " NEWWW");
	}


	/**一つのピースに対する全ての回転の形をArraylistに格納
	 * @param piece
	 */
	public void setAllVal(int[][] piece) {
		this.allRotations.add(piece);

		//ここまでは正常に動いてる（allPieceValに正しい値が入ってる）
		for(int i=0; i<3; i++) {
			int[][] temp = computeRotation(this.allRotations.get(i));
			this.allRotations.add(temp);
//			for(int[] x: allPieceVal.get(i+1)) {
//				for(int n: x) {
//					System.out.print(n+",");
//				}
//				System.out.print("..");
//			}
//			System.out.println("");
		}
		for(int[][] i: allRotations) {
			for(int[] n: i) {
				for(int x: n)
				System.out.print(x+",");
			}
			System.out.print("..");
		}
		System.out.println("");




	}

	/**curRotation変数をインクリメントし、次の回転の形をcurPieceに代入、*/
	public void nextRotation() {
		if (curRotation==2) {

			curRotation = 0;
			this.curPiece = allRotations.get(curRotation);
		}
		else{
			curRotation += 1;
			this.curPiece = allRotations.get(curRotation);

		}
		//DEBUG DEBUG
//		for(int[] i: curPiece) {
//			for(int n: i) {
//				System.out.print(n+",");
//			}
//			System.out.print("..");
//		}
//		System.out.println("");

	}


	//return current Piece max width (はみ出さないように
	public int minX() {
		int min = curPiece[0][0];
		for (int i = 0; i < 4; i++) {
			min = Math.min(min, curPiece[i][0]);
		}
		return min;
	}
	public int maxX() {
		int max = curPiece[0][0];
		for (int i = 0; i < 4; i++) {
			max = Math.max(max, curPiece[i][0]);
		}
		return max;
	}

	//return max y coord of Piece(下端の検出）
	public int maxY() {
		int max = curPiece[0][1];
		for (int i = 0; i < 4; i++) {
			max = Math.max(max, curPiece[i][1]);
		}
		return max;
	}


	//
	/**y=x, x=(-y) の変換を行っている
	 *
	 */
	public int[][] computeRotation(int[][] piece) {

		for(int i=0; i<4;i++) {

			int tempY = -(piece[i][1]);
			piece[i][1] = piece[i][0];
			piece[i][0] = tempY;

		}

//		for(int[] i: curPiece) {
//			for(int n: i) {
//				System.out.print(n+",");
//			}
//			System.out.print("..");
//		}
//		System.out.println("");


		return piece;
	}



	//Blockが次の移動先でかぶるかどうか。４つすべて見ている。
	public boolean hitCheck(int newX, int newY) {
		for(int i=0; i<4; i++) {
			if(board[curPiece[i][1]+curY+newY]
					[curPiece[i][0]+curX+newX]==1) {
				return false;
			}

		}
		return true;
	}



	/** 左右方向移動チェックと移動
	 * @param x 方向に応じたX増加量
	 * @param y 方向に応じたY増加量
	 */
	public void tryMoveLR(int newX, int newY) {
		//			System.out.println("curx"+curX);
		//			System.out.println("cury"+curY);

		//newXは絶対値ではないのでそのまま足していいはず？
		int leftX = curX+minX()+newX;
		int rightX = curX+maxX()+newX;

		int bottomY = curY+maxY()+newY;

		//これがTrueなら次の移動先は少なくともボードの範囲内
		if ((0 <= leftX && rightX <= WIDTH - 1)
				&& bottomY<=HEIGHT-1 ){
			if(hitCheck(newX, newY)) {
				curX+=newX;
				curY+=newY;
				repaint();
			}
		}
	}

	/**下に移動できるかどうか判定したのちcurYを更新
	 * FalseならDroppedを呼ぶ
	 * @param newX hitCheckに渡すために必要
	 * @param newY
	 */
	public void tryMoveDown(int newX, int newY) {
		int bottomY = curY+maxY()+newY;
		if(bottomY<=HEIGHT-1&&(hitCheck(newX,newY))) {
			curY+=1;

		}
		else {
			dropped();
		}
		repaint();

	}


	/** 自動で落ちる動作*/
	public void dropping() {
		curY+=1;
		repaint();

	}
	/**Pieceの着地処理。着地点のboard座標に1を代入し、newPieceで新しいPieceを作る*/
	public void dropped() {
		for (int i = 0; i < 4; i++) {
			board[curY + curPiece[i][1]]
					[curX + curPiece[i][0]] = 1;
		}
		newPiece();

	}

	/**デバッグ用の座標表示システム
	 * @param g
	 */
	public void debugPaint(Graphics g) {
		//Draw Grid lines for DEBUG
		for(int i=0; i<WIDTH; i++) {
			g.setColor(Color.blue);
			g.drawLine(i*blockPx, 0, i*blockPx, HEIGHT*blockPx);
		}
		for(int i=0; i<HEIGHT; i++) {
			g.setColor(Color.blue);
			g.drawLine(0, i*blockPx, WIDTH*blockPx, i*blockPx);
		}
		//Draw Grid coords for DEBUG
		g.setColor(Color.red);
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				g.drawString(x+","+y, x*blockPx, y*blockPx+20);
			}
		}
		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString(String.valueOf(curX+" "+curY), 30, board_H-30);
	}


	public void paintComponent(Graphics g) {
		//この一文は重要。これがないと前のPaintが残ってしまう。
		super.paintComponent(g);
		debugPaint(g);
		//Draw Dropped Pieces
		g.setColor(Color.black);
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] == 1) {
					g.fillRect((x * blockPx) + 1, (y * blockPx) + 1, blockPx - 2, blockPx - 2);
				}
			}
		}

		//Draw current moving piece
		for (int i = 0; i < 4; i++) {
			int x = curX + curPiece[i][0];
			int y = curY + curPiece[i][1];
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
			tryMoveLR(-1, 0);
			break;

		case (KeyEvent.VK_S):
			tryMoveDown(0, 1);

			break;

		case (KeyEvent.VK_D):
			tryMoveLR(1, 0);
			break;

		//rotation
		case (KeyEvent.VK_K):
			nextRotation();
			break;
		//change Piece(DEBUG)
//		case (KeyEvent.VK_I):
//			curPiece.setRandomPiece();
//			break;
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
//			tryMoveDown(0, 1);

		}
	}

}
