/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.craftingstation / EnergyUnitUI
*  created on 1.6.2019 , 22:08:31 
 */
package technicalities.ui.craftingstation.energy;

import SixGen.SixUI.Components.SixButton;
import SixGen.SixUI.Components.SixLabel;
import SixGen.SixUI.SixAction;
import java.awt.Color;
import technicalities.items.storage.Storage;
import technicalities.ui.inventory.InventoryGrid;
import technicalities.ui.tui.TUI;
import technicalities.variables.idls.UIDL;
import technicalities.world.objects.standable.craftingstation.EnergySourceUnit;

/**
 *
 * @author filip
 */
public class EnergyUnitUI extends TUI {
    
    private EnergySourceUnit unit;
    private Storage storage;
    
    public EnergyUnitUI(EnergySourceUnit unit) { 
        super(UIDL.energyUnitUI);
        this.unit = unit;
        storage = unit.getStorage();
        
        this.width = InventoryGrid.getSize(SLOTSIZE, SLOTMARGIN, 3);
        this.height = InventoryGrid.getSize(SLOTSIZE, SLOTMARGIN, 3) + SLOTSIZE + SLOTMARGIN;
        if(unit.getWrapper().title.equals("es_cranking_table")) {
            SixAction action = new SixAction() { 
                @Override
                public void method() { 
                    unit.addEnergy(1);
                }
            };
            SixButton button = new SixButton(action, 0, SLOTMARGIN + SLOTSIZE, this.width, this.width, "CRANK");
            components.add(button);
            
        } else {
            InventoryGrid grid = new InventoryGrid(
                storage, 
                (int)this.width, 
                (int)this.width, 
                SLOTSIZE, 
                INNERSLOTSIZE, 
                SLOTMARGIN);
            this.addComponent(grid);
            grid.setY(SLOTMARGIN + SLOTSIZE);
        }
        
        SixLabel label = new SixLabel(0, 0,(int)this.width, SLOTSIZE + SLOTMARGIN, unit.getWrapper().title, Color.red, Color.white);
        this.addComponent(label);
    }
    
}
