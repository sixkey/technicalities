package SixGen.Handler;

import SixGen.Utils.Utils;
import SixGen.Window.SixCanvas;
import SixGen.SixUI.SixUI;
import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.Window.Camera;
import SixGen.GameObject.GameObject;
import SixGen.Handler.Managers.HandlerManager;
import SixGen.Utils.ID;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;

/**
* Handler
* Abilities:
*   Takes care of objects
*       makes them tick and renders them
*       can find them by ID , Former , Bounds
*/

public class Handler extends Utils {

    //VARIABLES
   
    // canvas that is parent of this handler
    protected SixCanvas sixCanv;
    // list of all the objects that handler is taking care of
    protected LinkedList<GameObject>objects;
    
    protected ArrayList<HandlerLayer> layers;
    // list of objects that will be ticked
    protected LinkedList<GameObject>tickObjects;
    
    protected HandlerManager[] managers;
    // list of UIs that will be render on the top of everyThing
    protected LinkedList<SixUI> sixUIList;
    // amount of layers
    protected int maxLayer = 0;
    // amount of layers
    protected int minLayer = 0;
    // camera that is used in cropping the canvas by not rendering the objects that don't intersetcs the bounds of the camera
    protected Camera camera;
    // object that is used as the center point in the cordinate system
    protected GameObject corObj;
    // cor shows if the cordinate system will change in the next tick
    protected boolean cor;
    // render is boolean that controls if this handler will render the objects and the UI
    protected boolean render = true;
    // tick is boolean that controls if this handler will make the objects tick
    protected boolean tick = true;
    // renderType is type of the rendering that this handler will use *see RenderType for more info
    protected RenderType renderType;
    // radius of the space around of the camera that will be also rendered
    protected int cameraSafeSpace = 50;

    public enum RenderType {
        iso, front, top;
    }
    
    //CONSTRUCTORS
    
    public Handler(SixCanvas sixCanv, RenderType renderType) {
        //@Handler
        //@Handler#constructorSetters
        this.sixCanv = sixCanv;
        this.renderType = renderType;
        //@Handler#listsInitialization
        objects = new LinkedList<GameObject>();
        layers = new ArrayList<HandlerLayer>();
        tickObjects = new LinkedList<GameObject>();
        sixUIList = new LinkedList<SixUI>();
    }
    //RENDERING VOIDS 
    
    public void render(Graphics2D g) {
        //@render
        if(render) { 
            visibleCheck();
            if (renderType == RenderType.front) {
                frontRender(g);
            } else if (renderType == RenderType.iso) {
                isoRender(g);
            } else if (renderType == RenderType.top) {
                topRender(g);
            }
        }
    }

    public void frontRender(Graphics2D g) {
        //@frontRender
        layers.forEach((l) -> { 
            l.render(g);
        });
    }

    public void isoRender(Graphics2D g) {
        //@isoRender
        System.out.println("UNSUPORTED : @Handler.isoRender(Graphics2D g)");
    }

    public void topRender(Graphics2D g) {
        //@topRender
        System.out.println("UNSUPORTED : @Handler.topRender(Graphics2D g)");
    }

    public void visibleCheck() {
        //@visibleCheck
        if (camera != null) {
            for (int i = 0; i < objects.size(); i++) {
                GameObject temp = objects.get(i);
                if (temp.getBounds().intersects(getCameraBounds(camera))) {
                    if (temp.isRenderSwitch() == false) {
                        addRenderObject(temp);
                    }
                } else if (temp.isRenderSwitch() == true) {
                    removeRenderObject(temp);
                }
            }
        }
    }
    
    //TICK VOIDS
    
    public void tick() {
        //@tick
        if(tick) { 
            GameObject[] to = getTickObjects();
            for (GameObject o : to) {
                o.tickBase();
            }
            if(cor) { 
                if(corObj!=null) { 
                    setCordinateSystemRefrenceMethod(corObj);
                } 
            }
            if(managers!=null) {
                for(HandlerManager m : managers) { 
                    m.tick(to);
                }
            }
        }
        GUITick();
    }
    
    //GUI VOIDS
    
    public void GUIRender(Graphics2D g) {
        for (int i = 0; i < sixUIList.size(); i++) {
            sixUIList.get(i).render(g);
        }
    }

    public void GUITick() {
        for (int i = 0; i < sixUIList.size(); i++) {
            sixUIList.get(i).tick();
        }
    }
    
    //USER INPUT VOIDS
   
    public boolean mouseAction(MouseEvent e , MouseActionType mouseActionType) { 
        boolean result = false;
        for(int i = 0 ; i < sixUIList.size();i++) {
            if(sixUIList.get(i).mouseAction(e, mouseActionType)) {
                result = true;
            }
        }
        return result;
    }
    
    //VOIDS
    
    public void reset() {
        try {
            Thread.sleep(1000);
            layers = new ArrayList<HandlerLayer>();
            tickObjects = new LinkedList<GameObject>();
            objects = new LinkedList<GameObject>();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    //FINDING GAMEOBJECT VOIDS
    
    public GameObject findObjectViaID(ID id) {
        //@findObjectViaID
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getId() == id) {
                return temp;
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaID(ID id) {
        //@findObjectsViaID
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getId() == id) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject findObjectViaFormer(ID former) {
        //@findObjectViaFormer
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getFormer() == former) {
                return temp;
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaFormer(ID former) {
        //@findObjectsViaFormer
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getFormer() == former) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject findObjectViaBounds(Rectangle rect) {
        //@findObjectsViaBounds
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getBounds().intersects(rect)) {
                return temp;
            }
        }
        return null;
    }

    public GameObject[] findObjectsViaBounds(Rectangle rect) {
        //@findObjectsViaBounds
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
        //@findObjectsViaBounds
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getBounds().contains(point)) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject[] findObjectsInColumn(float x , float xx) { 
        //@findObjectsInColumn
        Rectangle rect = new Rectangle((int) x , 0 ,(int) (int)(xx -  x) , sixCanv.getHeight());
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getBounds().intersects(rect)) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    public GameObject[] findObjectsViaLayer(int layer) {
        //@findObectsViaLayer
        LinkedList<GameObject> resultList = new LinkedList<GameObject>();
        for (int i = 0; i < objects.size(); i++) {
            GameObject temp = objects.get(i);
            if (temp.getLayer() == layer) {
                resultList.add(temp);
            }
        }
        return resultList.toArray(new GameObject[resultList.size()]);
    }
    
    public GameObject[] findObjectViaLine(Line2D.Double line) { 
        Rectangle rect = new Rectangle(0 , 0 , (int)Math.abs(line.getX1() - line.getX2()) , (int)Math.abs(line.getY1() - line.getY2()));
        if(line.getX1() < line.getX2()) { 
            rect.setLocation((int)line.getX1(), 0);
        } else { 
            rect.setLocation((int)line.getX2(), 0);
        }
        if(line.getY1() < line.getY2()) { 
            rect.setLocation((int)rect.getX(), (int)line.getY1());
        } else { 
            rect.setLocation((int)rect.getX(), (int)line.getY2());
        }
        
        LinkedList<GameObject> result = new LinkedList<GameObject>();
        GameObject objs[] = findObjectsViaBounds(rect);
        for(GameObject obj : objs) { 
            if(objLineColl(obj , line)) {
                result.add(obj);
            } 
        }
        return result.toArray(new GameObject[result.size()]);
    }
    
    public GameObject[] findObjectViaLine(Line2D.Double line , GameObject exc[]) { 
        Rectangle rect = new Rectangle(0 , 0 , (int)Math.abs(line.getX1() - line.getX2()) , (int)Math.abs(line.getY1() - line.getY2()));
                    
        if(line.getX1() < line.getX2()) { 
            rect.setLocation((int)line.getX1(), 0);
        } else { 
            rect.setLocation((int)line.getX2(), 0);
        }
        if(line.getY1() < line.getY2()) { 
            rect.setLocation((int)rect.getX(), (int)line.getY1());
        } else { 
            rect.setLocation((int)rect.getX(), (int)line.getY2());
        }
        
        LinkedList<GameObject> result = new LinkedList<GameObject>();
        GameObject objs[] = findObjectsViaBounds(rect);
        for(GameObject obj : objs) { 
            boolean ex = false;
            for(GameObject e : exc) { 
                if(e == obj) { 
                    ex = true;
                }
            }
            if(!ex) {
                if(objLineColl(obj , line)) {
                    result.add(obj);
                } 
            }
        }
        return result.toArray(new GameObject[result.size()]);
    }
    
    //FREE LINE VOIDS
    
    public boolean freeLine(Line2D.Double line) { 
        Rectangle rect = new Rectangle(0 , 0 , (int)Math.abs(line.getX1() - line.getX2()) , (int)Math.abs(line.getY1() - line.getY2()));
        if(line.getX1() < line.getX2()) { 
            rect.setLocation((int)line.getX1(), 0);
        } else { 
            rect.setLocation((int)line.getX2(), 0);
        }
        if(line.getY1() < line.getY2()) { 
            rect.setLocation((int)rect.getX(), (int)line.getY1());
        } else { 
            rect.setLocation((int)rect.getX(), (int)line.getY2());
        }
        
        GameObject objs[] = findObjectsViaBounds(rect);
        for(GameObject obj : objs) { 
            if(objLineColl(obj , line)) {
                return false;
            } 
        }
        return true;
    }
    
    public boolean freeLine(Line2D.Double line , GameObject exceptions[]) { 
        Rectangle rect = new Rectangle(0 , 0 , (int)Math.abs(line.getX1() - line.getX2()) , (int)Math.abs(line.getY1() - line.getY2()));
        if(line.getX1() < line.getX2()) { 
            rect.setLocation((int)line.getX1(), 0);
        } else { 
            rect.setLocation((int)line.getX2(), 0);
        }
        if(line.getY1() < line.getY2()) { 
            rect.setLocation((int)rect.getX(), (int)line.getY1());
        } else { 
            rect.setLocation((int)rect.getX(), (int)line.getY2());
        }
        
        GameObject objs[] = findObjectsViaBounds(rect);
        for(GameObject obj : objs) { 
            boolean ex = false;
            for(GameObject e : exceptions) { 
                 if(e == obj) { 
                     ex = true;
                 }
            }
            if(!ex) {
                if(objLineColl(obj , line)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean freeLine(Line2D.Double line , GameObject exceptions[] , ID[] idExceptions) { 
        Rectangle rect = new Rectangle(0 , 0 , (int)Math.abs(line.getX1() - line.getX2()) , (int)Math.abs(line.getY1() - line.getY2()));
        if(line.getX1() < line.getX2()) { 
            rect.setLocation((int)line.getX1(), 0);
        } else { 
            rect.setLocation((int)line.getX2(), 0);
        }
        if(line.getY1() < line.getY2()) { 
            rect.setLocation((int)rect.getX(), (int)line.getY1());
        } else { 
            rect.setLocation((int)rect.getX(), (int)line.getY2());
        }
        
        GameObject objs[] = findObjectsViaBounds(rect);
        for(GameObject obj : objs) { 
            boolean ex = false;
            for(GameObject e : exceptions) { 
                if(e == obj) { 
                    ex = true;
                }
            }
            for(ID i : idExceptions) { 
                if(obj.getId() == i) { 
                    ex = true;
                }
            }
            if(!ex) {
                if(objLineColl(obj , line)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean freeLine(Line2D.Double line , ID[] idExceptions) { 
        Rectangle rect = new Rectangle(0 , 0 , (int)Math.abs(line.getX1() - line.getX2()) , (int)Math.abs(line.getY1() - line.getY2()));
        if(line.getX1() < line.getX2()) { 
            rect.setLocation((int)line.getX1(), 0);
        } else { 
            rect.setLocation((int)line.getX2(), 0);
        }
        if(line.getY1() < line.getY2()) { 
            rect.setLocation((int)rect.getX(), (int)line.getY1());
        } else { 
            rect.setLocation((int)rect.getX(), (int)line.getY2());
        }
        
        GameObject objs[] = findObjectsViaBounds(rect);
        for(GameObject obj : objs) { 
            boolean ex = false;
            for(ID i : idExceptions) { 
                if(obj.getId() == i) { 
                    ex = true;
                }
            }
            if(!ex) {
                if(objLineColl(obj , line)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean objLineColl(GameObject obj , Line2D.Double line) { 
        
        float rX = obj.getX();
        float rY = obj.getY();
        float rXX = obj.getXX();
        float rYY = obj.getYY();
        
        if(obj.isBounds(Direction.down)) {
            if(getLineIntersection( line,
                                    new Line2D.Double(rX , rYY , rXX , rYY))
                                    !=null) { 
                return true;
            }
        }
        if(obj.isBounds(Direction.up)) {
            if(getLineIntersection( line,
                                    new Line2D.Double(rX, rY , rXX , rY))
                                    !=null) { 
                return true;
            }
        }
        if(obj.isBounds(Direction.left)) { 
            if(getLineIntersection( line,
                                    new Line2D.Double(rX , rY , rX , rYY))
                                    !=null) {
                return true;
            }
        }
        if(obj.isBounds(Direction.right)) { 
            if(getLineIntersection( line,
                                    new Line2D.Double(rXX , rY , rXX , rYY))
                                    !=null) { 
                return true;
            }
        }
        return false;
    }
    
    //FINDING UI VOIDS
    
    public SixUI findUIViaID(ID id) { 
        //findUIViaID
        for(int i = 0 ; i < sixUIList.size() ;i++) { 
            SixUI temp = sixUIList.get(i);
            if(temp.getId() == id) {
                return temp;
            }
        }
        return null;
    }
    
    //GETTERS SETTERS
    
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public Rectangle getCameraBounds(Camera camera) {
        int x = (int) (camera.getX() - cameraSafeSpace);
        int y = (int) (camera.getY() - cameraSafeSpace);
        int width = (int) (camera.getWidth() + cameraSafeSpace * 2);
        int height = (int) (camera.getHeight() + cameraSafeSpace * 2);
//		System.out.printf("%d  %d  %d  %d%n" , x , y , width , height);
        return new Rectangle(x, y, width, height);
    }

    public LinkedList<GameObject> getTickObjectsList() {
        return tickObjects;
    }

    public GameObject[] getTickObjects() {
        return tickObjects.toArray(new GameObject[tickObjects.size()]);
    }
    
    public LinkedList<GameObject> getObjectsList() {
        return objects;
    }

    public GameObject[] getObjects() {
        return objects.toArray(new GameObject[objects.size()]);
    }
    
    public void addObj(GameObject obj) {
        if (obj != null) {
            objects.add(obj);
            obj.setHandled(true);
            addRenderObject(obj);
            tickObjects.add(obj);
            obj.setRenderSwitch(true);
            obj.setTickSwitch(true);
        }
    }
    
    public void addObj(GameObject obj, boolean render, boolean tick) {
        if (obj != null) {
            objects.add(obj);
            obj.setLayer(0);
            obj.setHandled(true);

            if (render) {
                obj.setRenderSwitch(true);
                addRenderObject(obj);
            }
            if (tick) {
                obj.setTickSwitch(true);
                tickObjects.add(obj);
            }
        }
    }

    public void addObj(GameObject obj, int layer, boolean render, boolean tick) {
        if (layer > 0 && obj != null) {
            objects.add(obj);
            if (render) {
                obj.setRenderSwitch(true);
                addRenderObject(obj);
            }
            if (tick) {
                tickObjects.add(obj);
                obj.setTickSwitch(true);
            }
            if (layer > maxLayer) {
                maxLayer = layer;
            }
            if(layer < minLayer) { 
                minLayer = layer;
            }
            obj.setLayer(layer);
            obj.setHandled(true);
        }
    }

    private void addRenderObject(GameObject obj) {
        if (!obj.isRenderSwitch()) {
            int layer = obj.getLayer();
            for(int l = 0; l < layers.size() ; l++) { 
                if(layers.get(l).getLayer() == layer) { 
                    addToLayer(obj, l);
                    return;
                }
            }
            initLayer(layer);
            addRenderObject(obj);
        }
    }
    
    private void initLayer(int l) {
        HandlerLayer layer = new HandlerLayer(l);
        layers.add(layer);
        sortLayers();
    }
    
    private void sortLayers() { 
        HandlerLayer[] array = layers.toArray(new HandlerLayer[layers.size()]);
        quickSort(array,0,array.length);
        
        //setting layers
        layers = new ArrayList<HandlerLayer>();
        for(int i = 0 ; i < array.length; i++) { 
            layers.add(array[i]);
        }
    }
    
    private void quickSort(HandlerLayer[] array, int left, int right) {
        if(right > left + 1) {
            HandlerLayer pivot = array[right - 1];
            int counter = left;
            for(int i = left; i < right - 1; i++) { 
                if(array[i].getLayer() <= pivot.getLayer()) { 
                    swap(array, i, counter);
                    counter++;
                }
            }
            swap(array, counter, right - 1);
            quickSort(array, left, counter);
            quickSort(array, counter, right);
        } 
    }
    
    private void swap(HandlerLayer array[], int i1, int i2) { 
        HandlerLayer temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }
    
    private void addToLayer(GameObject obj, int layer) { 
        layers.get(layer).addObject(obj);
        obj.setRenderSwitch(true);
    }

    public void removeRenderObject(GameObject obj) {
        if (obj.isRenderSwitch()) {
            int layer = obj.getLayer();
            for(int l = 0; l < layers.size() ; l++) { 
                if(layers.get(l).getLayer() == layer) { 
                    layers.get(l).removeObject(obj);
                    obj.setRenderSwitch(false);
                    return;
                }
            }
        }
    }

    private void addTickObject(GameObject obj) {
        if (!obj.isTickSwitch()) {
            tickObjects.add(obj);
            obj.setTickSwitch(true);
        }
    }

    private void removeTickObject(GameObject obj) {
        if (obj.isTickSwitch()) {
            tickObjects.remove(obj);
            obj.setTickSwitch(false);
        }
    }

    public void addSixUI(SixUI sixUi) {
        sixUIList.add(sixUi);
    }

    public void removeSixUI(SixUI sixUi) {
        sixUIList.remove(sixUi);
    }

    public void removeObj(GameObject obj) {
        if (obj != null) {
            objects.remove(obj);
            removeRenderObject(obj);
            tickObjects.remove(obj);
            obj.setRenderSwitch(false);
            obj.setTickSwitch(false);
            obj.setHandled(false);
        }
    }

    public void removeObj(GameObject obj, boolean objects, boolean render, boolean tick) {
        if (obj != null) {
            if (objects) {
                this.objects.remove(obj);
            }
            if (render) {
                removeRenderObject(obj);
                obj.setRenderSwitch(false);
            }
            if (tick) {
                tickObjects.remove(obj);
                obj.setTickSwitch(false);
            }
        }
    }

    public boolean isHandled(GameObject temp) { 
        for(int i = 0 ; i < objects.size() ;i++) { 
            if(objects.get(i) == temp) { 
                return true;
            }
        }
        return false;
    }
    
    public RenderType getRenderType() { 
        return renderType;
    }
    public void setRenderType(RenderType renderType) { 
        this.renderType = renderType;
    }
    public Dimension getDimensionFromObjects() { 
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
        return new Dimension((int)(maxX - minX),(int)(maxY - minY));
    }
    public void setCordinateSystemRefrence(GameObject object) { 
        cor = true;
        corObj = object;
    }
    public void setCordinateSystemRefrenceMethod(GameObject object) { 
        sixCanv.setTick(false);
        float velXDiff = object.getVelX();
        float velYDiff = object.getVelY();
        for(int i = 0 ; i < objects.size() ;i++) { 
            GameObject temp = objects.get(i);
            temp.setVelX(temp.getVelX() - velXDiff);
            temp.setVelY(temp.getVelY() - velYDiff);
        }
        cor = false;
        sixCanv.setTick(true);
    }

    public int getMaxLayer() {
        return maxLayer;
    }

    public void setMaxLayer(int maxLayer) {
        this.maxLayer = maxLayer;
    }

    public int getMinLayer() {
        return minLayer;
    }

    public void setMinLayer(int minLayer) {
        this.minLayer = minLayer;
    }
     public int getMouseX(MouseEvent e) { 
        return e.getX();
    }
    public int getMouseY(MouseEvent e) { 
        return e.getY();
    }
    public int getRealMouseX(int mouseX) { 
         if(camera!=null) { 
            return (int)camera.getX() + mouseX;
        } else { 
            return mouseX;
        }
    }
     public int getRealMouseY(int mouseY) { 
         if(camera!=null) { 
            return (int)camera.getY() + mouseY;
        } else { 
            return mouseY;
        }
    }
    public Point getRealPoint(MouseEvent e) { 
        if(camera!=null) { 
            return new Point((int)(camera.getX() + e.getX()) , (int)(camera.getY() + e.getY()));
        } else { 
            return new Point((int)(e.getX()) , (int)(e.getY()));
        }
    }
    public float getRealMouseX(MouseEvent e) { 
        if(camera!=null) { 
            return camera.getX() + e.getX();
        } else { 
            return e.getX();
        }
    }
    public float getRealMouseY(MouseEvent e) { 
        if(camera!=null) { 
            return camera.getY() + e.getY();
        } else { 
            return e.getY();
        }
    }
    public Rectangle getMouseBounds(MouseEvent e) { 
        return new Rectangle(e.getX() - 1 , e.getY() - 1 , 3 , 3);
    }
    
    public Rectangle getRealMouseBounds(MouseEvent e) { 
        if(camera!=null) { 
            float x = getRealMouseX(e);
            float y = getRealMouseY(e);
            return new Rectangle((int)x - 1 , (int)y - 1 , 3 , 3);
        } else { 
            return new Rectangle(e.getX() - 1 , e.getY() - 1 , 3 , 3);
        }
    }
    public void setManagers(HandlerManager[] managers) { 
        this.managers = managers;
    }
    
}
