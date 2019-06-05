/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.craftingstation / CraftingStationUI
*  created on 1.6.2019 , 22:30:52 
 */
package technicalities.ui.craftingstation;

import technicalities.ui.craftingstation.energy.EnergyCenterUI;
import technicalities.ui.tui.TUI;
import technicalities.variables.idls.UIDL;
import technicalities.world.objects.standable.craftingstation.CraftingStation;

/**
 *
 * @author filip
 */
public class CraftingStationUI extends TUI {
    
    public CraftingStationUI(CraftingStation craftingStation) { 
        super(UIDL.craftingStationUI);
        
        CraftingUI craftingUI = new CraftingUI(craftingStation);
        EnergyCenterUI energyCounterUI = new EnergyCenterUI(craftingStation.getEnergyCenter());
        energyCounterUI.setY(craftingUI.getYY() + SLOTSIZE);
        
        addComponent(craftingUI);
        addComponent(energyCounterUI);
        
        this.width = this.getWidthFromComponents();
        this.height = this.getHeightFromComponents();
        craftingUI.setXX(this.getXX());
        energyCounterUI.setXX(this.getXX());
        
    }
    
}
