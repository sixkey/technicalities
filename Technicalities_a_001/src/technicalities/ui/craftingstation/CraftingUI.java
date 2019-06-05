/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.craftingstation / NewClass
*  created on 31.5.2019 , 16:50:31 
 */
package technicalities.ui.craftingstation;

import SixGen.SixUI.Components.SixButton;
import SixGen.SixUI.SixAction;
import java.awt.Color;
import java.awt.Graphics2D;
import technicalities.configmanagers.recept.ReceptWrapper;
import technicalities.ui.TUIChannel;
import technicalities.ui.console.Console;
import technicalities.ui.inventory.InventoryGrid;
import technicalities.ui.tui.TUI;
import static technicalities.variables.globals.GlobalVariables.INNERSLOTSIZE;
import static technicalities.variables.globals.GlobalVariables.SLOTMARGIN;
import static technicalities.variables.globals.GlobalVariables.SLOTSIZE;
import technicalities.variables.idls.UIDL;
import technicalities.world.objects.standable.craftingstation.CraftingStation;

/**
 * 
 * @author filip
 */
public class CraftingUI extends TUI implements ReceptReturn{

    private final CraftingStation craftingStation;
    private InventoryGrid inputGrid;
    private InventoryGrid outputGrid;
    
    private ReceptGrid receptGrid;
    
    private ReceptWrapper recept;
    
    private SixButton receptSelect;
    private SixButton craftButton;
    
    public CraftingUI(CraftingStation craftingStation) {
        super(UIDL.craftingStationUI);
        this.craftingStation = craftingStation;
        
        int inputOutputWidth = InventoryGrid.getSize(SLOTSIZE, SLOTMARGIN, 3);
        
        //// INPUT OUTPUT STORAGES
        
        inputGrid = new InventoryGrid(
                craftingStation.getMainInputStorage(),
                inputOutputWidth, 
                InventoryGrid.getHeight(SLOTSIZE, SLOTMARGIN, inputOutputWidth, craftingStation.getMainInputStorage().size()), 
                SLOTSIZE, 
                INNERSLOTSIZE, 
                SLOTMARGIN);
        
        outputGrid = new InventoryGrid(
                craftingStation.getMainOutputStorage(), 
                inputOutputWidth, 
                InventoryGrid.getHeight(SLOTSIZE, SLOTMARGIN, inputOutputWidth, craftingStation.getMainOutputStorage().size()), 
                SLOTSIZE, 
                INNERSLOTSIZE, 
                SLOTMARGIN);
        
        addComponent(inputGrid);
        addComponent(outputGrid);
       
        //// RECEPT GRID
        
        receptGrid = new ReceptGrid(
                this,
                craftingStation.csw.recepts,
                ReceptGrid.getSize(SLOTSIZE, SLOTMARGIN, 3),
                ReceptGrid.getSize(SLOTSIZE, SLOTMARGIN, 3),
                SLOTSIZE,
                INNERSLOTSIZE,
                SLOTMARGIN);
        
        //// RECEPT SELECTION BUTTON
        
        SixAction action = new SixAction() { 
            @Override
            public void method() { 
                receptGrid.setVisible(true);
                exclusiveFocus = receptGrid;
                receptGrid.setCenterX(TUIChannel.getScreenCenterX());
                receptGrid.setCenterY(TUIChannel.getScreenCenterY());
                
                Console.log("select");
            }
        };
        
        receptSelect = new SixButton(action, inputGrid.getX() + inputGrid.getWidth() + SLOTMARGIN, SLOTMARGIN, SLOTSIZE, SLOTSIZE, "select");
        addComponent(receptSelect);
        addComponent(receptGrid);
        receptGrid.setVisible(false);
        
        //// CRAFT BUTTON
        
        SixAction craftAction = new SixAction() { 
            @Override
            public void method() { 
                craftingStation.craft(recept);
            }
        };
        
        craftButton = new SixButton(craftAction, receptSelect.getX(), receptSelect.getYY() + SLOTMARGIN, SLOTSIZE, SLOTSIZE, "craft");
        addComponent(craftButton);
        
        outputGrid.setX(receptSelect.getXX() + SLOTMARGIN);
        
        backgroundColor = Color.red;
        
        this.width = outputGrid.getXX();
        this.height = Math.max(outputGrid.getYY(), InventoryGrid.getSize(SLOTSIZE, SLOTMARGIN, 3));
    }
    
    @Override
    public void returnRecept(ReceptWrapper recept) { 
        this.recept = recept;
        receptSelect.setTexture(recept.output[0].itemWrapper.sprite);
        receptGrid.setVisible(false);
        exclusiveFocus = null;
    }
    
    

}
