
import javax.swing.JFrame;

public class MyAnim extends JFrame {
    public MyAnim() {
        super("Animation-floating letters");
    }

    public static void main(String[] args) {
        JFrame frame = new MyAnim();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(3);
        frame.add(new PolygonCanvas());
        frame.setVisible(true);
    }
}