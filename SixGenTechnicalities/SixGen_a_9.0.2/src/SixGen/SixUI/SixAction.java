// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.SixUI;

/**
 * SixAction
 * Abilities: 
 *  holds one abstract void 
 */
public abstract class SixAction {
    
    //VARIABLES
    
    // controls if the action triggers the method 
    private boolean active;
    
    //CONSTRUCTORS
    
    public SixAction() { 
        //@SixAction
        active = true;
    }
    
    public SixAction(boolean active) { 
        //@SixAction
        //@SixAction#constructorSetters
        this.active = active;
    }
   
    
    //ABSTRACT VOIDS 
    
    protected abstract void method();
    
    //VOIDS 
    
    public void action() { 
        //@action
        if(active) { 
            method();
        }
    }
    
    //GETTERS SETTERS
    
    public void setActive(boolean active) { 
        this.active = active;
    }
    public boolean isActive() { 
        return active;
    }
}

