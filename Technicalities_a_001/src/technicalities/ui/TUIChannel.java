/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui / TUIManager
*  created on 31.5.2019 , 17:37:47 
 */
package technicalities.ui;

import SixGen.SixUI.SixUI;
import SixGen.Utils.ID;
import SixGen.Utils.Utils;
import java.awt.image.BufferedImage;
import technicalities.items.item.Item;
import technicalities.items.storage.Slot;
import technicalities.ui.inventory.InventoryUI;
import technicalities.variables.idls.UIDL;

/**
 *
 * @author filip
 */
public class TUIChannel {
    private static SixUI[] uis;
    
    
    //// ITEM TRANSFERING
    private static Slot original;
    private static Item item;
    
    private static Utils utils;
    
    private static int screenWidth;
    private static int screenHeight;
    
    private static BufferedImage mouseSprite;
    
    ////// CONSTRUCTORS //////
    public TUIChannel(int screenWidth, int screenHeight) { 
        utils = new Utils(){};
        TUIChannel.screenWidth = screenWidth;
        TUIChannel.screenHeight = screenHeight;
    }
    
    public void setUIS(SixUI[] uis) { 
        TUIChannel.uis = uis;
    }
    
    /**
     * 
     * @param id
     * @return 
     * TODO: better finding algorithm
     */
    public static SixUI getUI(ID id) {
        for(SixUI u : uis) { 
            if(u.getId() == id) { 
                return u;
            }
        }
        return null;
    }
    
    //// ADDING SHIT TO THE INVENTORY UI
    
    public static void appendToInventoryUI(SixUI ui) { 
        InventoryUI inventory = (InventoryUI) getUI(UIDL.playerInventory);
        inventory.appendUI(ui);
    }
    
    //// MOVING ITEMS
    
    public static Item getItemFromMouse() { 
        return item;
    } 
    
    public static void returnItemToOriginalSlot(Item item) { 
        TUIChannel.purgeMouseSprite();
        original.add(item);
        TUIChannel.item = null;
    }
    
    public static void returnItemToOriginalSlot() { 
        TUIChannel.purgeMouseSprite();
        original.add(item);
        item = null;
    }
    
    public static void toMouse(Item item, Slot slot) { 
        if(item!=null && slot!=null) {
            TUIChannel.item = item;
            TUIChannel.original = slot;
            TUIChannel.setMouseSprite(item.itemWrapper.sprite);
            original.removeItem();
        }
    }
    
    public static boolean handEmpty() { 
        return item==null;
    }
    
    //// MOUSE SPRITE
    
    public static boolean isMouseSpriteEmpty() { 
        return mouseSprite == null;
    }
    
    public static BufferedImage getMouseSprite() { 
        return mouseSprite;
    }
    
    public static void purgeMouseSprite() { 
        mouseSprite = null;
    }
    
    public static void setMouseSprite(BufferedImage image) { 
        mouseSprite = utils.rescale(image, 32,32);
    }
    
    public static int getScreenCenterX() { 
        return TUIChannel.screenWidth / 2;
    }
    
    public static int getScreenCenterY() { 
        return TUIChannel.screenHeight / 2;
    }
    
    public static int getScreenWidth() { 
        return screenWidth;
    }
    
    public static int getScreenHeight() { 
        return screenHeight;
    }
}
