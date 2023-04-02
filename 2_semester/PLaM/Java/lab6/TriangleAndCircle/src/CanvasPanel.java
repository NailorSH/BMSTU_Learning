import javax.swing.*;
import java.awt.*;

public class CanvasPanel extends JPanel {
    private int sideA = 0;
    private int sideB = 0;

    public void setA(int a) {
        sideA = a;
        repaint();
    }

    public void setB(int b) {
        sideB = b;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());

        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;

        g.setColor(Color.RED);
        g.drawPolygon(new int[] {xCenter, xCenter, xCenter+sideB}, new int[] {yCenter-sideA, yCenter, yCenter}, 3);

        int radius = (int) (Math.sqrt(sideA*sideA + sideB*sideB) / 2);

        g.setColor(Color.BLUE);
        g.drawOval((int)(xCenter+sideB/2-radius),(int)(yCenter-sideA/2-radius),radius*2,radius*2);

//        g.setColor(Color.GREEN);
//        g.drawLine(0, yCenter-sideA/2, getWidth(), yCenter-sideA/2);
//        g.drawLine(xCenter+sideB/2, 0, xCenter+sideB/2, getHeight());
    }
}