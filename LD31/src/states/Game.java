package states;

import core.Canvas;
import core.Main;
import enemies.Basic;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import units.Player;

public class Game implements iState
{
    Player player;
    
    @Override
    public void init()
    {
        player = new Player();
        control.player = player;
    }

    @Override
    public void update()
    {
        player.update();
    }

    @Override
    public void draw(Graphics2D g)
    {
        g.drawImage(Main.background, 0, 0, null);
        player.draw(g);
    }

    @Override
    public void mousePressed(int b)
    {
        switch(b)
        {
            case(MouseEvent.BUTTON3):
                control.attach(new Basic(Canvas.mouse_x, Canvas.mouse_y));
                break;
        }
        player.mousePressed(b);
    }

    @Override
    public void mouseReleased(int b)
    {
        player.mouseReleased(b);
    }

    @Override
    public void keyPressed(int k)
    {
        switch(k)
        {
            case(KeyEvent.VK_ESCAPE):
                System.exit(0);
                break;
        }
        player.keyPressed(k);
    }

    @Override
    public void keyReleased(int k)
    {
        player.keyReleased(k);
    }
    
}
