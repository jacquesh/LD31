package weapons;

import projectiles.BrokenFinger;
import states.eControl;
import states.iSubject;
import util.Sprite;
import util.Utility;

public class Default extends aWeapon
{
    public Default(double x, double y, double dir)
    {
        super(x, y, dir);
        
        reload_time = 40;
        delay_time = 10;
        clip_val = clip_max = 10;
        ammo = -1;
        
        rotation = 2;

        spr = new Sprite(Utility.loadImage("/Resources/Images/Weapons/default.png"), 15, 23);
    }
    
    @Override
    public void shoot()
    {
        if(delay_val == 0 && reload_val == 0)
        {
            clip_val--;
            delay_val = delay_time;
            
            if(clip_val == 0)
            {
                reload_val = reload_time;
            }
            
            eControl.INSTANCE.attach(new BrokenFinger(x, y, direction), iSubject.ObsTypes.PROJECTILE);
            Utility.playSound("/Resources/Sounds/shot.wav");
        }
    }
}
