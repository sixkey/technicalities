/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui / MainCanvasUI
*  created on 31.5.2019 , 23:24:17 
 */
package technicalities.ui;

import SixGen.Events.Keyboard.SixAbstractKeyListener;
import SixGen.SixUI.SixUI;
import java.awt.event.KeyEvent;
import technicalities.ui.building.BuildingUI;
import technicalities.ui.console.Console;
import technicalities.ui.inventory.InventoryUI;
import technicalities.variables.globals.GlobalVariables;
import technicalities.variables.idls.UIDL;
import technicalities.world.World;
import technicalities.world.objects.creatures.Player;

/**
 * MainCanvasUI
 * contains all the subuis
 * @author filip
 */
public class MainCanvasUI extends SixUI implements GlobalVariables{
    
    ////// VARIABLES ////// 
    public InventoryUI inventoryUI;
    public BuildingUI buildingUI;
    public Console console;
    
    ////// CONSTRUCOTRS //////   
    
    public MainCanvasUI(World world, Player player, TUIChannel tuiChannel) {
        super(UIDL.mainCanvasUI);
        
        inventoryUI = new InventoryUI(player, SCREENWIDTH, SCREENHEIGHT);
        this.addComponent(inventoryUI);
        inventoryUI.setVisible(false);
        
        // buildings
        buildingUI = new BuildingUI(world, player);
        this.addComponent(buildingUI);
        
        // console
        console = new Console(0, SCREENHEIGHT - 200, 400, 200);
        this.addComponent(console);
        
        tuiChannel.setUIS(new SixUI[] {inventoryUI, buildingUI, console});
        
    }
    
    public void keyAction(KeyEvent e, SixAbstractKeyListener.KeyActionType kat) { 
        if(kat == SixAbstractKeyListener.KeyActionType.pressed) { 
            if(e.getKeyCode() == KeyEvent.VK_E) { 
                setInventoryVisible(!inventoryUI.isVisible());
            }
            if(e.getKeyCode() == KeyEvent.VK_C) { 
                console.setVisible(!console.isVisible());
            }
            if(e.getKeyCode() == KeyEvent.VK_B) { 
                buildingUI.setVisible(!buildingUI.isVisible());
            }
        }
    }
    
    public void setInventoryVisible(boolean visible) { 
        inventoryUI.setVisible(visible);
        if(inventoryUI.isVisible()) {
            exclusiveFocus = inventoryUI;
        } else { 
            exclusiveFocus = null;
        }
    }
}
