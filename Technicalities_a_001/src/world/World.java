/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.handler / World
*  created on 20.5.2019 , 19:19:08 
 */
package world;

import SixGen.Utils.Utils;
import world.handler.TechHandler;
import world.structure.Layer;
import world.structure.Tile;

/**
 * World
 * 
 * - room alternative (contains all the objects)
 * - spawning, communication with other objects
 * 
 * @author filip
 */
public class World {
    
    ////// VARIABLES //////
    public TechHandler handler;
    
    ////// CONSTRUCTORS ////// 
    
    public World(TechHandler handler) {  
        this.handler = handler;
    }
    
    ////// METHODS ////// 

    // SPAWNING //
    
    /**
     * creates and returns layer 
     * @param width layer's width
     * @param height layer's height 
     * @return 
     */
    public Layer spawnLayer(int width, int height) { 
        Layer layer = new Layer(width, height);
        
        Utils utils = new Utils() {};
        
        for(int y = 0; y < height; y++) { 
            for(int x = 0; x < width; x++) { 
                Tile t = new Tile(x, y);
                t.setColor(utils.randomColor(100,101,180,200,100,101));
                layer.tiles[y][x] = t;
            }
        }
        
        return layer;
    }
    
    
}
