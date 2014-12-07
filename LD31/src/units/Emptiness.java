package units;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import util.Utility;
import util.Sprite;

import states.iObserver;

public class Emptiness implements iObserver
{
    private static BufferedImage emptySprite = Utility.loadImage("/Resources/Images/Window/emptiness.png");

    public int x;
    public int y;
    public int w;
    public int h;

    private Sprite spr;

    public Emptiness(int x, int y, int w, int h)
    {
        this.x = x - w/2;
        this.y = y - h/2;
        this.w = w;
        this.h = h;

        spr = new Sprite(emptySprite, 0, 0);
    }

    public boolean update()
    {
        return false;
    }
    
    public void draw(Graphics2D g)
    {
        spr.draw(g, x, y);
    }
}
