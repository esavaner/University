package com.myprojects.paint;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
/** Panel programu, zawiera wszystkie obiekty */
public class MyPanel extends JPanel {
    public static List<Prostokat> prostokaty = new ArrayList<Prostokat>();
    public static List<Kolo> kola = new ArrayList<Kolo>();
    public static List<Wielokat> wielokaty = new ArrayList<Wielokat>();
    private static MyPopupMenu pm = new MyPopupMenu();
    public static int SWITCH = 0;
    public JLabel oxoy = new JLabel();
    public static Kolo coloredK = null;
    public static Prostokat coloredPr = null;
    public static Wielokat coloredW = null;
    private Wielokat wi;
    private Prostokat ppath;
    private MouseAdapter mouseListener = new MouseAdapter() {
        private Kolo draggedK = null;
        private Prostokat draggedP = null;
        private Wielokat draggedW = null;
        private float x;
        private float y;
        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            Point p = MouseInfo.getPointerInfo().getLocation();
            p = new Point(p.x,p.y);
            oxoy.setText(" X = " + (p.x-100) + " Y = " + (p.y-100));
            if(SWITCH == 0) {
                if((kola != null)) {
                    for(Kolo k : kola) {
                        if(k.isHit(x,y)) {
                        	if(e.getButton()==MouseEvent.BUTTON3 ) {
                        		if(coloredK == null) {
                        			coloredK = k;
                        		}
                        	}
                            draggedK = k;
                            break;
                        }
                    }
                }
                if(prostokaty != null) {
                    for(Prostokat pr : prostokaty) {
                         if(pr.isHit(x,y)) {
                        	 if(e.getButton()==MouseEvent.BUTTON3 ) {
                        		 if(coloredPr == null) {
                        			 coloredPr = pr;
                        		 }
                        	 }
                        	draggedP = pr;
                            break;
                        }
                    }   
                }
                if(wielokaty != null) {
                	for(Wielokat w : wielokaty) {
                		if(w.isHit(x,y)) {
                			if(e.getButton()==MouseEvent.BUTTON3 ) {
                				if(coloredW == null) {
                					coloredW = w;
                				}
                			}
                			draggedW = w;
                			break;
                		}
                	}
                }
            }
            else if(SWITCH == 3) {
                if(Wielokat.SWITCH == 0) {
                    ppath = new Prostokat(x-10,y-10,(float)20,(float)20,Color.BLACK);
                    ppath.setColor(Color.BLACK);
                    prostokaty.add(ppath);
                    wi = new Wielokat(x,y);
                    repaint();
                }
                else {
                    if(ppath.isHit(x,y)) {
                        SWITCH = 0;
                        prostokaty.remove(ppath);
                        wi.setColor(Color.GREEN);
                        wielokaty.add(wi);
                        wi.close();
                        repaint();
                    }
                    else {
                        wi.add(x,y);
                        repaint();
                    }
                }
            }    
        }
        @Override
        public void mouseDragged(MouseEvent e) {  
        if(SWITCH == 0) {
            float dx = e.getX() - x;
            float dy = e.getY() - y;
            if(draggedK != null) {               
                draggedK.addX(dx);
                draggedK.addY(dy);
                repaint();
            }
            if(draggedP != null) {               
                draggedP.addX(dx);
                draggedP.addY(dy);
                repaint();
            }
            if(draggedW != null) {
            	draggedW.move(dx,dy);
            	repaint();
            }
            x += dx;
            y += dy;            
        }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        float a = e.getX();
        float b = e.getY();
            if(SWITCH == 0) {
                draggedK = null;
                draggedP = null;
                draggedW = null;
            }    
            else if(SWITCH == 1) {
                float z = (float)(Math.sqrt(Math.pow(a-x,2) + Math.pow(b-y,2)));
                Kolo k = new Kolo((x+a-z)/2,(y+b-z)/2,z,z,Color.BLUE);
                k.setColor(Color.BLUE);
                kola.add(k);
                SWITCH = 0;
                repaint();
            }
            else if(SWITCH == 2) {
                Prostokat pr = new Prostokat(x,y,Math.abs(x-a),Math.abs(y-b),Color.RED);
                pr.setColor(Color.RED);
                prostokaty.add(pr);
                SWITCH = 0;
                repaint();
            }
        }
    };
    private MouseWheelListener mwl = new MouseWheelListener() {
        private Kolo scaledK = null;
        private Prostokat scaledP = null;
        private Wielokat scaledW = null;
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            doScale(e);
        }
        private void doScale(MouseWheelEvent e) {
            int x = e.getX();
            int y = e.getY();
            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                for(Kolo k : kola) {
                    if(k.isHit(x,y)) {
                        scaledK = k;
                        break;
                    }
                }
                for(Prostokat pr : prostokaty) {
                    if(pr.isHit(x,y)) {
                        scaledP = pr;
                        break;
                    }
                }
                for(Wielokat w : wielokaty) {
                	if(w.isHit(x,y)) {
                		scaledW = w;
                		break;
                	}
                }
                if (scaledP != null) {   
                    float amount =  e.getWheelRotation() * -8f;
                    scaledP.addWidth(amount);
                    scaledP.addHeight(amount);
                    repaint();
                }
                if (scaledK != null) {                   
                    float amount =  e.getWheelRotation() * -8f;
                    scaledK.addWidth(amount);
                    scaledK.addHeight(amount);
                    repaint();
                }
                if(scaledW != null ) {
                	float amount = e.getWheelRotation() * -1f;
                	scaledW.scale(amount, amount);
                	System.out.println(amount);
                	repaint();
                }
            }
            scaledP = null;
            scaledK = null;
            scaledW = null;
        }
    };
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i =0; i < prostokaty.size(); i++) {
            prostokaty.get(i).draw(g);
        }
        for(int i =0; i < kola.size(); i++) {
            kola.get(i).draw(g);
        }
        for(int i =0; i < wielokaty.size(); i++) {
        	wielokaty.get(i).draw(g);
        }
        repaint();
    }
    public void shapes() {
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addMouseWheelListener(mwl);
    }
    public MyPanel() {
        shapes();
        this.setComponentPopupMenu(pm); 
        this.add(oxoy);
    }
}
