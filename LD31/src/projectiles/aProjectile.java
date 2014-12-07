package projectiles;

import java.awt.Graphics2D;
import states.iObserver;
import util.Sprite;

public abstract class aProjectile implements iObserver
{
    double x, y, xPrev, yPrev, direction, speed, distance, damage;
    Sprite spr;
    
    public aProjectile(double x, double y, double dir)
    {
        this.x = x;
        this.y = y;
        direction = dir;
    }
    
    @Override
    public boolean update()
    {
        xPrev = x;
        yPrev = y;
        x += Math.cos(direction)*speed;
        y += Math.sin(direction)*speed;
        
        distance -= speed;
        if(distance <= 0)
            return true;
        return false;
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        spr.draw(g, x, y, direction);
    }
}