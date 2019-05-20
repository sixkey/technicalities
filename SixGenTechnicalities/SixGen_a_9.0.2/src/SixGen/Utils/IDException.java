// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.Utils;

import SixGen.GameObject.GameObject;

/**
 * IDException
 * Abilities
 *  Holds two values of ID , id1 and id2 
 *  Can check ids with the id1 and id2
 */
public class IDException {
    
    //VARIABLES
    
    //id1
    public ID id1;
    //id2
    public ID id2;
    
    //CONSTRUCTORS
    
    public IDException(ID id1 ,ID id2) { 
        //@IDException
        //@IDException#constructorSetters
        this.id1 = id1;
        this.id2 = id2;
    }

    //CHECKING VOIDS
    
    public boolean checkID(ID id1 ,ID id2) { 
        return this.id1 == id1 && this.id2 == id2;
    }
    public boolean checkObj(GameObject obj1 , GameObject obj2) { 
        return obj1.getId() == id1 | obj1.getFormer() == id1 && obj2.getId() == id2 | obj2.getFormer() == id2;
    }
    
    //GETTERS SETTERS
    
    public ID getId1() {
        return id1;
    }

    public void setId1(ID id1) {
        this.id1 = id1;
    }

    public ID getId2() {
        return id2;
    }

    public void setId2(ID id2) {
        this.id2 = id2;
    }
}
