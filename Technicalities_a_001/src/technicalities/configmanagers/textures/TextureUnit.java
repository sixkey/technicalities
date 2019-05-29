/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers.textures / TextureUnit
*  created on 25.5.2019 , 12:44:20 
 */
package technicalities.configmanagers.textures;

import java.awt.Point;

/**
 *
 * @author filip
 */
public class TextureUnit{
    public String itemId;
    public String spriteSheetId;
    public Point start;
    public Point end;
    
    public TextureUnit(String itemId, String spriteSheetId, Point start, Point end) { 
        this.itemId = itemId;
        this.spriteSheetId = spriteSheetId;
        this.start = start;
        this.end = end;
    }
    
    @Override
    public String toString() { 
        return itemId + " " + spriteSheetId + " " + start.toString() + " " + end.toString();
    }
}
