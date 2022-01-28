package drzewa;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        super("Zad 07");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(400,100);
        setSize(900,600);
        MyDialog d = new MyDialog();
        setResizable(false);
    }
}
