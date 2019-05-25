/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.standable / Nature
*  created on 21.5.2019 , 23:03:49 
 */
package world.objects.standable;

import configmanagers.nature.NatureWrapper;
import variables.idls.OIDL;
import world.World;
import world.structure.Tile;

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
    
}
