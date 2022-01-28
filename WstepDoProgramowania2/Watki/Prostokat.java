package watki;
import java.awt.*;
/**Klasa tworzaca wszystkie prostokaty */
public class Prostokat {
	public Multi t1 = new Multi();
    private Color kolor;
    private Rectangle rec;
    private int liczba;
	public Prostokat(float x, float y, float width, float height, Color kolor, int liczba) {
        rec = new Rectangle((int)x,(int)y,(int)width,(int)height);
        this.liczba = liczba;
    }
	public Prostokat(Rectangle rec) {
        this.rec = rec;
    }
	/**Funkcja zwracajaca kolor danego prostokata */
    public Color getColor() {
        return kolor;
    }
    /**Funkcja ustawiajaca kolor danego prostokata */
    public void setColor(Color kolor) {
        this.kolor = kolor;
    }
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect((int)rec.x, (int)rec.y, (int)rec.width, (int)rec.height);
    }
    /**Funkcja startujaca watki */
    public void start() {
    	t1.setPr(this.liczba);
    	t1.start();
    }
    /**Funkcja zmieniajaca kolor prostokata z prawdopodobienstwem p */
    public void changeColor() {
    	synchronized(this) {
    	if(MyPanel.gen.nextDouble() < Double.parseDouble(MyDialog.p.getText())) {
    		this.setColor(new Color(MyPanel.gen.nextInt(255), MyPanel.gen.nextInt(255), MyPanel.gen.nextInt(255)));
    	}
    	else {
    		float r1 =0, r2 =0, r3 =0, r4 =0, g1 =0, g2 =0, g3 =0, g4 =0, b1 =0, b2 =0, b3 =0, b4 =0; 
    		float counter =0; 
    		int licznik1 = this.liczba - Integer.parseInt(MyDialog.mx.getText());
    		int licznik2 = this.liczba + Integer.parseInt(MyDialog.mx.getText());
    		if(licznik1 >= 0) {
    			r1 = MyPanel.prostokaty.get(this.liczba - Integer.parseInt(MyDialog.mx.getText())).getColor().getRed();
    			g1 = MyPanel.prostokaty.get(this.liczba - Integer.parseInt(MyDialog.mx.getText())).getColor().getGreen();
    			b1 = MyPanel.prostokaty.get(this.liczba - Integer.parseInt(MyDialog.mx.getText())).getColor().getBlue();
    			counter++;
    		}
    		if(licznik2 < MyPanel.prostokaty.size()) {
    			r2 = MyPanel.prostokaty.get(this.liczba + Integer.parseInt(MyDialog.mx.getText())).getColor().getRed();
    			g2 = MyPanel.prostokaty.get(this.liczba + Integer.parseInt(MyDialog.mx.getText())).getColor().getGreen();
    			b2 = MyPanel.prostokaty.get(this.liczba + Integer.parseInt(MyDialog.mx.getText())).getColor().getBlue();
    			counter++;
    		} 
    		if(this.liczba - 1 >= 0) {
    			r3 = MyPanel.prostokaty.get(this.liczba - 1).getColor().getRed();
    			g3 = MyPanel.prostokaty.get(this.liczba - 1).getColor().getGreen();
    			b3 = MyPanel.prostokaty.get(this.liczba - 1).getColor().getBlue();
    			counter++;
    		}
    		if(this.liczba + 1 < MyPanel.prostokaty.size()) {
    			r4 = MyPanel.prostokaty.get(this.liczba + 1).getColor().getRed();
    			g4 = MyPanel.prostokaty.get(this.liczba + 1).getColor().getGreen();
    			b4 = MyPanel.prostokaty.get(this.liczba + 1).getColor().getBlue();
    			counter++;
    		}
    		this.setColor(new Color((int)((r1 + r2 + r3 + r4)/counter),(int)((g1 + g2 + g3 + g4)/counter),(int)((b1 + b2 + b3 + b4)/counter)));
    	}
    	}
    }
}