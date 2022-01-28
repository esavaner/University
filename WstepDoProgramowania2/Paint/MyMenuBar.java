package com.myprojects.paint;
import java.awt.event.*;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.geom.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
/** Dodaje menu do programu */
public class MyMenuBar extends JMenuBar {
    public JDialog dia = new JDialog();
    public JLabel l = new JLabel("'Paint'  Autor: Filip Rurak");
    public MyMenuBar() {
        JMenu plik, nowa, inne;
        JMenuItem i1, i2, i3, i4, i5, i6;
        plik = new JMenu("Plik");
        nowa = new JMenu("Nowa");
        inne = new JMenu("Inne");
        i1 = new JMenuItem("Zapisz");
        i2 = new JMenuItem("Wczytaj");
        i3 = new JMenuItem("Kolo");
        i4 = new JMenuItem("Prostokat");
        i5 = new JMenuItem("Wielokat");
        i6 = new JMenuItem("...");
        i3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyPanel.SWITCH = 1;
            }
        });
        i4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyPanel.SWITCH = 2;
            }
        });
        i5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyPanel.SWITCH = 3;
            }
        }); 
        i1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                FileOutputStream fosk = new FileOutputStream("t.tmpk");
                ObjectOutputStream oosk = new ObjectOutputStream(fosk);
                oosk.writeObject(MyPanel.kola);
                oosk.close();
                FileOutputStream fosp = new FileOutputStream("t.tmpp");
                ObjectOutputStream oosp = new ObjectOutputStream(fosp);
                oosp.writeObject(MyPanel.prostokaty);
                oosp.close();
                FileOutputStream fosw = new FileOutputStream("t.tmpw");
                ObjectOutputStream oosw = new ObjectOutputStream(fosw);
                oosw.writeObject(MyPanel.wielokaty);
                oosw.close();
                System.out.println("Zapisano");
                }
                catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });
        i2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                FileInputStream fisk = new FileInputStream("t.tmpk");
                ObjectInputStream oisk = new ObjectInputStream(fisk);
                MyPanel.kola = (ArrayList<Kolo>)oisk.readObject();
                oisk.close();
                FileInputStream fisp = new FileInputStream("t.tmpp");
                ObjectInputStream oisp = new ObjectInputStream(fisp);
                MyPanel.prostokaty = (ArrayList<Prostokat>)oisp.readObject();
                oisp.close();
                FileInputStream fisw = new FileInputStream("t.tmpw");
                ObjectInputStream oisw = new ObjectInputStream(fisw);
                MyPanel.wielokaty = (ArrayList<Wielokat>)oisw.readObject();
                oisw.close();
                repaint();
                }
                catch (IOException i) {
                    i.printStackTrace();
                }
                catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } 
                System.out.println("Wczytano");
            }
        });
        i6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dia.setBounds(1000,100,200,70);
                dia.add(l);
                dia.setVisible(true);
            }
        });
        plik.add(i1);
        plik.add(i2);
        nowa.add(i3);
        nowa.add(i4);
        nowa.add(i5);
        inne.add(i6);
        add(plik);
        add(nowa);
        add(inne);
    }
}
