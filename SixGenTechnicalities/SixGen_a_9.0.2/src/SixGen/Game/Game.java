package SixGen.Game;

import SixGen.Window.Camera;
import SixGen.Window.Console;
import SixGen.Window.SixCanvas;
import SixGen.Window.CanvasManager;
import SixGen.Window.Window;
import SixGen.Game.GameLoop.GameLoopType;
import SixGen.Utils.ID;
import java.awt.Graphics;

/**
 * Game
 * Abilities:
 *  Initalizing Canvases
 *  Creating Window
 * This class is core of the whole game.
 * Constructor takes care of initializing the whole game.
 * Constructor calls abstract the canvInit method that shoud contain everything
 * needed for adding all of the canvases
*/

public abstract class Game {
    
    //VARIABLES
    
    //Manager that takes care of all canvases.
    protected static CanvasManager canvMan;
    //Boolean that controls if the canvas is changed by camera
    protected boolean cameraSwitch;
    //Canvas that is both rendering and ticking
    protected int activeCanvas;
    //Size of the window
    protected int FRAMEWIDTH, FRAMEHEIGHT;
    //Dimensions of the frame divided by 2
    protected int centerX, centerY;
    //Title of the frame and game itself
    protected String TITLE;
    //Class that takes care of JFrame
    protected Window window;
    //Boolean that controls the console render
    protected static boolean message;
    //Class that's taking care of keeping information about camera position.
    //Graphics is translating by the cordinates taken from camera.
    //Camera also holds voids for movement
    protected Camera camera;
    //Class that displays iformation about FPS , TPS and title
    //Console can be displayed in all four corners
    //You can change the corner by changing Console.consolePosition
    protected static Console console;
    //GameLoop holds two types of gameLoop: 
    //  singleThread = Whole game runs on one thread
    //  multyThread = Game runs on two separated threads. One takes care of rendering and one of ticking
    protected GameLoopType gameLoopType;
    //FPS(Frame per Second) and TPS(Ticks per Second) locks
    //Theese variables control the maximum amount of render and ticks per second
    private int maxFPS, maxTPS;

    //CONTRUCTORS
    
    public Game(GameLoopType gameLoopType , int WIDTH, int HEIGHT, String TITLE, boolean message) {
        //@Game
        //@Game$constructorSetters
        this.FRAMEWIDTH = WIDTH;
        this.FRAMEHEIGHT = HEIGHT;
        this.TITLE = TITLE;
        this.gameLoopType = gameLoopType;
        this.centerX = WIDTH / 2;
        this.centerY = HEIGHT / 2;
        this.maxTPS = 60;
        this.maxFPS = 60;
        Game.message = message;
        //@Game#consoleInit
        Game.console = new Console(message , message , message , message , message);
        Game.console.setTitle(TITLE);
        Game.console.setEngineType(gameLoopType);
        //@Game#canvasManagerInit
        canvMan = new CanvasManager(window);
        canvInit(canvMan);
        //@Game#windowInit
        window = new Window(WIDTH, HEIGHT, TITLE, this, canvMan);
        init();
    }

    public Game(GameLoopType gameLoopType, int WIDTH, int HEIGHT, String TITLE, boolean message, int maxFPS, int maxTPS) {
        //@Game
        //@Game#constructorSetters
        this.gameLoopType = gameLoopType;
        this.FRAMEWIDTH = WIDTH;
        this.FRAMEHEIGHT = HEIGHT;
        this.TITLE = TITLE;
        this.centerX = WIDTH / 2;
        this.centerY = HEIGHT / 2;
        this.maxFPS = maxFPS;
        this.maxTPS = maxTPS;
        Game.message = message;
        //@Game#consoleInit
        Game.console = new Console(message, message, message , message , message);
        Game.console.setTitle(TITLE);
        Game.console.setEngineType(gameLoopType);
        //@Game#canvManagerInit
        canvMan = new CanvasManager(window);
        canvInit(canvMan);
        //@Game#winInit
        window = new Window(WIDTH, HEIGHT, TITLE, this, canvMan);
        init();
    }
    
    //ABSTRACT VOIDS
    
    public abstract void canvInit(CanvasManager canMan);
    public void init() { 
        
    }

    //UPDATE VOIDS
    
    public void render(Graphics g) { 
        //@render
    }
    
    public void tick() { 
        //@tick
    }
   
    //ENGINE VOIDS
    
    public synchronized void start() {
        //@start
        GameLoop gameLoop = new GameLoop(gameLoopType);
        gameLoop.startLoop(message, canvMan , maxFPS, maxTPS);
    }
     
    public synchronized void stop() {
        //@stop
    }
    
    //GETTERS SETTERS
    
    public void setCanvases(SixCanvas canvases[]) { 
        canvMan.setCanvases(canvases);
    }
    
     public SixCanvas getCanvas(ID id){
        return getCanvMan().getCanvas(id);
    }

    public static Console getConsole() {
        return console;
    }

    public static CanvasManager getCanvMan() {
        return canvMan;
    }
    public Window getWindow() { 
        return window;
    }
    public void setActiveCanvas(SixCanvas canv) { 
        canvMan.setActiveCanvas(canv);
    }
    public void setActiveCanvas(ID id) { 
        SixCanvas canvas = canvMan.getCanvas(id);
        if(canvas!=null) { 
            canvMan.setActiveCanvas(canvas);
        }
    }
    public int getWIDTH() {
        return FRAMEWIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.FRAMEWIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return FRAMEHEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.FRAMEHEIGHT = HEIGHT;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public String getTitle() {
        return TITLE;
    }

    public void setTitle(String TITLE) {
        this.TITLE = TITLE;
    }
}
