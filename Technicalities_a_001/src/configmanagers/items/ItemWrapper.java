/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers / ItemWrapper
*  created on 21.5.2019 , 20:58:49 
 */
package configmanagers.items;

import configmanagers.ConfigWrapper;

/**
 * ItemWrapper 
 * - wrapper for item configuration and init 
 * @author filip
 */
public class ItemWrapper implements ConfigWrapper {
    
    //id - name of the item in id form
    public final String id;
    //name of the texture set of items
    public final String set;
    //Texture ID - pointer to the textureset
    public final int TID;
    //tags used by the item, used in slot handling, crafting and so on
    public final String[] tags;
    //max amount of the item in one place or stacksize
    public final int stackSize;
    
    /**
     * wrapper for item configuration and init
     * @param id name of the item in id form
     * @param set name of the texture set of items
     * @param TID Texture ID - pointer to the textureset
     * @param stackSize max size of the item in one place
     * @param tags tags used by the item, used in slot handling, crafting and so on
     */
    public ItemWrapper(String id, String set, int TID, int stackSize, String[] tags) { 
        this.id = id;
        this.set = set;
        this.TID = TID;
        this.tags = tags;
        this.stackSize = stackSize;
    }
    
    @Override
    public String getId() { 
        return id;
    }
    
}
