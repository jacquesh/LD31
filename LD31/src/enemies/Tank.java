package enemies;

import states.eControl;
import states.iSubject;

public class Tank extends Enemy
{
    protected int timer;

    public Tank(double x, double y)
    {
        super(x, y);

        speedMax = 2;
        health = 8;
        rotationSpeed = (Math.random()-0.5)/2;
        
        makeImage((int)x, (int)y, 32);
        eControl.INSTANCE.attach(new Shield(this), iSubject.ObsTypes.ENEMY);
    }
    
    @Override
    public boolean update()
    {
        direction = Math.atan2(eControl.INSTANCE.player.y-y, eControl.INSTANCE.player.x-x);
        
        return super.update();
    }
}
