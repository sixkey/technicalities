/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers / ItemWrapper
*  created on 21.5.2019 , 20:58:49 
 */
package technicalities.configmanagers.craftingstations;

import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.energysources.EnergySourceWrapper;
import technicalities.configmanagers.miscwrappers.StorageWrapper;
import technicalities.items.item.ItemRequired;
import technicalities.configmanagers.recept.ReceptWrapper;

/**
 * ItemWrapper 
 * - wrapper for item configuration and init 
 * @author filip
 */
public class CraftingStationWrapper implements ConfigWrapper {
    
    //id - name of the station in id form
    public final String id;
    //recepts
    public final ReceptWrapper recepts[];
    //item required
    public final ItemRequired itemRequired;
    //energycenter wrappers 
    public final EnergySourceWrapper[] energySourceWrappers;
    
    public final StorageWrapper input, output;
    
    /**
     * wrapper for item configuration and init
     * @param id name of the item in id form
     * @param itemRequired items required for building
     * @param recepts recepts that can be crafted in the station
     * @param energySourceWrappers 
     * @param input
     * @param output
     */
    public CraftingStationWrapper(String id, ItemRequired itemRequired, 
            ReceptWrapper[] recepts, EnergySourceWrapper[] energySourceWrappers, 
            StorageWrapper input, StorageWrapper output) { 
        this.id = id;
        this.itemRequired = itemRequired;
        this.recepts = recepts;
        this.energySourceWrappers = energySourceWrappers;
        this.input = input;
        this.output = output;
    }
    
    @Override
    public String getId() { 
        return id;
    }
    
    @Override
    public String toString() { 
        String result = id;
        result+="RECEPTS: \n";
        for(ReceptWrapper w : recepts) {
            result+=w.toString() + "\n";
        }
        if(energySourceWrappers!=null) {
            result+="ENERGY SOURCES: \n";
            for(EnergySourceWrapper w : energySourceWrappers) {
                result+=w.toString() + "\n";
            }
        }
        result+=itemRequired.toString() + "\n";
        return result;
    }
}
