/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers.items.nature / NatureConfigManager
*  created on 21.5.2019 , 22:25:52 
 */
package configmanagers.textures;

import configmanagers.ConfigManager;
import configmanagers.ConfigWrapper;
import java.awt.Point;
import java.io.InputStream;

/**
 * NatureConfigManager
 * - config manager that gets information from texture texcfs and translates it into wrappers
 * @author filip
 */
public class TextureConfigManager extends ConfigManager {
    
    ////// VARIABLES //////
    
    public boolean headRead = false;
    
    public String spriteSheetName;
    public int columns;
    public int rows;
    public int texWidth;
    public int texHeight;
    
    public SpriteWrapper spriteWrapper;
    
    ////// CONSTRUCTORS //////  
    
    /**
     * On creaton grabs the file and translates it by its rules to a set of wrappers
     * @param configFile file that will be read to the rules of the manager
     */
    public TextureConfigManager(InputStream configFile) {
        super(configFile);
        
        ItemTextureWrapper[] wraps = new ItemTextureWrapper[this.getWrappers().length];
        for(int i = 0; i < wraps.length; i++) { 
            wraps[i] = (ItemTextureWrapper)this.getWrapper(i);
        }
        
        spriteWrapper = new SpriteWrapper(spriteSheetName, texWidth, texHeight, columns, rows, wraps);
    }

    ////// METHODS ///////  
    
    @Override
    public ConfigWrapper stringToWrapper(String line) {
        String[] words = line.split(" ");
        
        if(!headRead) { 
            //reading header of the texcf
            
            spriteSheetName = words[0];
            
            String[] dimensions = words[1].split("x");
            texWidth = Integer.parseInt(dimensions[0]);
            texHeight = Integer.parseInt(dimensions[1]);
            
            columns = Integer.parseInt(words[2]);
            rows = Integer.parseInt(words[3]);
            
            headRead = true;
            return null;
        } else { 
            //reading standart lines 
        
            String itemId = words[0];
            
            String[] startend = words[1].split(";");
            
            TextureUnit[] units = new TextureUnit[startend.length];
            
            for(int i = 0; i < startend.length; i++) {
                String[] points = startend[i].split(":");
                
                String[] a = points[0].substring(1, points[0].length() - 1).split(",");
                Point pa = new Point(Integer.parseInt(a[0]) , Integer.parseInt(a[1]));
                
                String[] b = points[1].substring(1, points[1].length() - 1).split(",");
                Point pb = new Point(Integer.parseInt(b[0]) , Integer.parseInt(b[1]));
                
                units[i] = new TextureUnit(itemId, spriteSheetName, pa, pb);
            }
            
            return new ItemTextureWrapper(itemId, units);
        }
        
    }
    
}
