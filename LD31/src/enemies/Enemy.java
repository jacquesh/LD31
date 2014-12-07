package enemies;

import java.awt.Graphics2D;
import states.iObserver;
import util.Sprite;
import util.Utility;

public abstract class Enemy implements iObserver
{
    private static Sprite highlight32;
    private static Sprite highlight16;

    protected double x, y, direction, speed, speedMax, health;
    protected double rotation, rotationSpeed, fade;
    protected Sprite spr;

    protected int spawnX;
    protected int spawnY;
    
    static
    {
        highlight32 = new Sprite(Utility.loadImage("/Resources/Images/Enemies/enemyBorder32.png"), 16, 16);
        highlight16 = new Sprite(Utility.loadImage("/Resources/Images/Enemies/enemyBorder16.png"), 8, 8);
    }

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
        if(fade == 0)
        {
            speed = speedMax;
            rotation += rotationSpeed;
        }
        else
        {
            fade--;
        }
        
        while(direction < 0)
        {
            direction += 2*Math.PI;
        }
        while(direction > 2*Math.PI)
        {
            direction -= 2*Math.PI;
        }
        
        if(speed > 0)
        {
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
        int w = spr.image.getWidth();
        if(w == 28)
        {
            highlight32.draw(g,x,y,rotation);
        }
        else if(w == 12)
        {
            highlight16.draw(g,x,y,rotation);
        }
        spr.draw(g, x, y, rotation);
    }
}
