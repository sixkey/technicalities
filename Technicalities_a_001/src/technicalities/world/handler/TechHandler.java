/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File handler / TechHandler
*  created on 20.5.2019 , 19:10:04 
 */
package technicalities.world.handler;

import SixGen.Handler.Handler;
import SixGen.Window.SixCanvas;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import technicalities.variables.globals.GlobalVariables;
import technicalities.world.World;
import technicalities.world.objects.TObject;
import technicalities.world.objects.Tickable;
import technicalities.world.handler.Chunk;
import technicalities.world.handler.Tile;

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
    
    private Chunk chunks[][];
    
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
        // getting coridnates of the chunks
        int xcb, ycb, xce, yce;
        
        xcb = (int)(camera.getX() / CHUNKSIZE) - 2;
        xce = (int)((camera.getX() + camera.getWidth()) / CHUNKSIZE) + 2;
        ycb = (int)(camera.getY() / CHUNKSIZE) - 2;
        yce = (int)((camera.getY() + camera.getHeight()) / CHUNKSIZE) + 2;
        
        xcb = Math.min(chunks.length, Math.max(0, xcb));
        xce = Math.min(chunks.length, Math.max(0, xce));
        ycb = Math.min(chunks[0].length, Math.max(0, ycb));
        yce = Math.min(chunks[0].length, Math.max(0, yce));
        
        // getting cordinates of the tiles 
        int xb, yb, xe, ye;
        int xbt, ybt, xet, yet;
        
        xb = (int)(camera.getX() / TILEWIDTH) - 2;
        xe = (int)((camera.getX() + camera.getWidth()) / TILEWIDTH) + 2;
        yb = (int)(camera.getY() / TILEHEIGHT) - 2;
        ye = (int)((camera.getY() + camera.getHeight()) / TILEHEIGHT) + 2;
        
        //rendering visible tiles
        for(int y = ycb; y < yce; y++) { 
            for(int x = xcb; x < xce; x++) { 
                Chunk temp = chunks[y][x];
                xbt = Math.min(TILESINCHUNK, Math.max(0, xb - x * TILESINCHUNK));
                xet = Math.min(TILESINCHUNK, Math.max(0, xe - x * TILESINCHUNK));
                ybt = Math.min(TILESINCHUNK, Math.max(0, yb - y * TILESINCHUNK));
                yet = Math.min(TILESINCHUNK, Math.max(0, ye - y * TILESINCHUNK));
                temp.render(g, xbt, xet, ybt, yet);
            }
        }
        //rendering objects and standables
        for(int y = ycb; y < yce; y++) { 
            for(int x = xcb; x < xce; x++) { 
                chunks[y][x].frontRender(g);
            }
        }
    }
    
    @Override
    public void tick() {  
        int xb, yb, xe, ye;
        
        xb = (int)(camera.getX() / CHUNKSIZE) - 2;
        xe = (int)((camera.getX() + camera.getWidth()) / CHUNKSIZE) + 2;
        yb = (int)(camera.getY() / CHUNKSIZE) - 2;
        ye = (int)((camera.getY() + camera.getHeight()) / CHUNKSIZE) + 2;
        
        xb = Math.min(chunks.length, Math.max(0, xb));
        xe = Math.min(chunks.length, Math.max(0, xe));
        yb = Math.min(chunks[0].length, Math.max(0, yb));
        ye = Math.min(chunks[0].length, Math.max(0, ye));
        
        //rendering visible tiles
        for(int y = yb; y < ye; y++) { 
            for(int x = xb; x < xe; x++) { 
                chunks[y][x].tick();
            }
        }
    }
    
    ////// GETTERS SETTERS ////// 
    
    public void setChunks(Chunk[][] chunks) { 
        this.chunks = chunks;
    }
    
    public void addTickable(Tickable t) { 
        this.tickables.add(t);
    }
    
    public void addObject(TObject o) { 
        int cy = Math.floorDiv((int)o.getCenterY(), CHUNKSIZE);
        int cx = Math.floorDiv((int)o.getCenterX(), CHUNKSIZE);
        chunks[cy][cx].addObject(o);
    }
    
    public void removeObject(TObject o) { 
        int cy = Math.floorDiv((int)o.getCenterY(), CHUNKSIZE);
        int cx = Math.floorDiv((int)o.getCenterX(), CHUNKSIZE);
        chunks[cy][cx].removeObject(o);
    }
    
    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0) return null;
        int cx = x / TILESINCHUNK;
        int cy = y / TILESINCHUNK;
        
        if(cx >= 0 && cx < chunks[0].length && cy >=0 && cy < chunks[1].length) { 
            return chunks[cy][cx].getTile(x%TILESINCHUNK, y%TILESINCHUNK);
        } else { 
            return null;
        }
    }
    
    public Tile getTile(MouseEvent e, World world) { 
        return world.getTileFromRealCords((int)getRealMouseX(e), (int)getRealMouseY(e));
    }
}
