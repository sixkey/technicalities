/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.world.objects.standable.craftingstation / EnergySource
*  created on 1.6.2019 , 13:26:28 
 */
package technicalities.world.objects.standable.craftingstation;

import technicalities.configmanagers.energysources.EnergySourceWrapper;
import technicalities.items.storage.Storage;

/**
 * EnergySource
 * @author filip
 */
public class EnergyCenter {
    
    ////// VARIABLES ////// 
    
    private CraftingStation craftingStation;
    private EnergySourceUnit[] units;
    
    private int energyLevel;
    private int maxEnergyLevel;
    
    ////// CONSTRUCTORS //////  
    
    public EnergyCenter(CraftingStation craftingStation, EnergySourceWrapper[] wrappers) { 
        this.craftingStation = craftingStation;
        
        units = new EnergySourceUnit[wrappers.length];
        for(int i = 0; i < units.length; i++) { 
            units[i] = new EnergySourceUnit(this, wrappers[i]);
        }
        
        maxEnergyLevel = 50;
        
        energyLevel = 0;
    }
    
    public void update() { 
        for (EnergySourceUnit unit : units) {
            unit.update();
        }
    }
    
    ////// GETTERS SETTERS //////   
    
    public void addEnergy(int energy) { 
        this.energyLevel += energy;
        if(energyLevel > maxEnergyLevel) { 
            energyLevel = maxEnergyLevel;
        }
    }
    
    /**
     * removes energy from the center
     * @param energy amount of energy we want to remove
     * @return returns energy that was cosumed
     */
    public int removeEnergy(int energy) { 
        int a = Math.min(this.energyLevel, energy);
        this.energyLevel-=a;
        return a;
    }
    
    public int getEnergyLevel() { 
        return energyLevel;
    }
    
    public int getFreeSpace() { 
        return maxEnergyLevel - energyLevel;
    }
    
    public double getEnergyDouble() {
        return ((double)energyLevel) / maxEnergyLevel;
    }
    
    public EnergySourceUnit[] getUnits() { 
        return units;
    }
}
