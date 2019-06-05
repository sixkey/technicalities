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
import technicalities.configmanagers.nature.NatureConfigManager;
import technicalities.configmanagers.nature.NatureWrapper;
import technicalities.items.item.Item;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import technicalities.variables.globals.GlobalVariables;
import technicalities.world.generators.BiomMapGenerator;
import technicalities.world.handler.TechHandler;
import technicalities.world.objects.TObject;
import technicalities.world.objects.creatures.Player;
import technicalities.world.objects.items.ItemObject;
import technicalities.world.objects.standable.Nature;
import technicalities.world.handler.Chunk;
import technicalities.world.handler.Tile;

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
    public Player player;
    
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
     * @param width worlds width in chunks
     * @param height worlds height in chunks
     * @return 
     */
    public Chunk[][] spawnLayer(int widthInChunks, int heightInChunks) { 
        
        int width = widthInChunks * TILESINCHUNK;
        int height = heightInChunks * TILESINCHUNK;
        
        Chunk[][] chunks = new Chunk[heightInChunks][];
        for(int y = 0; y < chunks.length; y++) { 
            chunks[y] = new Chunk[widthInChunks];
            for(int x = 0; x < chunks[y].length; x++) { 
                chunks[y][x] = new Chunk(handler, x, y, TILESINCHUNK, TILESINCHUNK);
            }
        }
        
        
        Utils utils = new Utils() {};
        
        int[][] biommap = BMG.generateBiomMap(width, height);
        
        for(int y = 0; y < height; y++) { 
            for(int x = 0; x < width; x++) { 
                Tile t = new Tile(x, y);
                BiomWrapper w = BMG.getBiomWrapper(biommap[y][x]);
                if(w!=null) {
                    BufferedImage sprite = technicalities.Technicalities.TTM.getRandomTexture(w.tileTitle);
                    if(sprite!=null) t.setSprite(sprite);
                }
                
                t.setColor(biommap[y][x] != 0 ? new Color(biommap[y][x]) : utils.randomColor(100,101,180,200,100,101));
                chunks[y / TILESINCHUNK][x / TILESINCHUNK].tiles[y % TILESINCHUNK][x % TILESINCHUNK] = t;
            }
        }
        
        ConfigWrapper[] wrappers = technicalities.Technicalities.BCM.getWrappers();
        NatureConfigManager NCM = technicalities.Technicalities.NCM;
        
        for(int i = 0; i < wrappers.length; i++) { 
            BiomWrapper w = (BiomWrapper)wrappers[i];
            IDNumberPair[] npc = w.naturePerChunk;
            for(IDNumberPair p : npc) { 
                for(int j = 0; j < (width*height*p.amount)/256; j++) { 
                    int nx = random.nextInt(width);
                    int ny = random.nextInt(height);
                    Tile temp = chunks[ny / TILESINCHUNK][nx / TILESINCHUNK].tiles[ny % TILESINCHUNK][nx % TILESINCHUNK];
                    if(temp.color.getRGB() == new Color(w.color).getRGB()) { 
                        Nature nature = new Nature((NatureWrapper)NCM.getWrapper(p.id), temp, this);
                        nature.setSprite(technicalities.Technicalities.TTM.getRandomTexture(p.id));
                        temp.setStandable(nature);
                    }
                }
            }
        }
        
        return chunks;
    }
    
    /**
     * spawns items in random positions 
     * @param items
     * @param x
     * @param y 
     * TODO:
     * better spawning sytem
     */
    public void dropItems(Item items[], int x, int y) { 
        for(int i = 0 ; i < items.length; i++) { 
            int xx = random.nextInt(TILEWIDTH * 2) - TILEWIDTH + x;
            int yy = random.nextInt(TILEHEIGHT * 2) - TILEHEIGHT + y;
            ItemObject io = new ItemObject(xx, yy, items[i]);
            io.setSprite(items[i].itemWrapper.sprite);
            handler.addObject(io);
        }
    }
    
    ////// GETTERS SETTERS //////   
    
    public void add(TObject object) { 
        handler.addObject(object);
    }
    
    public void remove(TObject object) { 
        handler.removeObject(object);
    }
    
    public Tile getTile(MouseEvent e) { 
        return handler.getTile(e, this);
    }
    
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
