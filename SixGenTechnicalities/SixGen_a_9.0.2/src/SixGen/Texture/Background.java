// Based on SixGenEngine version b1
// Created by SixKeyStudios
package SixGen.Texture;

import SixGen.SixUI.Animation.Animation;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Filip
 */
public class Background{
    
    public BufferedImage texture;
    public Animation animation;
    public int layer;
    public float multyX;
    public float multyY;
    public int width;
    public int height;
    
    public Background(BufferedImage texture ,int width , int height, int layer , float multy) { 
        this(texture , width , height , layer , multy , multy);
    }
    public Background(BufferedImage texture , int width , int height , int layer , float multyX , float multyY) { 
        this.texture = texture;
        this.layer = layer;
        this.multyX = multyX;
        this.multyY = multyY;
        this.width = width;
        this.height = height;
    }
    public void render(int x , int y , int width , int height, Graphics g) {
        if(animation!=null) { 
            animation.render(g, new Rectangle((int)(x * multyX) , (int)(y * multyY), width , height));
        } else {
            g.drawImage(texture, (int)(x * multyX) , (int)(y * multyY), width , height , null);
        }
    }
   
    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public float getMultyX() {
        return multyX;
    }

    public void setMultyX(float multy) {
        this.multyX = multy;
    }

    public float getMultyY() {
        return multyY;
    }

    public void setMultyY(float multyY) {
        this.multyY = multyY;
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
    
    
}
