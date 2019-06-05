/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers.items.nature / NatureConfigManager
*  created on 21.5.2019 , 22:25:52 
 */
package technicalities.configmanagers.craftingstations;

import technicalities.configmanagers.ConfigManager;
import technicalities.configmanagers.ConfigWrapper;
import java.io.InputStream;
import java.util.ArrayList;
import technicalities.configmanagers.CommonTranslation;
import technicalities.configmanagers.energysources.EnergySourceWrapper;
import technicalities.items.item.ItemRequired;
import technicalities.configmanagers.miscwrappers.StorageWrapper;
import technicalities.configmanagers.recept.ReceptWrapper;

/**
 * NatureConfigManager
 * - config manager that gets information from nature.tcf and translates it into wrappers
 * @author filip
 */
public class CraftingStationConfigManager extends ConfigManager {
    
    ////// VARIABLES ////// 
    private int writingToStation = 0; //0 beginning,1 recepts
    private String currentStation = null;
    private ItemRequired cSItemRequired = null;
    private ArrayList<ReceptWrapper> recepts;
    private EnergySourceWrapper[] energySourceWrappers;
    private StorageWrapper input, output;
        
    ////// CONSTRUCTORS //////  
    /**
     * On creaton grabs the file and translates it by its rules to a set of wrappers
     * @param configFile file that will be read to the rules of the manager
     */
    public CraftingStationConfigManager(InputStream configFile) {
        super(configFile);
    }

    ////// METHODS ///////  
    
    @Override
    public ConfigWrapper stringToWrapper(String line) {
        if(writingToStation == 0) {
            currentStation = null;
            cSItemRequired = null;
            recepts = new ArrayList<ReceptWrapper>();
            
            String[] words = line.split(" ");
            currentStation = words[0]; 
            cSItemRequired = new ItemRequired(CommonTranslation.toItems(words[1]));
            writingToStation = 1;
            
            energySourceWrappers = CommonTranslation.toEnergySourceWrappers(words[2]);
            
            input = CommonTranslation.toStorage(words[3]);
            output = CommonTranslation.toStorage(words[4]);
            
        } else if(writingToStation == 1) { 
            if(line.charAt(0) != '}') { 
                String receptId = line;
                recepts.add((ReceptWrapper)technicalities.Technicalities.RCM.getWrapper(receptId));
            } else { 
                writingToStation = 0;
                return new CraftingStationWrapper(
                        currentStation, 
                        cSItemRequired, 
                        recepts.toArray(new ReceptWrapper[recepts.size()]), 
                        energySourceWrappers,
                        input,
                        output
                        
                );
            }
        }
        return null;    
    }
    
}
