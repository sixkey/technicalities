/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.structure / Tile
*  created on 20.5.2019 , 19:11:46 
 */
package technicalities.world.structure;

import java.awt.Color;
import technicalities.variables.globals.GlobalVariables;
import technicalities.world.objects.standable.Standable;

/**
 * Tile
 * 
 * - tile object wrapper - contains cordinates, textures, standing objects
 * 
 * @author filip
 */
public class Tile implements GlobalVariables{
    
    ////// VARIABLES //////
    
    // tile based cordinates - in map, not in space
    public int x, y;
    // real cordinates of the tile
    public int rx, ry;
    // real dimensions 
    public int width, height;
    
    // tile bg color
    public Color color;
    // int id to the texture 
    public int TID;
    
    // object standing on the tile
    public Standable standable;
    
    ////// CONSTRUCTORS //////
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        
        this.rx = x * TILEWIDTH;
        this.ry = y * TILEHEIGHT;
        
        this.width = TILEWIDTH;
        this.height = TILEHEIGHT;
    }
    
    ////// METHODS //////
    
    ////// GETTERS SETTERS ////// 
    
    public void setColor(Color color) { 
        this.color = color;
    }
    
    public void setStandable(Standable standable) { 
        this.standable = standable;
    }
    
    public Standable getStandable() { 
        return standable;
    }
    
}
