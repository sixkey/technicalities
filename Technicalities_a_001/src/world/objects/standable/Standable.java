/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.standable / Standable
*  created on 20.5.2019 , 22:02:33 
 */
package world.objects.standable;

import SixGen.Utils.ID;
import java.awt.Graphics2D;
import world.World;
import world.objects.TObject;
import world.structure.Tile;

/**
 * Standable
 * - objects that stand on tiles and are static (buildings, trees, ...)
 * @author filip
 */
public abstract class Standable extends TObject {
    
    ////// VARIABLES ////// 
    public World world;
    
    ////// CONSTRUCTROS ////// 
    
    public Standable(Tile tile, World world, ID id) { 
        super(world.getTileX(tile), world.getTileY(tile), id);
        this.world = world;
    }
      
}
