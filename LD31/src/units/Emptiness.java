package units;

import java.awt.Graphics2D;

import states.iObserver;

public class Emptiness implements iObserver
{
    private int x;
    private int y;
    private int w;
    private int h;

    public Emptiness(int x, int y, int w, int h)
    {
        this.x = x - w/2;
        this.y = y - h/2;
        this.w = w;
        this.h = h;
    }

    public boolean update()
    {
        return false;
    }
    
    public void draw(Graphics2D g)
    {
        g.fillRect(x,y,w,h);
    }
}
