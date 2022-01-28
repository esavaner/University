package drzewa;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<Ramka> ramki = new ArrayList<Ramka>();
	public static List<StringN> strings = new ArrayList<StringN>();
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < ramki.size(); i++) {
			ramki.get(i).draw(g);
		}
		for(StringN s : strings) {
			s.draw(g);
		}
		repaint();
	}
	public MyPanel() {
		shapes();
	}
	public void shapes() {
		setSize(900,900);
		setBackground(Color.WHITE);
		setLayout(null);
		repaint();
	}
}