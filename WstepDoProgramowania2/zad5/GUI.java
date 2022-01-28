import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
   
/*class Text extends JTextField {
    Text() {
        setBounds(10,10,100,50);
    }
}*/
public class GUI extends Frame implements ActionListener {
    JMenu menu1, menu2, exit;
    JMenuItem i1, i2, i3, i4, i5, i6;
    JMenuBar mb;
    JFrame f;
    GUI() {
        f = new JFrame("Zad 04");
        menu1 = new JMenu("Tlo");
        menu2 = new JMenu("Kolory");
        exit = new JMenu("Wyjscie");
        mb = new JMenuBar();
        i1 = new JMenuItem("1");
        i2 = new JMenuItem("2");
        i3 = new JMenuItem("3");
        i4 = new JMenuItem("4");
        i5 = new JMenuItem("5");
        i6 = new JMenuItem("6");
        i1.addActionListener(this);
        i2.addActionListener(this);
        i3.addActionListener(this);
        i4.addActionListener(this);
        i5.addActionListener(this);
        i6.addActionListener(this);
        menu1.add(i1);
        menu1.add(i2);
        menu1.add(i3);
        menu2.add(i4);
        menu2.add(i5);
        menu2.add(i6);
        mb.add(menu1);
        mb.add(menu2);
        mb.add(exit);
        f.setJMenuBar(mb);
        f.add(mb);
        f.setBounds(100,100,540,540);
        f.setVisible(true);
        f.setLayout(null);
        f.setResizable(false);
     	   f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) {
    }
    public static void main(String[] args) {
        new GUI();
    }
}
