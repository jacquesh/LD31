package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Functionality for loading various internal files in a static context, regardless of the compile
 *  format of the game. All exception handling should be done in the supplied methods.
 */
public class Utility
{
    public static Properties path = new Properties();
    
    /**
     * Create a buffered image from an image file which was compiled in the game.
     * @param str File location, relative to source file.
     * @return Loaded image.
     */
    public static BufferedImage loadImage(String str)
    {
        BufferedImage img = null;
        InputStream resource = path.getClass().getResourceAsStream(str);
        if(resource == null)
        {
            System.err.println("Error loading image: "+str);
        }
        else
        {
            try
            {
                img = ImageIO.read(resource);
            }
            catch (IOException ex)
            {
                System.err.println("Error loading image: "+str+"\n"+ex);
            }
        }
        return img;
    }
    
    public static void playSound(String str)
    {
        try
        {
            Clip result = AudioSystem.getClip();
            InputStream resource = path.getClass().getResourceAsStream(str);
            AudioInputStream stream = AudioSystem.getAudioInputStream(resource);
            
            result.open(stream);
            result.start();
        }
        catch(LineUnavailableException lue)
        {
            lue.printStackTrace();
        }
        catch(UnsupportedAudioFileException uafe)
        {
            uafe.printStackTrace();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /**
     * Create and load a font from a font file which was compiled in the game.
     * @param str File location, relative to source file.
     * @param fontFormat Format of the font.
     * @param style Style of the font.
     * @param size Relative size of the font.
     * @return Loaded font.
     */
    public static Font loadFont(String str, int fontFormat, int style, float size)
    {
        Font font = null;
        InputStream resource = path.getClass().getResourceAsStream(str);
        
        if(resource == null)
        {
            System.err.println("Error loading font: "+str);
        }
        else
        {
            try
            {
                font = java.awt.Font.createFont(fontFormat, resource);
            }
            catch (FontFormatException | IOException ex)
            {
                System.err.println("Error loading font: "+str+"\n"+ex);
            }
        }
        return font.deriveFont(style, size);
    }
}
