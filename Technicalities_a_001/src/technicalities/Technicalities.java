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
import variables.idls.CIDL;

/**
 *
 * @author filip
 */
public class Technicalities extends Game {
    
    public Technicalities() { 
        super(GameLoop.GameLoopType.singleThread, 1600, 900, "technicalities", true, 60, 60);
    }
    
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
