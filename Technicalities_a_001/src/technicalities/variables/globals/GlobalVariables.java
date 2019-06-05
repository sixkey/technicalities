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
    // INTERFACE
    final int SLOTSIZE = 58;
    final int INNERSLOTSIZE = 48;
    final int SLOTMARGIN = 8;
    
    ///// WORLD  /////
    // TILES //
    final double textureRatio = 6;
    final int TILEWIDTH = (int)(16 * textureRatio), TILEHEIGHT = (int)(16 * textureRatio);
    // HANDLER //
    final int WORLDSIZE = 30; //number^2 of chunks in world 
    final int TILESINCHUNK = 32; //number of tiles in chunk
    final int CHUNKSIZE = TILESINCHUNK * TILEWIDTH;
    // PLAYER //
    final int PLAYER_SIZE = (int)(16 * textureRatio);
    
}
