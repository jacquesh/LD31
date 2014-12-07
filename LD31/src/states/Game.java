package states;

import core.Canvas;
import core.Main;
import enemies.Basic;
import enemies.Enemy;
import enemies.Splitter;
import enemies.Tank;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.PriorityQueue;
import projectiles.aProjectile;
import units.Destroyer;
import units.Emptiness;
import units.Player;
import util.GridPoint;
import util.Utility;

public class Game implements iState
{
    public static final int GRID_SIZE = 32;
    public static final int WAVE_LENGTH = 30*40; //40 seconds
    public static final int WAVE_END_LENGTH = 6;

    Player player;

    public PriorityQueue<GridPoint> spawnLocQueue;
    public boolean[][] spawnLocUsed;
    public int currentWave;

    private int spawnInterval;
    private int timeToSpawn;
    private int waveTime;
    private int waveEndTime;
    private boolean waveEnded;

    private boolean gameOver;

    private Destroyer destroyer;

    private Font countdownFont;
    private Color countdownBackground;

    @Override
    public void init()
    {
        player = new Player();
        control.player = player;
        spawnLocQueue = new PriorityQueue<GridPoint>(64);
        destroyer = new Destroyer();
        currentWave = 0;
        countdownFont = Utility.loadFont("/Resources/Fonts/PowerGreen.ttf", Font.TRUETYPE_FONT, Font.PLAIN, 48);
        countdownBackground = new Color(0, 0, 0, 128);

        gameOver = false;
        reset();
        player.reset();
    }

    private void reset()
    {
        control.getObservers(iSubject.ObsTypes.ENEMY).clear();

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

        spawnInterval = 45;
        waveTime = 0;
        waveEndTime = 0;
        waveEnded = false;
        timeToSpawn = spawnInterval;
    }

    @Override
    public void update()
    {
        if(gameOver)
            return;

        if(waveEnded)
        {
            destroyer.x += 180;

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
        
        //Check the bullet collisions
        for(iObserver o_P : control.getObservers(iSubject.ObsTypes.PROJECTILE))
        {
            
            aProjectile p = (aProjectile) o_P;
            for(iObserver o_E : control.getObservers(iSubject.ObsTypes.ENEMY))
            {
                if(o_E instanceof Emptiness)
                    continue;

                Enemy e = (Enemy) o_E;
                //Quick preliminary check
                if(Math.abs(e.x - p.xPrev) < p.speed + e.size &&
                        Math.abs(e.y - p.yPrev ) < p.speed + e.size)
                { //Found math here: http://stackoverflow.com/questions/1073336/circle-line-segment-collision-detection-algorithm
                    double dx = p.x - p.xPrev;
                    double dy = p.y - p.yPrev;
                    double fx = p.x - e.x;
                    double fy = p.y - e.y;
                    
                    double a = dx*dx + dy*dy;
                    double b = 2*(dx*fx + dy*fy);
                    double c = fx*fx + fy*fy  - e.size*e.size ;

                    double discriminant = b*b - 4*a*c;

                    if(discriminant >= 0)
                    {
                        discriminant = Math.sqrt( discriminant );

                        double t1 = (-b - discriminant)/(2*a);
                        double t2 = (-b + discriminant)/(2*a);

                        if((t1 >= 0 && t1 <= 1) || (t2 >= 0 && t2 <= 1))
                        {
                            e.health -= 1;
                            p.flag = true;
                        }
                    }
                }
            }
        }

        for(iObserver o_E : control.getObservers(iSubject.ObsTypes.ENEMY))
        {
            if(o_E instanceof Emptiness)
                continue;

            Enemy e = (Enemy)o_E;
            double dx = player.x - e.x;
            double dy = player.y - e.y;
            double dist = dx*dx + dy*dy;

            if(dist < (e.size/2+16)*(e.size/2+16))
            {
                player.health -= 1;
            }
        }
        player.update();

        if(player.health <= 0)
        {
            gameOver = true;
        }
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
        g.drawString(String.format("%05.2f", timeLeft), Canvas.hSize/2 - 50, Canvas.vSize - 10);

        if(gameOver)
        {
            g.setColor(countdownBackground);
            g.fillRoundRect((Canvas.hSize/2)-260, (Canvas.vSize/2)-120, 500,230, 30,30);

            g.setColor(Color.white);
            g.drawString("Game Over", (Canvas.hSize/2) - 100, (Canvas.vSize/2) - 50);
            g.drawString("Press <space> to restart", (Canvas.hSize/2) - 250, (Canvas.vSize/2) - 0);
            g.drawString("Press <escape> to quit", (Canvas.hSize/2) - 220, (Canvas.vSize/2) + 50);
        }
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
                newEnemy = new Splitter(spawnX, spawnY); // Enemy 1
            else
            {
                spawnProb += WaveData.waveData[currentWave].spawnChance[2];
                if(spawnSeed < spawnProb)
                    newEnemy = new Tank(spawnX, spawnY); // Enemy 2
                else
                {
                    newEnemy = new Tank(spawnX, spawnY); // Enemy 3
                }
            }
        }

        control.attach(newEnemy, iSubject.ObsTypes.ENEMY);
    }

    @Override
    public void mousePressed(int b)
    {
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
            case(KeyEvent.VK_SPACE):
                System.out.println("RESTART");
                gameOver = false;
                currentWave = 0;
                reset();
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
