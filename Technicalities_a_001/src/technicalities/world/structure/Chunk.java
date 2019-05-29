/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.structure / Layer
*  created on 20.5.2019 , 19:11:35 
 */
package technicalities.world.structure;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import technicalities.handler.TechHandler;
import technicalities.variables.globals.GlobalVariables;
import static technicalities.variables.globals.GlobalVariables.TILEHEIGHT;
import static technicalities.variables.globals.GlobalVariables.TILEWIDTH;
import technicalities.world.objects.TObject;
import technicalities.world.objects.standable.Standable;

/**
 * Chunk
 * 
 * - contains all the tiles in one chunk
 * 
 * @author filip
 */
public class Chunk implements GlobalVariables{
    
    ////// VARIABLES //////
    
    public Tile[][] tiles;
    public ArrayList<TObject> objects;
    
    public int width, height;
    //chunks cordinates in global chunks arrray
    public int cx, cy;
    public TechHandler handler;
    
    ////// CONSTRUCTORS //////
    
    public Chunk(TechHandler handler, int cx, int cy, int width, int height) { 
        this.width = width;
        this.height = height;
        this.cx = cx;
        this.cy = cy;
        this.handler = handler;
        
        tiles = new Tile[height][];
        for(int i = 0; i < tiles.length; i++) { 
            tiles[i] = new Tile[width];
        }
        objects = new ArrayList<>();
    }
    
    /**
     * rendering tiles 
     * @param g 
     */
    public void render(Graphics2D g) { 
        //rendering visible tiles
        for(int y = 0; y < height; y++) { 
            for(int x = 0; x < width; x++) { 
                Tile t = tiles[y][x];
                //rendering tile
                g.setColor(t.color);
                g.fillRect(x * TILEWIDTH + cx * CHUNKSIZE, y * TILEHEIGHT + cy * CHUNKSIZE, TILEWIDTH, TILEHEIGHT);
            }
        }
    }
    
    /**
     * rendering objects and standable
     * @param g 
     */
    public void frontRender(Graphics2D g) { 
        objects.sort(new Comparator<TObject>() { 
            @Override
            public int compare(TObject o1, TObject o2) {
                return o1.getCenterY() < o2.getCenterY() ? -1 : 1;
            }
        });
        for(int y = 0; y < height; y++) { 
            for(int i = 0; i < objects.size(); i++) { 
                TObject temp = objects.get(i);
                if(Math.floorDiv(((int)temp.getCenterY() + TILEHEIGHT / 2 )%CHUNKSIZE, TILEHEIGHT) == y) {
                    temp.render(g);
                }
            }
            //rendering standable 
            for(int x = 0; x < width; x++) { 
                Tile t = tiles[y][x];
                Standable s = t.getStandable();
                if(s!=null) { 
                    BufferedImage tex = s.getTexture();
                    if(tex!=null) { 
                        g.drawImage(tex, x * TILEWIDTH + cx * CHUNKSIZE + TILEWIDTH / 2 - tex.getWidth() / 2, y * TILEHEIGHT + cy * CHUNKSIZE + TILEHEIGHT / 2 - tex.getHeight(), null);
                    } else { 
                        g.setColor(Color.red);
                        g.fillRect(x * TILEWIDTH + cx * CHUNKSIZE + 10, y * TILEHEIGHT + cy * CHUNKSIZE + 10, TILEWIDTH - 20, TILEHEIGHT -20);
                    }
                    if(s.getTUI()!=null) { 
                        s.getTUI().render(g);
                    }
                }
            }
        }
    }
    
    public void tick() { 
        for(int i = 0; i < objects.size(); i++) { 
            TObject temp = objects.get(i);
            temp.tick();
            if(Math.floorDiv((int)temp.getCenterX(), CHUNKSIZE) != cx || Math.floorDiv((int)temp.getCenterY(), CHUNKSIZE) != cy) { 
                this.removeObject(temp);
                handler.addObject(temp);
            }
        }
    }
    
    ////// GETTERS SETTERS //////
    
    public Tile getTile(int x, int y) { 
        return tiles[y][x];
    }
    
    public void setTile(int x, int y, Tile tile) { 
        tiles[y][x] = tile;
    }
    
    public void addObject(TObject o) { 
        objects.add(o);
    }
    public void removeObject(TObject o) { 
        objects.remove(o);
    }
    
}
