/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.configmanagers / CommonTranslation
*  created on 1.6.2019 , 15:07:00 
 */
package technicalities.configmanagers;

import technicalities.configmanagers.energysources.EnergySourceWrapper;
import technicalities.configmanagers.items.ItemWrapper;
import technicalities.configmanagers.miscwrappers.SlotArrayWrapper;
import technicalities.configmanagers.miscwrappers.StorageWrapper;
import technicalities.items.item.Item;
import technicalities.items.storage.Storage;

/**
 *
 * @author filip
 */
public class CommonTranslation {
    
    public static Item[] toItems(String s) { 
        String[] itemListInString = s.split(";");
        
        Item[] items = new Item[itemListInString.length];
        
        for(int i = 0; i < itemListInString.length; i++) {
            String temp = itemListInString[i];
            String itemInfo[] = temp.substring(1, temp.length() - 1).split(":");
            String itemId = itemInfo[0];
            int itemAmount = Integer.parseInt(itemInfo[1]);
            
            items[i] = new Item((ItemWrapper)technicalities.Technicalities.ICM.getWrapper(itemId), itemAmount);
        }
        
        return items;
    }
    
    public static StorageWrapper toStorage(String s) { 
        if(s.equals("_")) return new StorageWrapper(new SlotArrayWrapper[0]);
        return new StorageWrapper(toArrayWrappers(s));
    }
    
    public static SlotArrayWrapper[] toArrayWrappers(String s) {
        String[] arraysInString = s.split(";");
        SlotArrayWrapper[] result = new SlotArrayWrapper[arraysInString.length];
        
        for(int i = 0; i < arraysInString.length; i++) {
            String temp = arraysInString[i].substring(1, arraysInString[i].length() - 1);
            String[] aWrapperInfo = temp.split(":");
            
            String[] whiteTags, blackTags;
            
            if(!aWrapperInfo[0].equals("_")) {
                String[] tagsSet = aWrapperInfo[0].split("!");
                whiteTags = tagsSet[0].split(",");
                blackTags = tagsSet[1].split(",");
            } else { 
                whiteTags = new String[0];
                blackTags = new String[0];
            }
            
            int n = Integer.parseInt(aWrapperInfo[1]);
            
            result[i] = new SlotArrayWrapper(n, whiteTags, blackTags);
        }
        
        return result;
    }
    
    public static EnergySourceWrapper[] toEnergySourceWrappers(String s) { 
        if("_".equals(s)) return new EnergySourceWrapper[0];
        String temp = s.substring(1, s.length() - 1);
        
        String[] wrappersInString = temp.split(";");
        EnergySourceWrapper[] wrappers = new EnergySourceWrapper[wrappersInString.length];
        
        System.out.println("@toEnergySourceWrappers");
        
        for(int i = 0; i < wrappersInString.length; i++) {
            System.out.println(wrappersInString[i]);
            wrappers[i] = (EnergySourceWrapper)technicalities.Technicalities.ESCM.getWrapper(wrappersInString[i]);
        }
        
        return wrappers;
    }
    
}
