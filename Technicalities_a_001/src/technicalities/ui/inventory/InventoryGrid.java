/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.inventory / InventoryGridUI
*  created on 29.5.2019 , 11:46:18 
 */
package technicalities.ui.inventory;

import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.SixUI.SixUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import technicalities.items.item.Item;
import technicalities.items.storage.Storage;
import technicalities.ui.TUIChannel;
import technicalities.variables.idls.UIDL;

/**
 *
 * @author filip
 */
public class InventoryGrid extends SixUI{
    
    
    private int slotSize;
    private int innerSlotSize;
    private int margin;
    
    private Storage storage;
    int columns, rows;
    
    private ItemUI itemUI;
    
    public InventoryGrid(Storage storage, int width, int height, int slotSize, int innerSlotSize, int margin) { 
        super(UIDL.inventoryGrid);
        this.storage = storage;
        this.width = width;
        this.height = height;
        this.slotSize = slotSize;
        this.innerSlotSize = innerSlotSize;
        this.margin = margin;
        
        itemUI = new ItemUI();
        addComponent(itemUI);
        
        columns = (int)Math.floorDiv(width - margin, slotSize + margin);
        rows = (int)Math.ceil(height / columns);
    }
    
    @Override
    public void render(Graphics2D g) { 
        
        g.setColor(Color.darkGray);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        
        for(int y = 0; y < rows;y++) { 
            for(int x = 0; x < columns; x++) { 
                int i = y * columns + x;
                if(i < storage.size()) { 
                    Item item = storage.getItem(i);
                    g.setColor(Color.lightGray);
                    g.fillRect(
                                (int)(this.x + x * (slotSize + margin) + margin), 
                                (int)(this.y + y * (slotSize + margin) + margin),
                                slotSize,
                                slotSize);
                    if(item!=null) { 
                        g.drawImage(
                                item.itemWrapper.sprite, 
                                (int)(this.x + x * (slotSize + margin) + margin) + (slotSize - innerSlotSize) / 2, 
                                (int)(this.y + y * (slotSize + margin) + margin) + (slotSize - innerSlotSize) / 2,
                                innerSlotSize,
                                innerSlotSize,
                                null);
                        g.setFont(g.getFont().deriveFont((float)slotSize/2f));
                        g.setColor(Color.white);
                        drawCenteredString(g, String.valueOf(item.getAmount()), 
                                new Rectangle(
                                    (int)(this.x + x * (slotSize + margin) + margin) + slotSize / 2, 
                                    (int)(this.y + y * (slotSize + margin) + margin) + slotSize / 2,
                                    slotSize*3/4,
                                    slotSize*3/4
                                ));
                    }
                }
            }
        }
        itemUI.render(g);
    }
    
    @Override
    public boolean mouseAction(MouseEvent e, MouseActionType mat) { 
        int ex = e.getX();
        int ey = e.getY();
        if(TUIChannel.handEmpty()) {
            if(ex >= this.x && ex < this.x + this.width && ey >= this.y && ey < this.y + this.height) { 
                int tx = Math.floorDiv((ex - (int)this.x),(margin + slotSize));
                int ty = Math.floorDiv((ey - (int)this.y),(margin + slotSize));

                int i = ty * columns + tx;
                if(mat == MouseActionType.pressed) {
                    if(i < storage.size()) { 
                        Item item = storage.getItem(i);
                        TUIChannel.toMouse(item, storage.getSlot(i));
                        return true;
                    }
                } else {
                    if(i < storage.size()) { 
                        Item item = storage.getItem(i);
                        this.itemUI.setItem(item);
                    }
                    itemUI.setX(ex);
                    itemUI.setY(ey);
                    itemUI.setVisible(true);
                    return false;
                }
            } else { 
                itemUI.setVisible(false);
                return false;
            }
        } else { 
            itemUI.setVisible(false);
            if(MouseActionType.released == mat) {
                if(ex >= this.x && ex < this.x + this.width && ey >= this.y && ey < this.y + this.height) { 
                    int tx = Math.floorDiv((ex - (int)this.x),(margin + slotSize));
                    int ty = Math.floorDiv((ey - (int)this.y),(margin + slotSize));

                    int i = ty * columns + tx;
                    if(i < storage.size()) { 
                        Item item = storage.getSlot(i).add(TUIChannel.getItemFromMouse());
                        TUIChannel.returnItemToOriginalSlot(item);
                        return true;
                    }
                } else { 
                    return false;
                }
            }
        }
        return false;
    }
    
    public static int getSize(int slotSize, int marginSize, int number) { 
        return marginSize + (slotSize + marginSize) * number;
    }
    
    public static int getHeight(int slotSize, int marginSize, int width, int number) {
        int amX = (width - marginSize) / (marginSize + slotSize);
        return getSize(slotSize, marginSize, (int)Math.ceil(number / amX));
    }
    
}
