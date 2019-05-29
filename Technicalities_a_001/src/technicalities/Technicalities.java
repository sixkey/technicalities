/*
*  Created by SixKeyStudios
*  Added in project Technicalities_a_001
*  File technicalities_a_001 / Technicalities_a_001
*  created on 20.5.2019 , 18:54:29 
 */
package technicalities;

import SixGen.Game.Game;
import SixGen.Game.GameLoop;
import SixGen.Utils.Files.FileManager;
import SixGen.Window.CanvasManager;
import SixGen.Window.SixCanvas;
import technicalities.canvases.MainCanvas;
import technicalities.configmanagers.bioms.BiomConfigManager;
import technicalities.configmanagers.items.ItemConfigManager;
import technicalities.configmanagers.nature.NatureConfigManager;
import technicalities.ui.TechTexManager;
import technicalities.variables.globals.GlobalVariables;
import technicalities.variables.idls.CIDL;

/**
 * Technicalities
 * 
 * - Game wrapper, starter and main method container
 * 
 * @author filip
 */
public class Technicalities extends Game {
    
    ////// VARIABLES //////
    
    public static ItemConfigManager ICM;
    public static NatureConfigManager NCM;
    public static BiomConfigManager BCM;
    
    public static TechTexManager TTM;
    
    ////// CONSTRUCTORS //////
    
    public Technicalities() { 
        super(GameLoop.GameLoopType.singleThread, GlobalVariables.SCREENWIDTH, GlobalVariables.SCREENHEIGHT, GlobalVariables.TITLE,true, 60, 60);
    }

    ////// METHODS //////
    
    @Override
    public void canvInit(CanvasManager canvMan) {
        
        FileManager fm = new FileManager();
        
        //item config
        ICM = new ItemConfigManager(fm.getFileFromClassSource(getClass(), "/configs/items.tcf"));
        //nature config 
        NCM = new NatureConfigManager(fm.getFileFromClassSource(getClass(), "/configs/nature.tcf"));
        //biom config
        BCM = new BiomConfigManager(fm.getFileFromClassSource(getClass(), "/configs/bioms.tcf"));
        
        TTM = new TechTexManager();
        
        MainCanvas mainCanvas = new MainCanvas(this);
        SixCanvas[] canvases = {mainCanvas};
        canvMan.setCanvases(canvases);
    }
    
    public static void main(String[] args) {
        Technicalities t = new Technicalities();
        t.setActiveCanvas(CIDL.mainCanvas);
    }
    
}
