/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.creatures.player / Player
*  created on 20.5.2019 , 21:33:16 
 */
package technicalities.world.objects.creatures;

import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.Utils.Timer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import technicalities.configmanagers.craftingstations.CraftingStationWrapper;
import technicalities.configmanagers.items.ItemWrapper;
import technicalities.items.item.Item;
import technicalities.items.storage.Storage;
import technicalities.ui.TUIChannel;
import technicalities.ui.console.Console;
import technicalities.variables.idls.OIDL;
import technicalities.world.World;
import technicalities.world.objects.TObject;
import technicalities.world.objects.items.ItemObject;
import technicalities.world.objects.standable.Standable;
import technicalities.world.handler.Tile;
import technicalities.world.objects.standable.craftingstation.CraftingStation;

/**
 * Player
 * - player object
 * @author filip
 */
public class Player extends Creature {
    
    ////// VARIABLES ////// 
    
    private Storage storage;
    
    //// DAMAGE
    private boolean damaging;
    private Timer timer;
    private Standable standable;
    
    private TUIChannel uiChannel;
    
    private CraftingStationWrapper csw;
    
    ////// CONSTRUCTORS ////// 
    public Player(float centerX, float centerY, World world, TUIChannel uiChannel) { 
        super(centerX, centerY, OIDL.player, world);
        setBoundsABottom(PLAYER_SIZE, PLAYER_SIZE);
        collisionRadius = PLAYER_SIZE * 2;
        color = Color.red;
        storage = new Storage(40);
        
        //REMOVE
        storage.add(new Item((ItemWrapper)technicalities.Technicalities.ICM.getWrapper("i_log"), 64));
        storage.add(new Item((ItemWrapper)technicalities.Technicalities.ICM.getWrapper("i_stone"), 64));
        storage.add(new Item((ItemWrapper)technicalities.Technicalities.ICM.getWrapper("i_coal"), 64));
        
        
        this.uiChannel = uiChannel;
        
        renderImage = technicalities.Technicalities.TTM.getRandomTexture("player");
    }
    
    ////// METHODS //////   
    
    @Override
    public void collision(TObject o) { 
        if(o.getId() == OIDL.item) { 
            ItemObject io = (ItemObject)o;
            Item i = io.getItem();
            i = storage.add(i);
            if(i!=null) { 
                io.setItem(i);
            } else { 
                world.remove(io);
            }
        }
    }
    
    public void tick() { 
        super.tick();
        
        if(damaging && timer!=null && standable!=null) { 
            if(timer.isFinished()) { 
                standable.damage(1);
                timer.reset();
                if(!standable.isAlive()) {
                    standable = null;
                    timer = null;
                    damaging = false;
                }
            }
        }
    }
    
    public void render(Graphics2D g) { 
        renderRenderImage(g);
    }
            
    @Override
    public void mouseAction(MouseEvent e, MouseActionType mat) { 
        if(mat == MouseActionType.released) { 
            damaging = false;
            standable = null;
            timer = null;
        }
    }
    
    public void mouseAction(MouseEvent e, MouseActionType mat, Tile tile) { 
        if(tile.getStandable()!=null) { 
            Standable s = tile.getStandable();
            if(mat == MouseActionType.pressed) {
                if(e.getButton() == 1) { 
                    damaging = true;
                    
                    standable = s;
                    timer = new Timer(200);
                } else if(e.getButton() == 3) {
                    Console.log(s.getTID());
                    if(s.getTID().charAt(0) == 'c') { 
                        CraftingStation cs = (CraftingStation) s;
                        uiChannel.appendToInventoryUI(cs.getCraftingTUI());
                    }
                    standable = s;
                }
            }
        }
    }
    
    ////// GETTERS SETTERS //////
    public Storage getStorage() { 
        return storage;
    }
    
    public String getTID() { 
        return "player";
    }
}
