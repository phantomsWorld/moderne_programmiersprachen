package swingGuiTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
	
public class Bar extends JComponent{

	private int val;
	private Color c;

	public Bar(int value){
	setPreferredSize(new Dimension(100,20));
	this.val = value;
	this.setColor(Color.BLACK);
	}
	
	public void setColor1(Color c) {
		this.setColor(c);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fill3DRect(0,0, 100, 20,true);
		g.setColor(Color.GRAY);
		g.fill3DRect(0, 0, val, 19,true);
	}

	public void setVal(int val){
	 this.val = val;
	 repaint();
	}

	public Color getColor() {
		return c;
	}

	public void setColor(Color c) {
		this.c = c;
	}
}


