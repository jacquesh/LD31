package enemies;

import core.Main;
import states.eControl;
import util.Sprite;
import util.Utility;

public class Basic extends Enemy
{
    protected boolean splitter = false;
    protected int timer;

    public Basic(double x, double y)
    {
        this(x, y, (int)x, (int)y);
    }

    public Basic(double x, double y, int spawnX, int spawnY)
    {
        super(x, y, spawnX, spawnY);

        speedMax = 5;
        health = 4;
        rotationSpeed = (Math.random()-0.5)/2;
        spr = new Sprite(Main.background.getSubimage(spawnX-16+2,spawnY-16+2, 28,28), 14, 14);
        
        if(Math.random()<0.15)
        {
            splitter = true;
            timer = 60 + (int)(Math.random()*60);
        }
    }
    
    @Override
    public boolean update()
    {
        direction = Math.atan2(eControl.INSTANCE.player.y-y, eControl.INSTANCE.player.x-x);
        
        if(splitter)
        {
            if(fade == 0)
            {
                if(timer > 0)
                {
                    timer--;
                }
                else
                {
                    for(int i=0; i<health; i++)
                    {
                        Enemy e = new Splitlett(x, y, spawnX, spawnY);
                        eControl.INSTANCE.attach(e);
                        e.direction = (i*0.5*Math.PI + rotation)%(2*Math.PI);
                    }
                    return true;
                }
            }
        }
        
        return super.update();
    }
}
