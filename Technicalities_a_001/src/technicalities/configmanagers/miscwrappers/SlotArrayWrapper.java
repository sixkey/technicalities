/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.configmanagers.miscwrappers / SlotArrayWrapper
*  created on 1.6.2019 , 15:27:41 
 */
package technicalities.configmanagers.miscwrappers;

/**
 *
 * @author filip
 */
public class SlotArrayWrapper {
    
    public final int n;
    public final String[] whiteList, blackList;
    
    public SlotArrayWrapper(int n, String whiteList[], String blackList[]) { 
        this.n = n;
        this.whiteList = whiteList;
        this.blackList = blackList;
    }
    
    @Override
    public String toString() { 
        String result = String.valueOf(n) + "WL: ";
        for(int i = 0; i < whiteList.length; i++) { 
            result+=whiteList[i] + ",";
        }
        result=result.substring(0, result.length()-1) + " BL: ";
        for(int i = 0; i < blackList.length; i++) { 
            result+=blackList[i] + ",";
        }
        result=result.substring(0, result.length()-1);
        return result;
    }
    
}
