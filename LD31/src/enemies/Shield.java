package enemies;

import states.eControl;
import states.iSubject;

public class Shield extends Enemy
{

    private Enemy orbittee;
    private double orbitRadius;

    public Shield(Enemy parent)
    {
        super(0, 0);
        
        orbittee = parent;
        orbitRadius = 48;
        spr = orbittee.spr;

        speedMax = 5;
        health = 5;
        size = 32;
        rotationSpeed = (Math.random()-0.5)/4;
        fade = 0;

        border = border16;
    }
    
    @Override
    public boolean update()
    {
        if(orbittee == null)
        {
            eControl.INSTANCE.getObservers(iSubject.ObsTypes.ENEMY).remove(this);
            return false;
        }

        rotation += rotationSpeed;
        if(rotation > 2*Math.PI)
            rotation -= 2*Math.PI;
        else if(rotation < -2*Math.PI)
            rotation += 2*Math.PI;

        x = orbittee.x + Math.cos(rotation)*orbitRadius;
        y = orbittee.y + Math.sin(rotation)*orbitRadius;
        
        return super.update();
    }
}
