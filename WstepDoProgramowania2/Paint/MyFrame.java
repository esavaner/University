package com.myprojects.paint;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/** Edytowane okno, np dopasowawny rozmiar do monitora */
public class MyFrame extends JFrame {
    public MyFrame() {
        super("Zad 05");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double width = new Double(0.5*screenSize.getWidth());
        Double height = new Double(0.8*screenSize.getHeight());
        setSize(height.intValue(),width.intValue());
        setLocation(100,50);
        setResizable(false);
    }
}
