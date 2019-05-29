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
import technicalities.world.structure.Chunk;
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
        // getting coridnates 
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
                chunks[y][x].render(g);
            }
        }
        //rendering objects and standables
        for(int y = yb; y < ye; y++) { 
            for(int x = xb; x < xe; x++) { 
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
        System.out.println(cy + " " + cx);
        
        chunks[cy][cx].addObject(o);
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
}
