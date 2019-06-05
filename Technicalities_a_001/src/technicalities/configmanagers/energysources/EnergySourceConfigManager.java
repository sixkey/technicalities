/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.configmanagers.energysources / EnergySourceConfigManager
*  created on 1.6.2019 , 13:30:15 
 */
package technicalities.configmanagers.energysources;

import java.io.InputStream;
import java.util.LinkedList;
import technicalities.configmanagers.CommonTranslation;
import technicalities.configmanagers.ConfigManager;
import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.miscwrappers.StorageWrapper;

/**
 *
 * @author filip
 */
public class EnergySourceConfigManager extends ConfigManager {
    
    ////// VARIABLES ////// 
        
    ////// CONSTRUCTORS //////  
    
    /**
     * On creaton grabs the file and translates it by its rules to a set of wrappers
     * @param configFile file that will be read to the rules of the manager
     */
    public EnergySourceConfigManager(InputStream configFile) {
        super(configFile);
    }

    ////// METHODS ///////  
    
    @Override
    public ConfigWrapper stringToWrapper(String line) {
        String[] words = line.split(" ");
        String id = words[0];
        
        LinkedList<EnergySourcePartWrapper> itemPartWrappers = new LinkedList<>();
        LinkedList<EnergySourcePartWrapper> tilePartWrappers = new LinkedList<>();

        if(!words[1].equals("_")) {
            String[] energySourceUnits = words[1].split(";");
            for(int i = 0; i < energySourceUnits.length;i++) { 
                String temp = energySourceUnits[i];
                temp = temp.substring(1,temp.length() - 1);
                String[] unitInfo = temp.split(":");
                String name = unitInfo[0];
                if(name.split("_")[0].equals("ta")) { 
                    tilePartWrappers.add(new EnergySourcePartWrapper(name, Integer.parseInt(unitInfo[1])));
                } else { 
                    String[] times = unitInfo[1].split("x");
                    itemPartWrappers.add(new EnergySourcePartWrapper(name, Integer.parseInt(times[0]), Integer.parseInt(times[1])));
                }
            }
        }
        
        StorageWrapper storageWrapper = CommonTranslation.toStorage(words[2]);
        
        return new EnergySourceWrapper(
                id, 
                itemPartWrappers.toArray(new EnergySourcePartWrapper[itemPartWrappers.size()]),
                tilePartWrappers.toArray(new EnergySourcePartWrapper[tilePartWrappers.size()]),
                storageWrapper);
    }
}
