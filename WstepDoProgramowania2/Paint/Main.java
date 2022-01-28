package com.myprojects.paint;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/** Glowna funkcja programu 
 * @author Filip Rurak
 */
public class Main {
    private static MyFrame f = new MyFrame();
    private static MyMenuBar mb = new MyMenuBar();
    private static MyPanel p = new MyPanel(); 
    public static void main(String[] args) {
        new Main();
    }
    public Main() {
        f.add(BorderLayout.NORTH,mb);
        f.add(p); 
        f.setVisible(true);
    }
}
