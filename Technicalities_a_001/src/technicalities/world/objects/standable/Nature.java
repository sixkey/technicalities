/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.standable / Nature
*  created on 21.5.2019 , 23:03:49 
 */
package technicalities.world.objects.standable;

import technicalities.configmanagers.nature.NatureWrapper;
import technicalities.variables.idls.OIDL;
import technicalities.world.World;
import technicalities.world.structure.Tile;

/**
 * Nature
 * - objects that are created from NatureWrapper
 * @author filip
 */
public class Nature extends Standable{
 
    public NatureWrapper natureWrapper;
    
    public Nature(NatureWrapper natureWrapper, Tile tile, World world) { 
        super(tile, world, OIDL.nature);
        this.natureWrapper = natureWrapper;
    }
    
    @Override
    public void death() { 
        world.dropItems(natureWrapper.itemID, natureWrapper.itemAmount, (int)getCenterX(), (int)getCenterY());
        this.tile.setStandable(null);
    }
    
}
