package units;

import core.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import util.Sprite;
import util.Utility;
import weapons.Default;
import weapons.aWeapon;

/**
 * Keeps track of various stocks for the player, in particular its weapons and ammo.
 */
public class Player
{
    public double x, y;
    protected double direction, friction, speed, speedMax, health;
    protected double rotation;
    protected boolean up, down, left, right, shoot;
    protected Sprite spr;
    protected aWeapon weaponBase, weapon;
    
    public Player()
    {
        x = Canvas.hSize/2;
        y = Canvas.vSize/2;
        direction = 0;
        friction = 2;
        speed = 0;
        speedMax = 12;
        health = 100;
        
        up = false;
        down = false;
        left = false;
        right = false;
        shoot = false;
        
        spr = new Sprite(Utility.loadImage("/Resources/Images/Units/player.png"), 9, 19);
        
        weaponBase = new Default(x, y, direction);
        weapon = weaponBase;
    }
    
    public void update()
    {
        int v = 0;
        int h = 0;
        if(up) v--;
        if(down) v++;
        if(left) h--;
        if(right) h++;
        
        if(v!=0 || h!=0)
        {
            direction = Math.atan2(v, h);
            speed = speedMax;
        }
        else
        {
            if(speed > 0)
                speed -= friction;
            if(speed < 0)
                speed = 0;
        }
        
        if(speed > 0)
        {
            x += Math.cos(direction)*speed;
            y += Math.sin(direction)*speed;
        }
        
        rotation = Math.atan2(Canvas.mouse_y-y,Canvas.mouse_x-x)+2;
        
        weapon.update();
        weapon.setLocation(x, y, rotation-2);
        if(shoot)
        {
            weapon.shoot();
        }
    }
    
    public void draw(Graphics2D g)
    {
        spr.draw(g, x, y, rotation);
        weapon.draw(g);
    }
    
    /**
     * Provides functionality for the required keys, in particular, the movement.
     *  Later utility keys and possible keybinding shall be implemented. Sets the required boolean
     *  values to the true state.
     * @param k Pressed keyboard key code.
     */
    public void keyPressed(int k)
    {
        switch(k)
        {
            case KeyEvent.VK_W:
                up = true;
                break;
                
            case KeyEvent.VK_S:
                down = true;
                break;
                
            case KeyEvent.VK_A:
                left = true;
                break;
                
            case KeyEvent.VK_D:
                right = true;
                break;
        }
    }
    
    /**
     * Provides functionality for the required keys, in particular, the movement.
     *  Later utility keys and possible keybinding shall be implemented. Sets the required boolean
     *  values to the false state.
     * @param k Released keyboard key code.
     */
    public void keyReleased(int k)
    {
        switch(k)
        {
            case KeyEvent.VK_W:
                up = false;
                break;
                
            case KeyEvent.VK_S:
                down = false;
                break;
                
            case KeyEvent.VK_A:
                left = false;
                break;
                
            case KeyEvent.VK_D:
                right = false;
                break;
        }
    }
    
    /**
     * Provides functionality for the shoot button.
     *  Later additional buttons and possible keybinding shall be implemented.
     *  Sets the required boolean value to the true state.
     * @param b Pressed mouse button code.
     */
    public void mousePressed(int b)
    {
        switch(b)
        {
            case MouseEvent.BUTTON1:
                shoot = true;
                break;
        }
    }
    
    /**
     * Provides functionality for the shoot button.
     *  Later additional buttons and possible keybinding shall be implemented.
     *  Sets the required boolean value to the false state.
     * @param b Released mouse button code.
     */
    public void mouseReleased(int b)
    {
        switch(b)
        {
            case MouseEvent.BUTTON1:
                shoot = false;
                break;
        }
    }
}
