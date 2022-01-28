package drzewa;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<Node> poziom2 = new ArrayList<Node>();
	public static List<Node> poziom3 = new ArrayList<Node>();
	public static List<Node> nodes = new ArrayList<Node>();
	public static JTextField in;
	public static Node root = new Node();
	public MyDialog() {
		setLayout(new FlowLayout());
		nodes.add(root);
		//setPreferredSize(new Dimension(900, 100));
		in = new JTextField("");
		in.setPreferredSize(new Dimension(120, 30));
		JButton b2 = new JButton("Search");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double x = Double.parseDouble(in.getText());
				Node szukana = MyDialog.nodes.get(Node.szukaj(x));
				for(int l =0; l< szukana.node.size(); l++) {
					if(x == szukana.node.get(l).getValue()) {
						JDialog dia = new JDialog();
						dia.setLayout(new FlowLayout());
						dia.setBounds(800,300,100,70);
						dia.add(new JLabel("Znaleziono"));
						dia.setVisible(true);
					}
					else {
						JDialog dia2 = new JDialog();
						dia2.setLayout(new FlowLayout());
						dia2.setBounds(800,300,100,70);
						dia2.add(new JLabel("Nie znaleziono"));
						dia2.setVisible(true);
					}
				}
			}
		});
		JButton b1 = new JButton("Insert");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				root.addN(Double.parseDouble(in.getText()));
				int x = 200;
				int y = 300;
				for(Node n : nodes) {
					System.out.println("Node " + n.number + " " + n.level);
					for(int i =0; i < n.node.size(); i++) {
						System.out.println(n.node.get(i).getValue() + " ptr " + n.node.get(i).getPointer());
					}
					if(n.level == MyDialog.root.level + 1) {
						Ramka r1 = new Ramka(x,y,30,30);
						MyPanel.ramki.add(r1);
						x+=200;
					}
				}
				MyPanel.ramki.clear();
				MyPanel.strings.clear();
				Ramka r = new Ramka(400, 50, 30, 30);
				MyPanel.ramki.add(r);
				float zx = 406;
				for(int k=0; k< MyDialog.root.node.size(); k++) {
					StringN s = new StringN(root.node.get(k).getValue(),zx,69);
					MyPanel.strings.add(s);
					zx+=30;
				}
				if(poziom2.isEmpty()==false) {
					float poz2 = 800/(poziom2.size() +1);
					for(Node n : poziom2) {
						Ramka r1 = new Ramka(poz2,200,30,30);
						MyPanel.ramki.add(r1);
						float z = poz2 +6;
						for(int i = 0; i < n.node.size(); i++) {
							StringN s = new StringN(n.node.get(i).getValue(),z,219);
							MyPanel.strings.add(s);
							z+=30;
						}
						poz2+=800/(poziom2.size() +1);
					}
				}
				if(poziom3.isEmpty()==false) {
					int pozy;
					if(poziom2.isEmpty()==true) {
						pozy = 200;
					}
					else
						pozy = 350;
					float poz3 = 800/(poziom3.size() +1);
					for(Node n : poziom3) {
						Ramka r1 = new Ramka(poz3,pozy,30,30);
						MyPanel.ramki.add(r1);
						float z = poz3 + 6;
						for(int i = 0; i < n.node.size(); i++) {
							StringN s = new StringN(n.node.get(i).getValue(),z,pozy + 19);
							MyPanel.strings.add(s);
							z+=30;
						}
						poz3+=800/(poziom3.size() +1);
					}
				}
				repaint();
			}
		});
		add(in);
		add(b2);
		add(b1);
		setBounds(1400,300,160,140);
		setResizable(false);
		setVisible(true);
	}
}