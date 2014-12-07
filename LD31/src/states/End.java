package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import util.Utility;

public class End implements iState
{
    private Font fontTitle, fontMain;
    
    public End()
    {
        fontTitle = Utility.loadFont("/Resources/Fonts/accent.ttf", Font.TRUETYPE_FONT, Font.BOLD, 100);
        fontMain = Utility.loadFont("/Resources/Fonts/GOUDOS.TTF", Font.TRUETYPE_FONT, Font.PLAIN, 16);
    }

    @Override public void init() {}
    @Override public void update() {}

    @Override
    public void draw(Graphics2D g)
    {
        // Draw title
        g.setColor(Color.WHITE);
        g.setFont(fontTitle);
        g.drawString("GAME OVER", 50, 120);
                
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
    }

    @Override public void keyReleased(int k) {}
    @Override public void mousePressed(int b) {}
    @Override public void mouseReleased(int b) {}
}
