package enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import states.iObserver;
import util.Sprite;
import util.Utility;

public abstract class Enemy implements iObserver
{
    protected static final BufferedImage highlight64 = Utility.loadImage("/Resources/Images/Enemies/enemyBorder64.png");
    protected static final BufferedImage highlight32 = Utility.loadImage("/Resources/Images/Enemies/enemyBorder64.png");
    protected static final BufferedImage highlight16 = Utility.loadImage("/Resources/Images/Enemies/enemyBorder64.png");

    public double x, y, direction, speed, speedMax, health;
    protected double rotation, rotationSpeed, fade;
    protected Sprite spr;

    protected int spawnX;
    protected int spawnY;

    public Enemy(double x, double y)
    {
        this(x, y, (int)x, (int)y);
    }

    public Enemy(double x, double y, int spawnX, int spawnY)
    {
        this.x = x;
        this.y = y;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
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
}
