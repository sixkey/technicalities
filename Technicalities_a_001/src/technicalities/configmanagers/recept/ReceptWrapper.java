/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers / ItemWrapper
*  created on 21.5.2019 , 20:58:49 
 */
package technicalities.configmanagers.recept;

import technicalities.configmanagers.ConfigWrapper;
import technicalities.items.item.ItemRequired;
import technicalities.items.item.Item;

/**
 * ItemWrapper 
 * - wrapper for item configuration and init 
 * @author filip
 */
public class ReceptWrapper implements ConfigWrapper {
    
    //id - name of the recept in id form
    public final String id;
    public ItemRequired input;
    public Item[] output; 
    
    public int energyCost;
    /**
     * wrapper for item configuration and init
     * @param id name of the item in id form
     * @param input input items
     * @param output output items
     * @param energyCost cost in energy
     */
    public ReceptWrapper(String id, ItemRequired input, Item[] output, int energyCost) { 
        this.id = id;
        this.input = input;
        this.output = output;
        this.energyCost = energyCost;
    }
    
    @Override
    public String getId() { 
        return id;
    }
    
    @Override
    public String toString() { 
        String result = id + " [energy cost: " + energyCost + "] :\n";
        result+="INPUT\n";
        for(Item item : input.items) { 
            result+=item.toString() + ",";
        }
        result=result.substring(0,result.length()-1) + "\n";
        result+="OUTPUT\n";
        for(Item item : output) { 
            result+=item.toString() + ",";
        }
        result=result.substring(0,result.length()-1);
        return result;
    }
}
