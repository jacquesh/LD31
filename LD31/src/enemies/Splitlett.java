package enemies;

import states.eControl;

public class Splitlett extends Enemy
{

    public Splitlett(double x, double y)
    {
        super(x, y);
        
        speedMax = 8;
        health = 1;
        rotationSpeed = (Math.random()-0.5)/1.8;
        fade = 0;
        size = 16;
        
        border = border16;
    }
    
    @Override
    public boolean update()
    {
        double targetDir = Math.atan2(eControl.INSTANCE.player.y-y, eControl.INSTANCE.player.x-x);
        double angleDiff = direction - targetDir;

        if(Math.abs(angleDiff) > Math.PI)
        {
            if(direction > targetDir)
                angleDiff -= 2*Math.PI;
            else
                angleDiff += 2*Math.PI;
        }

        if(angleDiff > 0)
            direction -= 0.07;
        else
            direction += 0.07;
        
        return super.update();
    }
}
