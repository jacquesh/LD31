package enemies;

import states.eControl;
import states.iSubject;

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
        size = 32;
        
        makeImage((int)x, (int)y, 32);
        
        if(Math.random()<0.25)
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
                    speed -= 0.04;
                    if(rotationSpeed < 0)
                    {
                        rotationSpeed -= 0.005;
                    }
                    else
                    {
                        rotationSpeed += 0.005;
                    }
                }
                else
                {
                    for(int i=0; i<health; i++)
                    {
                        Enemy e = new Splitlett(x, y);
                        eControl.INSTANCE.attach(e, iSubject.ObsTypes.ENEMY);
                        e.direction = i*0.5*Math.PI + rotation;
                        switch(i)
                        {
                            case(0): e.image = image.getSubimage( 0,  0, 16, 16); break;
                            case(1): e.image = image.getSubimage(16,  0, 16, 16); break;
                            case(2): e.image = image.getSubimage(16, 16, 16, 16); break;
                            case(3): e.image = image.getSubimage( 0, 16, 16, 16); break;
                            default: e.image = image.getSubimage( 8,  8, 16, 16); break; //Middle in case
                        }
                        e.makeBorder(16);
                    }
                    return true;
                }
            }
        }
        
        return super.update();
    }
}
