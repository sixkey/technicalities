/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.standable / Nature
*  created on 21.5.2019 , 23:03:49 
 */
package technicalities.world.objects.standable;

import technicalities.configmanagers.items.ItemWrapper;
import technicalities.configmanagers.nature.NatureWrapper;
import technicalities.items.item.Item;
import technicalities.variables.idls.OIDL;
import technicalities.world.World;
import technicalities.world.handler.Tile;

/**
 * Nature
 * - objects that are created from NatureWrapper
 * @author filip
 */
public class Nature extends Standable{
 
    ////// VARIABLES ////// 
    
    //wrapper of all information
    public NatureWrapper natureWrapper;
    
    ////// CONSTRUCTORS //////
    
    public Nature(NatureWrapper natureWrapper, Tile tile, World world) { 
        super(tile, world, OIDL.nature);
        this.natureWrapper = natureWrapper;
    }
    
    ////// METHODS //////
    
    @Override
    public void death() { 
        if(isAlive()) { 
            super.death();
            world.dropItems(Item.copy(natureWrapper.items), (int)getCenterX(), (int)getCenterY());
            this.tile.setStandable(null);
        }
    }
    
    ////// GETTERS SETTERS //////   
    
    @Override
    public String getTID() { 
        return natureWrapper.id;
    }
    
}
