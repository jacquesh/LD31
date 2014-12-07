package units;

import java.awt.Graphics2D;

import util.Sprite;
import util.Utility;

public class Destroyer
{
    public int x;
    public Sprite spr;

    public Destroyer()
    {
        spr = new Sprite(Utility.loadImage("/Resources/Images/Units/destroyer.png"),0,0);
    }

    public void draw(Graphics2D g)
    {
        spr.draw(g, x, 0);
    }
}
