/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File ui / TechTexManager
*  created on 24.5.2019 , 23:39:38 
 */
package ui;

import SixGen.Texture.SpriteSheet;
import SixGen.Texture.TextureManager;
import SixGen.Utils.Files.FileManager;

/**
 * TechTexManager
 * -texture manager
 * @author filip
 */
public class TechTexManager extends TextureManager {
    
    public TechTexManager() { 
        FileManager fm = new FileManager();
        
        SpriteSheet[] sheets = {
            new SpriteSheet(fm.getImageFromClassSource(getClass(), "/textures/tiles.png"), "tiles", 3, 3),
            new TechSpriteSheet("nature_spritesheet")   
        };
        
        this.addSpriteSheets(sheets);
    }
    
}
