package projectiles;

import util.Sprite;
import util.Utility;

public class BrokenFinger extends aProjectile
{
    public BrokenFinger(double x, double y, double dir)
    {
        super(x, y, dir);
        
        speed = 40;
        distance = 1000;
        damage = 2;
        
        spr = new Sprite(Utility.loadImage("/Resources/Images/Projectiles/brokenFinger.png"), 8, 3.5);
    }
}
