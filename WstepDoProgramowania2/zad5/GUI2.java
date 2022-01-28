import java.awt.*;
import java.awt.event.*;

public class GUI2 extends WindowAdapter {
    Frame f;
    GUI2() {
        f = new Frame("Zad 04");
        f.addWindowListener(this);
        f.setBounds(100,100,640,640);
        f.setVisible(true);
        f.setResizable(false);
    }
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    public static void main(String[] args) {
        new GUI2();
    }
}
