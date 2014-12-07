package enemies;

import core.Main;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import states.iObserver;
import util.Sprite;
import util.Utility;

public abstract class Enemy implements iObserver
{
    protected static final BufferedImage border64 = Utility.loadImage("/Resources/Images/Enemies/enemyBorder64.png");
    protected static final BufferedImage border32 = Utility.loadImage("/Resources/Images/Enemies/enemyBorder32.png");
    protected static final BufferedImage border16 = Utility.loadImage("/Resources/Images/Enemies/enemyBorder16.png");
    protected static final BufferedImage border08 = Utility.loadImage("/Resources/Images/Enemies/enemyBorder08.png");

    protected double x, y, direction, speed, speedMax, health;
    protected double rotation, rotationSpeed, fade;
    protected BufferedImage image, border;
    protected Sprite spr;

    public Enemy(double x, double y)
    {
        this.x = x;
        this.y = y;
        
        direction = 0;
        speed = 0;
        fade = 60;
        rotation = 0;
    }
    
    @Override
    public boolean update()
    {
        if(speed <= 0)
        {
            if(fade == 0)
            {
                speed = speedMax;
            }
            else
            {
                fade--;
            }
        }
        else
        {
            rotation += rotationSpeed;
            while(direction < 0)
            {
                direction += 2*Math.PI;
            }
            while(direction > 2*Math.PI)
            {
                direction -= 2*Math.PI;
            }
            
            x += Math.cos(direction)*speed;
            y += Math.sin(direction)*speed;
        }
        
        if(health <= 0)
            return true;
        return false;
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        spr.draw(g, x, y, rotation);
    }
    
    public void makeImage(int x, int y, int size)
    {
        switch(size)
        {
            case(8): border = border08; break;
            case(16): border = border16; break;
            case(32): border = border32; break;
            case(64): border = border64; break;
            default: border = border64; break;

        }
        
        image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D image_g = (Graphics2D)image.getGraphics();
        
        image_g.drawImage(Main.background.getSubimage(x-size/2, y-size/2, size, size), 0, 0, null);
        image_g.drawImage(border, 0, 0, null);
        image_g.dispose();
        spr = new Sprite(image, size/2, size/2);
    }
    
    public void makeBorder(int size)
    {
        Graphics2D image_g = (Graphics2D)image.getGraphics();
        image_g.drawImage(border, 0, 0, null);
        image_g.dispose();
        spr = new Sprite(image, size/2, size/2);
    }
}
