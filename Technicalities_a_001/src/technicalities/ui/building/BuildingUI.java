/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.building / BuildingUI
*  created on 30.5.2019 , 14:52:23 
 */
package technicalities.ui.building;

import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.SixUI.SixAction;
import SixGen.SixUI.SixUI;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.craftingstations.CraftingStationWrapper;
import technicalities.ui.TUIChannel;
import technicalities.ui.console.Console;
import technicalities.variables.idls.UIDL;
import technicalities.world.World;
import technicalities.world.objects.creatures.Player;
import technicalities.world.objects.standable.craftingstation.CraftingStation;
import technicalities.world.handler.Tile;

/**
 *
 * @author filip
 */
public class BuildingUI extends SixUI {
    
    ////// VARIABLES //////
    
    private Player player;
    private World world;
    private CraftingStationWrapper nextBuild;
    
    ////// CONSTRUCTORS //////
    
    public BuildingUI(World world, Player player) { 
        super(UIDL.buildingUI);
        this.player = player;
        this.world = world;
        
        //// SETTING UP THE BUTTONS
        
        ConfigWrapper[] wrappers = technicalities.Technicalities.CSCM.getWrappers();
        SixAction[] actions = new SixAction[wrappers.length];
        BufferedImage[] textures = new BufferedImage[wrappers.length];
        
        for(int i = 0; i < wrappers.length; i++) {
            CraftingStationWrapper csw = (CraftingStationWrapper) wrappers[i];
            actions[i] = new SixAction() { 
                @Override
                protected void method() {
                    build(csw.id);
                }
            };
            textures[i] = technicalities.Technicalities.TTM.getRandomTexture(csw.id);
        }
        
        addButtonsWithAligment(SixUI.AligmentType.vertical, actions, textures, 10, 10, 64f, 64f, 10, false);
    }
    
    @Override
    public boolean mouseAction(MouseEvent e, MouseActionType mat) { 
        boolean result = super.mouseAction(e, mat);
        
        if(result)return true;
        
        if(nextBuild!=null) { 
            if(mat == MouseActionType.pressed) {
                Tile t = world.getTile(e);
                if(t!=null) {
                    //if nothing stands on the tile and players has the items
                    if(t.standable==null && player.getStorage().contains(nextBuild.itemRequired)) {
                        //removing stuff from players storage
                        player.getStorage().remove(nextBuild.itemRequired);
                        CraftingStation station = new CraftingStation(nextBuild, t, world);
                        station.setSprite(technicalities.Technicalities.TTM.getRandomTexture(nextBuild.id));
                        t.setStandable(station);
                        result = true;
                    } else { 
                        if(t.standable != null) {
                            Console.log("tile is full");
                        } else { 
                            Console.log("not enough items");
                        }
                    }
                } else { 
                    Console.log("tile is null");
                }
                nextBuild=null;
                TUIChannel.purgeMouseSprite();
            } 
        }
        
        if(result)return true;
        return result;
    }
    
    public void build(String stationId) { 
        CraftingStationWrapper csw = (CraftingStationWrapper)technicalities.Technicalities.CSCM.getWrapper(stationId);
        
        boolean playerHasItems = player.getStorage().contains(csw.itemRequired);
        
        if(playerHasItems) {
            this.nextBuild = csw;
            TUIChannel.setMouseSprite(technicalities.Technicalities.TTM.getRandomTexture(csw.id));
            Console.log("Building " + stationId);
        } else { 
            Console.log("Unable to build " + stationId + " cause of lack of items");
        }
        
    }
    
}
