package com.myprojects.paint;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
/** Dodaje kolo do programu */
public class Kolo implements Serializable {
        private Color kolor;
        private Ellipse2D.Float ell;
        public Kolo(float x, float y, float width, float height, Color kolor) {
            ell = new Ellipse2D.Float(x,y,width,height);
        }
        public Kolo(Ellipse2D.Float ell) {
            this.ell = ell;
        }
        /** Zwraca kolor kola*/
        public Color getColor() {
            return kolor;
        }
        /** Ustawia kolor kola*/
        public void setColor(Color kolor) {
            this.kolor = kolor;
        }
        public Ellipse2D.Float getEll() {
            return ell;
        }
        /** Sprawdza czy myszka jest wewnatrz kola */
        public boolean isHit(float x, float y) {            
            return ell.getBounds2D().contains(x, y);
        }
        /** Przesuwa kolo o wartosc x */
        public void addX(float x) {            
            ell.x += (int)x;
        }
        /** Przesuwa kolo o wartosc y */
        public void addY(float y) {   
            ell.y += (int)y;
        }
        /** Zmienia wysokosc kola */
        public void addWidth(float w) {   
            ell.width += (int)w;
        }
        /** Zmienia wysokosc kola */
        public void addHeight(float h) {   
            ell.height += (int)h;
        }
        public void draw(Graphics g) {
            g.setColor(getColor()); 
            g.fillOval((int)ell.x, (int)ell.y, (int)ell.width, (int)ell.height);
        }
}
