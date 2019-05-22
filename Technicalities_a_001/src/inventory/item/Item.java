/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.items / Item
*  created on 21.5.2019 , 23:04:47 
 */
package inventory.item;

import configmanagers.items.ItemWrapper;

/**
 * Item 
 * - item object
 * @author filip
 */
public class Item {
    
    ////// VARIABLES //////
    
    //wrapper corresponding to the item
    public final ItemWrapper itemWrapper;
    //amount of the item
    private int amount;
    
    ////// CONSTRUCTORS //////  
    
    public Item(ItemWrapper itemWrapper) { 
        this.itemWrapper = itemWrapper;
        this.amount = 0;
    }
    
    public Item(ItemWrapper itemWrapper, int amount) { 
        this.itemWrapper = itemWrapper;
        this.amount = amount;
    }
    
    
    ////// VARIABLES ////// 
    
    /**
     * @param amount amount that we want to add 
     * @return amount not added (amount that is returned) 
     */
    public int add(int amount) { 
        if(itemWrapper.stackSize!=-1) { 
            int a = Math.min(amount, itemWrapper.stackSize - this.amount);
            this.amount+=a;
            return amount - a;
        } else { 
            this.amount+=amount;
            return 0;
        }   
    }
    
    /**
     * @param amount amount we want to remove
     * @return amount we reamoved or gathered from the item
     */
    public int remove(int amount) { 
        int a = Math.min(this.amount, amount);
        amount-=a;
        return a;
    }
    
    ////// GETTERS SETTERS //////  
    
    public int getAmount() { 
        return amount;
    }
    
    public void setAmount(int amount) { 
        this.amount = amount;
    }
    
    public boolean isEmpty() { 
        return amount == 0;
    }
    
    @Override
    public String toString() { 
        return itemWrapper.id.toUpperCase() + ": " + amount;
    }
}
