/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.craftingstation / EnergyCenterUI
*  created on 1.6.2019 , 22:06:29 
 */
package technicalities.ui.craftingstation.energy;

import technicalities.ui.tui.TUI;
import technicalities.variables.idls.UIDL;
import technicalities.world.objects.standable.craftingstation.EnergyCenter;
import technicalities.world.objects.standable.craftingstation.EnergySourceUnit;

/**
 *
 * @author filip
 */
public class EnergyCenterUI extends TUI{
    
    private EnergyCenter energyCenter;
    
    public EnergyCenterUI(EnergyCenter energyCenter) { 
        super(UIDL.energyCenterUI);
        this.energyCenter = energyCenter;
        
        EnergySourceUnit[] units = energyCenter.getUnits();
        
        int currentX = 0;
        int maxHeight = 100;
        
        for(int i = 0; i < units.length; i++) {
            EnergyUnitUI euui = new EnergyUnitUI(units[i]);
            euui.setX(currentX);
            currentX+=euui.getWidth();
            if(euui.getHeight() > maxHeight) { 
                maxHeight = (int)euui.getHeight();
            }
            addComponent(euui);
        }
        
        EnergyBar bar = new EnergyBar(energyCenter, currentX, 0, maxHeight);
        addComponent(bar);
        
        this.width = getWidthFromComponents();
        this.height = maxHeight;
    }
    
}
