package com.myprojects.paint;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
/** Dodaje prostokat do programu */
public class Prostokat implements Serializable {
        private Color kolor;
        private Rectangle rec;
        public Prostokat(float x, float y, float width, float height, Color kolor) {
            rec = new Rectangle((int)x,(int)y,(int)width,(int)height);
        }
        public Prostokat(Rectangle rec) {
            this.rec = rec;
        }
        /** Zwraca kolor prostokata */
        public Color getColor() {
            return kolor;
        }
        /** Ustawia kolor prostokata*/
        public void setColor(Color kolor) {
            this.kolor = kolor;
        }
        public Rectangle getRec() {
            return rec;
        }
        /** Sprawdza czy myszka jest wewnatrz prostokata */
        public boolean isHit(float x, float y) {            
            return rec.getBounds2D().contains(x, y);
        }
        /** Przesuwa prostokat o wartosc x */
        public void addX(float x) {            
            rec.x += (int)x;
        }
        /** Przesuwa prostokat o wartosc y */
        public void addY(float y) {   
            rec.y += (int)y;
        }
        /** Zmienia szerokosc prostokata */
        public void addWidth(float w) {   
            rec.width += (int)w;
        }
        /** Zmienia wysokosc prostokata */
        public void addHeight(float h) {   
            rec.height += (int)h;
        }
        public void draw(Graphics g) {
            g.setColor(getColor());
            g.fillRect((int)rec.x, (int)rec.y, (int)rec.width, (int)rec.height);
        }
}
