package SixGen.Window;

import SixGen.Utils.ID;
import SixGen.Window.Window;
import java.util.LinkedList;

/**
 * CanvasManager
 * Abilities:
 *  Taking care of canvases
 *      ticking 
 *      rendering
 *  initializing canvas via adding SixCanv array (setCanvases) in canvInit
 *  setting main canvas in init function
 */



public class CanvasManager {

    //VARIABLES
    
    // Canvas that is ticking and being rendered
    private SixCanvas activeCanvas;
    // List of all canvases. CanvasManagers keeps info about them
    private SixCanvas[] canvases;
    // JFrame of the game
    private Window window;
    
    private boolean active = true;
    
    //CONSTRUCTORS
        
    public CanvasManager(Window window) { 
        this.window = window;
    }
    
    //VOIDS
    
    public void render() {
        //@render
        if (activeCanvas != null && active) {
            activeCanvas.renderBase();
        }
    }

    public void tick() {
        //@tick
        if (activeCanvas != null && active) {
            activeCanvas.tick();
        }
    }

    //GETTERS SETTERS
    
    public void setActiveCanvas(SixCanvas canv) {
        active = false;
        if(canv!=null) {
            for(SixCanvas c : canvases) {
                window.minimazeCanvas(c);
            }
            window.maximizeCanvas(canv);
            activeCanvas = canv;
        }
        active = true;
    }

    public SixCanvas getActiveCavnas() {
        return activeCanvas;
    }
    public void setCanvases(SixCanvas canv[]) { 
        active = false;
        this.canvases = canv;
        active = true;
    }

    public SixCanvas getCanvas(int i) {
        return canvases[i];
    }

    public SixCanvas getCanvas(ID Id) {
        SixCanvas result = null;
        for (int i = 0; i < canvases.length; i++) {
            SixCanvas temp = canvases[i];
            if (temp.getId().equals(Id)) {
                result = temp;
            }
        }
        return result;
    }

    public int getCanavasesAmount() {
        return canvases.length;
    }
    
    public void setWindow(Window window) { 
        this.window = window;
    }
    public Window getWindow() { 
        return window;
    }
    
    public void setActive(boolean active) { 
        this.active = active;
    }
    
    public boolean isActive() { 
        return active;
    }
}
