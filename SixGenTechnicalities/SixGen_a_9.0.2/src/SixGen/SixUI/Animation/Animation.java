// Based on SixGenEngine version b1
// Created by SixKeyStudios
package SixGen.SixUI.Animation;

import SixGen.Utils.Utils;
import SixGen.Utils.Utils.Direction;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Filip
 */
/**
* Animation
* Abilities:
*  Holds all frames of the animation
*  Renders theese frames in framerate that you put ins
*      frame rate is not in fps but in rpf so renders per frame
*       if the framerate is 1 then the frame will be rendered for one renders
*       if the framerate is 2 then the frame will be rendered for two renders 
*/
public class Animation extends Utils {

    // arrya of frames that are then used in animatino
    protected  BufferedImage[] frames;
    // counter counts the renderes between frames
    protected  int counter;
    // this counter count's in whitch part of the animation it currently is
    protected  int frameCounter;
    // number of renders that the image will be displayed in
    protected int frameLength;
    // number of frames in the animation
    protected int animationLength;

    public Animation(BufferedImage[] frames, int frameLength) {
        //@Animation
        //@Animation#constructorSetters
        animationLength = frames.length;
        this.frames = frames;
        this.frameLength = frameLength;
    }

    public void render(Graphics g , Rectangle bounds) {
        //@render
        counter++;
        if (counter % frameLength == 0) {
            frameCounter++;
        }
        if (frameCounter >= animationLength) {
            frameCounter = 0;
            counter = 0;
        }
        drawImage(g , frames[frameCounter], bounds);
    }
    
    public void render(Graphics g , Rectangle bounds , Direction dir) { 
        counter++;
        if (counter % frameLength == 0) {
            frameCounter++;
        }
        if (frameCounter >= animationLength) {
            frameCounter = 0;
            counter = 0;
        }
        if(dir == Direction.right) { 
            drawImage(g , frames[frameCounter], bounds);
        } else if(dir == Direction.left) { 
            drawImage(g , getHorSwapedImage(frames[frameCounter]) , bounds);
        }
    }
    
    public Animation flipFramesX() { 
        for(int i = 0 ; i < frames.length; i++) { 
            frames[i] = flipX(frames[i]);
        }
        return this;
    }
    
    public BufferedImage flipX(BufferedImage image) { 
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1));
        at.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));
        BufferedImage newImage = new BufferedImage(
            image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public int getFrameCounter() {
        return frameCounter;
    }

    public void setFrameCounter(int frameCounter) {
        this.frameCounter = frameCounter;
    }

    public int getAnimationLength() {
        return animationLength;
    }

    public void setAnimationLength(int animationLength) {
        this.animationLength = animationLength;
    }
    
    public BufferedImage getFrame(int i) { 
        return frames[i];
    }
    
    public BufferedImage[] getFrames() { 
        return frames;
    }
    
    public int getFrameLength() { 
        return frameLength;
    }
    
}
