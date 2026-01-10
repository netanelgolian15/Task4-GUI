//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
    double nScale = (double)2.0F;
    double mScale = (double)1.5F;
    double nX = (double)50.0F;
    double nY = (double)50.0F;
    double nVelX = (double)3.0F;
    double nVelY = (double)3.0F;
    double mX = (double)200.0F;
    double mY = (double)200.0F;
    double mVelX = (double)-3.0F;
    double mVelY = (double)-3.0F;
    double mAngle = (double)0.0F;
    int tick_counter = 0;
    int n_color_number = 0;
    int m_color_number = 2;
    Color[] colors;

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

    public PolygonCanvas() {
        this.colors = new Color[]{Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE};
        this.timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Polygon n = this.createPolygon("polygon coordinates.txt", 0);
        this.drawLetter(g2d, n, this.nX, this.nY, this.nScale, (double)0.0F, this.colors[this.n_color_number]);
        Polygon m = this.createPolygon("polygon coordinates.txt", 1);
        this.drawLetter(g2d, m, this.mX, this.mY, this.mScale, this.mAngle, this.colors[this.m_color_number]);
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawLetter(Graphics2D g2d, Polygon p, double x, double y, double scale, double angle, Color c) {
        AffineTransform old = g2d.getTransform();
        g2d.translate(x, y);
        g2d.scale(scale, scale);
        g2d.rotate(angle, (double)15.0F, (double)25.0F);
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
        if (this.nX < (double)0.0F || this.nX + (double)50.0F * this.nScale > (double)width) {
            this.nVelX = -this.nVelX;
        }

        if (this.nY < (double)0.0F || this.nY + (double)50.0F * this.nScale > (double)height) {
            this.nVelY = -this.nVelY;
        }

        this.nX += this.nVelX;
        this.nY += this.nVelY;
        if (this.mX < (double)0.0F || this.mX + (double)30.0F * this.mScale > (double)width) {
            this.mVelX = -this.mVelX;
        }

        if (this.mY < (double)0.0F || this.mY + (double)50.0F * this.mScale > (double)height) {
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

        this.repaint();
    }
}
