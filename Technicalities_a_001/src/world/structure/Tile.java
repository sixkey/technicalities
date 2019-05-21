/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.structure / Tile
*  created on 20.5.2019 , 19:11:46 
 */
package world.structure;

import java.awt.Color;

/**
 * Tile
 * 
 * - tile object wrapper - contains cordinates, textures, standing objects
 * 
 * @author filip
 */
public class Tile {
    
    ////// VARIABLES //////
    
    // tile based cordinates - in map, not in space
    public int x, y;
    
    // tile bg color
    public Color color;
    // int id to the texture 
    public int TID;
    
    ////// CONSTRUCTORS //////
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    ////// METHODS //////
    
    ////// GETTERS SETTERS ////// 
    
    public void setColor(Color color) { 
        this.color = color;
    }
    
}
