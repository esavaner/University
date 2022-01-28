package com.myprojects.paint;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/** Dodaje menu do programu pozwalajace zmieniac kolory figur */
public class MyPopupMenu extends JPopupMenu {
    public MyPopupMenu() {
        JMenu kolor = new JMenu("Kolor");
        JMenuItem nb, zi, cz, zo;
        nb = new JMenuItem("Niebieski");
        nb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(MyPanel.coloredK != null) {
                    MyPanel.coloredK.setColor(Color.BLUE);
                    MyPanel.coloredK = null;
                }
                else if(MyPanel.coloredPr != null) {
                    MyPanel.coloredPr.setColor(Color.BLUE);
                    MyPanel.coloredPr = null;
                }
                else if(MyPanel.coloredW != null) {
                    MyPanel.coloredW.setColor(Color.BLUE);
                    MyPanel.coloredW = null;
                }
            }
        });
        zi = new JMenuItem("Zielony");
        zi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(MyPanel.coloredK != null) {
                    MyPanel.coloredK.setColor(Color.GREEN);
                    MyPanel.coloredK = null;
                }
                else if(MyPanel.coloredPr != null) {
                    MyPanel.coloredPr.setColor(Color.GREEN);
                    MyPanel.coloredPr = null;
                }
                else if(MyPanel.coloredW != null) {
                    MyPanel.coloredW.setColor(Color.GREEN);
                    MyPanel.coloredW = null;
                }
            }
        });
        cz = new JMenuItem("Czerwony");
        cz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(MyPanel.coloredK != null) {
                    MyPanel.coloredK.setColor(Color.RED);
                    MyPanel.coloredK = null;
                }
                else if(MyPanel.coloredPr != null) {
                    MyPanel.coloredPr.setColor(Color.RED);
                    MyPanel.coloredPr = null;
                }
                else if(MyPanel.coloredW != null) {
                    MyPanel.coloredW.setColor(Color.RED);
                    MyPanel.coloredW = null;
                }
            }
        });
        zo = new JMenuItem("Zolty");
        zo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(MyPanel.coloredK != null) {
                    MyPanel.coloredK.setColor(Color.YELLOW);
                    MyPanel.coloredK = null;
                }
                else if(MyPanel.coloredPr != null) {
                    MyPanel.coloredPr.setColor(Color.YELLOW);
                    MyPanel.coloredPr = null;
                }
                else if(MyPanel.coloredW != null) {
                    MyPanel.coloredW.setColor(Color.YELLOW);
                    MyPanel.coloredW = null;
                }
            }
        });
        kolor.add(nb);
        kolor.add(zi);
        kolor.add(cz);
        kolor.add(zo);
        add(kolor);
    }
}
