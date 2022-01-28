package com.myprojects.paint;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
/** Dodaje wielokat do programu */
public class Wielokat implements Serializable {
	public static int SWITCH = 0;
	private Color kolor;
	private float x;
	private float y;
	private GeneralPath path;
	public Wielokat(float x, float y) {
		path = new GeneralPath();
		path.moveTo((int)x, (int)y);
		this.x = x;
		this.y = y;
		this.SWITCH = 1;
	}
	/** Zwraca kolor wielokata */
	public Color getColor() {
        return kolor;
    }
	/** Ustawia kolor wielokata */
    public void setColor(Color kolor) {
        this.kolor = kolor;
    }
    /** Sprawdza czy myszka jest wewnatrz wielokata*/
    public boolean isHit(float x, float y) {    
        return path.getBounds2D().contains(x, y);
    }
    public void move(float x, float y) {
    	AffineTransform tx = AffineTransform.getTranslateInstance(x, y);
    	path.transform(tx);
    }
    public void scale(float x, float y) {
    	if(x != 0) {
        	AffineTransform tx = AffineTransform.getScaleInstance(x, y);
        	path.transform(tx);
    	}
    }
	public void add(float x, float y) {
		path.lineTo((int)x, (int)y);
	}
	public void close() {
		path.closePath();
		this.SWITCH = 0;
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getColor());
        g2d.fill(path);
    }
}