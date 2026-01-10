import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.Timer;

class PolygonCanvas extends Canvas implements ActionListener {
    Timer timer = new Timer(30, this);
    Random rand = new Random();
    double nScale = 2.0;
    double mScale = 1.5;
    double nX = 50.0, nY = 50.0;
    double nVelX = 3.0, nVelY = 3.0;
    double mX = 200.0, mY = 200.0;
    double mVelX = -3.0, mVelY = -3.0;
    double mAngle = 0.0;
    int tick_counter = 0;
    int n_color_number = 0;
    int m_color_number = 2;
    Color[] colors;

    public PolygonCanvas() {
        this.colors = new Color[]{Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE};
        this.timer.start();
    }

    // שים לב לשינויים בתוך מתודת ה-paint
    @Override
    public void paint(Graphics g) {
        // 1. הסרנו את super.paint(g) כדי למנוע StackOverflow

        // 2. ניקוי ידני של המסך כדי למנוע את המריחות שראינו בסרטון
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // טעינת וציור האותיות
        Polygon n = this.createPolygon("polygon coordinates.txt", 0);
        this.drawLetter(g2d, n, this.nX, this.nY, this.nScale, 0.0, this.colors[this.n_color_number]);

        Polygon m = this.createPolygon("polygon coordinates.txt", 1);
        this.drawLetter(g2d, m, this.mX, this.mY, this.mScale, this.mAngle, this.colors[this.m_color_number]);

        // מבטיח סנכרון חלק של הגרפיקה
        Toolkit.getDefaultToolkit().sync();
    }

    // המתודות createPolygon ו-drawLetter נשארות כפי שהיו
    private Polygon createPolygon(String fileName, int targetIndex) {
        Polygon polygon = new Polygon();
        try {
            File myFile = new File(fileName);
            Scanner scanner = new Scanner(myFile);
            int currentIndex = 0;
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.equalsIgnoreCase("END")) {
                    currentIndex++;
                } else if (currentIndex == targetIndex && !line.isEmpty()) {
                    String[] coordinates = line.split(",");
                    if (coordinates.length == 2) {
                        polygon.addPoint(Integer.parseInt(coordinates[0].trim()), Integer.parseInt(coordinates[1].trim()));
                    }
                }
                if (currentIndex > targetIndex) break;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("קובץ לא נמצא!");
        }
        return polygon;
    }

    private void drawLetter(Graphics2D g2d, Polygon p, double x, double y, double scale, double angle, Color c) {
        AffineTransform old = g2d.getTransform();
        g2d.translate(x, y);
        g2d.scale(scale, scale);
        g2d.rotate(angle, 15.0, 25.0);
        g2d.setColor(c);
        g2d.fillPolygon(p);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(p);
        g2d.setTransform(old);
    }

    public void actionPerformed(ActionEvent e) {
        // לוגיקת התנועה שלך נשארת זהה
        int width = getWidth();
        int height = getHeight();
        mAngle += 0.05;

        if (nX < 0 || nX + 50 * nScale > width) nVelX = -nVelX;
        if (nY < 0 || nY + 50 * nScale > height) nVelY = -nVelY;
        nX += nVelX; nY += nVelY;

        if (mX < 0 || mX + 30 * mScale > width) mVelX = -mVelX;
        if (mY < 0 || mY + 50 * mScale > height) mVelY = -mVelY;
        mX += mVelX; mY += mVelY;

        if (++tick_counter >= 30) {
            n_color_number = rand.nextInt(colors.length);
            m_color_number = rand.nextInt(colors.length);
            tick_counter = 0;
        }
        repaint();
    }
}