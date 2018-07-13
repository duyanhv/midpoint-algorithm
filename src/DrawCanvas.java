import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by duyanh on 7/10/18.
 */
public class DrawCanvas extends JPanel{
    BufferedImage onePixel;
    Graphics backGraphics;

    public DrawCanvas(){
        setBackground(Color.black);
        onePixel = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        backGraphics = onePixel.createGraphics();

    }
    public void render(){
        backGraphics.setColor(Color.red);
        backGraphics.fillRect(0,0, onePixel.getWidth(), onePixel.getHeight());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        test(g, 10, 10, 500,10);
        test(g, 10, 10, 150,150);
        test(g, 150, 150, 250,150);
        test(g, 250, 150, 500,10);
    }

    public void test(Graphics g, int x1,int y1,int x2, int y2){
        int dx, y, x, dy,c1, c2;
        float p;
        dy= y2-y1;
        dx= x2-x1;
        y=y1;
        x=x1;
//        c1 = 2*dy;
//        c2 = 2*(dy-dx);
        g.drawImage(onePixel,x,y,null);
        if(dy<dx){
            p = dy - (dx/2);
            while(x<x2){
                if(p >= 0){
                    g.drawImage(onePixel,x+1,y+1,null);
                    p += dy-dx;
                    y++;
                }else{
                    g.drawImage(onePixel,x+1,y,null);
                    p+=dy;
                }
                x++;
            }
        }else{
            p = dx/2 -dy;
            while (y <=y2){
                if(p>=0){
                    g.drawImage(onePixel,x+1,y+1,null);
                    x++;
                    p+=dx-dy;
                }else{
                    g.drawImage(onePixel,x,y+1,null);
                    p+=dx;
                }
                y++;
            }
        }
        g.drawImage(onePixel,x2,y2,null);

    }
}
