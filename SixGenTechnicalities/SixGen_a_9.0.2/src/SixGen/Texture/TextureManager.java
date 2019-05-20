package SixGen.Texture;

import SixGen.SixUI.Animation.Animation;
import SixGen.Utils.Utils;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;
import static javax.swing.UIManager.get;

/**
 * TextureManager
 * - contains textures
 * - add sprite sheets with addSpriteSheets
 * 
 * @author filip
 */

public class TextureManager extends Utils{

    LinkedList<SpriteSheet> spriteSheets = new LinkedList<SpriteSheet>();
    LinkedList<BufferedImage[]> texturesSets = new LinkedList<BufferedImage[]>();
    
    private Random random;

    public TextureManager() {
        random = new Random();
    }

    public TextureManager(SpriteSheet[] spriteSheet) {
        for (int i = 0; i < spriteSheet.length; i++) {
            addSpriteSheet(spriteSheet[i]);
        }
    }

    public BufferedImage[] getTexturesFromSheet(String title) { 
        for (int i = 0; i < spriteSheets.size(); i++) {
            if (spriteSheets.get(i).getTitle().equals(title)) {
                return spriteSheets.get(i).getTextures();
            }
        }
        return null;
    }
    /**
     * 
     * @param title title of the sheet
     * @param min minimal index
     * @param max maximum index
     * @return 
     */
    public BufferedImage[] getTexturesFromSheet(String title, int min, int max) { 
        for (int i = 0; i < spriteSheets.size(); i++) {
            if (spriteSheets.get(i).getTitle().equals(title)) {
                return spriteSheets.get(i).getTextures(min, max);
            }
        }
        return null;
    }
    
    public BufferedImage getTextureFromSheet(String title, int id) {
        for (int i = 0; i < spriteSheets.size(); i++) {
            if (spriteSheets.get(i).getTitle().equals(title)) {
                if (id <= texturesSets.get(i).length) {
                    return texturesSets.get(i)[id];
                }
            }
        }
        return null;
    }
    public BufferedImage getRandomTextureFromSheet(String title) { 
        for (int i = 0; i < spriteSheets.size(); i++) {
            if (spriteSheets.get(i).getTitle().equals(title)) {
                random.setSeed(System.nanoTime());
                return texturesSets.get(i)[(int)(random.nextInt(texturesSets.get(i).length))];
            }
        }
        return null;
    }
    
    public Animation getAnimationFromSheet(String title , int id , int length , int frameLength) {
        BufferedImage[] tex = new BufferedImage[length];
        for(int i = 0 ; i < length ; i++) {
            tex[i] = getTextureFromSheet(title, id + i);
        }
        return new Animation(tex , frameLength);
    }

    public void addSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheets.add(spriteSheet);
        LinkedList<BufferedImage> images = new LinkedList<BufferedImage>();
        for (int yy = 0; yy < spriteSheet.getRows(); yy++) {
            for (int xx = 0; xx < spriteSheet.getColumns(); xx++) {
                BufferedImage texture = spriteSheet.getTexture(xx, yy);
                images.add(texture);
            }
        }
        this.texturesSets.add(images.toArray(new BufferedImage[images.size()]));
    }

    public void addSpriteSheets(SpriteSheet[] spriteSheet) { 
        for (int i = 0; i < spriteSheet.length; i++) {
            addSpriteSheet(spriteSheet[i]);
        }
    }
    
    public void removeSpriteSheet(SpriteSheet spriteSheet) {
        for (int i = 0; i < spriteSheets.size(); i++) {
            if (spriteSheet == spriteSheets.get(i)) {
                spriteSheets.remove(i);
                texturesSets.remove(i);
            }
        }
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
    
    public BufferedImage rotateImage(BufferedImage image, double angle) {
        return null;
    }
}
