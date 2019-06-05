/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.inventory / Inventory
*  created on 29.5.2019 , 11:38:14 
 */
package technicalities.ui.inventory;

import SixGen.SixUI.SixUI;
import java.awt.Color;
import java.awt.Graphics2D;
import technicalities.ui.console.Console;
import technicalities.variables.globals.GlobalVariables;
import technicalities.variables.idls.UIDL;
import technicalities.world.objects.creatures.Player;

/**
 * Inventory UI
 * - inventory user interface
 * - displays players inventory
 * - displays all the items in the game
 * - displays appendedUI (chests, crafting stations and so on)
 * @author filip
 */
public class InventoryUI extends SixUI implements GlobalVariables{
    
    ////// VARIABLES ////// 
    
    private int frameWidth, frameHeight;
    
    InventoryGrid grid;
    
    private SixUI appendedUI;
    private int appendXX, appendY;
    
    private int margin;
    
    ////// CONSTRUCTORS //////  
    
    public InventoryUI(Player player, int frameWidth, int frameHeight) {
        super(UIDL.playerInventory);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        
        
        //setting up the grid
        int slotSize = SLOTSIZE;
        int innerSlotSize = INNERSLOTSIZE;
        int marginSize = SLOTMARGIN;
        int slotWidth = 8;
        int slotHeight = 5;
        int inventoryWidth = slotWidth * (slotSize + marginSize) + marginSize;
        int inventoryHeight = slotHeight * (slotSize + marginSize) + marginSize;
        
        margin = 20;
        
        //inventory gird
        
        grid = new InventoryGrid(player.getStorage(), inventoryWidth, inventoryHeight, slotSize, innerSlotSize, marginSize);
        grid.setX(margin);
        grid.setY(margin);
        
        //setting up the appendedUI
        
        appendXX = frameWidth;
        appendY = 0;
        
        this.addComponent(grid);
    }
    
    @Override
    public void render(Graphics2D g) { 
        if(visible) {
            g.setColor(Color.black);
            g.setComposite(getAlphaComposite(0.8f));
            g.fillRect(0, 0, frameWidth, frameHeight);
            g.setComposite(getAlphaComposite(1f));
            
            super.render(g);
        }
    }
    
    public void appendUI(SixUI appendedUI) {
        if(this.appendedUI!=null) { 
            this.removeComponent(this.appendedUI);
        }
        Console.log(String.valueOf(appendedUI.getId()));
        appendedUI.setX(appendXX - appendedUI.getWidth() - margin);
        appendedUI.setY(appendY + margin);
        this.addComponent(appendedUI);
        this.appendedUI = appendedUI;
    }
    
    public void setVisible(boolean visible) { 
        super.setVisible(visible);
        if(!visible) {
            this.removeComponent(this.appendedUI);
        }
    }
    
}
