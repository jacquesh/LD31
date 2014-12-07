package core;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Main
{
    public static BufferedImage background;
    public static void main(String[] args)
    {
        try
        {
            //Get the current desktop image
            background = new Robot().createScreenCapture(
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            
            //Build the framework
            JFrame window = new JFrame("LD31Game");
            window.add(new Canvas());
            window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            window.setUndecorated(true);
            window.setVisible(true);
        }
        catch(AWTException ex)
        {
            System.err.println("Error capturing screen.\n"+ex);
        }
    }
}
