/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers.bioms / BiomConfigManager
*  created on 22.5.2019 , 21:02:46 
 */
package technicalities.configmanagers.bioms;

import technicalities.configmanagers.ConfigManager;
import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.IDNumberPair;
import java.io.InputStream;

/**
 * BiomConfigManager
 * - config manager that gets information from biomss.tcf and translates it into wrappers
 * @author filip
 */
public class BiomConfigManager extends ConfigManager {

    ////// CONSTRUCTORS //////
    
    /**
     * On creaton grabs the file and translates it by its rules to a set of wrappers
     * @param configFile file that will be read to the rules of the manager
     */
    public BiomConfigManager(InputStream configFile) {
        super(configFile);
    }
    
    ////// METHODS //////   
    
    @Override
    public ConfigWrapper stringToWrapper(String line) {
        String[] words = line.split(" ");
        
        String id = words[0];
        String tileTitle = words[1];
        int c = Integer.parseInt(words[2],16);
        int s = Integer.parseInt(words[3]);
        int p = Integer.parseInt(words[4]);
        int q = Integer.parseInt(words[5]);
        int tl = Integer.parseInt(words[6]);
        
        IDNumberPair[] pairs;
        if(!words[6].equals("_")) {
            String[] natureInfo = words[7].split(",");
            pairs = new IDNumberPair[natureInfo.length];
            for(int i = 0; i < natureInfo.length; i++) { 
                String[] info = natureInfo[i].split(":");
                pairs[i] = new IDNumberPair(info[0], Integer.parseInt(info[1]));
            }
        } else { 
            pairs = new IDNumberPair[0];
        }
        
        return new BiomWrapper(id, tileTitle, c, s, p, q, tl, pairs);   
    }

}
