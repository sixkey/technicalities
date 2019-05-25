/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File ui / TechSprite
*  created on 25.5.2019 , 12:31:55 
 */
package ui;

import SixGen.Texture.SpriteSheet;
import SixGen.Utils.Files.FileManager;
import configmanagers.textures.SpriteWrapper;
import configmanagers.textures.TextureConfigManager;
import java.awt.image.BufferedImage;

/**
 *
 * @author filip
 */
public class TechSpriteSheet extends SpriteSheet {
    
    FileManager fm;
    
    public TechSpriteSheet(String title) { 
        super(null, title, 0, 0);
        
        fm = new FileManager();
        this.image = fm.getImageFromClassSource(getClass(), "/textures/" + title + ".png");
        
        TextureConfigManager tcm = new TextureConfigManager(fm.getFileFromClassSource(getClass(), "/textures/" + title + ".texcf"));
        
        SpriteWrapper wrapper = tcm.spriteWrapper;
        this.columns = wrapper.columns;
        this.rows = wrapper.rows;
        
        System.out.println(wrapper);
        
        this.updateParcelBounds();
    }
    
}
