import java.awt.*;
import java.awt.event.*;

class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}

class MyButtonAdapter implements ActionListener {
    MyFrame f;
    MyButtonAdapter(MyFrame f) {
        this.f = f;
    }
    public void actionPerformed(ActionEvent e) {
        f.action();
    }
}

class MyButton extends Button {
    MyButton(MyFrame f) {
        super("Przepisz");
        addActionListener(new MyButtonAdapter(f));
    }
}

class MyFrame extends Frame {
    Label wynik;
    TextField dane;
    MyFrame() {
        super("Program");
        setBounds(100,100,640,640);
        addWindowListener(new MyWindowAdapter());
        setFont(new Font(Font.SANS_SERIF,Font.PLAIN,40));
        setLayout(new GridLayout (4,1));
        wynik = new Label();
        dane = new TextField(40);
        add(dane);
        add(wynik);
        setResizable(false);
    }
    public void action() {
        wynik.setText(dane.getText());
        dane.setText("");
    }
}

public class program1 {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.setVisible(true);
    }
}
