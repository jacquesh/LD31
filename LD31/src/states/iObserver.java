package states;

import java.awt.Graphics2D;

/**
 * Observer interface for the observer design pattern.
 */
public interface iObserver
{
    /**
     * Update the observer, returning whether it should be destroyed.
     * @return Call destroy if true, not if false.
     */
    public boolean update();
    
    /**
     * Draw the observer to the graphics element.
     * @param g Graphics element to draw on.
     */
    public void draw(Graphics2D g);
}