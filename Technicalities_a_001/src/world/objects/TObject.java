/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects / TObject
*  created on 20.5.2019 , 21:43:20 
 */
package world.objects;

import SixGen.GameObject.GameObject;
import SixGen.Utils.ID;
import variables.globals.GlobalVariables;

/**
 * TObject
 * - class inherited by all independent objects in game 
 * @author filip
 */
public abstract class TObject extends GameObject implements GlobalVariables{
    
    public TObject(float centerX, float centerY, ID id) { 
        super(centerX, centerY, id);
    }
    
}
