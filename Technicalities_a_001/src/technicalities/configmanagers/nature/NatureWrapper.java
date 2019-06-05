/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers / ItemWrapper
*  created on 21.5.2019 , 20:58:49 
 */
package technicalities.configmanagers.nature;

import technicalities.configmanagers.ConfigWrapper;
import technicalities.items.item.Item;

/**
 * ItemWrapper 
 * - wrapper for item configuration and init 
 * @author filip
 */
public class NatureWrapper implements ConfigWrapper {
    
    //id - name of the item in id form
    public final String id;
    //items that drop
    public Item[] items;
    
    /**
     * wrapper for item configuration and init
     * @param id name of the item in id form
     * @param items items that drop on destroy
     */
    public NatureWrapper(String id, Item[] items) { 
        this.id = id;
        this.items = items;
    }
    
    @Override
    public String getId() { 
        return id;
    }
}
