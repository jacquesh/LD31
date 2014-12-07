package projectiles;

import java.awt.Graphics2D;
import states.iObserver;
import util.Sprite;

public abstract class aProjectile implements iObserver
{
    public double x, y, xPrev, yPrev, speed, damage;
    protected double direction, distance;
    protected Sprite spr;
    public boolean flag = false;
    
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
            flag = true;
        
        return flag;
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        spr.draw(g, x, y, direction);
    }
}