/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects / TObject
*  created on 20.5.2019 , 21:43:20 
 */
package technicalities.world.objects;

import SixGen.GameObject.GameObject;
import SixGen.Utils.ID;
import technicalities.ui.tui.TUI;
import technicalities.variables.globals.GlobalVariables;

/**
 * TObject
 * - class inherited by all independent objects in game 
 * @author filip
 */
public abstract class TObject extends GameObject implements GlobalVariables{
    
    //objects ui
    protected TUI tui;
    
    private int hp = 0;
    private int maxHP = 0;
    
    public TObject(float centerX, float centerY, ID id) { 
        super(centerX, centerY, id);
    }
    
    ////// GETTERS SETTERS //////
    
    public int getHP() { 
        return hp;
    }
    
    protected void setHP(int hp) { 
        this.hp = hp;
    }
    
    public void damage(int damage) { 
        this.hp = hp - damage;
        if(hp<0) { 
            hp = 0;
            death();
        }
    }
    
    protected void death() {
        
    }   
    
    protected void setMaxHP(int maxHP) { 
        this.maxHP = maxHP;
    }
    
    public int getMaxHP() { 
        return maxHP;
    }
    
    public TUI getTUI() {
        return tui;
    }
    
}
