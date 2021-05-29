package second.lab.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Drawer extends JPanel {

    private final static int SCALE = 10;

    public Drawer(String title, final ArrayList<Line2D> edges, final boolean showAxis, final Point2D point, final ArrayList<Line2D> answer) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int screenHeight = 700;
        final int screenWidth = 800;
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(new Dimension(screenWidth, screenHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            Graphics2D g2;

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2 = (Graphics2D) g;
                g2.translate(screenWidth / 2, screenHeight / 2);
                g2.scale(3.0, -3.0);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //draw Axis
                g.setColor(Color.LIGHT_GRAY);
                if (showAxis) {
                    for (int i = 0; i < screenWidth / SCALE; i++) {
                        g2.draw(new Line2D.Double(-screenWidth, -i * SCALE, screenWidth, -i * SCALE));
                        g2.draw(new Line2D.Double(-screenWidth, i * SCALE, screenWidth, i * SCALE));
                        g2.draw(new Line2D.Double(-i * SCALE, -screenHeight, -i * SCALE, screenHeight));
                        g2.draw(new Line2D.Double(i * SCALE, -screenHeight, i * SCALE, screenHeight));
                    }
                    g.setColor(Color.DARK_GRAY);
                    g2.draw(new Line2D.Double(-screenWidth, 0, screenWidth, 0));
                    g2.draw(new Line2D.Double(0, -screenHeight, 0, screenHeight));
                }

                //draw Polygon
                for (Line2D l : edges) {
                    g2.setColor(Color.BLUE);
                    g2.draw(l);
                }

                //draw Area
                for (Line2D l : answer) {
                    g2.setColor(Color.GREEN);
                    g2.draw(l);
                }

                //draw Point
                Color pointColor = Color.RED;
                g2.setColor(pointColor);

                g2.fill(new Ellipse2D.Double(point.getX() - 2, point.getY() - 2, 4, 4));
            }
        };

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}