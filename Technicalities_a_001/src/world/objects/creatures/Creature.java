/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.creatures / Creature
*  created on 20.5.2019 , 21:33:23 
 */
package world.objects.creatures;

import variables.globals.GlobalVariables;
import variables.idls.OIDL;
import world.World;
import world.objects.TObject;

/**
 * Creature
 * - abstract class inherited by all living things 
 * @author filip
 */
public abstract class Creature extends TObject{
    
    ////// VARIABLES //////
    
    protected World world;
    
    ////// CONSTRUCTORS ////// 
    
    public Creature(float centerX, float centerY, OIDL id, World world) { 
        super(centerX, centerY, id);
        this.world = world;
    }
    
}
