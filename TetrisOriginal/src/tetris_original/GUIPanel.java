package tetris_original;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIPanel extends JPanel{
	private int panel_W = 200;
	private int panel_H = 200;
	
	private int gui_W = 200;
	private int gui_H = 600;
	
	private JLabel score;
	private JButton debug;
	private JPanel upper;
	private JPanel middle;
	private JPanel bottom;
	
	private FlowLayout flayout  = new FlowLayout();
	
	
	public GUIPanel() {
		setSize(gui_W, gui_H);
		setBackground(Color.blue);
		setLayout(flayout);
		upper = new JPanel();
		middle = new JPanel();
		bottom = new JPanel();
		upper.setSize(panel_W, panel_H);
		upper.setBackground(Color.green);
		middle.setSize(panel_W, panel_H);
		middle.setBackground(Color.gray);
		bottom.setSize(panel_W, panel_H);
		bottom.setBackground(Color.cyan);
		add(upper);
		add(middle);
		add(bottom);
	
//		
//		
//		score = new JLabel("Score :");
//		upper.add(score);
//		
//		debug = new JButton("debug");
//		middle.add(debug);
//		bottom.add(debug);
		
		

		
		
		
	}

	

}
