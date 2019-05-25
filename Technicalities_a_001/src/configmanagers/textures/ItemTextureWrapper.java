/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers.textures / TextureDuo
*  created on 25.5.2019 , 12:40:02 
 */
package configmanagers.textures;

import configmanagers.ConfigWrapper;

/**
 *
 * @author filip
 */
public class ItemTextureWrapper implements ConfigWrapper{
    public String itemId;
    public TextureUnit[] textureUnits;
    
    public ItemTextureWrapper(String itemId, TextureUnit[] textureUnits) { 
        this.itemId = itemId;
        this.textureUnits = textureUnits;
    }
    
    @Override
    public String getId() { 
        return itemId;
    }
    
    @Override
    public String toString() { 
        String result = "";
        for(int i = 0; i < textureUnits.length; i++) { 
            result+=textureUnits[i].toString();
        }
        return result;
    }
}
