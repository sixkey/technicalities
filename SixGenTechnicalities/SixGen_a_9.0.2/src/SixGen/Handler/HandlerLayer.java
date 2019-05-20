/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SixGen.Handler;

import SixGen.GameObject.GameObject;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author filip
 */
public class HandlerLayer {
    
    private ArrayList<GameObject> objects;
    private int layer;
            
    public HandlerLayer(int layer) { 
        this.layer = layer;
        objects = new ArrayList<GameObject>();
    }
    
    public void render(Graphics2D g) { 
        objects.forEach((o) -> { 
            o.render(g);
        });
    }
    
    public void addObject(GameObject obj) { 
        objects.add(obj);
    }
    public void removeObject(GameObject obj) { 
        objects.remove(obj);
    }
    
    public int getLayer() { 
        return layer;
    }
    @Override
    public String toString() { 
        return String.valueOf(layer);
    }
}
