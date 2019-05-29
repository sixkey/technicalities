/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.standable.configmanagers / StandableManager
*  created on 21.5.2019 , 20:51:46 
 */
package technicalities.configmanagers.items;

import technicalities.configmanagers.ConfigManager;
import technicalities.configmanagers.ConfigWrapper;
import java.io.InputStream;

/**
 * ItemConfigManager
 * - config manager that gets information from items.tcf and translates it into wrappers
 * @author filip
 */
public class ItemConfigManager extends ConfigManager {

    ////// CONSTRUCTORS //////
    
    /**
     * On creaton grabs the file and translates it by its rules to a set of wrappers
     * @param configFile file that will be read to the rules of the manager
     */
    public ItemConfigManager(InputStream configFile) {
        super(configFile);
    }
    
    ////// METHODS //////   
    
    @Override
    public ConfigWrapper stringToWrapper(String line) {
        String[] words = line.split(" ");
        
        String id = words[0];
        
        int stackSize = Integer.parseInt(words[1]);
        
        String[] tags = words[2].split(",");
        
        return new ItemWrapper(id, stackSize, tags);   
    }

}
