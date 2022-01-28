package watki;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
/** Panel prgramu zawierajacy wszystkie prostokaty */
public class MyPanel extends JPanel {
	public static List<Prostokat> prostokaty = new ArrayList<Prostokat>();
	static Random gen = new Random();
	/**Funkcja updatujaca dane planszy */
	public static void update() {
		for(Prostokat p : prostokaty) {
			p.t1.stopThread();
		}
		float m = Integer.parseInt(MyDialog.mx.getText());
		float n = Integer.parseInt(MyDialog.ny.getText());
		prostokaty.clear();
		float x = 0;
		float y = 0;
		int liczba = 0;
		for(int i = 0; y < MyFrame.h - 20; i ++) {
			for(int j = 0; x < MyFrame.w; j ++) {
				Prostokat pr = new Prostokat(x, y, MyFrame.w/m - 10/m, MyFrame.h/n - 35/n, new Color(gen.nextInt(255), gen.nextInt(255), gen.nextInt(255)), liczba);
				pr.setColor(new Color(gen.nextInt(255), gen.nextInt(255), gen.nextInt(255)));
				prostokaty.add(pr);
				x = x + MyFrame.w/m - 10/m;
				liczba++;
			}
			x= 0;
			y = y + MyFrame.h/n - 35/n;
		}
		y= 0;
	}
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i =0; i < prostokaty.size(); i++) {
            prostokaty.get(i).draw(g);
        }
        repaint();
    }   
	public MyPanel() {
		update();
	}
}