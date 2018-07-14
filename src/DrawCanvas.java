import com.sun.xml.internal.xsom.impl.scd.Iterators;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

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
//        vẽ hình thang = midpoint
//        midPoint(g, 10, 10, 500,10);
//        midPoint(g, 10, 10, 50,150);
//        midPoint(g, 500, 10, 350,150);
//        midPoint(g, 350, 150, 50,150);

//        xén hình = liang-barsky
//        lineClipping(g, 150, 150, 400,400, 100, 100,250,250);

//        stretch(g, 10, 10, 500,10, 10, 10);
//        stretch(g, 10, 10, 50,150, 10, 10);
//        stretch(g, 500, 10, 350,150, 10, 10);
//        stretch(g, 350, 150, 50,150, 10, 10);
    }

    /**
    * https://nguyenvanquan7826.wordpress.com/2013/12/07/school_dhmt-thuat-toan-midpoint-ve-duong-thang/
    * TODO: compare breseham and midpoint
    * */
    public void midPoint(Graphics g, int x1,int y1,int x2, int y2){
//        int dx, y, x, dy,c1, c2;
//        float p;
//        dy= y2-y1;
//        dx= x2-x1;
//        y=y1;
//        x=x1;
////        c1 = 2*dy;
////        c2 = 2*(dy-dx);
//        g.drawImage(onePixel,x1,y1,null);
//        if(dy<dx){
//            p = dy - (dx/2);
//            while(x<x2){
//                if(p >= 0){
//                    g.drawImage(onePixel,x+1,y+1,null);
//                    p += dy-dx;
//                    y++;
//                }else{
//                    g.drawImage(onePixel,x+1,y,null);
//                    p+=dy;
//                }
//                x++;
//            }
//        }else{
//            p = dx/2 -dy;
//            while (y <=y2){
//                if(p>=0){
//                    g.drawImage(onePixel,x+1,y+1,null);
//                    x++;
//                    p+=dx-dy;
//                }else{
//                    g.drawImage(onePixel,x,y+1,null);
//                    p+=dx;
//                }
//                y++;
//            }
//        }
//        g.drawImage(onePixel,x2,y2,null);
        int c2, c, Dx, Dy, x, y;
        Dx = Math.abs(x2 - x1);
        Dy = Math.abs(y2 - y1);
        c = Dx - Dy;
        c2 = 2*c;
        x = x1;
        y = y1;

        int x_unit = 1, y_unit = 1;

        if (x2 - x1 < 0)
            x_unit = -x_unit;
        if (y2 - y1 < 0)
            y_unit = -y_unit;

        g.drawImage(onePixel,x,y,null);

        if (x1 == x2)   // duong thang dung
        {
            while (y != y2)
            {
                    y += y_unit;
                    g.drawImage(onePixel,x,y,null);

            }
        }

        else if (y1 == y2)  // duong ngang
        {
            while (x != x2)
            {
                    x += x_unit;
                    g.drawImage(onePixel,x,y,null);
            }
        }

        else if (x1 != x2 && y1 != y2)  // duong xien
        {
            while(x != x2+1)
            {
                c2 =2*c;
                if (c2 > -Dy)
                {
                    c = c - Dy;
                    x = x + x_unit;
                }
                if (c2 < Dx)
                {
                    c = c + Dx;
                    y = y + y_unit;
                }
                g.drawImage(onePixel,x,y,null);
            }
        }
    }

    public void lineClipping(Graphics g, int x1, int y1, int x2, int y2, int xmin, int ymin, int xmax, int ymax){
        int xx1,xx2,yy1,yy2,dx,dy;
        float t1,t2,temp;
        float[] q = new float[]{0,0,0,0};
        float[] p = new float[]{0,0,0,0};
        float[] t = new float[]{0,0,0,0};
        dx=x2-x1;
        dy=y2-y1;
//        left specifies the X-coordinate of top left corner,
//        top specifies the Y-coordinate of top left corner,
//        right specifies the X-coordinate of right bottom corner,
//        bottom specifies the Y-coordinate of right bottom corner.
//        rectangle(int left, int top, int right, int bottom);
        midPoint(g, xmin, ymin, xmax, ymin);
        midPoint(g, xmin, ymin, xmin, ymax);
        midPoint(g, xmin, ymax, xmax, ymax);
        midPoint(g, xmax, ymax, xmax, ymin);
        p[0]=-dx;
        p[1]=dx;
        p[2]=-dy;
        p[3]=dy;

        q[0]=x1-xmin;
        q[1]=xmax-x1;
        q[2]=y1-ymin;
        q[3]=ymax-y1;
        for(int i=0;i<4;i++)
        {
            if(p[i]==0)
            {
                System.out.println("line is parallel to one of the clipping boundary");
                if(q[i]>=0)
                {
                    if(i<2)
                    {
                        if(y1<ymin)
                        {
                            y1=ymin;
                        }

                        if(y2>ymax)
                        {
                            y2=ymax;
                        }

                        midPoint(g, x1,y1,x2,y2);
                    }

                    if(i>1)
                    {
                        if(x1<xmin)
                        {
                            x1=xmin;
                        }

                        if(x2>xmax)
                        {
                            x2=xmax;
                        }

                        midPoint(g, x1,y1,x2,y2);
                    }
                }
            }
        }

        t1=0;
        t2=1;

        for(int i=0;i<4;i++)
        {
            temp=q[i]/p[i];

            if(p[i]<0)
            {
                if(t1<=temp)
                    t1=temp;
            }
            else
            {
                if(t2>temp)
                    t2=temp;
            }
        }

        if(t1<t2)
        {
            xx1 = (int) (x1 + t1 * p[1]);
            xx2 = (int) (x1 + t2 * p[1]);
            yy1 = (int) (y1 + t1 * p[3]);
            yy2 = (int) (y1 + t2 * p[3]);
            System.out.println("line after clipping:");
            System.out.println(xx1 + ", " + yy1 + ", " + xx2 + ", " + yy2);
            midPoint(g, xx1,yy1,xx2,yy2);
        }
    }

    public void translate(Graphics g, int x1,int y1,int x2,int y2, int sx,int sy){
        int xx1;
        int yy1;
        int xx2;
        int yy2;
        xx1= (int) Math.ceil(x1+sx);
        yy1= (int) Math.ceil(y1+sy);
        xx2= (int) Math.ceil(x2+sx);
        yy2= (int) Math.ceil(y2+sy);
        midPoint(g, xx1,yy1,xx2,yy2);
    }


    /**
    * formula: x' = sx * x, y' = sy * y
    * */
    public void stretch(Graphics g, int x1,int y1,int x2,int y2, int sx,int sy){
        int xx1;
        int yy1;
        int xx2;
        int yy2;
        xx1= (int) Math.ceil(x1*sx);
        yy1= (int) Math.ceil(y1*sy);
        xx2= (int) Math.ceil(x2*sx);
        yy2= (int) Math.ceil(y2*sy);
        midPoint(g, xx1,yy1,xx2,yy2);
    }

    /**
     * formula: x' = x*Cos(s * pi/180) - y*Sin(s*pi/180)
     * y' = x*Sin(s * pi/180) + y*Cos(s*pi/180)
     * */
    public void turnBySDegree (Graphics g, int x1,int y1,int x2,int y2, double s){
        double val;
        val = PI / 180;
        int xx1;
        int yy1;
        int xx2;
        int yy2;
        xx1= (int) Math.ceil(x1*cos(s*val)-y1*sin(s*val));
        yy1=(int) Math.ceil(x1*sin(s*val)+y1*cos(s*val));
        xx2=(int) Math.ceil(x2*cos(s*val)-y2*sin(s*val));
        yy2=(int) Math.ceil(x2*sin(s*val)+y2*cos(s*val));
        midPoint(g, xx1,yy1,xx2,yy2);
    }
}
