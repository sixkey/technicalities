/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.world.objects.standable.craftingstation / EnergyUnit
*  created on 1.6.2019 , 16:41:46 
 */
package technicalities.world.objects.standable.craftingstation;

import technicalities.configmanagers.energysources.EnergySourcePartWrapper;
import technicalities.configmanagers.energysources.EnergySourceWrapper;
import technicalities.items.item.Item;
import technicalities.items.storage.Storage;

/**
 * EnergySourceUnit
 * @author filip
 */
public class EnergySourceUnit {
    
    private final EnergySourceWrapper wrapper;
    private final EnergyCenter center;
    private final Storage storage;
    
    private boolean burning;
    private int perTime;
    private int duration;
    private int burningCounter = 0;
    
    public EnergySourceUnit(EnergyCenter center, EnergySourceWrapper wrapper) { 
        this.center = center;
        this.wrapper = wrapper;
        this.storage = new Storage(wrapper.storageWrapper);
    }
    
    public void update() {
        //ITEMS
        if(burning) { 
            if(burningCounter < duration) { 
                center.addEnergy(perTime);
            } else { 
                burning = false;
            }
            burningCounter++;
        } else { 
            Item i = getBestItemToBurn(storage);
            if(i!=null) { 
                EnergySourcePartWrapper pw = wrapper.getItemPartWrapper(i.itemWrapper.id);
                perTime = pw.perTime;
                duration = pw.duration;
                burning = true;
                burningCounter = 0;
                storage.remove(i);
            }
        }
        //TILES
        for(EnergySourcePartWrapper w : wrapper.tilePartWrappers) { 
            center.addEnergy(w.perTime);
        }
    }
    
    public Item getBestItemToBurn(Storage storage) { 
        for(Item i : wrapper.possibleItems) {
            if(storage.contains(i)) { 
                EnergySourcePartWrapper w = wrapper.getItemPartWrapper(i.itemWrapper.id);
                if(w.perTime * w.duration <= center.getFreeSpace()) {
                    return i;
                }
            }
        }
        return null;
    }
    
    public Storage getStorage() { 
        return storage;
    }
    
    public EnergySourceWrapper getWrapper() { 
        return wrapper;
    }
    
    public void addEnergy(int energy) { 
        this.center.addEnergy(energy);
    }
}
