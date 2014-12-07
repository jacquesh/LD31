package states;

import core.Canvas;
import core.Main;
import enemies.Basic;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.PriorityQueue;
import java.util.ArrayList;

import core.Canvas;
import core.Main;
import enemies.Enemy;
import enemies.Basic;
import units.Player;
import units.Emptiness;
import units.Destroyer;

import util.GridPoint;
import util.Utility;

public class Game implements iState
{
    public static final int GRID_SIZE = 32;
    public static final int WAVE_LENGTH = 30*20; //20 seconds
    public static final int WAVE_END_LENGTH = 30*3; //3 seconds

    Player player;

    public PriorityQueue<GridPoint> spawnLocQueue;
    public boolean[][] spawnLocUsed;
    public int currentWave;

    private int spawnInterval;
    private int timeToSpawn;
    private int waveTime;
    private int waveEndTime;
    private boolean waveEnded;

    private ArrayList<iObserver> destroyList;
    private Destroyer destroyer;

    private Font countdownFont;
    private Color countdownBackground;

    @Override
    public void init()
    {
        player = new Player();
        control.player = player;
        spawnLocQueue = new PriorityQueue<GridPoint>(64);
        destroyList = new ArrayList<iObserver>();
        destroyer = new Destroyer();
        currentWave = 0;
        countdownFont = Utility.loadFont("/Resources/Fonts/PowerGreen.ttf", Font.TRUETYPE_FONT, Font.PLAIN, 48);
        countdownBackground = new Color(0, 0, 0, 128);

        reset();
        player.reset();
    }

    private void reset()
    {
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

        spawnLocQueue.clear();
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

        destroyer.x = -64;

        spawnInterval = 30;
        waveTime = 0;
        waveEndTime = 0;
        waveEnded = false;
        timeToSpawn = spawnInterval;
    }

    @Override
    public void update()
    {
        if(waveEnded)
        {
            destroyer.x += 200;
            for(int i=destroyList.size()-1; i>=0; --i)
            {
                iObserver obs = destroyList.get(i);
                if(obs instanceof Enemy)
                {
                    Enemy e = (Enemy)obs;
                    if(e.x < destroyer.x)
                        control.getObservers(iSubject.ObsTypes.ENEMY).remove(e);
                }
                else if(obs instanceof Emptiness)
                {
                    Emptiness e = (Emptiness)obs;
                    if(e.x < destroyer.x)
                        control.getObservers(iSubject.ObsTypes.ENEMY).remove(e);
                }
            }
            ++waveEndTime;
            if(waveEndTime > WAVE_END_LENGTH)
            {
                waveEnded = false;
                waveEndTime = 0;
                reset();
            }
        }
        else
        {
            ++waveTime;
            if(waveTime >= WAVE_LENGTH)
            {
                currentWave = (currentWave < 9) ? currentWave+1 : currentWave; //Clamp to 9
                waveTime = 0;
                waveEnded = true;
                Utility.playSound("/Resources/Sounds/destroy.wav");
                for(iObserver iob : control.getObservers(iSubject.ObsTypes.ENEMY))
                {
                    if((iob instanceof Enemy) || (iob instanceof Emptiness))
                    {
                        destroyList.add(iob);
                    }
                }
            }
            else
            {
                --timeToSpawn;
                if(timeToSpawn <= 0)
                {
                    spawnEnemy();
                    timeToSpawn = spawnInterval;
                    spawnInterval -= 1;
                }
            }
        }
        player.update();
    }

    @Override
    public void draw(Graphics2D g)
    {
        g.drawImage(Main.background, 0, 0, null);
        player.draw(g);
    }

    @Override
    public void postDraw(Graphics2D g)
    {
        if(waveEnded)
        {
            destroyer.draw(g);
        }

        g.setColor(countdownBackground);
        g.fillRoundRect((Canvas.hSize/2)-60, Canvas.vSize-52, 120,50, 30,30);

        double timeLeft = (WAVE_LENGTH - waveTime)/30.0;
        g.setColor(Color.white);
        g.setFont(countdownFont);
        g.drawString(String.format("%.2f", timeLeft), Canvas.hSize/2 - 50, Canvas.vSize - 10);
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

        control.attach(new Emptiness(spawnX, spawnY, GRID_SIZE, GRID_SIZE), iSubject.ObsTypes.ENEMY);

        Enemy newEnemy = null;
        double spawnSeed = Math.random();
        double spawnProb = WaveData.waveData[currentWave].spawnChance[0];
        if(spawnSeed < spawnProb)
            newEnemy = new Basic(spawnX, spawnY); // Enemy 0
        else
        {
            spawnProb += WaveData.waveData[currentWave].spawnChance[1];
            if(spawnSeed < spawnProb)
                newEnemy = new Basic(spawnX, spawnY); // Enemy 1
            else
            {
                spawnProb += WaveData.waveData[currentWave].spawnChance[2];
                if(spawnSeed < spawnProb)
                    newEnemy = new Basic(spawnX, spawnY); // Enemy 2
                else
                {
                    spawnProb += WaveData.waveData[currentWave].spawnChance[3];
                    if(spawnSeed < spawnProb)
                        newEnemy = new Basic(spawnX, spawnY); // Enemy 3
                    else
                        newEnemy = new Basic(spawnX, spawnY); // Enemy 4
                }
            }
        }

        control.attach(newEnemy, iSubject.ObsTypes.ENEMY);
    }

    @Override
    public void mousePressed(int b)
    {
        if(b == 1)
        {
            Utility.playSound("/Resources/Sounds/shot.wav");
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
