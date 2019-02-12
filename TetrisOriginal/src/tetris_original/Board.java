package tetris_original;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements KeyListener {



	/** Wxh unit board grid*/
	int[][] board;
	private int[] highestY = new int[10];
	
	
	private int score = 0;
	
	Timer timer;
	ScheduleTask task;
	
	private int board_W = 300;
	private int board_H = 660;

	private int WIDTH = 12;
	private int HEIGHT = 22;
	private int blockPx = 0;

	private boolean[] filledLines;
	private boolean[] removedLines;

	private Shape shape;
	private int[][] curPiece;
	/**Pieceの現在位置X*/
	private int curX;
	private int curY;

	private boolean isStarted = false;
	private boolean isPaused = false;
	private boolean isGameOver = false;

//////////////////////////////////////////////////////////

	public Board() {
		setFocusable(true);
		blockPx = board_W / WIDTH;
		board = new int[HEIGHT][WIDTH];
		setSize(board_W,board_H);
		//Pieceを初期化
		newPiece();
		addKeyListener(this);
//		timer = new Timer();
//		task = new ScheduleTask();
//		timer.scheduleAtFixedRate(task, 300, 300);
//		
	}



	/** BoardのサイズをDimension型で返す */
	public Dimension getWHSize() {
		return new Dimension(board_W,board_H);
	}

	//create new Piece .board[][]のIndexはそれぞれx,y座標を表しており、piece[][]のIndexもそれぞれx,y座標を表しているのでこのようになる。
	public void newPiece() {
		shape = new Shape();

		//動作するピースの二次元配列をShapeオブジェクトから取得
		curPiece = shape.getPiece();

		curX = WIDTH / 2;
		curY = 2;
	}


	/**y=x, x=(-y) の変換を行っている
	 *
	 */
	public int[][] computeRotation(int[][] piece) {
		int[][] nextPiece = new int[4][2];
		for(int i=0; i<4;i++) {

			nextPiece[i][1] = piece[i][0];
			nextPiece[i][0] = -piece[i][1];

		}
		return nextPiece;
	}


	//return current Piece max width (はみ出さないように
	public int minX(int[][] piece) {
		int min = piece[0][0];
		for (int i = 0; i < 4; i++) {
			min = Math.min(min, piece[i][0]);
		}
		return min;
	}
	public int maxX(int[][] piece) {
		int max = piece[0][0];
		for (int i = 0; i < 4; i++) {
			max = Math.max(max, piece[i][0]);
		}
		return max;
	}

	//return max y coord of Piece(下端の検出）
	public int maxY(int[][] piece) {
		int max = piece[0][1];
		for (int i = 0; i < 4; i++) {
			max = Math.max(max, piece[i][1]);
		}
		return max;
	}



	//Blockが次の移動先でかぶるかどうか。４つすべて見ている。
	public boolean hitCheck(int newX, int newY, int[][] piece) {
		for(int i=0; i<4; i++) {
			if(board[piece[i][1]+curY+newY]
					[piece[i][0]+curX+newX]==1) {
				return false;
			}
		}
		return true;
	}



	/** 左右方向移動チェックと移動
	 * @param x 方向に応じたX増加量
	 * @param y 方向に応じたY増加量
	 */
	public boolean tryMoveLR(int newX, int newY, int[][] piece) {
		//			System.out.println("curx"+curX);
		//			System.out.println("cury"+curY);

		//newXは絶対値ではないのでそのまま足していいはず？
		int leftX = curX+minX(piece)+newX;
		int rightX = curX+maxX(piece)+newX;

		int bottomY = curY+maxY(piece)+newY;

		//これがTrueなら次の移動先は少なくともボードの範囲内
		//outlineのオフセット分も考慮する
		if ((1 <= leftX && rightX <= WIDTH - 2)
				&& bottomY<=HEIGHT-2 ){
			if(hitCheck(newX, newY,piece)) {
				curX+=newX;
				curY+=newY;
				repaint();
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		
	}

	/**下に移動できるかどうか判定したのちcurYを更新
	 * FalseならDroppedを呼ぶ
	 * @param newX hitCheckに渡すために必要
	 * @param newY
	 */
	public void tryMoveDown(int newX, int newY) {
		int bottomY = curY+maxY(curPiece)+newY;
		if(bottomY<=HEIGHT-2&&(hitCheck(newX,newY,curPiece))) {
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
	/**Pieceの着地処理。着地点のboard座標に1を代入し、newPieceで新しいPieceを作る
	 * 並行してFilledlinesの判定もおこなう
	 * */
	public void dropped() {
		
		//boardのY座標は、curY(Pieceの現在位置）とそのPieceの各BlockのY座標を足したものに当たるので次のような式になる。
		for (int i = 0; i < 4; i++) {
			board[curY + curPiece[i][1]]
					[curX + curPiece[i][0]] = 1;
			if(curY+curPiece[i][1] == 1) {
				System.out.println(curY+curPiece[i][1]);
				gameOver();
			}
		}
		
		
		filledLines = new boolean[board_H];
		//行が埋まっているか判断するためのカウントナンバー
		int filles = 0;
		//行が埋まっているかのFlag
		boolean filledFlag = false;
		// Boardを行ごとに走査し、1が入った要素をfillesに格納し、fillesの数でfilled or notを判定。fillssは行ごとに初期化する
		for (int y = 0; y < board.length; y++) {
			filles = 0;
			for (int x = 0; x < board[y].length; x++) {
				if(board[y][x]==1) {
					filles+=1;
//					System.out.print(filledGrids); debug
				}
			}
//			System.out.println(""); debug
			//fillesが１０であるということは埋まった行があるので何行めが埋まったか記録
			if(filles ==10) {
				filledFlag = true;
				filledLines[y] = true;
				filles = 0;
			}
		}
		if(filledFlag) {
//			System.out.println("filled");
			removeLine();
		}
		
		
		newPiece();
	}
	
	/**列を消去*/
	public void removeLine() {
		removedLines = new boolean[board_H];
		int removeCount = 0; //ブロックをいくつ下げるか
		int removeStartedY = -1; //ブロックをどこから下げるのに使う
		for(int i=0; i<filledLines.length;i++) {
			if(filledLines[i]) {
				Arrays.fill(board[i], 0);
				removedLines[i] = true;
				if(removeStartedY<0) {
					removeStartedY = i;
				}
				removeCount+=1;
			}
			 
		}
		Arrays.fill(filledLines, false);
		levelDecrease(removeStartedY, removeCount);
		addScore(removeCount);
	}
	public void levelDecrease(int start, int counts) {
		System.out.println("start "+start+"; counts "+counts);
		for (int y = start-1; y > 0; y--) {
			for (int x = 0; x < board[y].length; x++) {
				board[y+counts][x] = board[y][x];
				

			}
		}
	}
	
	/**Score加算
	 * @param removeCount
	 */
	public void addScore(int removeCount) {
		score += 10*removeCount*removeCount;
	}
	
	public void paintComponent(Graphics g) {
		//この一文は重要。これがないと前のPaintが残ってしまう。
		super.paintComponent(g);
		debugPaint(g);
		//Draw Dropped Pieces
		g.setColor(Color.black);
		paintOutline(g);
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
		paintGUI(g);
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
	
	
	/**GUI表示用のPaintメソッド */
	public void paintGUI(Graphics g) {
		g.setColor(Color.red);

		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("Score : "+score, 200, 50);
		if(isPaused) {
			g.setFont(new Font("TimesRoman", Font.BOLD, 30));
			g.drawString("Pause", 130, 300);	

		}
		if(!isStarted) {
			g.setFont(new Font("TimesRoman", Font.BOLD, 30));
			g.drawString("Press X to start", 50, 270);
		}
		if(isGameOver) {
			g.drawString("GameOver", 75, 300);
		}		
	}
	
	public void paintOutline(Graphics g) {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if(y==0||y==HEIGHT-1) {
					g.fillRect((x * blockPx)+1 , (y * blockPx) + 1, blockPx - 2, blockPx - 2);
				}
				if(x==0||x==board[y].length-1) {
				g.fillRect((x * blockPx) + 1, (y * blockPx) + 1, blockPx - 2, blockPx - 2);
				}
			}
		}
	}
	
	
	
	
	public void start() {
		if(!isStarted&&!isPaused) {	
			for(int y=0; y<board.length; y++) {
				for(int x=0; x<board[y].length; x++) {
					board[y][x] = 0;
				}
			}
			resume();
			isStarted = true;
			isGameOver = false;
			System.out.println("DEBUG: game started, isGameOver flag is false");
	
		}
	}
	
	public void pause() {
		if(!isPaused) {
			isPaused = true;
			timer.cancel();
			repaint();
			System.out.println("DEBUG: game paused");
			
		}
		else {
			resume();
			isPaused = false;
			System.out.println("DEBUG: game resume");
			
		}

	}
	
	public void gameOver() {	
		if(isStarted) {
			timer.cancel();
			repaint();
			isStarted = false;
			isGameOver = true;		
		}
	}
	
	private void resume() {
		timer = new Timer();
		task = new ScheduleTask();
		timer.scheduleAtFixedRate(task, 0, 500);
	}
	

	//keylisten and move
	@Override
	public void keyPressed(KeyEvent e) {
		
		int keycode = e.getKeyCode();
		switch (keycode) {
		case (KeyEvent.VK_A):
			if(!isPaused&&isStarted) {
				tryMoveLR(-1, 0, curPiece);
				
			}
			break;
		case (KeyEvent.VK_S):
			if(!isPaused&&isStarted) {
				tryMoveDown(0, 1);
				
			}
			break;
		case (KeyEvent.VK_D):
			if(!isPaused&&isStarted) {
				tryMoveLR(1, 0, curPiece);
				
			}
			break;

		//rotation
		case (KeyEvent.VK_K):
			if(!isPaused&&isStarted) {
				if(tryMoveLR(0,0,computeRotation(curPiece))) {
					curPiece = computeRotation(curPiece);
					
				}
			}
			break;
		case (KeyEvent.VK_P):
			pause();
			break;
		case (KeyEvent.VK_L):
			start();
			break;
		//change Piece(DEBUG)
//		case (KeyEvent.VK_I):
//			curPiece.setRandomPiece();
//			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	@Override
	public void keyReleased(KeyEvent e) {

	}

	private class ScheduleTask extends TimerTask{
		@Override
		
		public void run() {
			
				tryMoveDown(0, 1);
			

		}
	}

}
