/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers / ConfigManager
*  created on 21.5.2019 , 22:20:16 
 */
package technicalities.configmanagers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filip
 */
public abstract class ConfigManager {
    private ConfigWrapper[] wrappers;
    public int SIZE;
    private Random random;
    
    public ConfigManager(InputStream configFile) {
        random = new Random();
        LinkedList<ConfigWrapper> hTypesList = new LinkedList<ConfigWrapper>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(configFile))) {
            String line = br.readLine();
            while (line != null) {
                ConfigWrapper wrapper = stringToWrapper(line);
                if(wrapper!=null) {
                    hTypesList.add(wrapper);
                }
                
                line = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        SIZE = hTypesList.size();

        this.wrappers = hTypesList.toArray(new ConfigWrapper[hTypesList.size()]);
    }

    
    public abstract ConfigWrapper stringToWrapper(String line);


    public ConfigWrapper getWrapper(int i) {
        return wrappers[Math.min(i, wrappers.length - 1)];
    }

    public ConfigWrapper getWrapper(String title) {
        return wrappers[getId(title)];
    }

    public ConfigWrapper[] getWrappers() {
        return wrappers;
    }
    
    public ConfigWrapper[] getWrappers(int ids[]) {
        ConfigWrapper[] wrapper = new ConfigWrapper[ids.length];
        for (int i = 0; i < ids.length; i++) {
            wrapper[i] = wrappers[ids[i]];
        }
        return wrapper;
    }

    public ConfigWrapper[] getWrappers(String ids[]) {
        ArrayList<ConfigWrapper> res = new ArrayList<>();
        for (String id : ids) {
            for (ConfigWrapper wrapper : wrappers) {
                if (id.equals(wrapper.getId())) {
                    res.add(wrapper);
                    break;
                }
            }
        }
        return res.toArray(new ConfigWrapper[res.size()]);
    }

    public int getId(String title) {
        for (int i = 0; i < wrappers.length; i++) {
            if (title.equals(wrappers[i].getId())) {
                return i;
            }
        }
        return -1;
    }

    public String randomTitle() {
        return wrappers[random.nextInt(SIZE)].getId();
    }

    public ConfigWrapper randomWrapper() {
        return wrappers[random.nextInt(SIZE)];
    }

    public String getTitle(int id) {
        return wrappers[id].getId();
    }
}
