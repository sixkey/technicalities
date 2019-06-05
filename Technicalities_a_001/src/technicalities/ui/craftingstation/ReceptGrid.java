/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.craftingstation / CraftingList
*  created on 31.5.2019 , 20:24:37 
 */
package technicalities.ui.craftingstation;

import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.SixUI.SixUI;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import technicalities.configmanagers.ConfigWrapper;
import technicalities.configmanagers.recept.ReceptWrapper;
import technicalities.items.item.Item;
import technicalities.ui.TUIChannel;
import technicalities.ui.inventory.ItemUI;
import technicalities.variables.idls.UIDL;

/**
 *
 * @author filip
 */
public class ReceptGrid extends SixUI{
    
    
    private int slotSize;
    private int innerSlotSize;
    private int margin;
    
    int columns, rows;
    
    private ItemUI itemUI;
    private ReceptWrapper[] recepts;
    
    private final ReceptReturn returner;
    
    private int frameWidth, frameHeight;
    
    public ReceptGrid(ReceptReturn returner, ReceptWrapper[] recepts,  int width, int height, int slotSize, int innerSlotSize, int margin) { 
        super(UIDL.receptGrid);
        
        this.width = width;
        this.height = height;
        this.slotSize = slotSize;
        this.innerSlotSize = innerSlotSize;
        this.margin = margin;
        this.returner = returner;
        
        itemUI = new ItemUI();
        addComponent(itemUI);
        
        this.recepts = recepts;
        
        columns = (int)Math.floorDiv(width - margin, slotSize + margin);
        rows = (int)Math.ceil(height / columns);
        
        this.frameWidth = TUIChannel.getScreenWidth();
        this.frameHeight = TUIChannel.getScreenHeight();
    }
    
    @Override
    public void render(Graphics2D g) { 
        if(visible) {
            g.setColor(Color.black);
            g.setComposite(getAlphaComposite(0.8f));
            g.fillRect(0, 0, frameWidth, frameHeight);
            g.setComposite(getAlphaComposite(1f));
            
            g.setColor(Color.darkGray);
            g.fillRect((int)x, (int)y, (int)width, (int)height);

            for(int y = 0; y < rows;y++) { 
                for(int x = 0; x < columns; x++) { 
                    int i = y * columns + x;
                    if(i < recepts.length) { 
                        Item item = recepts[i].output[0];
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
    }
    
    @Override
    public boolean mouseAction(MouseEvent e, MouseActionType mat) { 
        int ex = e.getX();
        int ey = e.getY();
        if(ex >= this.x && ex < this.x + this.width && ey >= this.y && ey < this.y + this.height) { 
            int tx = Math.floorDiv((ex - (int)this.x),(margin + slotSize));
            int ty = Math.floorDiv((ey - (int)this.y),(margin + slotSize));
            
            int i = ty * columns + tx;
            if(mat == MouseActionType.pressed) {
                if(i < recepts.length) { 
                    returner.returnRecept(recepts[i]);
                    return true;
                }
            } else {
                if(i < recepts.length) { 
                    itemUI.setItem(recepts[i].output[0]);
                } else { 
                    itemUI.setVisible(false);
                }
            }
            itemUI.setX(ex);
            itemUI.setY(ey);
            itemUI.setVisible(true);
            return false;
        } else { 
            itemUI.setVisible(false);
            return false;
        }
    }
    
    public static int getSize(int slotSize, int marginSize, int number) { 
        return marginSize + (slotSize + marginSize) * number;
    }
    
}
