/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers.items.nature / NatureConfigManager
*  created on 21.5.2019 , 22:25:52 
 */
package configmanagers.nature;

import configmanagers.ConfigManager;
import configmanagers.ConfigWrapper;
import java.io.InputStream;

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
        
        String[] resources = words[1].split(",");
        String[] itemID = new String[resources.length];
        int[] itemAmount = new int[resources.length];
        
        for(int i = 0; i < resources.length; i++) { 
            String[] inf = resources[i].split(":");
            itemID[i] = inf[0];
            itemAmount[i] = Integer.parseInt(inf[1]);
        }
        
        return new NatureWrapper(id, itemID, itemAmount);
    }
    
}
