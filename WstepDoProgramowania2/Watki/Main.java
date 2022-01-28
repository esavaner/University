package watki;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/** Glowna funkcja programu 
 * @author Filip Rurak
 */
public class Main {
	static MyFrame f = new MyFrame();
	static MyDialog d = new MyDialog();
	static MyPanel p = new MyPanel();
	public static void main(String[] args) {
		f.add(p);
		f.setVisible(true);
	}
}