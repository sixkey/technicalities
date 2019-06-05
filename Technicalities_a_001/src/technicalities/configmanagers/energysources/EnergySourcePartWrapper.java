/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.configmanagers.energysources / EnergySourceUnit
*  created on 1.6.2019 , 13:44:20 
 */
package technicalities.configmanagers.energysources;

/**
 *
 * @author filip
 */
public class EnergySourcePartWrapper {
    
    public enum EnergySourceUnitType { 
        tile, item
    }
    
    public String title;
    public EnergySourceUnitType type;
    public int perTime;
    public int duration;
    
    public EnergySourcePartWrapper(String title, int perTime) { 
        type = EnergySourceUnitType.tile;
        this.title = title;
        this.perTime = perTime;
    }
    
    public EnergySourcePartWrapper(String title, int perTime, int duration) { 
        type = EnergySourceUnitType.item;
        this.title = title;
        this.perTime = perTime;
        this.duration = duration;
    }
    
    @Override
    public String toString() { 
        return title + " " + String.valueOf(type) + " " + perTime + " " + duration;
    }
}
