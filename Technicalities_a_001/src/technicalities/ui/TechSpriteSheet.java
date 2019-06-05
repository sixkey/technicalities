/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File ui / TechSprite
*  created on 25.5.2019 , 12:31:55 
 */
package technicalities.ui;

import SixGen.Texture.SpriteSheet;
import SixGen.Utils.Files.FileManager;
import SixGen.Utils.Utils;
import technicalities.configmanagers.textures.ItemTextureWrapper;
import technicalities.configmanagers.textures.SpriteWrapper;
import technicalities.configmanagers.textures.TextureConfigManager;

/**
 *
 * @author filip
 */
public class TechSpriteSheet extends SpriteSheet {
    
    private FileManager fm;
    
    private SpriteWrapper wrapper;
    
    public TechSpriteSheet(String title, double textureRatio) { 
        super(null, title, 0, 0);
        
        fm = new FileManager();
        
        Utils utils = new Utils(){};
        
        
        
        this.image = utils.rescale(fm.getImageFromClassSource(getClass(), "/textures/" + title + ".png"), textureRatio);
        
        TextureConfigManager tcm = new TextureConfigManager(fm.getFileFromClassSource(getClass(), "/textures/" + title + ".texcf"));
        
        this.wrapper = tcm.spriteWrapper;
        this.columns = wrapper.columns;
        this.rows = wrapper.rows;
        
        this.updateParcelBounds();
    }
    
    public ItemTextureWrapper[] getItemTextureWrappers() { 
        return wrapper.wrappers;
    }

}
