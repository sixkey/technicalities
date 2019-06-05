/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.inventory / ItemUI
*  created on 29.5.2019 , 22:01:17 
 */
package technicalities.ui.inventory;

import SixGen.SixUI.SixUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import technicalities.items.item.Item;
import technicalities.variables.idls.UIDL;

/**
 *
 * @author filip
 */
public class ItemUI extends SixUI {
    
    private Item item;
    
    
    public ItemUI() { 
        super(UIDL.itemUI);
        width = 150;
        height = 100;
    }
    
    public void render(Graphics2D g) { 
        if(visible && item!=null) {
            Item temp = item;
            g.setColor(Color.black);
            g.fillRect((int)x, (int)y, (int)width, (int)height);

            g.drawImage(temp.itemWrapper.sprite, (int)x + 10, (int)y + 10, 32, 32, null);
            
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(12f));
            drawCenteredString(g, temp.toString(), new Rectangle((int)x + 52, (int)y + 10, (int)width - 52, 32));
        }
    }
    
    public void setItem(Item item) { 
        this.item = item;
    }
    
    public Item getItem() { 
        return item;
    }
    
}
