package drzewa;

import java.awt.Graphics;

public class StringN {
	public String t;
	public double x;
	public double y;
	public StringN(double t, double x, double y) {
		this.t = String.valueOf(t);
		this.x = x;
		this.y = y;
	}
	public void draw(Graphics g) {
		g.drawString(t, (int)x, (int)y);
	}
}