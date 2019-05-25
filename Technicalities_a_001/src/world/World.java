/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.handler / World
*  created on 20.5.2019 , 19:19:08 
 */
package world;

import SixGen.Utils.Utils;
import configmanagers.ConfigWrapper;
import configmanagers.IDNumberPair;
import configmanagers.bioms.BiomWrapper;
import configmanagers.nature.NatureConfigManager;
import configmanagers.nature.NatureWrapper;
import java.awt.Color;
import java.util.Random;
import variables.globals.GlobalVariables;
import world.generators.BiomMapGenerator;
import world.handler.TechHandler;
import world.objects.standable.Nature;
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
public class World implements GlobalVariables {
    
    ////// VARIABLES //////
    public TechHandler handler;
    private BiomMapGenerator BMG;
    
    ////// CONSTRUCTORS ////// 
    
    public World(TechHandler handler) {  
        this.handler = handler;
        this.BMG = new BiomMapGenerator(technicalities.Technicalities.BCM);
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
        
        int[][] biommap = BMG.generateBiomMap(width, height);
        
        for(int y = 0; y < height; y++) { 
            for(int x = 0; x < width; x++) { 
                Tile t = new Tile(x, y);
                t.setColor(biommap[y][x] != 0 ? new Color(biommap[y][x]) : utils.randomColor(100,101,180,200,100,101));
                layer.tiles[y][x] = t;
            }
        }
        
        ConfigWrapper[] wrappers = technicalities.Technicalities.BCM.getWrappers();
        NatureConfigManager NCM = technicalities.Technicalities.NCM;
        
        Random random = new Random();
        
        for(int i = 0; i < wrappers.length; i++) { 
            BiomWrapper w = (BiomWrapper)wrappers[i];
            IDNumberPair[] npc = w.naturePerChunk;
            for(IDNumberPair p : npc) { 
                System.out.println(p.id);
                
                for(int j = 0; j < (width*height*p.amount)/256; j++) { 
                    int nx = random.nextInt(width);
                    int ny = random.nextInt(height);
                    Tile temp = layer.tiles[ny][nx];
                    if(temp.color.getRGB() == new Color(w.color).getRGB()) { 
                        temp.setStandable(new Nature((NatureWrapper)NCM.getWrapper(p.id), temp, this));
                    }
                }
            }
        }
        
        return layer;
    }
    
    ////// GETTERS SETTERS //////   
    public int getTileX(Tile tile) { 
        return tile.x * TILEWIDTH;
    }
    public int getTileY(Tile tile) { 
        return tile.y * TILEHEIGHT;
    }
    
    
}
