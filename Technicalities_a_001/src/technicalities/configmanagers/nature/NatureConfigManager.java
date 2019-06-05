/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers.items.nature / NatureConfigManager
*  created on 21.5.2019 , 22:25:52 
 */
package technicalities.configmanagers.nature;

import technicalities.configmanagers.ConfigManager;
import technicalities.configmanagers.ConfigWrapper;
import java.io.InputStream;
import technicalities.configmanagers.CommonTranslation;
import technicalities.items.item.Item;

/**
 * NatureConfigManager
 * - config manager that gets information from nature.tcf and translates it into wrappers
 * @author filip
 */
public class NatureConfigManager extends ConfigManager {
    
    ////// CONSTRUCTORS //////  
    
    /**
     * On creaton grabs the file and translates it by its rules to a set of wrappers
     * @param configFile file that will be read to the rules of the manager
     */
    public NatureConfigManager(InputStream configFile) {
        super(configFile);
    }

    ////// METHODS ///////  
    
    @Override
    public ConfigWrapper stringToWrapper(String line) {
        String[] words = line.split(" ");
        
        String id = words[0];
        
        String itemsInString = words[1];
        Item[] items = CommonTranslation.toItems(itemsInString);
        
        return new NatureWrapper(id, items);
    }
    
}
