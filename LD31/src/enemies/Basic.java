package enemies;

import states.eControl;
import util.Sprite;
import util.Utility;

public class Basic extends Enemy
{
    protected boolean splitter = false;
    protected int timer;
    
    public Basic(double x, double y)
    {
        super(x, y);
        
        speedMax = 5;
        health = 4;
        rotationSpeed = (Math.random()-0.5)/2;
        spr = new Sprite(Utility.loadImage("/Resources/Images/Enemies/enemyBase32.png"), 15.5, 15.5);
        
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
                        Enemy e = new Splitlett(x, y);
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
