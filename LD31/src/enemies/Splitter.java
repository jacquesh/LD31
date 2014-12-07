package enemies;

import states.eControl;

public class Splitter extends Enemy
{
    int timer;
    
    public Splitter(double x, double y)
    {
        super(x, y);
        
        speedMax = 14;
        health = 100;
        rotationSpeed = (Math.random()-0.5)/1.8;
        
        makeImage((int)x, (int)y, 32);
        
        timer = 45;
    }
    
    @Override
    public boolean update()
    {
        direction = Math.atan2(eControl.INSTANCE.player.y-y, eControl.INSTANCE.player.x-x);

        if(fade == 0)
        {
            if(timer > 0)
            {
                timer--;
                speed -= 0.08;
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
                for(int i=0; i<4; i++)
                {
                    Enemy e = new Splitlett(x, y);
                    eControl.INSTANCE.attach(e);
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
        
        return super.update();
    }
}
