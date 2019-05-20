// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.Handler;

import SixGen.Window.Camera;
import SixGen.GameObject.GameObject;
import SixGen.Handler.Chunk;
import SixGen.Handler.Handler;
import SixGen.Handler.Handler.RenderType;
import SixGen.Utils.ID;
import SixGen.Window.SixCanvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * ChunkHandler
 * Abilities:
 *  Taking care of Chunks
 *      rendering the active
 *      ticking the active
 *      checking which one is active and which is not
 *      finding
 *          objects in them by 
 *              id 
 *              former
 *              bounds
 *              layer
 *          chunks by 
 *              chunkPoint
 *              bounds
 */

public class ChunkHandler extends Handler{
    
    //VARIABLES
    
    // List of all chunks. Theese chunks are just handled. The ones that are active *see activeChunks also tick and render
    protected LinkedList<Chunk> chunks;
    // This handler has special system of deciding which chunks are rendered and do tick *see visibleCheck()
    protected LinkedList<Chunk> activeChunks;
    // Chunk that is in the view of the camera and is recognized as center *see visibleCheck()
    protected Chunk centerChunk;
    // Dimensions of chunks and size of the grid that is used in spawning theese chunks
    protected int gridWidth , gridHeight;
    // Radius of chunks that is active. Center of the final circle of chunks is the ChunkHandler.centerChunk
    protected int activeChunksRadius;
    /** Switch that controls if the shape of the active chunk array is circle or square
    * circle    square      X- not active
    * XXXXXXX   XXXXXXX     O- active
    * XXXOXXX   XOOOOOX  
    * XXOOOXX   XOOOOOX
    * XOOOOOX   XOOOOOX
    * XXOOOXX   XOOOOOX
    * XXXOXXx   XXXXXXX
    */
    protected boolean activeChunksSquare;
    //layering rendering
    protected boolean layerRender;
    
    
    //CONSTRUCTORS
    
    public ChunkHandler(SixCanvas sixCanv , RenderType renderType , Camera camera) { 
        //@ChunkHandler
        super(sixCanv , renderType);
        //@ChunkHandler#constructorSetters
        this.camera = camera;
        //@ChunkHandler#defaultSetters
        chunks = new LinkedList<Chunk>();
        activeChunks = new LinkedList<Chunk>();
        activeChunksRadius = 2;
        gridWidth = 250;
        gridHeight = 250;
        activeChunksSquare = false;
    }
    public ChunkHandler(SixCanvas sixCanv , RenderType renderType , Camera camera , int gridWidth , int gridHeight) { 
        //@ChunkHandler
        super(sixCanv , renderType);
        //@ChunkHandler#constructorSetters
        this.camera = camera;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        //@ChunkHandler#defaultSetters
        chunks = new LinkedList<Chunk>();
        activeChunks = new LinkedList<Chunk>();
        activeChunksRadius = 2;
        activeChunksSquare = false;
    }
    @Override 
    public void visibleCheck() { 
        //@visibleCheck
        if(camera!=null && centerChunk!=null) {
            // if the centerChunk is not in the view of the camera than find new one else and generate active chunks 
            if(!centerChunk.getBounds().contains(camera.getCenterPoint())) {
                // try to find the new centerChunk in activeChunks
                for(int i = 0 ; i < activeChunks.size();i++) { 
                    Chunk temp = activeChunks.get(i);
                    if(temp.getBounds().contains(camera.getCenterPoint())) { 
                        centerChunk = temp;
                        generateActiveChunk();
                        return;
                    }
                }
                // if the centerChunk is not in activeChunks try all chunks
                for(int i = 0 ; i < chunks.size(); i++ ) { 
                    Chunk temp = chunks.get(i);
                    if(temp.getBounds().contains(camera.getCenterPoint())) { 
                        centerChunk = temp;
                        generateActiveChunk();
                        return;
                    }
                }
                centerChunk = null;
            }
        } else if(centerChunk == null) { 
            // if the centerChunk is null try to find brand new one
            centerChunk = findChunk(camera.getCenterPoint());
            generateActiveChunk();
        } else { 
            // the centerChunk still the same
            return;
        }
    } 
    public void generateActiveChunk() { 
        //@generateActiveChunk
        if(centerChunk!=null) {  
            //remove all activeChunks
            while(!activeChunks.isEmpty()) { 
                activeChunks.removeFirst();
            }
            int amount;
            // if the shape of array is square then
            if(activeChunksSquare) {
                // square
                for(int xx = 0 ; xx < activeChunksRadius * 2 - 1 ; xx++) { 
                    for(int yy = 0 ; yy < activeChunksRadius * 2 - 1;yy++ ) {
                        Chunk temp = findChunkInGrid(new Point(   (int)centerChunk.getChunkPoint().getX() + xx + 1 - activeChunksRadius , 
                                                            (int)centerChunk.getChunkPoint().getY() + yy + 1 - activeChunksRadius));
                        if(temp!=null) {
                            activeChunks.add(temp);
                        }
                    }
                }
            } else { 
                // circle
                for(int xx = 0 ; xx < activeChunksRadius * 2 - 1 ; xx++) { 
                    if(xx < activeChunksRadius) {
                        amount = xx * 2 + 1;
                    } else { 
                        amount = activeChunksRadius * 4 - xx * 2 - 3;
                    }
                    for(int yy = 0 ; yy < amount ; yy++ ) { 
                        Chunk temp = findChunkInGrid(new Point(   (int)centerChunk.getChunkPoint().getX() + xx + 1 - activeChunksRadius , 
                                                            (int)centerChunk.getChunkPoint().getY() + yy - amount / 2));
                        if(temp!=null) {
                            activeChunks.add(temp);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void render(Graphics2D g) { 
        //@render
        visibleCheck();
        for(int l = 0 ; l < maxLayer + 1 ; l++) { 
            for(int i = 0 ; i < activeChunks.size() ; i++) { 
                activeChunks.get(i).render(g , l);
            }
        }
    }
    @Override
    public void tick() { 
        //@tick
        for(Chunk ch : activeChunks) { 
            ch.tick();
        }
    }
    
    //GETTERS SETTERS
    
    public Chunk findChunk(Rectangle rect) { 
        for(int i = 0 ; i < chunks.size() ; i++) { 
            Chunk temp = chunks.get(i);
            if(temp.getBounds().intersects(rect)) { 
                return temp;
            }
        }
        return null;
    }
    public Chunk findOtherChunk(Chunk chunk , Rectangle rect) { 
        for(int i = 0 ; i < chunks.size() ; i++) { 
            Chunk temp = chunks.get(i);
            if(temp!=chunk & temp.getBounds().intersects(rect)) { 
                return temp;
            }
        }
        return null;
    }
    public Chunk findChunk(GameObject o) { 
        for(Chunk ch : activeChunks) { 
            if(ch.getBounds().intersects(o.getBounds())) { 
                return ch;
            }
        }
        return findChunk(o.getBounds());
    }
    public Chunk findChunk(Point point) { 
        for(int i = 0 ; i < chunks.size() ; i++) { 
            Chunk temp = chunks.get(i);
            if(temp.getBounds().contains(point)) { 
                return temp;
            }
        }
        return null;
    }
    public Chunk findChunkInGrid(Point point) { 
        for(int i = 0 ; i < chunks.size() ; i++) { 
            Chunk temp = chunks.get(i);
            if(temp.getChunkPoint().getX() == point.getX()&&temp.getChunkPoint().getY() == point.getY()) {
                return temp;
            }
        }
        return null;
    }
    @Override
    public void addObj(GameObject obj) { 
        obj.setHandled(true);
        if(obj.getLayer() > maxLayer) { 
            maxLayer = obj.getLayer();
        }
        Chunk temp = ChunkHandler.this.findChunk(obj.getBounds());
        if(temp!=null) {
            temp.addObj(obj);
//            System.out.printf("%d old %n" , counter);
            obj.setChunkPoint(temp.getChunkPoint());
        } else {
            int xx = (int)(Math.floor(obj.getCenterX()/gridWidth));
            int yy = (int)(Math.floor(obj.getCenterY()/gridHeight));
            Chunk chunk = new Chunk(this , xx * gridWidth, yy * gridHeight , gridWidth , gridHeight);
            chunk.setChunkPoint(new Point(xx , yy));
            chunks.add(chunk);
            chunk.addObj(obj);
//            System.out.printf("%d new %n", counter);
            obj.setChunkPoint(chunk.getChunkPoint());
            return; 
        }
    }
    @Override
    public void removeObj(GameObject obj) {
        if(isHandled(obj)) { 
            ChunkHandler.this.findChunk(obj.getBounds()).removeObj(obj);
            obj.setRenderSwitch(false);
            obj.setTickSwitch(false);
            obj.setHandled(false);
        }
    }
    
    public int getActiveChunksRadius() {
        return activeChunksRadius;
    }

    public void setActiveChunksRadius(int activeChunksRadius) {
        this.activeChunksRadius = activeChunksRadius;
    }

    public boolean isActiveChunksSquare() {
        return activeChunksSquare;
    }

    public void setActiveChunksSquare(boolean activeChunksSquare) {
        this.activeChunksSquare = activeChunksSquare;
        generateActiveChunk();
    }
    
    public void setCenterChunk(Chunk chunk) { 
        this.centerChunk = chunk;
        generateActiveChunk();
    }
    @Override 
    public Dimension getDimensionFromObjects() { 
        float minX = 0 , minY = 0 , maxX = 0 , maxY = 0;
        for(int i = 0 ; i < chunks.size(); i ++) { 
            Point temp[] = chunks.get(i).getDimensionFromObjects();
            if(i == 0) { 
                minX = (float)temp[0].getX();
                maxX = (float)temp[1].getX();
                minY = (float)temp[0].getY();
                maxY = (float)temp[1].getY();
            } else { 
                if(temp[0].getX() < minX) { 
                    minX = (float)temp[0].getX();
                }
                if(temp[1].getX() > maxX) { 
                    maxX = (float)temp[1].getX();
                }
                if(temp[0].getY() < minY) { 
                    minY = (float)temp[0].getY();
                }
                if(temp[1].getY() > maxY) {
                    maxY = (float)temp[1].getY();
                }
            }
        }
        return new Dimension((int)(maxX - minX),(int)(maxY - minY));
    }
    public Dimension getDimensionFromChunks() { 
        float minX = 0 , minY = 0 , maxX = 0 , maxY = 0;
        for(int i = 0 ; i < chunks.size(); i ++) { 
            Chunk temp = chunks.get(i);
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
        return new Dimension((int)(maxX - minX),(int)(maxY - minY));
    }
    @Override
    public GameObject[] getTickObjects() { 
        LinkedList<GameObject> tickObjects = new LinkedList<GameObject>();
        for(Chunk ch : activeChunks) { 
            for(GameObject o : ch.getObjects()) { 
                tickObjects.add(o);
            }
        }
        return tickObjects.toArray(new GameObject[tickObjects.size()]);
    }
    
    @Override
    public LinkedList<GameObject> getTickObjectsList() { 
        LinkedList<GameObject> tickObjects = new LinkedList<GameObject>();
        for(Chunk ch : activeChunks) { 
            for(GameObject o : ch.getObjects()) { 
                tickObjects.add(o);
            }
        }
        return tickObjects;
    }
    
//    @Override
//    public GameObject[] getRenderObjects() { 
//        LinkedList<GameObject> renderObjects = new LinkedList<GameObject>();
//        for(Chunk ch : activeChunks) { 
//            for(GameObject o : ch.getObjects()) { 
//                renderObjects.add(o);
//            }
//        }
//        return renderObjects.toArray(new GameObject[renderObjects.size()]);
//    }
//    
//    @Override
//    public LinkedList<GameObject> getRenderObjectsList() { 
//        LinkedList<GameObject> renderObjects = new LinkedList<GameObject>();
//        for(Chunk ch : activeChunks) { 
//            for(GameObject o : ch.getObjects()) { 
//                renderObjects.add(o);
//            }
//        }
//        return renderObjects;
//    }
    
    @Override
    public boolean isHandled(GameObject obj) { 
        for(Chunk ch : activeChunks) { 
            for(GameObject o : ch.getObjects()) { 
                if(obj == o) { 
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public GameObject findObjectViaID(ID id) {
        for (Chunk ch : chunks) {
            GameObject temp = ch.findObjectViaID(id);
            if (temp!=null) {
                return temp;
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaID(ID id) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < chunks.size(); i++) {
            GameObject temp[] = chunks.get(i).findObjectsViaID(id);
            for(int f = 0 ; f < temp.length;f++) { 
                resultList.add(temp[f]);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject findObjectViaFormer(ID id) {
        for (int i = 0; i < chunks.size(); i++) {
            GameObject temp = chunks.get(i).findObjectViaFormer(id);
            if (temp!=null) {
                return temp;
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaFormer(ID id) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < chunks.size(); i++) {
            GameObject temp[] = chunks.get(i).findObjectsViaFormer(id);
            for(int f = 0 ; f < temp.length ; f++) { 
                resultList.add(temp[f]);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject findObjectViaBounds(Rectangle rect) {
        for(Chunk chunk : chunks) { 
            if(chunk.getBounds().intersects(rect)) { 
                return chunk.findObjectViaBounds(rect);
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaBounds(Rectangle rect) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for(Chunk ch : chunks) { 
            if(ch.getBounds().intersects(rect)) {
                GameObject temp[] = ch.findObjectsViaBounds(rect);
                for(int f = 0; f < temp.length ; f++)  { 
                    resultList.add(temp[f]);
                }
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject findObjectViaPoint(Point point) {
        for(Chunk chunk : chunks) { 
            if(chunk.getBounds().contains(point)) { 
                return chunk.findObjectViaPoint(point);
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaPoint(Point point) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for(Chunk ch : chunks) { 
            if(ch.getBounds().contains(point)) {
                GameObject temp[] = ch.findObjectsViaPoint(point);
                for(int f = 0; f < temp.length ; f++)  { 
                    resultList.add(temp[f]);
                }
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject[] findOBjectsViaLayer(int layer) {
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < chunks.size(); i++) {
            GameObject temp[] = chunks.get(i).findOBjectsViaLayer(layer);
            for(int f = 0 ; f < temp.length ; f++) { 
                resultList.add(temp[f]);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    public int getChunksAmount() { 
        return chunks.size();
    }
    public int getActiveChunksAmount() { 
        return activeChunks.size();
    }
}
