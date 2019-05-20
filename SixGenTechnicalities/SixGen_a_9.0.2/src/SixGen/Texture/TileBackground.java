// Based on SixGenEngine version b1
// Created by SixKeyStudios
package SixGen.Texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Filip
 */
public class TileBackground extends Background{
    
    public TileBackground(BufferedImage texture ,int width , int height, int layer , float multy) { 
        super(texture, width, height, layer, multy);
    }
    public TileBackground(BufferedImage texture , int width , int height , int layer , float multyX , float multyY) { 
        super(texture , width , height , layer , multyX , multyY);
    }
    public void render(int x , int y , int cameraWidth , int cameraHeight , Graphics g) { 
        int ax = (int)((cameraWidth / width) + 2);
        int ay = (int)((cameraHeight / height) + 2);
        int multX = (int)(-(x * multyX) % width);
        int multY = (int)(-(y * multyY) % height);
        for(int xx = 0 ; xx < ax ; xx++) {
            for(int yy = 0 ; yy < ay ; yy++) { 
                g.drawImage(texture, (int)(multX + width * xx), (int)(multY + height*yy), width , height , null);
            }
        }
    }
}
