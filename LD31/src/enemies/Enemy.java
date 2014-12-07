package enemies;

import java.awt.Graphics2D;
import states.iObserver;
import util.Sprite;

public abstract class Enemy implements iObserver
{
    protected double x, y, direction, speed, speedMax, health;
    protected double rotation, rotationSpeed, fade;
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
        spr.draw(g, x, y, rotation);
    }
}
