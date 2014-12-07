package enemies;

import states.eControl;
import util.Sprite;
import util.Utility;

public class Splitter extends Enemy
{
    int timer;
    
    public Splitter(double x, double y)
    {
        super(x, y);
        
        speedMax = 14;
        health = 100;
        rotationSpeed = (Math.random()-0.5)/1.8;
        spr = new Sprite(Utility.loadImage("/Resources/Images/Enemies/enemyBase32.png"), 15.5, 15.5);
        
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
                    e.direction = (i*0.5*Math.PI + rotation)%(2*Math.PI);
                }
                return true;
            }
        }
        
        return super.update();
    }
}
