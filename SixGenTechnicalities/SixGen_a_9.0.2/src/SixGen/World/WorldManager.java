package SixGen.World;

import SixGen.GameObject.GameObject;
import SixGen.Handler.Handler;
import SixGen.Window.SixCanvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import SixGen.GameObject.GameObject.TexturePosition;
import SixGen.Utils.Utils.Direction;

/** 
 * 
 * WorldManager
 * This class contains voids useful for generating the world.
 * Abilities :
 *  Generating world from BufferedImage map
 *  Generating world from Level level
*/


public abstract class WorldManager {

    protected Handler handler;
    protected Color color;
    protected Color colors[] = new Color[9];

    LinkedList<Level> levels = new LinkedList<Level>();

    public WorldManager(Handler handler) {
        this.handler = handler;
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(0, 0, 0);
        }
    }

    public abstract GameObject objectSpawning(int xx, int yy, int gridSizeWidth, int gridSizeHeight);

    public void spawnLevelFromMap(BufferedImage map, int gridSize) {
        spawnLevelFromMap(map, gridSize, gridSize);
    }

    public void spawnLevelFromMap(BufferedImage map, int gridSizeWidth, int gridSizeHeight) {
        int w = map.getWidth();
        int h = map.getHeight();
        int counter = 0;
        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                counter = 0;
                for (int yyy = 0; yyy < 3; yyy++) {
                    for (int xxx = 0; xxx < 3; xxx++) {
                        int getX = xx - 1 + xxx;
                        int getY = yy - 1 + yyy;
                        if (getX < 0) {
                            getX = 0;
                        }
                        if (getY < 0) {
                            getY = 0;
                        }
                        if (getX > w) {
                            getX = w;
                        }
                        if (getY > h) {
                            getY = h;
                        }
                        int pixel;
                        if(getX < w && getY < h) { 
                            pixel = map.getRGB(getX, getY);
                        } else { 
                            pixel = color.black.getRGB();
                        }
                        int r = (pixel >> 16) & 0xff;
                        int g = (pixel >> 8) & 0xff;
                        int b = (pixel) & 0xff;
                        colors[counter] = new Color(r, g, b);
                        counter++;
                    }
                }
                color = colors[4];
                objectSpawning(xx * gridSizeWidth, yy * gridSizeHeight, gridSizeWidth, gridSizeHeight);
            }
        }
    }

    public void spawnLevelFromMap(BufferedImage map, int gridSize, int x, int y, boolean center) {
        spawnLevelFromMap(map, gridSize, gridSize, x, y, center);
    }

    public void spawnLevelFromMap(BufferedImage map, int gridSizeWidth, int gridSizeHeight, int x, int y, boolean center) {
        int counter = 0;
        int w = map.getWidth();
        int h = map.getHeight();
        int bx, by;
        if (center) {
            bx = x - w * gridSizeWidth / 2;
            by = y - h * gridSizeHeight / 2;
        } else {
            bx = x;
            by = y;
        }
        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                counter = 0;
                for (int yyy = 0; yyy < 3; yyy++) {
                    for (int xxx = 0; xxx < 3; xxx++) {
                        int getX = xx - 1 + xxx;
                        int getY = yy - 1 + yyy;
                        if (getX < 0) {
                            getX = 0;
                        }
                        if (getY < 0) {
                            getY = 0;
                        }
                        if (getX > w - 1) {
                            getX = w - 1;
                        }
                        if (getY > h - 1) {
                            getY = h - 1;
                        }
                        int pixel = map.getRGB(getX, getY);
                        int r = (pixel >> 16) & 0xff;
                        int g = (pixel >> 8) & 0xff;
                        int b = (pixel) & 0xff;
                        colors[counter] = new Color(r, g, b);
//						System.out.printf("%d  %d  %d %n" ,counter ,getX , getY);
                        counter++;
                    }
                }
                color = colors[4];
                objectSpawning(bx + xx * gridSizeWidth, by + yy * gridSizeHeight, gridSizeWidth, gridSizeHeight);
            }
        }
    }
    
    public boolean isColor(Color color) {
        if (color.equals(this.color)) {
            return true;
        } else {
            return false;
        }
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public void removeLevel(Level level) {
        levels.remove(level);
    }

    public void spawnLevel(int levelId) {
        for (int i = 0; i < levels.size(); i++) {
            Level level = levels.get(i);
            if (level.getLevelId() == levelId) {
                spawnLevelFromMap(level.getMap(), level.getGridSizeWidth(), level.getGridSizeHeight(), level.getX(), level.getY(), level.isCenter());
            }
        }
    }
    
    public void spawnLevel(int levelId , SixCanvas canv) { 
        for (int i = 0; i < levels.size(); i++) {
            Level level = levels.get(i);
            if (level.getLevelId() == levelId) {
                canv.setBoundsClamp(level);
                spawnLevelFromMap(level.getMap(), level.getGridSizeWidth(), level.getGridSizeHeight(), level.getX(), level.getY(), level.isCenter());
            }
        }
    }

    public void spawnLevel(Level level) {
        spawnLevelFromMap(level.getMap(), level.getGridSizeWidth(), level.getGridSizeHeight(), level.getX(), level.getY(), level.isCenter());
    }
    
    public void spawnLevel(Level level , SixCanvas canv) {
        canv.setBoundsClamp(level);
        spawnLevelFromMap(level.getMap(), level.getGridSizeWidth(), level.getGridSizeHeight(), level.getX(), level.getY(), level.isCenter());
    }

    public Level getLevel(int levelId) {
        for (int i = 0; i < levels.size(); i++) {
            Level level = levels.get(i);
            if (level.getLevelId() == levelId) {
                return level;
            }
        }
        return null;
    }

    public TexturePosition getFromColours() {
        boolean same[] = new boolean[colors.length];
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].getRGB() == colors[4].getRGB()) {
                same[i] = true;
            } else {
                same[i] = false;
            }
        }
        return texutrePositionDecide(same);
    }

    public TexturePosition texutrePositionDecide(boolean same[]) {
        if (!same[1] && !same[3] && same[5] && same[7]) {
            return TexturePosition.topLeft;
        } else if (!same[1] && same[3] && !same[5] && same[7]) {
            return TexturePosition.topRight;
        } else if (same[1] && !same[3] && same[5] && same[7]) {
            return TexturePosition.left;
        } else if (same[1] && same[3] && !same[5] && same[7]) {
            return TexturePosition.right;
        } else if (same[1] && !same[3] && same[5] && !same[7]) {
            return TexturePosition.bottomLeft;
        } else if (same[1] && same[3] && same[5] && !same[7]) {
            return TexturePosition.bottom;
        } else if (same[1] && same[3] && !same[5] && !same[7]) {
            return TexturePosition.bottomRight;
        } else if (!same[1]) {
            return TexturePosition.top;
        } else {
            return TexturePosition.center;
        }
    }
    
    /**
     * Checks if objects near by aren't of the same type (if they are functions will edit objects bounds). 
     * @author filip
     * @param object object that will be edited
     */
    
    public void checkForBounds(GameObject object) { 
        boolean same[] = new boolean[colors.length];
        for (int i = 0; i < colors.length; i++) {
            same[i] = colors[i].getRGB() == colors[4].getRGB();
        }
        object.setBoundsSwitch(Direction.up, !same[1]);
        object.setBoundsSwitch(Direction.left, !same[3]);
        object.setBoundsSwitch(Direction.right, !same[5]);
        object.setBoundsSwitch(Direction.down, !same[7]);
    }
    
    public void checkForBounds(GameObject object , Color[] c){ 
        Color[] check = new Color[1 + c.length];
        for(int i = 0 ; i < check.length ; i++) { 
            if(i!=check.length - 1) {
                check[i] = c[i];
            } else { 
                check[i] = colors[4];
            }
        } 
        boolean same[] = new boolean[colors.length];
        for (int i = 0; i < colors.length; i++) {
            boolean result = false;
            for(int f = 0 ; f < check.length ; f++) { 
                if(colors[i].getRGB() == check[f].getRGB()) { 
                    result = true;
                }
            }
            same[i] = result;
        }
        object.setBoundsSwitch(Direction.up, !same[1]);
        object.setBoundsSwitch(Direction.left, !same[3]);
        object.setBoundsSwitch(Direction.right, !same[5]);
        object.setBoundsSwitch(Direction.down, !same[7]);
    }
    
    public void setHandler(Handler handler) { 
        this.handler = handler;
    }
    
    public Handler getHandler() { 
        return handler;
    }
}
