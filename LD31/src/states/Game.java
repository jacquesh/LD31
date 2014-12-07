package states;

import core.Canvas;
import core.Main;
import enemies.Basic;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.PriorityQueue;
import units.Emptiness;
import units.Player;
import util.GridPoint;

public class Game implements iState
{
    public static final int GRID_SIZE = 32;

    Player player;

    public PriorityQueue<GridPoint> spawnLocQueue;
    public boolean[][] spawnLocUsed;
    
    @Override
    public void init()
    {
        player = new Player();
        control.player = player;

        spawnLocQueue = new PriorityQueue<GridPoint>(64);
        int xBlocks = Canvas.hSize/GRID_SIZE;
        int yBlocks = Canvas.vSize/GRID_SIZE;

        spawnLocUsed = new boolean[yBlocks][xBlocks];
        for(int y=0; y<yBlocks; ++y)
        {
            for(int x=0; x<xBlocks; ++x)
            {
                spawnLocUsed[y][x] = false;
            }
        }

        for(int y=0; y<yBlocks; ++y)
        {
            spawnLocQueue.add(new GridPoint(0, y));
            spawnLocQueue.add(new GridPoint(xBlocks-1, y));
        }
        for(int x=0; x<xBlocks; ++x)
        {
            spawnLocQueue.add(new GridPoint(x, 0));
            spawnLocQueue.add(new GridPoint(x, yBlocks-1));
        }
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

    private void spawnEnemy()
    {
        GridPoint spawnLoc = spawnLocQueue.poll();
        spawnLocUsed[spawnLoc.y][spawnLoc.x] = true;

        for(int y=-1; y<=1; ++y)
        {
            for(int x=-1; x<=1; ++x)
            {
                if(x == 0 && y == 0)
                    continue;
                int tempX = spawnLoc.x + x;
                int tempY = spawnLoc.y + y;
                if(tempX < 0 || tempX >= spawnLocUsed[0].length || tempY < 0 || tempY >= spawnLocUsed.length)
                    continue;
                if(spawnLocUsed[tempY][tempX])
                    continue;
                GridPoint newPoint = new GridPoint(tempX, tempY);
                if(spawnLocQueue.contains(newPoint))
                    continue;
                spawnLocQueue.add(newPoint);
            }
        }

        int spawnX = (spawnLoc.x * GRID_SIZE) + GRID_SIZE/2;
        int spawnY = (spawnLoc.y * GRID_SIZE) + GRID_SIZE/2;
        control.attach(new Emptiness(spawnX, spawnY, GRID_SIZE, GRID_SIZE), iSubject.ObsTypes.GENERAL);
        control.attach(new Basic(spawnX, spawnY), iSubject.ObsTypes.ENEMY);
    }

    @Override
    public void mousePressed(int b)
    {
        switch(b)
        {
            case(MouseEvent.BUTTON3):
                spawnEnemy();
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
