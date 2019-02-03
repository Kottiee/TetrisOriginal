package tetris_original;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;


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
	private int HEIGHTpx = 500;
	private int WIDTH = 10;
	private int HEIGHT = 20;
	private int blockWPx = 0;
	private int blockHPx = 0;
	
	private int curX;
	private int curY;
	
	private boolean isStarted = false;
	private boolean isPaused = false;

	
	
	//Constructor
	public DropTest() {
		setFocusable(true);
		board = new int[WIDTH][HEIGHT];
		blockWPx = WIDTHpx/WIDTH;
		blockHPx = HEIGHTpx/HEIGHT;
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
	
	//Rotation 
	
	//tryMove and move
	public void tryMove(int x, int y) {
		curX += x;
		curY += y;
		
	}
	
	//TimerTask run method Override
	
	//Paint method　
	public void paint(Graphics g) {
		g.setColor(Color.black);
		for(int x = 0; x<board.length; x++) {
			for(int y=0; y<board[x].length; y++) {
				g.fillRect(blockWPx, blockHPx, blockWPx, blockHPx);
				
			}
		}
	}
	//
	
	//keylisten and move
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch(keycode) {
		case(KeyEvent.VK_W):
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
			tryMove(0, -1);
			System.out.println("timer test");
		}
	}


}
