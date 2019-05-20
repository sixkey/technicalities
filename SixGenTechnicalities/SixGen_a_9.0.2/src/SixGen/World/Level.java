package SixGen.World;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Level 
 * Abilities:
 * Level just holds variables thet are listed in the VARIABLES section
 */
public class Level {
    
    //VARIABLES
    
    // BufferedImage that is used in the world manager , method spawn world from level
    protected BufferedImage map;
    // Id of the value in int
    protected int levelId;
    // position of the level. If the position is [100,0] then in the spawning method the level will be spawned relative to this position
    protected int x, y;
    // size of the grid that is used in the spawning method. If the gridSize is [100,100] than the objs will be spawned 100 pixels apart
    protected int gridSizeWidth, gridSizeHeight;
    // if the level is centered relative to the screen
    protected boolean center;
    // dimensions of the level in pixels
    protected int width, height;
    protected int mapWidth, mapHeight;

    //CONSTRUCTORS
    
    public Level(BufferedImage map, int levelId, int gridSize) {
        //@Level
        //@Level#constructorSetters
        this.map = map;
        this.levelId = levelId;
        this.gridSizeWidth = gridSize;
        this.gridSizeHeight = gridSize;
        this.mapWidth = map.getWidth();
        this.mapHeight = map.getHeight();
        this.width = gridSizeWidth * map.getWidth();
        this.height = gridSizeHeight * map.getHeight();
        //@Level#defaultSetters
        x = 0;
        y = 0;
        center = false;
    }

    public Level(BufferedImage map, int levelId, int gridSizeWidth, int gridSizeHeight) {
        //@Level
        //@Level#constructorSetters
        this.map = map;
        this.levelId = levelId;
        this.gridSizeWidth = gridSizeWidth;
        this.gridSizeHeight = gridSizeHeight;
        this.mapWidth = map.getWidth();
        this.mapHeight = map.getHeight();
        this.width = gridSizeWidth * map.getWidth();
        this.height = gridSizeHeight * map.getHeight();
        //@Level#defaultSetters
        x = 0;
        y = 0;
        center = false;
    }

    public Level(BufferedImage map, int levelId, int gridSizeWidth, int gridSizeHeight, int x, int y) {
        //@Level
        //@Level#constructorSetters
        this.map = map;
        this.levelId = levelId;
        this.gridSizeWidth = gridSizeWidth;
        this.gridSizeHeight = gridSizeHeight;
        this.mapWidth = map.getWidth();
        this.mapHeight = map.getHeight();
        this.width = gridSizeWidth * map.getWidth();
        this.height = gridSizeHeight * map.getHeight();
        this.x = x;
        this.y = y;
        //@Level#defaultSetters
        center = false;
    }

    public Level(BufferedImage map, int levelId, int gridSizeWidth, int gridSizeHeight, int x, int y, boolean center) {
        //@Level
        //@Level#constructorSetters
        this.map = map;
        this.levelId = levelId;
        this.gridSizeWidth = gridSizeWidth;
        this.gridSizeHeight = gridSizeHeight;
        this.mapWidth = map.getWidth();
        this.mapHeight = map.getHeight();
        this.width = gridSizeWidth * map.getWidth();
        this.height = gridSizeHeight * map.getHeight();
        this.x = x;
        this.y = y;
        this.center = center;
    }

    //GETTERS SETTERS
    
    public BufferedImage getMap() {
        return map;
    }

    public void setMap(BufferedImage map) {
        this.map = map;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
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

    public int getGridSizeWidth() {
        return gridSizeWidth;
    }

    public void setGridSizeWidth(int gridSizeWidth) {
        this.gridSizeWidth = gridSizeWidth;
    }

    public int getGridSizeHeight() {
        return gridSizeHeight;
    }

    public void setGridSizeHeight(int gridSizeHeight) {
        this.gridSizeHeight = gridSizeHeight;
    }

    public boolean isCenter() {
        return center;
    }

    public void setCenter(boolean centered) {
        this.center = centered;
    }

    public Dimension getDimensions() {
        Dimension dim = new Dimension(map.getWidth() * gridSizeWidth, map.getHeight() * gridSizeHeight);
        return dim;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public int getMapWidth() { 
        return mapWidth;
    }
    public int getMapHeight() { 
        return mapHeight;
    }
}
