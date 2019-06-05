/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File canvases / MainCanvas
*  created on 20.5.2019 , 19:05:54 
 */
package technicalities.canvases;

import SixGen.Events.Keyboard.BasicIsoMovement;
import SixGen.Events.Keyboard.SixAbstractKeyListener;
import SixGen.Events.Keyboard.SixAbstractKeyListener.KeyActionType;
import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.Events.Mouse.SixMouseListener;
import SixGen.Game.Game;
import SixGen.Handler.Handler.RenderType;
import SixGen.Window.Camera;
import SixGen.Window.SixCanvas;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import technicalities.ui.MainCanvasUI;
import technicalities.ui.TUIChannel;
import technicalities.variables.idls.CIDL;
import technicalities.world.World;
import technicalities.world.handler.TechHandler;
import technicalities.variables.globals.GlobalVariables;
import static technicalities.variables.globals.GlobalVariables.WORLDSIZE;
import static technicalities.variables.idls.UIDL.buildingUI;
import static technicalities.variables.idls.UIDL.console;
import technicalities.world.objects.creatures.Player;
import technicalities.world.handler.Chunk;
import technicalities.world.handler.Tile;

/**
 * MainCanvas
 * 
 * - main canvas
 * 
 * @author filip
 */
public class MainCanvas extends SixCanvas implements GlobalVariables{
    
    ////// VARIABLES ////// 
    private Player player;
    private World world;
    
    //// User Interface
    private MainCanvasUI mainCanvasUI;
    private TUIChannel tuiChannel;

    private int mx, my;
    
    ////// CONSTRUCTORS ///////
    
    public MainCanvas(Game game) { 
        super(game, RenderType.front, CIDL.mainCanvas);
    }
    
    ////// METHODS //////
    
    public void init() { 
        //// handler and camera init 
        TechHandler handler = new TechHandler(this);
        setHandler(handler);
        
        Camera camera = new Camera(0, 0, getWidth(), getHeight());
        setCamera(camera);
        camera.setClamp(false);
        
        //// world init 
        world = new World(handler);
        // spawn world
        Chunk[][] chunks = world.spawnLayer(WORLDSIZE, WORLDSIZE);
        handler.setChunks(chunks);
        
        
        tuiChannel = new TUIChannel(getWidth(), getHeight());
        
        //// player init 
        player = new Player(200, 200, world, tuiChannel);
        handler.addObject(player);
        world.player = player;
        
        
//        camera.setTarget(player);
//        camera.setFollowType(Camera.FollowType.smoothObjFollow);
        
        //// UI
        mainCanvasUI = new MainCanvasUI(world, player, tuiChannel);
        handler.addSixUI(mainCanvasUI);
        
        //// input
        //movement 
        BasicIsoMovement BIM = new BasicIsoMovement(this, player, SixAbstractKeyListener.KeyDefaultsType.wasd, 8, 8);
        this.addKeyListener(BIM);
        
        //mouse 
        SixMouseListener SML = new SixMouseListener(this);
        SML.activate();
    }
    
    @Override
    public void addRender(Graphics2D g) {
        if(!TUIChannel.isMouseSpriteEmpty()) { 
            g.drawImage(TUIChannel.getMouseSprite(), mx, my, null);
        }
    }
    
    @Override
    public void mouseAction(MouseEvent e, MouseActionType mat) { 
        super.mouseAction(e, mat);
        
        int ex = (int) getRealMouseX(e);
        int ey = (int) getRealMouseY(e);
        
        mx = e.getX();
        my = e.getY();
        
        player.mouseAction(e, mat);
        
        if(mat == MouseActionType.pressed) {
            Tile tile = world.getTileFromRealCords(ex, ey);
            if(tile!=null) { 
                if(tile.getStandable()!=null) { 
                    player.mouseAction(e, mat, tile);
                }
            }
        }
    }
    
    
    @Override
    public void keyAction(KeyEvent e, KeyActionType kat) { 
        mainCanvasUI.keyAction(e, kat);
    }
    
}
