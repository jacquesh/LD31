package launcher;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Launcher
{
    public static void main(String[] args)
    {
        BufferedImage image;
        try
        {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File("C:/Users/H3katonkheir/Desktop/screenshot.png"));
            
            Desktop.getDesktop().open(new File("C:/Users/H3katonkheir/Desktop/screenshot.png"));
        }
        catch(AWTException | IOException ex)
        {
            System.out.println("ERROR");
        }
    }
}
