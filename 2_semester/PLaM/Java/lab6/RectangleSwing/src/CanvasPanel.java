import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class CanvasPanel extends JPanel {
    private int sideA = 0;
    private int sideB = 0;
    private int angle = 0;

    public void setA(int a) {
        sideA = a;
        repaint();
    }

    public void setB(int b) {
        sideB = b;
        repaint();
    }

    public void setAlpha(int alpha) {
        angle = alpha;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);

        Graphics2D g2d = (Graphics2D) g;

        int x = getWidth() / 2;
        int y = getHeight() / 2;
        Rectangle shape = new Rectangle(x, y, sideA, sideB);

        AffineTransform tx = new AffineTransform();
        double radians = Math.toRadians(angle);
        tx.rotate(-radians, x, y);
        g2d.setColor(Color.RED);
        Shape newShape = tx.createTransformedShape(shape);

        g2d.draw(newShape);
        g2d.dispose();
    }
}