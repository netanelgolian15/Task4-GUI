import javax.swing.JFrame;
import java.awt.*;

public class MyAnim {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Animation - Floating Letters");
        PolygonCanvas canvas = new PolygonCanvas();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setVisible(true);
    }
}