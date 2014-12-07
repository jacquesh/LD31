package states;

import java.awt.Graphics2D;

/**
 * Subject interface for the observer design pattern
 */
public interface iSubject
{
    enum ObsTypes
    {
        GENERAL,
        PROJECTILE,
        ENEMY;
    }
    
    /**
     * Add an observer to the list.
     * @param o Observer.
     */
    public void attach(iObserver o, ObsTypes t);
    
    /**
     * Remove an observer from the list at the given index.
     * @param index Observer index in the list.
     */
    public void detach(int index, ObsTypes t);
    
    /**
     * Notify all observers that they must be updated.
     */
    public void notifyUpdate();
    
    /**
     * Notify all observers that they must be drawn to the graphics element.
     * @param g Graphics element to draw on.
     */
    public void notifyDraw(Graphics2D g);
}
