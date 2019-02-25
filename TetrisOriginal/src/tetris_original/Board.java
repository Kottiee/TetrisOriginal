package tetris_original;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements KeyListener {



	/** この二次元配列に接地したピースを記録する。配列の各要素はshapeEnum型、つまり７種類の定数もしくはNullのどれかになる*/
	Shape.shapeEnum[][] board;
	/**背景色用のColorクラスインスタンス */
	private Color backColor = new Color(20,0,25);
	
//	private int[] highestY = new int[10];
	
	private int score = 0;
	
	Timer timer;
	ScheduleTask task;
	
	//Boardの縦横ピクセルサイズ
	private int board_W = 300;
	private int board_H = 550;

	private int WIDTH = 12;
	private int HEIGHT = 22;
	//今のところBlockは正四角形とするのでフィールド変数は一つにする。
	private int blockPx = 0;
//	private int blockPxH = 0;

	/**行番号とIndexが対応しており、埋まった行にTrueを入れる*/
	private boolean[] filledLines;
	private boolean[] removedLines;

	/**Shapeクラスのインスタンス。つまりはBoard上で稼働しているピース。常に一つがBoardに存在するようになる*/
	private Shape shape;
	/**現在稼働しているpieceを4つのxy座標で表したもの。Shapeクラスのpiece変数のコピーが入る*/
	private int[][] curPiece;
	/**Pieceの現在位置X*/
	private int curX;
	private int curY;

	private boolean isStarted = false;
	private boolean isPaused = false;
	private boolean isGameOver = false;
	
	private Sounds sounds;

//////////////////////////////////////////////////////////
	//Constructor
	public Board() {
		
		//Initializing sounds;
		sounds = new Sounds();
				
		//この宣言はこのコンストラクによって生成されるインスタンスがキーボードに反応するために必要
		setFocusable(true);
		blockPx = board_W / WIDTH;
//		blockPxH = board_H / HEIGHT;
		//ここでboard二次元配列が全値Nullで初期化
		board = new Shape.shapeEnum[HEIGHT][WIDTH];
		this.setPreferredSize(new Dimension(board_W,board_H));
		Shape.initColor();
		
		newPiece();
		//keyboardからのイベントに反応するためのListener。thisを引数に入れることでこのコンストラクタで生成されるインスタンスがListenerをAddされることになる。
		addKeyListener(this);
	
	}


//	//getter
//	/** BoardのサイズをDimension型（Componentの縦横を表す整数２個）で返す (今んとこ不要）*/
//	public Dimension getBoardPxSize() {
//		return new Dimension(board_W,board_H);
//	}

	//create new Piece .board[][]のIndexはそれぞれx,y座標を表しており、piece[][]のIndexもそれぞれx,y座標を表しているのでこのようになる。
	public void newPiece() {
		shape = new Shape();

		//動作するピースの二次元配列をShapeオブジェクトから取得
		curPiece = shape.getPiece();

		//落ちてくるピースの初期位置を決定するアサインステイトメント。width/2することでほぼ中央から落とすことを表す
		//Yを２にする理由はcurPiece配列がマイナス値を含むため
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


	
	/**curPieceのｘ座標の最小値を求める。枠からはみ出さないようにするために必要。
	 * ｘ座標とはつまりpiece[i][0]のこと。[i][1]はy座標を表している。
	 * @param piece
	 * @return
	 */
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



	//現在のピースの各ブロックとすでに設置されたブロックが被らないかをチェック
	//Enum配列の空のIndexはNullである
	/**
	 * @param newX　押したキーの方向などに応じたXの増減
	 * @param newY	押したキーの方向などに応じたYの増減
	 * @param piece 現在のピース配列
	 * @return
	 */
	public boolean hitCheck(int newX, int newY, int[][] piece) {
		for(int i=0; i<4; i++) {
			//この式は、つまりboard[i]はｙなので、piece[i][1]と現在のピースに位置であるcurY、引数に受け取ったｙの増減を表すnewY
			//それらを全て足すことによって、もし移動が承認された場合の各ブロックの位置を求めている。もしその位置にnullでない、つまり
			//すでに接地したブロックがあれば、移動はできないのでfalseを返す。
			if(board[piece[i][1]+curY+newY]
					[piece[i][0]+curX+newX]!=null) {
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
			//さらにHitcheckを行って移動先にすでにブロックがないことを確認
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
	 * FalseならDroppedを呼びピースを接地させる。
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
		//このrepaintは必要ないかも
		repaint();

	}


	/** 自動で落ちる動作*/
	public void dropping() {
		curY+=1;
		repaint();

	}
	/**Pieceの着地処理。着地点のboard座標に現在のShapeNameを代入し、newPieceで新しいPieceを作る
	 * 並行してFilledlinesの判定もおこなう
	 * */
	public void dropped() {
		
		//play hit sound effect 
		sounds.hitSound.playOnce();
		
		//boardのY座標は、curY(Pieceの現在位置）とそのPieceの各BlockのY座標を足したものに当たるので次のような式になる。
		for (int i = 0; i < 4; i++) {
			board[curY + curPiece[i][1]]
					[curX + curPiece[i][0]] = shape.getShapeName();
			
			//Y座標が１であればGameOver
			if(curY+curPiece[i][1] == 1) {
				System.out.println("DEBUG dropped method each block's current y is "+curY+curPiece[i][1]);
				gameOver();
			}
		}
		
		
		//何行目が埋まったか示すBoolean配列
		filledLines = new boolean[board_H];
		
		//ある行に存在するブロックの数を数えることにより行のFilled or notを判定
		int filles = 0;
		//行が埋まっているかのFlag
		boolean filledFlag = false;
		// Boardを行ごとに走査し、1が入った要素をfillsに格納し、fillsの数でfilled or notを判定。fillssは行ごとに初期化する
		for (int y = 0; y < board.length; y++) {
			filles = 0;
			for (int x = 0; x < board[y].length; x++) {
				if(board[y][x]!=null) {
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
				Arrays.fill(board[i], null);
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
		
		//sound effect 
		sounds.filledSound.playOnce();
	}
	/**行を消去したあと、残った上部分を下げる。
	 * @param start
	 * @param counts
	 */
	public void levelDecrease(int start, int counts) {
		System.out.println("start "+start+"; counts "+counts);
		for (int y = start-1; y > 0; y--) {
			for (int x = 0; x < board[y].length; x++) {
				board[y+counts][x] = board[y][x];
				

			}
		}
	}
	
	/**Score加算　同時に消した行が多いほど高得点！
	 * @param removeCount
	 */
	public void addScore(int removeCount) {
		score += 10*removeCount*removeCount;
	}
	
	/* (非 Javadoc) このメソッドはピースの描画の核になっている。移動中のピースを描くロジックと接地済みブロックを描くロジックは分かれている。
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		//この一文は重要。これがないと前のPaintの軌跡が残ってしまう。
		super.paintComponent(g);
		setBackground(backColor);
		//debugpaintを有効にすればグリッドとその番号を直接表示する。
//		debugPaint(g);
		//Draw Dropped Pieces
		paintOutline(g);
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] != null) {
					//board[y][x]にある定数の名前をStringで取得し、それをShapeクラスのgetColorsメソッドに渡すことでColorを取得して色を決定する。
					g.setColor(Shape.getColors(board[y][x].name()));
					g.fillRect((x * blockPx) + 1, (y * blockPx) + 1, blockPx - 2, blockPx - 2);
				}
			}
		}
		g.setColor(shape.getCurColor());
//		System.out.println("DEBUG" +shape.getCurColor());
		
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
		g.setColor(Color.white);
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				g.drawString(x+","+y, x*blockPx, y*blockPx+20);
			}
		}
		g.setColor(Color.black);
		g.setFont(new Font("SansSerif", Font.BOLD, 20));
		g.drawString(String.valueOf(curX+" "+curY), 30, board_H-30);
	}
	
	
	/**GUI表示用のPaintメソッド */
	public void paintGUI(Graphics g) {
		g.setColor(Color.white);

		g.setFont(new Font("SansSerif", Font.BOLD, 20));
		g.drawString("Score : "+score, 200, 50);
		if(isPaused) {
			g.setFont(new Font("SansSerif", Font.BOLD, 30));
			g.drawString("Pause", 100, 300);	

		}
		if(!isStarted) {
			g.setFont(new Font("SansSerif", Font.BOLD, 30));
			g.drawString("Press X to start", 30, 270);
		}
		if(isGameOver) {
			g.drawString("GameOver", 75, 300);
		}		
	}
	
	public void paintOutline(Graphics g) {
		g.setColor(Color.gray);
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
					board[y][x] = null;
				}
			}
			resume();
			sounds.bgmSound.play();
			isStarted = true;
			isGameOver = false;
			System.out.println("DEBUG: game started, isGameOver flag is false");
	
		}
	}
	
	public void pause() {
		if((!isPaused) && isStarted) {
			isPaused = true;
			timer.cancel();
			sounds.bgmSound.pause();
			repaint();
			System.out.println("DEBUG: game paused");
			
		}
		else if (isPaused){
			resume();
			sounds.bgmSound.resumeAudio();
			isPaused = false;
			System.out.println("DEBUG: game resume");
			
		}

	}
	
	public void gameOver() {	
		if(isStarted) {
			timer.cancel();
			sounds.gameOverSound.playOnce();
			sounds.bgmSound.stop();
			sounds.bgmSound.resetAudioStream();
			isStarted = false;
			isGameOver = true;
			repaint();
			//repaint()をフラッグ変更の直後に入れることで、paintGUIメソッドでの条件式がすぐさま働くようになる
		}
	}
	
	/**PauseやGameoverでTimerをキャンセルしてTimerオブジェクトを消去するのでResumeではインスタンスの作成を行う。
	 * Timer関係をここで一括管理することでスレッドの乱立などに対処を容易にする。
	 */
	private void resume() {
		timer = new Timer();
		task = new ScheduleTask();
		timer.scheduleAtFixedRate(task, 0, 500);
	}
	

	//keylisten and move
	@Override
	public void keyPressed(KeyEvent e) {
		//keyが押下されるとKyePressedが駆動する。
		//KeyEventクラスオブジェクト のGetKeyCodeメソッドにより、キーボードのキーに対応したint型の整数を受け取る
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
		case (KeyEvent.VK_X):
			start();
			break;
		//change Piece(DEBUG)
//		case (KeyEvent.VK_I):
//			curPiece.setRandomPiece();
//			break;
		}

	}
	
	//この二つはKeyListenerを継承したクラスでは必ずオーバーライドしなくてはならない
	@Override
	public void keyTyped(KeyEvent e) {

	}
	@Override
	public void keyReleased(KeyEvent e) {

	}
	
	//runメソッドをオーバーライドするためにTimerTaskを継承したクラスが必要
	private class ScheduleTask extends TimerTask{
		@Override
		public void run() {		
			//この中にフレームごとに行う作業（描画以外）を書く
				tryMoveDown(0, 1);
			

		}
	}

}
