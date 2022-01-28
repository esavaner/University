package watki;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**Okno programu zawierajace glowne parametry */
public class MyDialog extends JDialog {
	public static JTextField mx;
	public static JTextField ny;
	public static JTextField k;
	public static JTextField p;
	public MyDialog() {
		mx = new JTextField("10");
		mx.setPreferredSize(new Dimension(170, 40));
		ny = new JTextField("10");
		ny.setPreferredSize(new Dimension(170, 40));
		k = new JTextField("1000");
		k.setPreferredSize(new Dimension(170, 40));
		p = new JTextField("1");
		p.setPreferredSize(new Dimension(170, 40));
		JLabel l1 = new JLabel("Ilosc x");
		JLabel l2 = new JLabel("Ilosc y");
		JLabel l3 = new JLabel("Szybk.");
		JLabel l4 = new JLabel("Prawd.");
		JButton b1 = new JButton("Start");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Prostokat p : MyPanel.prostokaty) {
					p.start();
				}
			}
		});
		JButton b = new JButton("Update");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyPanel.update();
			}
		});
		setLayout(new FlowLayout());
		add(l1);
		add(mx);
		add(l2);
		add(ny);
		add(l3);
		add(k);
		add(l4);
		add(p);
		add(b1);
		add(b);
		setBounds(900,50,270,270);
		setResizable(false);
		setVisible(true);
	}
}