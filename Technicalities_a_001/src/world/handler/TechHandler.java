/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File handler / TechHandler
*  created on 20.5.2019 , 19:10:04 
 */
package world.handler;

import SixGen.Handler.Handler;
import SixGen.Window.SixCanvas;
import java.awt.Graphics2D;
import java.util.ArrayList;
import variables.globals.GlobalVariables;
import world.objects.TObject;
import world.objects.Tickable;
import world.structure.Layer;
import world.structure.Tile;

/**
 * TechHandler
 * 
 * - tile based renderer
 * - interface based updater - updates only interfaces manually added to the handler
 * 
 * @author filip
 */
public class TechHandler extends Handler implements GlobalVariables{
    
    ////// VARIABLES //////
    
    private Layer layer;
    
    private ArrayList<Tickable> tickables;
    private ArrayList<TObject> objects;
    
    ////// CONSTRUCTORS //////
    
    public TechHandler(SixCanvas sixCanv) {
        super(sixCanv, null);
        tickables = new ArrayList();
        objects = new ArrayList();
    }
    
    ////// METHODS //////
    
    @Override 
    public void render(Graphics2D g) { 
        int xb, yb, xe, ye;
        
        xb = (int)(camera.getX() / TILEWIDTH) - 1;
        xe = (int)((camera.getX() + camera.getWidth()) / TILEWIDTH) + 1;
        yb = (int)(camera.getY() / TILEHEIGHT) - 1;
        ye = (int)((camera.getY() + camera.getHeight()) / TILEHEIGHT) + 1;
        
        xb = Math.min(layer.width, Math.max(0, xb));
        xe = Math.min(layer.width, Math.max(0, xe));
        yb = Math.min(layer.width, Math.max(0, yb));
        ye = Math.min(layer.width, Math.max(0, ye));
        
        for(int y = yb; y < ye; y++) { 
            for(int x = xb; x < xe; x++) { 
                Tile t = layer.tiles[y][x];
                g.setColor(t.color);
                g.fillRect(x * TILEWIDTH, y * TILEHEIGHT, TILEWIDTH, TILEHEIGHT);
            }
        }
        
        
        for(int i = 0; i < objects.size(); i++) { 
            objects.get(i).render(g);
        }
        
    }
    
    @Override
    public void tick() {  
        for(int i = 0; i < tickables.size(); i++) { 
            tickables.get(i).tick();
        }
        for(int i = 0; i < objects.size(); i++) { 
            objects.get(i).tick();
        }
    }
    
    ////// GETTERS SETTERS ////// 
    
    public void setLayer(Layer layer) { 
        this.layer = layer;
    }
    
    public void addTickable(Tickable t) { 
        this.tickables.add(t);
    }
    
    public void addObject(TObject o) { 
        objects.add(o);
    }
}
