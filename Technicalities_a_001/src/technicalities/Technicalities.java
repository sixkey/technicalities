/*
*  Created by SixKeyStudios
*  Added in project Technicalities_a_001
*  File technicalities_a_001 / Technicalities_a_001
*  created on 20.5.2019 , 18:54:29 
 */
package technicalities;

import SixGen.Game.Game;
import SixGen.Game.GameLoop;
import SixGen.Window.CanvasManager;
import SixGen.Window.SixCanvas;
import canvases.MainCanvas;
import variables.globals.GlobalVariables;
import variables.idls.CIDL;

/**
 * Technicalities
 * 
 * - Game wrapper, starter and main method container
 * 
 * @author filip
 */
public class Technicalities extends Game {
    
    ////// CONSTRUCTORS //////
    
    public Technicalities() { 
        super(GameLoop.GameLoopType.singleThread, GlobalVariables.SCREENWIDTH, GlobalVariables.SCREENHEIGHT, GlobalVariables.TITLE,true, 60, 60);
    }

    ////// METHODS //////
    
    @Override
    public void canvInit(CanvasManager canvMan) {
        MainCanvas mainCanvas = new MainCanvas(this);
        SixCanvas[] canvases = {mainCanvas};
        canvMan.setCanvases(canvases);
    }
    
    public static void main(String[] args) {
        Technicalities t = new Technicalities();
        t.setActiveCanvas(CIDL.mainCanvas);
    }
    
}
