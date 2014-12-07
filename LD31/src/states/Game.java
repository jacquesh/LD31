package states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import java.util.PriorityQueue;

import core.Canvas;
import core.Main;
import enemies.Basic;
import units.Player;
import units.Emptiness;
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
        int spawnX = (spawnLoc.x * GRID_SIZE) + GRID_SIZE/2;
        int spawnY = (spawnLoc.y * GRID_SIZE) + GRID_SIZE/2;
        control.attach(new Emptiness(spawnX, spawnY, GRID_SIZE, GRID_SIZE));
        control.attach(new Basic(spawnX, spawnY));
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
