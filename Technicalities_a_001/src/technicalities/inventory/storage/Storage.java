/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File inventory.storage / Storage
*  created on 22.5.2019 , 18:38:39 
 */
package technicalities.inventory.storage;

import technicalities.inventory.item.Item;

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
    
    ////// METHODS //////   
    
    
    /**
     * adding item to the storage 
     * @param item item that we want to add
     * @return returns everything that can't be added to the invenotory 
     */
    public Item add(Item item) { 
        Item temp = item;
        for(Slot s : slots) { 
            temp = s.add(item);
            if(temp == null) { 
                break;
            }
        }
        return temp;
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
}
