package drzewa;

import java.awt.*;

import javax.swing.BoxLayout;

/** Glowna funkcja programu 
 * @author Filip Rurak
 */
public class Main {
	private static MyFrame f = new MyFrame();
	private static MyPanel p = new MyPanel();
	public static void main(String[] args) {
		f.add(p);
		f.setVisible(true);
	}
}