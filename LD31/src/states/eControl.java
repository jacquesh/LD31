package states;

import java.awt.Graphics2D;
import java.util.ArrayList;
import units.Player;

/**
 * The backbone of the game logic, it calls the update and draw methods, the mouse and keyboard
 *  events, and the notifying of entities for the current state. All possible state classes need to
 *  be defined in the States enumeration. The control also holds the current player profile, null if
 *  it has not been loaded or created. This is also implements the iSubject class, making it a
 *  concrete subject class for the observer design pattern.
 */
public enum eControl implements iSubject
{
    INSTANCE;
    
    private States currentState;

    private ArrayList<iObserver> observers = new ArrayList<>();
    private ArrayList<iObserver> obs_Proj  = new ArrayList<>();
    private ArrayList<iObserver> obs_Enemy = new ArrayList<>();
    
    public Player player = null;

    /**
     * List of all possible states and defines a static state; its state is non-volatile.
     */
    public enum States
    {
        GAME(new Game());
        
        public iState state;
        
        /**
         * Sets the state associated with the state.
         * @param s New state.
         */
        private States(iState s)
        {
            state = s;
        }
    }
    
    public ArrayList<iObserver> getObservers(ObsTypes t)
    {
        switch(t)
        {
            case GENERAL:       return observers;
            case PROJECTILE:    return obs_Proj;
            case ENEMY:         return obs_Enemy;
            default:            return null;
        }
    }
    
    /**
     * Updates the current state and notifies the observers.
     */
    public void update()
    {
        currentState.state.update();
        notifyUpdate();
    }
    
    /**
     * Draws the current state and notifies the observers.
     * @param g Graphical element to draw to.
     */
    public void draw(Graphics2D g)
    {
        currentState.state.draw(g);
        notifyDraw(g);

        currentState.state.postDraw(g);
    }
    
    /**
     * Retrieves the current player variable, note that this can be null if the player has not been
     *  loaded or created.
     * @return Player variable.
     */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Set the current state to another from the States enumeration.
     * @param state Selected state.
     */
    public void setState(States state)
    {
        currentState = state;
        currentState.state.init();
    }
    
    /**
     * Pass any mouse press events to the current state.
     * @param b Mouse button.
     */
    public void mousePressed(int b)
    {
        currentState.state.mousePressed(b);
    }
    
    /**
     * Pass any mouse release events to the current state.
     * @param b Mouse button.
     */
    public void mouseReleased(int b)
    {
        currentState.state.mouseReleased(b);
    }
    
    /**
     * Pass any keyboard key press events to the current state.
     * @param k Keyboard key.
     */
    public void keyPressed(int k)
    {
        currentState.state.keyPressed(k);
    }
    
    /**
     * Pass any keyboard key release events to the current state.
     * @param k Keyboard key.
     */
    public void keyReleased(int k)
    {
        currentState.state.keyReleased(k);
    }
    
    @Override
    public void attach(iObserver o, ObsTypes t)
    {
        switch(t)
        {
            case GENERAL:
                observers.add(o);
                break;
                
            case PROJECTILE:
                obs_Proj.add(o);
                break;
                
            case ENEMY:
                obs_Enemy.add(o);
                break;
        }
    }

    @Override
    public void detach(int index, ObsTypes t)
    {
        switch(t)
        {
            case GENERAL:
                observers.remove(index);
                break;
                
            case PROJECTILE:
                obs_Proj.remove(index);
                break;
                
            case ENEMY:
                obs_Enemy.remove(index);
                break;
        }
    }

    @Override
    public void notifyUpdate()
    {
        for(int i=0; i<observers.size(); i++)
        {
            if(observers.get(i).update())
            {
                detach(i, ObsTypes.GENERAL);
                i--;
            }
        }
        
        for(int i=0; i<obs_Proj.size(); i++)
        {
            if(obs_Proj.get(i).update())
            {
                detach(i, ObsTypes.PROJECTILE);
                i--;
            }
        }
        
        for(int i=0; i<obs_Enemy.size(); i++)
        {
            if(obs_Enemy.get(i).update())
            {
                detach(i, ObsTypes.ENEMY);
                i--;
            }
        }
    }

    @Override
    public void notifyDraw(Graphics2D g)
    {
        for(iObserver o : observers)
        {
            o.draw(g);
        }
        
        for(iObserver o : obs_Proj)
        {
            o.draw(g);
        }
        
        for(iObserver o : obs_Enemy)
        {
            o.draw(g);
        }
    }
}
