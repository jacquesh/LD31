package util;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Sprite
{
    protected BufferedImage image;
    protected double offset_x, offset_y;
    
    public Sprite(BufferedImage image, double offset_x, double offset_y)
    {
        this.image = image;
        this.offset_x = offset_x;
        this.offset_y = offset_y;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
    
    public void draw(Graphics2D g, double x, double y)
    {
        AffineTransform t = new AffineTransform();
        t.translate(x-offset_x, y-offset_y);
        g.drawImage(image, t, null);
    }
    
    public void draw(Graphics2D g, double x, double y, double rotation)
    {
        /* A value of 0.5 is added|subtracted to|from the position|origin values in order to align
         *  the origin with a single pixel.
         */
        AffineTransform t = new AffineTransform();
        t.translate(x+0.5, y+0.5);                  //Positioning.
        t.rotate(rotation);                         //Rotation.
        t.translate(-offset_x-0.5, -offset_y-0.5);  //Set origin.
        g.drawImage(image, t, null);
    }

    public void draw(Graphics2D g, double x, double y, double rotation, double scaleX, double scaleY)
    {
        /* A value of 0.5 is added|subtracted to|from the position|origin values in order to align
         *  the origin with a single pixel.
         */
        AffineTransform t = new AffineTransform();
        t.translate(x+0.5, y+0.5);                  //Positioning.
        t.rotate(rotation);                         //Rotation.
        t.scale(scaleX, scaleY);                    //Scaling.
        t.translate(-offset_x-0.5, -offset_y-0.5);  //Set origin.
        g.drawImage(image, t, null);
    }
}
