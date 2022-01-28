package drzewa;

import java.awt.*;
public class Ramka {
	public float x;
	public float y;
	public float w;
	public float h;
	public Ramka(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public void draw(Graphics g) {
		float x = this.x;
		g.setColor(Color.BLACK);
		for(int i=0; i < 4; i++) {
			g.drawRect((int)x, (int)y, (int)w, (int)h);
			x =x +30;
		}
	}
}