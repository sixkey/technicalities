/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File handler / TechHandler
*  created on 20.5.2019 , 19:10:04 
 */
package technicalities.handler;

import SixGen.Handler.Handler;
import SixGen.Window.SixCanvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import technicalities.variables.globals.GlobalVariables;
import technicalities.world.objects.TObject;
import technicalities.world.objects.Tickable;
import technicalities.world.objects.standable.Standable;
import technicalities.world.structure.Layer;
import technicalities.world.structure.Tile;

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
        // getting coridnates 
        int xb, yb, xe, ye;
        
        xb = (int)(camera.getX() / TILEWIDTH) - 3;
        xe = (int)((camera.getX() + camera.getWidth()) / TILEWIDTH) + 3;
        yb = (int)(camera.getY() / TILEHEIGHT) - 3;
        ye = (int)((camera.getY() + camera.getHeight()) / TILEHEIGHT) + 3;
        
        xb = Math.min(layer.width, Math.max(0, xb));
        xe = Math.min(layer.width, Math.max(0, xe));
        yb = Math.min(layer.width, Math.max(0, yb));
        ye = Math.min(layer.width, Math.max(0, ye));
        
        //rendering visible tiles
        for(int y = yb; y < ye; y++) { 
            for(int x = xb; x < xe; x++) { 
                Tile t = layer.tiles[y][x];
                //rendering tile
                g.setColor(t.color);
                g.fillRect(x * TILEWIDTH, y * TILEHEIGHT, TILEWIDTH, TILEHEIGHT);
            }
            for(int i = 0; i < objects.size(); i++) { 
                TObject temp = objects.get(i);
                if(Math.floorDiv((int)temp.getCenterY() + TILEHEIGHT / 2, TILEHEIGHT) == y) {
                    temp.render(g);
                }
            }
            //rendering standable 
            for(int x = xb; x < xe; x++) { 
                Tile t = layer.tiles[y][x];
                Standable s = t.getStandable();
                if(s!=null) { 
                    BufferedImage tex = s.getTexture();
                    if(tex!=null) { 
                        g.drawImage(tex, x*TILEWIDTH + TILEWIDTH / 2 - tex.getWidth() / 2, y*TILEHEIGHT + TILEHEIGHT / 2 - tex.getHeight(), null);
                    } else { 
                        g.setColor(Color.red);
                        g.fillRect(x * TILEWIDTH + 10, y * TILEHEIGHT +10, TILEWIDTH - 20, TILEHEIGHT -20);
                    }
                    if(s.getTUI()!=null) { 
                        s.getTUI().render(g);
                    }
                }
            }
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
    
    public Tile getTile(int x, int y) { 
        if(x >= 0 && x < layer.width && y >= 0 && y < layer.height) {
            return layer.getTile(x, y);
        } else { 
            return null;
        }
    }
}
