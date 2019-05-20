package SixGen.Utils.Files;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * FileLoader Abilities Load image
 */
public class FileManager {

    // VOIDS
    public InputStream getFileFromClassSource(Class clasz, String path) {
        InputStream in = clasz.getResourceAsStream(path);
        return in;
    }

    public static Font getFont(InputStream in) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public BufferedImage getImageFromClassSource(Class clasz, String path) {
        //@getImageFromSource
        BufferedImage result = null;
        try {
            result = ImageIO.read(clasz.getResource(path));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public BufferedImage getImageFromSource(String path) {
        BufferedImage result = null;
        try {
            result = ImageIO.read(new URL(path));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public void saveImageToPath(String path, BufferedImage image) {
        try {
            File outputfile = new File(path);
            ImageIO.write(image, "png", outputfile);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public String[] getString(String path) {
        BufferedReader br = null;
        String result[] = null;
        try {
            InputStream inputStream = getClass().getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            result = sb.toString().split("\n");
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return result;
    }

}
