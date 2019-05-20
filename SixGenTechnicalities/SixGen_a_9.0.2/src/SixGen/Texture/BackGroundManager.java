// Based on SixGenEngine version b1
// Created by SixKeyStudios
package SixGen.Texture;

import SixGen.Window.Camera;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Filip
 */
public class BackGroundManager {
    private ArrayList<Background> bgs = new ArrayList<Background>();
    
    int maxLayer;
    
    public void render(Camera camera , Graphics g) { 
        int x = (int)camera.getX();
        int y = (int)camera.getY();
        int width = (int)camera.getWidth();
        int height = (int)camera.getHeight();
        for(int i = 0 ; i < maxLayer + 1 ; i++) {    
            for(Background bg : bgs) { 
                if(bg.getLayer() == i) {
                    bg.render(x , y , width , height , g);
                }
            }
        }
    }
    public void addBackground(Background bg) { 
        bgs.add(bg);
        if(bg.getLayer() > maxLayer) { 
            maxLayer = bg.getLayer();
        }
    }
    public void removeBackground(Background bg) { 
        bgs.remove(bg);
    }
}
