import javax.swing.*;
import java.awt.*;

public class BoomFrame extends JFrame {
    static int W = 700;
    static int H = 720;
    public BoomFrame() {
        setTitle("Boom");
        setSize(W, H);
        setLocationRelativeTo(null);
        setResizable(false);
        BoomPanel p = new BoomPanel();
        add(p);
    }

    public static void main(String[] args) {
        BoomFrame f = new BoomFrame();
        f.setVisible(true);
    }
}
