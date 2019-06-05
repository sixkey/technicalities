/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.craftingstation.energy / EnergyBar
*  created on 1.6.2019 , 22:21:53 
 */
package technicalities.ui.craftingstation.energy;

import SixGen.SixUI.SixUI;
import java.awt.Color;
import java.awt.Graphics2D;
import technicalities.variables.idls.UIDL;
import technicalities.world.objects.standable.craftingstation.EnergyCenter;

/**
 *
 * @author filip
 */
public class EnergyBar extends SixUI {
    
    private EnergyCenter center;
    
    private int barWidth;
    
    public EnergyBar(EnergyCenter center, int x, int y, int height) { 
        super(UIDL.energyBar);
        this.x = x;
        this.y = y;
        this.barWidth = 20;
        this.width = barWidth + 50;
        this.height = height;
        this.center = center;
    }
    
    @Override
    public void render(Graphics2D g) { 
        if(visible) { 
            g.setColor(Color.black);
            g.fillRect((int)(this.x + this.width - barWidth), (int)this.y, barWidth, (int)this.height);
            g.setColor(Color.blue);
            g.fillRect((int)(this.x + this.width - barWidth), (int)(this.y + this.height - center.getEnergyDouble() * this.height), barWidth, (int)(center.getEnergyDouble() * this.height));
        }
    }
    
}
