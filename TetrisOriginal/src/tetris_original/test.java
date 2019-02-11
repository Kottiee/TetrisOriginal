package tetris_original;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class test {
	
	
	public static void main (String [] args) {
		JFrame frame = new JFrame();
		frame.setSize(500,500);
		frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		JPanel panel1 = new JPanel();
		panel1.setSize(250,500);
		panel1.setBackground(Color.blue);
		
		JPanel panel2 = new JPanel();
		panel2.setSize(250,500);
		panel2.setBackground(Color.yellow);
		panel2.setLayout(new FlowLayout());
		panel2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		
		frame.add(panel1);
		frame.add(panel2);
		
		JPanel panel23 = new JPanel();
		panel23.setSize(250,200);
		panel23.setBackground(Color.green);
		panel2.add(panel23);
		panel23.add(btncr());
		panel23.add(btncr());
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
	}
	public static JButton btncr() {
		JButton btn = new JButton("test");
		return btn;
	}
}