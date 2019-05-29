/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File ui / TechTexManager
*  created on 24.5.2019 , 23:39:38 
 */
package technicalities.ui;

import SixGen.Texture.SpriteSheet;
import SixGen.Texture.TextureManager;
import SixGen.Utils.Files.FileManager;
import technicalities.configmanagers.textures.ItemTextureWrapper;
import technicalities.configmanagers.textures.TextureUnit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * TechTexManager
 * -texture manager
 * @author filip
 * 
 * TODO:
 * - better search algorithm
 * - merging ItemTextureWrappers with same itemId
 */
public class TechTexManager extends TextureManager {
    
    ////// VARIABLES //////     
    
    public ArrayList<ItemTextureWrapper> itemWrappers;
    
    public Random random;
    
    ////// CONSTRUCTORS //////  
    
    public TechTexManager() { 
        FileManager fm = new FileManager();
        //getting nontech textures
        SpriteSheet[] sheets = {
            new SpriteSheet(fm.getImageFromClassSource(getClass(), "/textures/tiles.png"), "tiles", 3, 3),
        };
        
        //gettting tech textures
        
        TechSpriteSheet[] techSheets = { 
            new TechSpriteSheet("nature_spritesheet"),
            new TechSpriteSheet("item_spritesheet")    
        };
        
        //opening tech textures
        itemWrappers = new ArrayList();
        for (TechSpriteSheet techSheet : techSheets) {
            itemWrappers.addAll(Arrays.asList(techSheet.getItemTextureWrappers()));
        }
        
        //sorting tech textures
        itemWrappers.sort(new Comparator<ItemTextureWrapper>() {
            @Override
            public int compare(ItemTextureWrapper a, ItemTextureWrapper b) { 
                return a.itemId.compareTo(b.itemId);
            }
        });
        
        //random init
        random = new Random();
        
        //adding textures to the manager
        this.addSpriteSheets(sheets);
        this.addSpriteSheets(techSheets);
        
        System.out.println("TEX");
        
        for(SpriteSheet s :spriteSheets) { 
            System.out.println(s.getTitle());
        }
    }
    
    /**
     * returns random scaled texture for the desired object
     * @param itemId id of the item in string form
     * TODO: animation handle
     * @return random scaled texture fro the desired object id
     */
    public BufferedImage getRandomTexture(String itemId) { 
        for(ItemTextureWrapper w : itemWrappers) { 
            if(w.itemId.equals(itemId)) { 
                TextureUnit unit = w.textureUnits[random.nextInt(w.textureUnits.length)];
                BufferedImage result = getTextureFromSheet(unit.spriteSheetId, unit.start.x, unit.start.y);
                return result;
            }
        }
        return null;
    }
}
