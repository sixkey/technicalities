/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers.items.nature / NatureConfigManager
*  created on 21.5.2019 , 22:25:52 
 */
package technicalities.configmanagers.recept;

import technicalities.configmanagers.ConfigManager;
import technicalities.configmanagers.ConfigWrapper;
import java.io.InputStream;
import technicalities.configmanagers.CommonTranslation;
import technicalities.items.item.Item;
import technicalities.items.item.ItemRequired;

/**
 * NatureConfigManager
 * - config manager that gets information from nature.tcf and translates it into wrappers
 * @author filip
 */
public class ReceptConfigManager extends ConfigManager {
    
    ////// CONSTRUCTORS //////  
    
    /**
     * On creaton grabs the file and translates it by its rules to a set of wrappers
     * @param configFile file that will be read to the rules of the manager
     */
    public ReceptConfigManager(InputStream configFile) {
        super(configFile);
    }

    ////// METHODS ///////  
    
    @Override
    public ConfigWrapper stringToWrapper(String line) {
        
        //word 0 - name of the recept
        //word 1 - recept
        String[] words = line.split(" ");
        
        String title = words[0];
        int energyCost = Integer.parseInt(words[1]);
        
        //RE 0 - inputs
        //RE 1 - outputs
        String[] receptElements = words[2].split(">");
        
        Item[] inputs = CommonTranslation.toItems(receptElements[0]);
        Item[] outputs = CommonTranslation.toItems(receptElements[1]);
        
        
        return new ReceptWrapper(title, new ItemRequired(inputs), outputs, energyCost);
    }
    
}
