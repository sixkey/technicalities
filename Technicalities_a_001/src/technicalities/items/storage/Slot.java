/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File inventory.storage / Slot
*  created on 22.5.2019 , 18:39:02 
 */
package technicalities.items.storage;

import technicalities.items.item.Item;

/**
 * Slot 
 * - smallest storage unit 
 * - contains tag whitelist a blacklist 
 * @author filip
 */
public class Slot {
    
    ////// VARIABLES ////// 
    
    public String[] whiteList;
    public String[] blackList;
    
    public Item item;
    
    ////// CONSTRUCTORS //////  
    
    public Slot() { 
        this.whiteList = new String[0];
        this.blackList = new String[0];
    }
    
    public Slot(String[] whiteList, String[] blackList) { 
        this.whiteList = whiteList;
        this.blackList = blackList;
    }
    
    ////// METHODS //////   
    
    /**
     * tries to add the item to current slot, if item present tries to add, if empty tries to place
     * @param item
     * @return eveything that wasn't added
     */
    public Item add(Item item) { 
        if(item!=null) {
            Item result = new Item(item.itemWrapper, item.getAmount());
            if(!isFree()) {
                if(result.itemWrapper.id.equals(this.item.itemWrapper.id)) { 
                    int a = this.item.add(result.getAmount());
                    result.setAmount(a);
                    if(result.isEmpty()) { 
                        return null;
                    } else {
                        return result;
                    }
                } else {
                    return result;
                }
            } else { 
                if(compatible(result)) { 
                    this.item = result;
                    return null;
                } else { 
                    return result;
                }
            } 
        } else { 
            return null;
        }
    }
    
    /**
     * tries to remove from the item
     * @param it
     * @return everything that was removed
     */
    public Item remove(Item it) { 
        if(isFree()) {
            return null; 
        } else { 
            if(it.itemWrapper.id.equals(this.item.itemWrapper.id)) { 
                int a = this.item.remove(it.getAmount());
                if(this.item.isEmpty()) { 
                    this.item = null;
                }
                Item result = new Item(it.itemWrapper, a);
                return result;
            } else { 
                return null;
            }
        }
    }
   
    public int howMuchCanTake(Item item) { 
        if(isFree()) { 
            if(compatible(item)) {
                return item.itemWrapper.stackSize;
            }
        } else { 
            if(this.item.itemWrapper.id.equals(item.itemWrapper.id)) { 
                return this.item.itemWrapper.stackSize - this.item.getAmount();
            }
        }
        return 0;
    }
    
    /**
     * checks if the item is compatible to current slot 
     * TODO:very bruteforced crosscheck
     * @param item
     * @return returns if the item is compatible with the slot 
     */
    public boolean compatible(Item item) { 
        boolean wlc = whiteList.length == 0; // white list confirmation

        for (String tag : item.itemWrapper.tags) {
            //checking blacklist 
            for(String bs:blackList) { 
                if(tag.equals(bs)) {
                    return false;
                }
            }
            //checking whitelist 
            if(!wlc) {
                for(String ws:whiteList) { 
                    if(tag.equals(ws)) {
                        wlc = true;
                        break;
                    }
                }
            }
        }
        
        return wlc;
    }
    
    public void purge() { 
        this.item = null;
    }
    
    public Item removeItem() {
        Item temp = item;
        item = null;
        return temp;
    }
    
    ////// GETTERS SETTERS //////   
    
    public boolean isFree() { 
        return item == null;
    }
    
    @Override
    public String toString() { 
        if(isFree()) { 
            return "SLOT: FREE";
        } else { 
            return "SLOT: " + item.toString();
        }
    }
    
}
