import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PictureForm {
    private JPanel mainPanel ;
    private JSpinner sideASpinner;
    private JSpinner sideBSpinner;
    private JSpinner angleSpinner;
    private CanvasPanel canvasPanel;

    public PictureForm() {
        sideASpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int a = (int)sideASpinner.getValue();
                canvasPanel.setA(a);
            }
        });
        sideBSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int b = (int)sideBSpinner.getValue();
                canvasPanel.setB(b);
            }
        });
        angleSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int alpha = (int)angleSpinner.getValue();
                canvasPanel.setAlpha(alpha);
            }
        });
    }

    public static void main (String[] args) {
        JFrame frame = new JFrame("Rectangle");
        frame.setContentPane (new PictureForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}