/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.structure / Layer
*  created on 20.5.2019 , 19:11:35 
 */
package technicalities.world.structure;

/**
 * Layer
 * 
 * - contains all the tiles in one 'z' cord. layer
 * 
 * @author filip
 */
public class Layer {
    
    ////// VARIABLES //////
    
    public Tile[][] tiles;
    
    public int width, height;
    
    ////// CONSTRUCTORS //////
    
    public Layer(int width, int height) { 
        this.width = width;
        this.height = height;
        tiles = new Tile[height][];
        for(int i = 0; i < tiles.length; i++) { 
            tiles[i] = new Tile[width];
        }
    }
    
    ////// GETTERS SETTERS //////
    
    public Tile getTile(int x, int y) { 
        return tiles[y][x];
    }
    
    public void setTile(int x, int y, Tile tile) { 
        tiles[y][x] = tile;
    }
    
}
