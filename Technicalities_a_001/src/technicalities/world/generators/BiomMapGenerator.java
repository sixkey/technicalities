/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.generators / BiomMapGenerator
*  created on 22.5.2019 , 21:21:26 
 */
package technicalities.world.generators;

import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.bioms.BiomConfigManager;
import technicalities.configmanagers.bioms.BiomWrapper;
import java.util.Random;

/**
 * BiomMapGenerator
 * - generates biommaps
 * @author filip
 */
public class BiomMapGenerator { 
    
    ////// VARIABLES ////// 
    
    private BiomConfigManager BCM;
    private Random random;
    
    ////// CONSTRUCTROS //////  
    
    public BiomMapGenerator(BiomConfigManager BCM) { 
        this.BCM = BCM;
        random = new Random();
    }

    ////// METHODS //////
    
    /**
     * generates biom map with color coted values using wrappers from BCM (BIOMCONFIGMANAGER)
     * @param width width of the map
     * @param height height of the map
     * @return 
     */
    public int[][] generateBiomMap(int width, int height) { 
        //array init 
        int[][] map = new int[height][];
        for(int y = 0; y < height; y++) { 
            map[y] = new int[width];
        }
        //wrappers gather
        ConfigWrapper[] wrappers = BCM.getWrappers();
        
        
        for(int i = 0; i < wrappers.length; i++) { 
            BiomWrapper w = (BiomWrapper) wrappers[i];
            for(int y = 0; y < height; y++) { 
                for(int x = 0; x < width; x++) {
                    if(random.nextInt(1000) <= w.spawnChance) {
                        executeCell(map, x, y, w, w.color, 0);
                    }
                }
            }
        }
        
        return map;
    }
    
    /**
     * changes one tile and chooses what to do with the neighboors
     * @param map biome map
     * @param x cordinate of the cell
     * @param y cordinate of the cell
     * @param w wrapper of biom
     * @param value value of that biom in map
     * @param layer current recursion layer
     */
    public void executeCell(int[][] map, int x, int y, BiomWrapper w, int value, int layer) { 
        map[y][x] = value;
        if(layer!=w.terminateLayer) { 
            if(y > 0 ? map[y - 1][x] != value : false) { 
                if(random.nextInt(1000) <= w.propChance * Math.pow(w.propQuot / 1000f, layer)) { 
                    executeCell(map, x, y - 1, w, value, layer + 1);
                }
            }
            if(y < map.length - 1 ? map[y + 1][x] != value : false) {  
                if(random.nextInt(1000) <= w.propChance * Math.pow(w.propQuot / 1000f, layer)) { 
                    executeCell(map, x, y + 1, w, value, layer + 1);
                }
            }
            if(x > 0 ? map[y][x - 1] != value : false) {  
                if(random.nextInt(1000) <= w.propChance * Math.pow(w.propQuot / 1000f, layer)) { 
                    executeCell(map, x - 1, y, w, value, layer + 1);
                }
            }
            if(x < map[0].length - 1 ? map[y][x + 1] != value : false) {  
                if(random.nextInt(1000) <= w.propChance * Math.pow(w.propQuot / 1000f, layer)) { 
                    executeCell(map, x + 1, y, w, value, layer + 1);
                }
            }
        }
        
    }
}
