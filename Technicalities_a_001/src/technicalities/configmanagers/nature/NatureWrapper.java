/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers / ItemWrapper
*  created on 21.5.2019 , 20:58:49 
 */
package technicalities.configmanagers.nature;

import technicalities.configmanagers.ConfigWrapper;

/**
 * ItemWrapper 
 * - wrapper for item configuration and init 
 * @author filip
 */
public class NatureWrapper implements ConfigWrapper {
    
    //id - name of the item in id form
    public final String id;
    //id of the items stored
    public final String[] itemID;
    //amount of the items stored
    public final int[] itemAmount;
    
    /**
     * wrapper for item configuration and init
     * @param id name of the item in id form
     * @param itemID id of the items stored
     * @param itemAmount amount of the items stored
     */
    public NatureWrapper(String id, String[] itemID, int[] itemAmount) { 
        this.id = id;
        this.itemID = itemID;
        this.itemAmount = itemAmount;
    }
    
    @Override
    public String getId() { 
        return id;
    }
}
