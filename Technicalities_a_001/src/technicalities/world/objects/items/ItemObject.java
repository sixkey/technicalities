/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.items / ItemObjet
*  created on 28.5.2019 , 21:58:29 
 */
package technicalities.world.objects.items;

import technicalities.inventory.item.Item;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import technicalities.variables.idls.OIDL;
import technicalities.world.objects.TObject;

/**
 *
 * @author filip
 */
public class ItemObject extends TObject{
    
    private BufferedImage sprite;
    private Item item;
    
    public ItemObject(float centerX, float centerY, Item item) { 
        super(centerX, centerY, OIDL.item);
        this.item = item;
    }
    
    @Override
    public void render(Graphics2D g) { 
        if(sprite!=null) { 
            g.drawImage(sprite, (int)centerX-sprite.getWidth() / 2, (int)centerY-sprite.getHeight()/2, null);
        }
    }
    
    public void setSprite(BufferedImage image) { 
        this.sprite = image;
    }
    
}
