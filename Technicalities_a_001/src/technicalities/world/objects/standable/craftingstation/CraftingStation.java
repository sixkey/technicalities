/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.world.objects.standable / CraftingStation
*  created on 30.5.2019 , 21:46:47 
 */
package technicalities.world.objects.standable.craftingstation;

import technicalities.configmanagers.craftingstations.CraftingStationWrapper;
import technicalities.configmanagers.recept.ReceptWrapper;
import technicalities.items.storage.Storage;
import technicalities.ui.craftingstation.CraftingStationUI;
import technicalities.ui.tui.TUI;
import technicalities.variables.idls.OIDL;
import technicalities.world.World;
import technicalities.world.handler.Tile;
import technicalities.world.objects.standable.Standable;

/**
 * CraftingStation
 * -
 * 
 * @author filip
 */
public class CraftingStation extends Standable {
    
    public CraftingStationWrapper csw;
    
    private TUI craftingTUI;
    
    private Storage mainInputStorage;
    private Storage mainOutputStorage;
    
    private EnergyCenter energyCenter;

    public CraftingStation(CraftingStationWrapper csw, Tile tile, World world) {
        super(tile, world, OIDL.craftingStation);
        this.csw = csw;
        
        //storages
        mainInputStorage = new Storage(csw.input);
        mainOutputStorage = new Storage(csw.output);
        
        energyCenter = new EnergyCenter(this, csw.energySourceWrappers);
        
        this.craftingTUI = new CraftingStationUI(this);
    }
    
    @Override
    public void death() { 
        if(isAlive()) { 
            super.death();
            this.tile.setStandable(null);
        }
    }
    
    @Override
    public void bigTick() { 
        this.energyCenter.update();
    }
    
    @Override
    public void tick() {
        
    }
    
    public void craft(ReceptWrapper wrapper) { 
        if(mainInputStorage.contains(wrapper.input) && mainOutputStorage.canTake(wrapper.output) && energyCenter.getEnergyLevel() >= wrapper.energyCost) { 
            energyCenter.removeEnergy(wrapper.energyCost);
            mainInputStorage.remove(wrapper.input);
            mainOutputStorage.add(wrapper.output);
        }
    }
    
    ///// GETTERS SETTERS //////    
    
    public Storage getMainInputStorage() { 
        return mainInputStorage;
    }
    
    public Storage getMainOutputStorage() { 
        return mainOutputStorage;
    }
    
    @Override
    public String getTID() { 
        return csw.id;
    }
    
    public TUI getCraftingTUI() { 
        return craftingTUI;
    }
    
    public EnergyCenter getEnergyCenter() { 
        return energyCenter;
    }
}
