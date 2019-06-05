package SixGen.Window;

import SixGen.Texture.BackGroundManager;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import SixGen.Handler.Handler.RenderType;
import SixGen.Events.Keyboard.SixAbstractKeyListener.KeyActionType;
import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.Events.Keyboard.BasicFrontMovement;
import SixGen.Events.Keyboard.BasicIsoMovement;
import SixGen.Utils.Files.FileManager;
import SixGen.Game.Game;
import SixGen.GameObject.GameObject;
import SixGen.Handler.Handler;
import SixGen.Utils.ID;
import SixGen.Utils.Utils;
import SixGen.World.Level;
import SixGen.World.WorldManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class SixCanvas extends Canvas {

    private static final long serialVersionUID = -6564492752447447222L;
    protected ID canvId;
    protected Game game;
    protected Camera camera;
    protected Color backgroundColor = Color.black;
    protected boolean cameraSwitch;
    protected Handler handler;
    protected FileManager fileManager = new FileManager();
    protected int centerX, centerY;
    protected boolean tick = true, render = true;
    protected BasicFrontMovement BFM;
    protected BasicIsoMovement BIM;
    protected WorldManager ALS;
    protected Utils utils;
    protected BackGroundManager backgroundManager;
    protected boolean antialiasing = true;
    protected ID lastID;
    protected boolean windowComponent = false;
    protected boolean switchedOn = false;

    private SixCanvas(Game game, ID canvId) {
        this.game = game;
        this.canvId = canvId;
        setBackground(Color.black);
    }

    public SixCanvas(Game game, RenderType renderType, ID canvId) {
        this(game, canvId);
        this.cameraSwitch = false;
        handler = new Handler(this, renderType);
        handler.setCamera(camera);
        setSize(game.getWIDTH(), game.getHEIGHT());
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        init();
    }

    public SixCanvas(Game game, Handler handler, ID canvId) {
        this(game, canvId);
        this.cameraSwitch = false;
        this.handler = handler;
        handler.setCamera(camera);
        setSize(game.getWIDTH(), game.getHEIGHT());
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        setBounds(game.getWIDTH(), game.getHEIGHT());
        init();
    }

    public SixCanvas(Game game, RenderType renderType, ID canvId, int WIDTH, int HEIGHT, Camera camera) {
        this(game, canvId);
        this.camera = camera;
        this.cameraSwitch = true;
        handler = new Handler(this, renderType);
        handler.setCamera(camera);
        centerX = WIDTH / 2;
        centerY = HEIGHT / 2;
        setSize(WIDTH, HEIGHT);
        init();
    }

    public SixCanvas(Game game, Handler handler, ID canvId, int cameraWidth, int cameraHeight) {
        this(game, canvId);
        camera = new Camera(0, 0, cameraWidth, cameraHeight);
        camera.setSixCanv(this);
        this.handler = handler;
        handler.setCamera(camera);
        this.cameraSwitch = true;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        setBounds(game.getWIDTH(), game.getHEIGHT());
        init();
    }

    public SixCanvas(Game game, RenderType renderType, ID canvId, int cameraWidth, int cameraHeight) {
        this(game, canvId);
        camera = new Camera(0, 0, cameraWidth, cameraHeight);
        camera.setSixCanv(this);
        handler = new Handler(this, renderType);
        handler.setCamera(camera);
        this.cameraSwitch = true;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        setBounds(game.getWIDTH(), game.getHEIGHT());
        init();
    }

    public void init() {

    }

    public void RInit() {

    }

    public void addRender(Graphics2D g) {

    }

    public void addTick() {

    }

    public void keyAction(KeyEvent e, KeyActionType action) {

    }

    public void mouseAction(MouseEvent e, MouseActionType action) {
        handler.mouseAction(e, action);
    }

    public void mouseWheelAction(MouseWheelEvent e) {

    }
    

    public void renderBase() {

//        System.out.println("render");
        //@render
        if(windowComponent) {
            try {
                BufferStrategy bs = this.getBufferStrategy();
                if (bs == null || switchedOn) {
                    this.createBufferStrategy(3);
                    switchedOn = false;
                    return;
                }
                Graphics g1 = bs.getDrawGraphics();
                Graphics2D g = (Graphics2D) g1;
                //@render#backGround
                //        game.render(g);
                g.setColor(backgroundColor);
                g.fillRect(0, 0, getWidth(), getHeight());

                if (antialiasing) {
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                }

                render(g);
                //@render#show
                Game.getConsole().render(g);
                g.dispose();
                bs.show();
                renderEnd(g);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(String.valueOf(getId()).toUpperCase());
                System.out.println(windowComponent);
            }
        }
    }

    public void renderEnd(Graphics2D g) {

    }

    public void render(Graphics2D g) {
//        if (render) {
//            //@render#cameraon
        preCameraRender(g);
        if (camera != null) {
            camera.cameraAdjust();
        }
        if (backgroundManager != null) {
            backgroundManager.render(camera, g);
        }
        int cameraX = 0, cameraY = 0;
        if (camera != null) {
            cameraX = (int) camera.getX();
            cameraY = (int) camera.getY();
        }

        if (cameraSwitch) {
            g.translate(-cameraX, -cameraY);
        }
        preHandlerRender(g);
        if (handler != null) {
            handler.render(g);
        }
        postHandlerRender(g);
//            //@render#actualRender
        if (cameraSwitch) {
            g.translate(cameraX, cameraY);
        }
        preGUIRender(g);
        handler.GUIRender(g);
        addRender(g);
//        }
    }

    public void preCameraRender(Graphics2D g) {

    }

    public void preHandlerRender(Graphics2D g) {

    }

    public void postHandlerRender(Graphics2D g) {

    }

    public void preGUIRender(Graphics2D g) {

    }

    public void tick() {
        if (tick) {
            preHandlerTick();
            if (handler != null) {
                handler.tick();
            }
            addTick();
        }
    }

    public void preHandlerTick() {

    }

    public ID getId() {
        return canvId;
    }

    public void setId(ID canvId) {
        this.canvId = canvId;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        handler.setCamera(camera);
        cameraSwitch = true;
        this.camera = camera;
    }

    public boolean isCameraSwitch() {
        return cameraSwitch;
    }

    public void setCameraSwitch(boolean cameraSwitch) {
        this.cameraSwitch = cameraSwitch;
    }

    public Handler handler() {
        return handler;
    }

    public void renderHelpingLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(setAlpha(0.3f));
        g.setColor(new Color(0, 255, 0));
        g.drawRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(0, 255, 255));
        int partWidth = getWidth() / 4;
        int partHeight = getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            g.drawLine(partWidth * (i + 1), 0, partWidth * (i + 1), getHeight());
            g.drawLine(0, partHeight * (i + 1), getWidth(), partHeight * (i + 1));
        }
    }

    public AlphaComposite setAlpha(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return AlphaComposite.getInstance(type, alpha);
    }

    @Override
    public void setSize(Dimension dim) {
        centerX = (int) (dim.getWidth() / 2);
        centerY = (int) (dim.getHeight() / 2);
        if (camera != null) {
            camera.setCanvasDimension(dim);
        }
        resize(dim);
    }

    @Override
    public void setSize(int width, int height) {
        centerX = width / 2;
        centerY = height / 2;
        if (camera != null) {
            camera.setCanvasDimension(width, height);
        }
        resize(width, height);

    }

    public void activateLevel(int levelId) {
        render = false;
        tick = false;
        handler.reset();
        if (ALS != null) {
            Level level = ALS.getLevel(levelId);
            Dimension dim = level.getDimensions();
            this.setSize(dim);
            ALS.spawnLevel(level);
            System.out.println(dim.getWidth() + " " + dim.getHeight());
        }
        if (BFM != null) {
            GameObject target = BFM.getTarget();
            if (target != null) {
                ID player = target.getId();
                GameObject obj = handler.findObjectViaID(player);
                BFM.reset();
                if (obj != null) {
                    BFM.setTarget(obj);
                }
            } else {
                BFM.reset();
            }
        }
        if (BIM != null) {
            ID player = BIM.getTarget().getId();
            BIM.reset();
            GameObject obj = handler.findObjectViaID(player);
            if (obj != null) {
                BIM.setTarget(obj);
            }
        }
        if (camera != null) {
            camera.setTarget(handler.findObjectViaID(camera.getTarget().getId()));
        }
        render = true;
        tick = true;
    }

    public void refreshBounds() {
        setSize(getWidth(), getHeight());
    }

    public void setBounds(int WIDTH, int HEIGHT) {
        setSize(WIDTH, HEIGHT);
    }

    public void setBounds(Level level) {
        setSize(level.getDimensions());
    }

    public void setBounds(Dimension d) {
        setSize(d);
    }

    public void setBoundsClamp(int width, int height) {
        if (width < game.getWIDTH()) {
            width = game.getWIDTH();
        }
        if (height < game.getHEIGHT()) {
            height = game.getHEIGHT();
        }
        setSize(width, height);
    }

    public void setBoundsClamp(Level level) {
        int width = level.getWidth();
        int height = level.getHeight();
        if (width < game.getWIDTH()) {
            width = game.getWIDTH();
        }
        if (height < game.getHEIGHT()) {
            height = game.getHEIGHT();
        }
        setSize(width, height);
    }

    public void setBoundsClamp(Dimension d) {
        int width = (int) d.getWidth();
        int height = (int) d.getHeight();
        if (width < game.getWIDTH()) {
            width = game.getWIDTH();
        }
        if (height < game.getHEIGHT()) {
            height = game.getHEIGHT();
        }
        setSize(width, height);
    }

    public int getMouseX(MouseEvent e) {
        return e.getX();
    }

    public int getMouseY(MouseEvent e) {
        return e.getY();
    }

    public int getRealMouseX(int mouseX) {
        if (camera != null) {
            return (int) camera.getX() + mouseX;
        } else {
            return mouseX;
        }
    }

    public int getRealMouseY(int mouseY) {
        if (camera != null) {
            return (int) camera.getY() + mouseY;
        } else {
            return mouseY;
        }
    }

    public Point getRealPoint(MouseEvent e) {
        if (camera != null) {
            return new Point((int) (camera.getX() + e.getX()), (int) (camera.getY() + e.getY()));
        } else {
            return new Point((int) (e.getX()), (int) (e.getY()));
        }
    }

    public float getRealMouseX(MouseEvent e) {
        if (camera != null) {
            return camera.getX() + e.getX();
        } else {
            return e.getX();
        }
    }

    public float getRealMouseY(MouseEvent e) {
        if (camera != null) {
            return camera.getY() + e.getY();
        } else {
            return e.getY();
        }
    }

    public Rectangle getMouseBounds(MouseEvent e) {
        return new Rectangle(e.getX() - 1, e.getY() - 1, 3, 3);
    }

    public Rectangle getRealMouseBounds(MouseEvent e) {
        if (camera != null) {
            float x = getRealMouseX(e);
            float y = getRealMouseY(e);
            return new Rectangle((int) x - 1, (int) y - 1, 3, 3);
        } else {
            return new Rectangle(e.getX() - 1, e.getY() - 1, 3, 3);
        }
    }

    public void restart() {
        handler.reset();
        RInit();
    }

    public void pause() {
        tick = !tick;
    }

    public void setTick(boolean tick) {
        this.tick = tick;
    }

    public boolean isTick() {
        return tick;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public boolean isRender() {
        return render;
    }

    public Game getGame() {
        return game;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void addObject(GameObject object) {
        handler.addObj(object);
    }

    public void removeObject(GameObject object) {
        handler.removeObj(object);
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

    public ID getLastID() {
        return lastID;
    }

    public void setLastID(ID lastID) {
        this.lastID = lastID;
    }
    
    public void setWindowComponent(boolean windowComponent) { 
        this.windowComponent = windowComponent;
        switchedOn = true;
    }
    
}
