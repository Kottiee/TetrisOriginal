package explain_draw;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class test extends JPanel{

	public static void main(String [] args) {
		JFrame frame = new JFrame();
		test test = new test();
		frame.setSize(300,300);
		frame.add(test);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(100, 100, 100, 100);
	}
}