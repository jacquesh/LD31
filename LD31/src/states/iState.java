package states;

import java.awt.Graphics2D;

/**
 * The basis for any state, provides the required methods and sets an easy-access variable to the
 *  controller singleton (enumeration).
 */
public interface iState
{
    public static final eControl control = eControl.INSTANCE;
    
    /**
     * On setting the state as the current state.
     */
    public abstract void init();
    
    /**
     * Per frame update method, for game logic such as movement, regeneration and timing.
     */
    public abstract void update();
    
    /**
     * Per frame drawing method, all drawing/calls must be done here.
     * @param g Graphics element to draw on.
     */
    public abstract void draw(Graphics2D g);

    /**
     * Per frame drawing that happens after all other draws (IE after all attached observers have been drawn)
     * @param g Graphics element to draw on.
     */
    public abstract void postDraw(Graphics2D g);
    
    /**
     * On mouse press events, often this will need to be passed to other objects.
     * @param b Mouse button.
     */
    public abstract void mousePressed(int b);
    
    /**
     * On mouse release events, often this will need to be passed to other objects.
     * @param b Mouse button.
     */
    public abstract void mouseReleased(int b);
    
    /**
     * On keyboard key press events, often this will need to be passed to other objects.
     * @param k Keyboard key.
     */
    public abstract void keyPressed(int k);
    
    /**
     * On keyboard key release events, often this will need to be passed to other objects.
     * @param k Keyboard key.
     */
    public abstract void keyReleased(int k);
}
