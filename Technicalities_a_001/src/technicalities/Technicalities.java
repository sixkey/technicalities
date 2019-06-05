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
import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.bioms.BiomConfigManager;
import technicalities.configmanagers.craftingstations.CraftingStationConfigManager;
import technicalities.configmanagers.craftingstations.CraftingStationWrapper;
import technicalities.configmanagers.energysources.EnergySourceConfigManager;
import technicalities.configmanagers.items.ItemConfigManager;
import technicalities.configmanagers.nature.NatureConfigManager;
import technicalities.configmanagers.recept.ReceptConfigManager;
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
    public static ReceptConfigManager RCM;
    public static NatureConfigManager NCM;
    public static BiomConfigManager BCM;
    public static CraftingStationConfigManager CSCM;
    public static EnergySourceConfigManager ESCM;
    
    public static TechTexManager TTM;
    
    ////// CONSTRUCTORS //////
    
    public Technicalities() { 
        super(GameLoop.GameLoopType.singleThread, GlobalVariables.SCREENWIDTH, GlobalVariables.SCREENHEIGHT, GlobalVariables.TITLE,true, 60, 60);
    }

    ////// METHODS //////
    
    @Override
    public void canvInit(CanvasManager canvMan) {
        FileManager fm = new FileManager();
        
        //textures
        TTM = new TechTexManager();
        
        //item config
        ICM = new ItemConfigManager(fm.getFileFromClassSource(getClass(), "/configs/items.tcf"));
        RCM = new ReceptConfigManager(fm.getFileFromClassSource(getClass(), "/configs/recepts.tcf"));
        
        //energy source
        ESCM = new EnergySourceConfigManager(fm.getFileFromClassSource(getClass(), "/configs/energy_source.tcf"));
        System.out.println(ESCM.toString());
        
        //craftign station  
        CSCM = new CraftingStationConfigManager(fm.getFileFromClassSource(getClass(), "/configs/crafting_stations.tcf"));
        System.out.println(CSCM.toString());
        
        //nature config 
        NCM = new NatureConfigManager(fm.getFileFromClassSource(getClass(), "/configs/nature.tcf"));
        //biom config
        BCM = new BiomConfigManager(fm.getFileFromClassSource(getClass(), "/configs/bioms.tcf"));
        
        MainCanvas mainCanvas = new MainCanvas(this);
        SixCanvas[] canvases = {mainCanvas};
        canvMan.setCanvases(canvases);
    }
    
    public static void main(String[] args) {
        Technicalities t = new Technicalities();
        t.setActiveCanvas(CIDL.mainCanvas);
    }
    
}
