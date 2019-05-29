/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers / ItemWrapper
*  created on 21.5.2019 , 20:58:49 
 */
package technicalities.configmanagers.textures;

import technicalities.configmanagers.ConfigWrapper;

/**
 * ItemWrapper 
 * - wrapper for item configuration and init 
 * @author filip
 */
public class SpriteWrapper implements ConfigWrapper {
    
    public String id;
    public int texWidth;
    public int texHeight;
    public int columns;
    public int rows;
    public ItemTextureWrapper[] wrappers;
    
    public SpriteWrapper(String id, int texWidth, int texHeight, int columns, int rows, ItemTextureWrapper[] wrappers) { 
        this.id = id;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.columns = columns;
        this.rows = rows;
        this.wrappers = wrappers;
    }
    
    @Override
    public String getId() { 
        return id;
    }
    
    @Override
    public String toString() { 
        String result = id.toUpperCase();
        for(int i = 0; i < wrappers.length; i++) { 
            result+="\n" + wrappers[i].toString();
        }
        return result;
    }
    
}
