/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.handler / World
*  created on 20.5.2019 , 19:19:08 
 */
package technicalities.world;

import SixGen.Utils.Utils;
import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.IDNumberPair;
import technicalities.configmanagers.bioms.BiomWrapper;
import technicalities.configmanagers.items.ItemWrapper;
import technicalities.configmanagers.nature.NatureConfigManager;
import technicalities.configmanagers.nature.NatureWrapper;
import technicalities.inventory.item.Item;
import java.awt.Color;
import java.util.Random;
import technicalities.variables.globals.GlobalVariables;
import technicalities.world.generators.BiomMapGenerator;
import technicalities.handler.TechHandler;
import technicalities.world.objects.items.ItemObject;
import technicalities.world.objects.standable.Nature;
import technicalities.world.structure.Layer;
import technicalities.world.structure.Tile;

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
    
    private Random random;
    
    ////// CONSTRUCTORS ////// 
    
    public World(TechHandler handler) {  
        this.handler = handler;
        this.BMG = new BiomMapGenerator(technicalities.Technicalities.BCM);
        
        random = new Random();
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
                        Nature nature = new Nature((NatureWrapper)NCM.getWrapper(p.id), temp, this);
                        nature.setTexture(technicalities.Technicalities.TTM.getRandomTexture(p.id));
                        temp.setStandable(nature);
                    }
                }
            }
        }
        
        return layer;
    }
    
    /**
     * spawns items in random positions 
     * @param ids
     * @param amounts
     * @param x
     * @param y 
     * TODO:
     * better spawning sytem
     */
    public void dropItems(String[] ids, int[] amounts, int x, int y) { 
        for(int i = 0 ; i < ids.length; i++) { 
            
            int xx = random.nextInt(TILEWIDTH * 2) - TILEWIDTH + x;
            int yy = random.nextInt(TILEHEIGHT * 2) - TILEHEIGHT + y;
            
            ItemObject io = new ItemObject(xx, yy, new Item((ItemWrapper)technicalities.Technicalities.ICM.getWrapper(ids[i]),
                                                    amounts[i]));
            io.setSprite(technicalities.Technicalities.TTM.getRandomTexture(ids[i]));
            handler.addObject(io);
        }
    }
    
    ////// GETTERS SETTERS //////   
    public int getTileX(Tile tile) { 
        return tile.x * TILEWIDTH;
    }
    public int getTileY(Tile tile) { 
        return tile.y * TILEHEIGHT;
    }
    
    public Tile getTile(int x, int y) { 
        return handler.getTile(x, y);
    }
    public Tile getTileFromRealCords(int x, int y) { 
        return handler.getTile(Math.floorDiv(x, TILEWIDTH), Math.floorDiv(y, TILEHEIGHT));
    }
    
}
