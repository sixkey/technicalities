/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File variables.globals / GlobalVariables
*  created on 20.5.2019 , 19:29:36 
 */
package technicalities.variables.globals;

/**
 * Global Variables
 * @author filip
 */
public interface GlobalVariables {
    
    ///// WINDOW ///// 
    final int SCREENWIDTH = 1600, SCREENHEIGHT = 900;
    final String TITLE = "technicalities";
    
    ///// WORLD  /////
    // TILES //
    final int TILEWIDTH = 64, TILEHEIGHT = 64;
    // HANDLER //
    final int WORLDSIZE = 3; //number^2 of chunks in world 
    final int TILESINCHUNK = 16; //number of tiles in chunk
    final int CHUNKSIZE = TILESINCHUNK * TILEWIDTH;
    // PLAYER //
    final int PLAYER_SIZE = 32;
    
}
