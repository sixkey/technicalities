/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers.bioms / BiomWrapper
*  created on 22.5.2019 , 21:02:51 
 */
package technicalities.configmanagers.bioms;

import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.IDNumberPair;

/**
 * ItemWrapper 
 * - wrapper for biom configuration and init 
 * @author filip
 */
public class BiomWrapper implements ConfigWrapper {
    
    // id of the biom in 
    public final String id;
    public final int color;
    public final int spawnChance;
    public final int propChance;
    public final int propQuot;
    public final int terminateLayer;
    public final IDNumberPair[] naturePerChunk;
    public final String tileTitle;
    
    /**
     * wrapper for bioms configuration and init
     * @param id id of the biom
     * @param color color representation of the biom 
     * @param spawnChance chance of the biom spawning in promile
     * @param propChance chance of the biom propagating in promile
     * @param propQuot quotient of the biom propagation sizing (q<1 -> lowering and q>1 -> growing)
     * @param terminateLayer layer at which the recursive generation cyclus ends
     */
    public BiomWrapper(String id, String tileTitle, int color, int spawnChance, int propChance, int propQuot, int terminateLayer, IDNumberPair[] natureAPerChunk) { 
        this.id = id;
        this.color = color;
        this.spawnChance = spawnChance;
        this.propChance = propChance;
        this.propQuot = propQuot;
        this.terminateLayer = terminateLayer;
        this.naturePerChunk = natureAPerChunk;
        this.tileTitle = tileTitle;
    }
    
    @Override
    public String getId() { 
        return id;
    }
    
}
