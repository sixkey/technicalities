/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.configmanagers.energysources / EnergySourceWrapper
*  created on 1.6.2019 , 13:52:10 
 */
package technicalities.configmanagers.energysources;

import java.util.LinkedList;
import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.items.ItemWrapper;
import technicalities.configmanagers.miscwrappers.StorageWrapper;
import technicalities.items.item.Item;

/**
 *
 * @author filip
 */
public class EnergySourceWrapper implements ConfigWrapper {
    
    public String title;
    public EnergySourcePartWrapper[] itemPartWrappers;
    public EnergySourcePartWrapper[] tilePartWrappers;
    
    public Item[] possibleItems;
    
    public StorageWrapper storageWrapper;
    
    public String[] itemTitles;
    
    public EnergySourceWrapper(String title, 
            EnergySourcePartWrapper[] itemPartWrappers, EnergySourcePartWrapper[] tilePartWrappers, 
            StorageWrapper storageWrapper) { 
        this.title = title;
        this.itemPartWrappers = itemPartWrappers;
        this.tilePartWrappers = tilePartWrappers;
        this.storageWrapper = storageWrapper;
        
        LinkedList<Item> itemList = new LinkedList<>();
        for(EnergySourcePartWrapper w : itemPartWrappers) { 
            if(w.type == EnergySourcePartWrapper.EnergySourceUnitType.item) { 
                itemList.add(new Item((ItemWrapper)technicalities.Technicalities.ICM.getWrapper(w.title), 1));
            }
        }
        
        possibleItems = itemList.toArray(new Item[itemList.size()]);
    }
    
    @Override
    public String getId() { 
        return title;
    }
    
    @Override
    public String toString() { 
        String result = title.toUpperCase() + "\n";
        if(storageWrapper!=null) { 
            result+="Storage : " + storageWrapper.toString() + "\n";
        } else { 
            result+="Storage : null \n";
        }
        for (EnergySourcePartWrapper itemPartWrapper : itemPartWrappers) {
            result += itemPartWrapper.toString() + "\n";
        }
        for (EnergySourcePartWrapper itemPartWrapper : tilePartWrappers) {
            result += itemPartWrapper.toString() + "\n";
        }
        return result;
    }
    
    public EnergySourcePartWrapper getItemPartWrapper(String title) { 
        for(EnergySourcePartWrapper w : itemPartWrappers) { 
            if(w.title.equals(title)) { 
                return w;
            }
        }
        return null;
    }
    
}
