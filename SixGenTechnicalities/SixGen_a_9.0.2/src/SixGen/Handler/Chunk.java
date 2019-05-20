// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.Handler;

import SixGen.GameObject.GameObject;
import SixGen.Handler.Handler.RenderType;
import SixGen.Utils.ID;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * Chunk
 * Abilities:
 *  Chunk is used in more complicated handler(ChunkHandler)
 *  Chunk takes care of objects that are in his bounds
 *      rendering
 *      ticking
 *      finding by 
 *          id 
 *          former
 *          bounds
 *          layer
 */

public class Chunk {
    
    //VARIABLES
    
    // List of all objects that are in the chunk all of theese objects do tick and render
    protected LinkedList<GameObject> objects = new LinkedList<GameObject>();
    // Point has information about chunk's position in the grid not it's real x and y 
    protected Point chunkPoint;
    // Real position of the chunk in x and y
    protected int x , y ;
    // Real dimensions of the chunk
    protected int width , height;
    
    // ChunkHandler that is handling this chunk
    protected ChunkHandler handler;
    // Type of rendering that this chunk uses. See RenderType description for more info
    protected RenderType renderType;
    
    //CONSTRUCTORS
    
    public Chunk(ChunkHandler handler , int x , int y , int width , int height) { 
        //@Chunk
        //@Chunk#constructorSetters
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    //VOIDS
    
    public void tick() { 
        //@tick
//        System.out.println(objects.size());
        LinkedList<GameObject> b = new LinkedList<GameObject>();
        for(GameObject temp : objects) { 
            //if the object is in the chunk then tick , if not than remove the object
            if(temp.getBounds().intersects(this.getBounds())) { 
                temp.tick();
            } else {
                temp.tick();
                Chunk chunk = handler.findChunk(temp.getBounds());
                if(chunk!=null && chunk!=this) {
                    chunk.addObj(temp);
                    b.add(temp);
                }
            }
        }
        for(GameObject o : b) { 
            objects.remove(o);
        }
    }
    public void render(Graphics2D g) { 
        //@render
        for(GameObject o : objects) {
            o.render(g);
        }
    }
    public void render(Graphics2D g , int layer) { 
        //@render
        for(GameObject o : objects) { 
            if(o.getLayer() == layer) { 
                o.render(g);
            }
        }
    }
    
    //SETTERS GETTERS
    
    public Rectangle getBounds() { 
        return new Rectangle((int)x  , (int) y , (int) width , (int) height);
    }
    
    public void addObj(GameObject obj) { 
        objects.add(obj);
    }
    public void removeObj(GameObject obj) {
        objects.remove(obj);
    }
    public GameObject[] getObjects() { 
        return objects.toArray(new GameObject[objects.size()]);
    }
    public Point getChunkPoint() { 
        return chunkPoint;
    }
    public void setChunkPoint(Point point) {
        this.chunkPoint = point;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public void setRenderType(RenderType renderType) {
        this.renderType = renderType;
    }
    
    public Point[] getDimensionFromObjects() { 
        float minX = 0 , minY = 0 , maxX = 0 , maxY = 0;
        for(int i = 0 ; i < objects.size(); i ++) { 
            GameObject temp = objects.get(i);
            if(i == 0) { 
                minX = temp.getX();
                maxX = temp.getX() + temp.getWidth();
                minY = temp.getY();
                maxY = temp.getY() + temp.getHeight();
            } else { 
                if(temp.getX() < minX) { 
                    minX = temp.getX();
                }
                if(temp.getX() + temp.getWidth() > maxX) { 
                    maxX = temp.getX() + temp.getWidth();
                }
                if(temp.getY() < minY) { 
                    minY = temp.getY();
                }
                if(temp.getY() + temp.getHeight() > maxY) {
                    maxY = temp.getY() + temp.getHeight();
                }
            }
        }
        Point[] result = {new Point((int)minX , (int)minY) , new Point((int)maxX , (int) maxY)};
        return result; 
    }
    
    public GameObject findObjectViaID(ID id) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getId() == id) {
                return temp;
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaID(ID id) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getId() == id) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject findObjectViaFormer(ID id) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getFormer() == id) {
                return temp;
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaFormer(ID id) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getFormer() == id) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject findObjectViaBounds(Rectangle rect) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getBounds().intersects(rect)) {
                return temp;
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaBounds(Rectangle rect) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getBounds().intersects(rect)) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject findObjectViaPoint(Point point) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getBounds().contains(point)) {
                return temp;
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaPoint(Point point) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getBounds().contains(point)) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject[] findOBjectsViaLayer(int layer) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getLayer() == layer) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
}
