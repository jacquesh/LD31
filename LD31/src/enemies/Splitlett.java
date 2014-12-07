package enemies;

import core.Main;
import states.eControl;
import util.Sprite;
import util.Utility;

public class Splitlett extends Enemy
{

    public Splitlett(double x, double y)
    {
        this(x, y, (int)x, (int)y);
    }

    public Splitlett(double x, double y, int spawnX, int spawnY)
    {
        super(x, y, spawnX, spawnY);
        
        speedMax = 10;
        health = 1;
        rotationSpeed = (Math.random()-0.5)/1.8;
        //spr = new Sprite(Utility.loadImage("/Resources/Images/Enemies/enemyBase16.png"), 7.5, 7.5);
        spr = new Sprite(Main.background.getSubimage(spawnX-8+2,spawnY-8+2, 12, 12), 6, 6);
        fade = 0;
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
