/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.standable / Standable
*  created on 20.5.2019 , 22:02:33 
 */
package technicalities.world.objects.standable;

import SixGen.Utils.ID;
import java.awt.image.BufferedImage;
import technicalities.ui.tui.HealthBar;
import technicalities.world.World;
import technicalities.world.objects.TObject;
import technicalities.world.structure.Tile;

/**
 * Standable
 * - objects that stand on tiles and are static (buildings, trees, ...)
 * @author filip
 */
public abstract class Standable extends TObject {
    
    ////// VARIABLES ////// 
    protected World world;
    protected Tile tile;
    
    protected BufferedImage texture;
    
    ////// CONSTRUCTROS ////// 
    
    public Standable(Tile tile, World world, ID id) { 
        super(world.getTileX(tile) + TILEWIDTH / 2, world.getTileY(tile) + TILEHEIGHT / 2, id);
        this.tile = tile;
        this.world = world;
        setHP(10);
        setMaxHP(10);
        tui = new HealthBar(this);
    }
    
    ////// GETTERS SETTERS //////   
    
    public void setTexture(BufferedImage texture) { 
        this.texture = texture;
    }
    
    public BufferedImage getTexture() { 
        return texture;
    }
    
    
}
