package SixGen.Game;

import SixGen.Window.CanvasManager;
import static SixGen.Game.Game.canvMan;

/**
 * GameLoop 
 * Abilities:
 *  Creates single threaded or multi threaded game loop
 *      single threaded loop is loop where there is just one loop that both renders and ticks
 *      multi threaded loop is loop where thera are two separated loops one for rendering and one for ticking
 */

public class GameLoop {
    
    //VARIABLES
    
    // Type of the game loop (singleThread , multiThread) *for more info see GameLoopType description
    private GameLoopType gameLoopType;
    // Switch that controls if the loop is giving values to the console
    private boolean message;
    // Active canvas from this CanvasManager is rendered and ticked
    private CanvasManager canvMan;
    
    //GameLoop holds two types of gameLoop: 
    public enum GameLoopType {
        //single threaded loop is loop where there is just one loop that both renders and ticks
        singleThread, 
        //multi threaded loop is loop where thera are two separated loops one for rendering and one for ticking
        multiThread;
    }
    
    //CONSTRUCTORS
    
    public GameLoop(GameLoopType engineType) {
        //@GameLoop
        //@GameLoop#constructorSetters
        this.gameLoopType = engineType;
    }

    //VOIDS
    
    public void startLoop(boolean message, CanvasManager canvMan, int maxFPS, int maxTPS) {
        //@startLoop
        this.message = message;
        this.canvMan = canvMan;
        if (gameLoopType == GameLoopType.singleThread) {
            SingleThreadedGameLoop STGL = new SingleThreadedGameLoop(maxFPS);
            STGL.start();
        } else if (gameLoopType == GameLoopType.multiThread) {
            MultyThreadedGameLoop.startMultyThreadedGameLoop(maxFPS, maxTPS, message, canvMan);
        }
    }

    /**
     * SingleThreadedGameLoop
     * Abilities : 
     *  Creates a thread and game loop that provides ticking and rendering
    */
    public class SingleThreadedGameLoop implements Runnable {
        // Boolean that controls if the game loop is running
        private boolean running;
        // The Game loop is placed into this thread
        private Thread thread;
        // This loop can't be updated more than maxUPS(max updates per second) times in one second
        private float maxUPS;
        
        //CONSTRUCTORS
        
        public SingleThreadedGameLoop(float maxUPS) { 
            //@SingleThreadedGameLoop
            //@SingleThreadedGameLoop#constructorSetters
            this.maxUPS = maxUPS;
        }
        
        public synchronized void start() {
            //@start
            thread = new Thread(this);
            thread.start();
            running = true;
        }

        public synchronized void stop() {
            //@stop
            try {
                thread.join();
                running = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            //@run
            double NSPF = 1000000000D / maxUPS;
            double now = System.nanoTime();
            double lastTime = now;
            double delta;
            int frames = 0;

            double timer = System.currentTimeMillis();
            while (running) {
                now = System.nanoTime();
                delta = now - lastTime;
                // if the game wasn't updated for NSPF milisecs than update
                if (delta > NSPF) {
                    canvMan.tick();
                    canvMan.render();
                    lastTime = now;
                    frames++;
                }
                if (message) {
                    if (timer + 1000 < System.currentTimeMillis()) {
                        Game.console.setFPS(frames);
                        Game.console.setTPS(frames);
                        frames = 0;
                        timer = System.currentTimeMillis();
                    }
                }
            }
        }
    }

    /**
     * MulytThreadedGameLoop
     * Abilities : 
     *  Creates two threads , one for rendering and one for ticking
    */
    public static class MultyThreadedGameLoop implements Runnable {
        
        //VARIABLES
        
        // Type of the thread , renderThread for rendering thread or tickThread for ticking thread
        private ThreadType threadType;
        // Threads
        private Thread renderThread, tickThread;
        // Active canvas from this CanvasManager is rendered and ticked
        private static CanvasManager canvMan;

        //FPS(Frame per Second) and TPS(Ticks per Second) locks
        //Theese variables control the maximum amount of render and ticks per second
        public static double maxFPS, maxTPS;
        // Boolean that controls if the game loop is running
        public static boolean running;
        // Switch that controls if the loop is giving values to the console
        public static boolean message;

        // Type of the threads
        private enum ThreadType {
            // Thread that has this type will hold render game loop
            renderThread,
            // Thread that has this type will hold tick game loop
            tickThread;
        }

        public MultyThreadedGameLoop(ThreadType threadType) {
            //@MultyThreadedGameLoop
            //@MultyThreadedGameLoop#constructorSetters
            this.threadType = threadType;
            //@MultyThreadedGameLoop#creatingThread
            if (threadType == ThreadType.renderThread) {
                renderThread = new Thread(this);
                renderThread.start();
            } else if (threadType == ThreadType.tickThread) {
                tickThread = new Thread(this);
                tickThread.start();
            }
            running = true;
        }

        public void run() {
            //@run
            if (threadType == ThreadType.renderThread) {
                startRenderLoop();
            } else if (threadType == ThreadType.tickThread) {
                startTickLoop();
            }
        }

        public void startRenderLoop() {
            //@startRenderLoop
            double NSPF = 1000000000D / maxFPS;
            double now = System.nanoTime();
            double lastTime = now;
            double delta;
            int frames = 0;

            double timer = System.currentTimeMillis();
            while (running) {
                now = System.nanoTime();
                delta = now - lastTime;
                if (delta > NSPF) {
                    canvMan.render();
                    lastTime = now;
                    frames++;
                }
                if (message) {
                    if (timer + 1000 < System.currentTimeMillis()) {
                        Game.console.setFPS(frames);
                        frames = 0;
                        timer = System.currentTimeMillis();
                    }
                }
            }
        }

        public void startTickLoop() {
            //@startTickLoop
            double NSPT = 1000000000D / maxTPS;
            double now = System.nanoTime();
            double lastTime = now;
            double delta;
            int ticks = 0;

            double timer = System.currentTimeMillis();
            while (running) {
                now = System.nanoTime();
                delta = now - lastTime;
                if (delta > NSPT) {
                    canvMan.tick();
                    lastTime = now;
                    ticks++;
                }
                if (message) {
                    if (timer + 1000 < System.currentTimeMillis()) {
                        Game.console.setTPS(ticks);
                        ticks = 0;
                        timer = System.currentTimeMillis();
                    }
                }
            }
        }

        public static void startMultyThreadedGameLoop(double FPS, double TPS, boolean message, CanvasManager canvMan) {
            //@startMultyThreadedGameLoop
            MultyThreadedGameLoop.maxFPS = FPS;
            MultyThreadedGameLoop.maxTPS = TPS;
            MultyThreadedGameLoop.message = message;
            MultyThreadedGameLoop.canvMan = canvMan;
            new MultyThreadedGameLoop(ThreadType.renderThread);
            new MultyThreadedGameLoop(ThreadType.tickThread);
        }

        public static void stopGameLoop() {
            //@stopGameLoop
            running = false;
        }
    }
}
