import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JPanel;
import javax.swing.Timer;

class PolygonCanvas extends JPanel implements ActionListener {
    Timer timer = new Timer(30, this);
    Random rand = new Random();

    // משתני גודל
    double nScale = 2.0;
    double mScale = 1.5;

    // משתני שינוי גודל (מהירות הגדילה/הקטנה)
    double nScaleDelta = 0.02;
    double mScaleDelta = 0.015;

    double nX = 50.0;
    double nY = 50.0;
    double nVelX = 3.0;
    double nVelY = 3.0;
    double mX = 200.0;
    double mY = 200.0;
    double mVelX = -3.0;
    double mVelY = -3.0;
    double mAngle = 0.0;
    int tick_counter = 0;
    int n_color_number = 0;
    int m_color_number = 2;
    Color[] colors;

    public PolygonCanvas() {
        this.colors = new Color[]{Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE};
        this.timer.start();
    }

    private Polygon createPolygon(String fileName, int targetIndex) {
        Polygon polygon = new Polygon();
        try {
            File myFile = new File(fileName);
            Scanner scanner = new Scanner(myFile);
            int currentIndex = 0;
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.equalsIgnoreCase("END")) {
                    ++currentIndex;
                } else {
                    if (currentIndex == targetIndex && !line.isEmpty()) {
                        String[] coordinates = line.split(",");
                        if (coordinates.length == 2) {
                            int x = Integer.parseInt(coordinates[0].trim());
                            int y = Integer.parseInt(coordinates[1].trim());
                            polygon.addPoint(x, y);
                        }
                    }
                    if (currentIndex > targetIndex) {
                        break;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException var11) {
            System.out.println("קובץ לא נמצא!");
        }
        return polygon;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Polygon n = this.createPolygon("polygon coordinates.txt", 0);
        this.drawLetter(g2d, n, this.nX, this.nY, this.nScale, 0.0, this.colors[this.n_color_number]);

        Polygon m = this.createPolygon("polygon coordinates.txt", 1);
        this.drawLetter(g2d, m, this.mX, this.mY, this.mScale, this.mAngle, this.colors[this.m_color_number]);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawLetter(Graphics2D g2d, Polygon p, double x, double y, double scale, double angle, Color c) {
        AffineTransform old = g2d.getTransform();
        g2d.translate(x,y);
        g2d.scale(scale,scale);
        g2d.rotate(angle, 15.0, 25.0);
        g2d.setColor(c);
        g2d.fillPolygon(p);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(p);
        g2d.setTransform(old);
    }

    public void actionPerformed(ActionEvent e) {
        int width = this.getWidth();
        int height = this.getHeight();

        this.mAngle += 0.05;

        // --- לוגיקת שינוי גודל (Scaling) ---
        this.nScale += this.nScaleDelta;
        if (this.nScale > 3.0 || this.nScale < 0.5) {
            this.nScaleDelta = -this.nScaleDelta;
        }

        this.mScale += this.mScaleDelta;
        if (this.mScale > 2.5 || this.mScale < 0.7) {
            this.mScaleDelta = -this.mScaleDelta;
        }
        // ----------------------------------

        // עדכון תנועה וזיהוי התנגשות
        if (this.nX < 0.0 || this.nX + 50.0 * this.nScale > (double)width) {
            this.nVelX = -this.nVelX;
        }
        if (this.nY < 0.0 || this.nY + 50.0 * this.nScale > (double)height) {
            this.nVelY = -this.nVelY;
        }
        this.nX += this.nVelX;
        this.nY += this.nVelY;

        if (this.mX < 0.0 || this.mX + 30.0 * this.mScale > (double)width) {
            this.mVelX = -this.mVelX;
        }
        if (this.mY < 0.0 || this.mY + 50.0 * this.mScale > (double)height) {
            this.mVelY = -this.mVelY;
        }
        this.mX += this.mVelX;
        this.mY += this.mVelY;

        ++this.tick_counter;
        if (this.tick_counter >= 30) {
            this.n_color_number = this.rand.nextInt(this.colors.length);
            this.m_color_number = this.rand.nextInt(this.colors.length);
            this.tick_counter = 0;
        }
//ציור מחדש
        this.repaint();
    }
}