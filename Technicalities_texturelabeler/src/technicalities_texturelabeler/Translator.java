/*
*  Created by SixKeyStudios
*  Added in project TextureLabeler
*  File technicalities_texturelabeler / Translator
*  created on 25.5.2019 , 0:46:17 
 */
package technicalities_texturelabeler;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filip
 */
public class Translator {
    
    public void translate(String folder, IDInfo[] info, String path, String resolution, int amountX, int amountY) { 
        PrintWriter writer;
        try {
            writer = new PrintWriter(folder + path + ".texcf", "UTF-8");
            writer.println(path + " " + resolution + " " + amountX + " " + amountY);
            for(IDInfo i : info) { 
                String result = i.id + " ";
                for(Point p: i.points) { 
                    String pointString = "[" + (int)p.getX() + "," + (int)p.getY() +"]";
                    result += pointString + ":" + pointString + ";";
                }
                result = result.substring(0, result.length() - 1);
                writer.println(result);
            }

            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Translator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Translator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
