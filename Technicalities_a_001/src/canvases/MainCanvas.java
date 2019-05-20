/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File canvases / MainCanvas
*  created on 20.5.2019 , 19:05:54 
 */
package canvases;

import SixGen.Game.Game;
import SixGen.Handler.Handler.RenderType;
import SixGen.Window.SixCanvas;
import variables.idls.CIDL;

/**
 *
 * @author filip
 */
public class MainCanvas extends SixCanvas {
    
    public MainCanvas(Game game) { 
        super(game, RenderType.front, CIDL.mainCanvas);
    }
    
    public void init() { 
        
    }
    
}
