package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import states.eControl;
import util.Utility;

/**
 * The core of the game, containing the running thread, the canvas calls the update and draw method
 *  of the controller which essentially controls the game logic. Additionally this extends the
 *  JPanel which is drawn to constantly.
 */
public final class Canvas extends JPanel implements Runnable
{
    //Game control
    private eControl control;
    public static final int FPS = 30;
    private final long time = 1000/FPS;
    private Thread thread;

    //Graphics
    public static int hSize = Main.background.getWidth();
    public static int vSize = Main.background.getHeight();
    private BufferedImage image;
    private Graphics2D g;
    
    //Mouse
    private static final BufferedImage CURSOR = Utility.loadImage("/Resources/Images/Window/cursor.png");
    private static final int CURSOR_X = CURSOR.getWidth()/2;
    private static final int CURSOR_Y = CURSOR.getHeight()/2;
    public static int mouse_x, mouse_y;
    
    /**
     * Set some of the previously defined variables.
     */
    public Canvas()
    {
        super();
        
        setPreferredSize(new Dimension(hSize, vSize));
        setFocusable(true);
        requestFocus();

        control = eControl.INSTANCE;
        control.setState(eControl.States.GAME);
        
        image = new BufferedImage(hSize, vSize, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        
        //Render details
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        //Set a blank cursor
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                Utility.loadImage("/Resources/Images/Window/blankcursor.png"), new Point(0, 0), "Blank Cursor"));
    }
    
    @Override
    public void addNotify()
    {
        super.addNotify();
        //Create the thread and add the listeners.
        if(thread == null)
        {
            thread = new Thread(this);
            addKeyListener(new keyListener());
            addMouseListener(new mouseListener());
            thread.start();
        }
    }
    
    @Override
    public void run()
    {
        long start;
        long elapsed;
        long wait;
        
        //Game loop
        while(true)
        {
            start = System.nanoTime();
            
            update();
            draw();
            
            elapsed = System.nanoTime() - start;
            wait = time - elapsed / 1000000; //Wait time before next frame
            
            try
            {
                if(wait > 0)
                {
                    //Sleep the thread for the set amount of time, to get a consistent FPS.
                    thread.sleep(wait);
                }
            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Sets the on-screen mouse position and sends an update request to the controller.
     */
    private void update()
    {
        mouse_x = MouseInfo.getPointerInfo().getLocation().x;
        mouse_y = MouseInfo.getPointerInfo().getLocation().y;
        control.update();
    }
    
    /**
     * Nothing exciting, just redraw itself, other functionality may be added later.
     */
    private void draw()
    {
        repaint();
    }
    
    /**
     * The lowest user defined drawing, draws the image and cursor to the JPanel.
     * @param g2d JPanel graphics element.
     */
    @Override
    public void paintComponent(Graphics g2d)
    {
        super.paintComponent(g2d);
        
        //Clear the old graphics
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //Draw the graphics
        control.draw(g);
        
        //Draw the cursor
        g.drawImage(CURSOR, mouse_x-CURSOR_X, mouse_y-CURSOR_Y, null);
        
        //Draw the graphics to the screen
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }

    /**
     * Pass any mouse press or release actions to the controller where it gets properly handled
     *  based on the current state.
     */
    private class mouseListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent button)
        {
            control.mousePressed(button.getButton());
        }
        
        @Override
        public void mouseReleased(MouseEvent button)
        {
            control.mouseReleased(button.getButton());
        }
    }
    
    /**
     * Pass any keyboard press or release actions to the controller where it gets properly handled
     *  based on the current state.
     */
    private class keyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent key)
        {
            control.keyPressed(key.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent key)
        {
            control.keyReleased(key.getKeyCode());
        }
    }
}