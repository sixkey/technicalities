/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File inventory.storage / Storage
*  created on 22.5.2019 , 18:38:39 
 */
package technicalities.items.storage;

import java.util.ArrayList;
import technicalities.configmanagers.miscwrappers.SlotArrayWrapper;
import technicalities.configmanagers.miscwrappers.StorageWrapper;
import technicalities.items.item.Item;
import technicalities.items.item.ItemRequired;

/**
 * Storage 
 * - any storage in the game 
 * - array of slots 
 * @author filip
 */
public class Storage {
    
    ////// VARIABLES //////  
    
    //slots in the storage
    private Slot[] slots;
    
    ////// CONSTRUCTORS ////// 
    
    public Storage(int number) { 
        slots = new Slot[number];
        
        for(int i = 0; i < number; i++) { 
            slots[i] = new Slot();
        }
    }
    
    public Storage(int number, String[] whiteList, String[] blackList) { 
        slots = new Slot[number];
        
        for(int i = 0; i < number; i++) { 
            slots[i] = new Slot(whiteList, blackList);
        }
    }
    
    public Storage(StorageWrapper wrapper) { 
        slots = new Slot[wrapper.size];
        
        for(int i = 0; i < slots.length; i++) {
            SlotArrayWrapper sw = wrapper.getSlotArrayWraper(i);
            slots[i] = new Slot(sw.whiteList, sw.blackList);
        }
    }
    
    ////// METHODS //////   
    
    public static Storage copy(Storage storage) { 
        Storage copy = new Storage(storage.size());
        for(Slot s : storage.getSlots()) { 
            if(!s.isFree()) {
                copy.add(Item.copy(s.item));
            }
        } 
        return copy;
    }
    
    //// ADDING ////    
    
    /**
     * adding item to the storage 
     * @param item item that we want to add
     * @return returns everything that can't be added to the invenotory 
     */
    public Item add(Item item) {
        if(item == null) return null;
        Item temp = new Item(item.itemWrapper, item.getAmount());
        for(Slot s : slots) { 
            temp = s.add(item);
            if(temp == null) { 
                break;
            }
        }
        return temp;
    }
    
    /**
     * adding items to the storage
     * @param items items that we want to add
     * @return returns everything that can't be added to the inventory
     */
    public Item[] add(Item[] items) { 
        ArrayList<Item> resultList = new ArrayList<Item>();
        for(Item i : items) { 
            Item ret = this.add(i);
            if(ret != null) { 
                resultList.add(ret);
            }
        }
        return resultList.toArray(new Item[resultList.size()]);
    }
    
    //// REMOVING ////
    
    /**
     * 
     * @param itemReq
     * @return all the items that was gathered
     */
    public Item[] remove(ItemRequired itemReq) { 
        Item result[] = new Item[itemReq.items.length];
        
        for(int i = 0 ; i < itemReq.items.length; i++) {
            result[i] = this.remove(itemReq.items[i]);
        }
        
        return result;
    }
    
    /**
     * 
     * @param item item we want to get from the storage
     * @return all the item that was gathered
     */
    public Item remove(Item item) { 
        Item temp = new Item(item.itemWrapper, item.getAmount());
        for(Slot s : slots) { 
            //s.remove returns stuff that was removed thus removed from the temp - desired price
            Item itemp = s.remove(temp);
            if(itemp!=null) {
                temp.remove(itemp.getAmount());
            }
            if(temp.isEmpty()) { 
                break;
            }
        }
        return new Item(item.itemWrapper, item.getAmount() - temp.getAmount());
    }
    
    //// CONTAINS ////
    
    /**
     * if storage contains everything in the intemrequired
     * @param itemRequired
     * @return if storage contains everything in the intemrequired
     */
    public boolean contains(ItemRequired itemRequired) {
        boolean result = true;
        
        for(Item i : itemRequired.items) { 
            result = result && contains(i);
        }
        
        return result;
    }
    
    /**
     * if storage contains item
     * @param item
     * @return if storage contains item
     */
    public boolean contains(Item item) { 
        return contains(item.itemWrapper.id, item.getAmount());
    }
    
    /**
     * if the storage contains said amount of the item
     * @param itemId id of the item
     * @param amount amount of the item needed
     * @return if the storage contains said amount of the item
     */
    public boolean contains(String itemId, int amount) { 
        int result = 0;
        for(Slot s : slots) { 
            Item i = s.item;
            if(i!=null) { 
                if(i.itemWrapper.id.equals(itemId)) { 
                    result+=i.getAmount();
                    if(result>=amount) { 
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    //// CAN TAKE ////  
    
    public boolean canTake(Item item) { 
        int count = 0;
        for(Slot s : slots) {
            int t = s.howMuchCanTake(item);
            t+=count;
            if(t > item.getAmount()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean canTake(Item[] items) { 
        Storage simulation = Storage.copy(this);
        for(Item i : items) {
            if(canTake(i)) { 
                simulation.add(i);
            } else { 
                return false;
            }
        }
        return true;
    }
    
    /**
     * returns total amount of the itemid in the storage
     * @param itemId id of the item we want to count
     * @return returns total amount of the itemid in the storage
     */
    public int getAmountFromItem(String itemId) { 
        int result = 0;
        for(Slot s : slots) { 
            Item i = s.item;
            if(i!=null) { 
                if(i.itemWrapper.id.equals(itemId)) { 
                    result+=i.getAmount();
                }
            }
        }
        return result;
    }
    
    ////// GETTERS SETTERS //////   
    
    @Override
    public String toString() { 
        String result = "STORAGE: \n";
        for(int i = 0; i < slots.length; i++) {
            result+= slots[i].toString() + "\n";
        }
        return result;
    }
    
    public Item getItem(int slotI) { 
        return slots[slotI].item;
    }
    
    public Slot[] getSlots() { 
        return slots;
    }
    
    public Slot getSlot(int slotI) { 
        return slots[slotI];
    }
    
    public int size() { 
        return slots.length;
    }
}
