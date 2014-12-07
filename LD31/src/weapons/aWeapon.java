package weapons;

import java.awt.Graphics2D;
import states.iObserver;
import util.Sprite;

public abstract class aWeapon implements iObserver
{
    protected int reload_val, reload_time;
    protected int delay_val, delay_time; //time between shots.
    protected int clip_val, clip_max, ammo; //negative ammo value indicated infinite amount.
    protected double x, y, direction, rotation;
    protected Sprite spr;
    
    public aWeapon(double x, double y, double dir)
    {
        this.x = x;
        this.y = y;
        direction = dir;
        rotation = 0;
        
        reload_val = 0;
        delay_val = 0;
    }
    
    public abstract void shoot();
    
    public void setLocation(double x, double y, double dir)
    {
        this.x = x;
        this.y = y;
        direction = dir;
    }
    
    @Override
    public boolean update()
    {
        if(delay_val > 0)
        {
            delay_val--;
        }
        
        if(reload_val > 0)
        {
            reload_val--;
            if(reload_val == 0)
            {
                if(ammo > clip_max || ammo < 0)
                {
                    clip_val = clip_max;
                    ammo -= clip_max;
                }
                else
                {
                    clip_val = ammo;
                    ammo = 0;
                }
            }
        }
        
        if(ammo == 0 && clip_val == 0)
            return true;
        return false;
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        spr.draw(g, x, y, direction+rotation);
    }
}
