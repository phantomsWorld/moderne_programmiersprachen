package swingGuiTest;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import javax.swing.JComponent;
	
public class Bar extends JComponent{

	private int val;

	public Bar(int value){
	setPreferredSize(new Dimension(100,20));
	this.val = value;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.PINK);
		g.fill3DRect(0,0, 100, 20,true);
		g.setColor(Color.red);
		g.fill3DRect(0, 0, val, 19,true);
	}

	public void setVal(int val){
	 this.val = val;
	 repaint();
	}
}


