package watki;
import java.awt.*;
import javax.swing.*;

/**Okno calego programu */
public class MyFrame extends JFrame {
	public static float h;
	public static float w;
    public MyFrame() {
        super("Zad 06");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Float width = (float)(0.6*screenSize.getWidth());
        Float height = new Float(0.8*screenSize.getHeight());
        setSize(width.intValue(),height.intValue());
        h = height;
        w = width;
        setLocation(100,50);
        setResizable(false);
    }
}
