import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by duyanh on 7/10/18.
 */
public class DrawWindow extends JFrame {
    DrawCanvas drawCanvas;
    public DrawWindow() {

        this.drawCanvas = new DrawCanvas();
        this.setSize(800, 600);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);

        this.setContentPane(this.drawCanvas);
        this.setVisible(true);
    }

    public void run(){
        drawCanvas.render();
    }

}
