/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.configmanagers.miscwrappers / StorageWrapper
*  created on 1.6.2019 , 15:25:31 
 */
package technicalities.configmanagers.miscwrappers;

/**
 *
 * @author filip
 */
public class StorageWrapper {
    
    public SlotArrayWrapper[] slotArrayWrapper;
    public int size;
    
    public StorageWrapper(SlotArrayWrapper[] storageWrapper) { 
        this.slotArrayWrapper = storageWrapper;
        size = 0;
        for(SlotArrayWrapper w : slotArrayWrapper) { 
            size+=w.n;
        }
    }
    
    @Override
    public String toString() { 
        String result = "";
        for(SlotArrayWrapper w : slotArrayWrapper) {
            result+=w.toString() + " ";
        }
        return result;
    }
    
    public SlotArrayWrapper getSlotArrayWraper(int slotIndex) { 
        int counter = 0;
        for (SlotArrayWrapper storageWrapper : slotArrayWrapper) {
            counter += storageWrapper.n;
            if (counter>= slotIndex) {
                return storageWrapper;
            }
        }
        return null;
    }
    
}
