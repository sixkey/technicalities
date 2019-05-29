/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File canvases / MainCanvas
*  created on 20.5.2019 , 19:05:54 
 */
package technicalities.canvases;

import SixGen.Events.Keyboard.BasicIsoMovement;
import SixGen.Events.Keyboard.SixAbstractKeyListener;
import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.Events.Mouse.SixMouseListener;
import SixGen.Game.Game;
import SixGen.Handler.Handler.RenderType;
import SixGen.Window.Camera;
import SixGen.Window.SixCanvas;
import java.awt.event.MouseEvent;
import technicalities.variables.idls.CIDL;
import technicalities.world.World;
import technicalities.handler.TechHandler;
import technicalities.variables.globals.GlobalVariables;
import static technicalities.variables.globals.GlobalVariables.WORLDSIZE;
import technicalities.world.objects.creatures.player.Player;
import technicalities.world.structure.Chunk;
import technicalities.world.structure.Tile;

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
        
        //// player init 
        player = new Player(200, 200, world);
        handler.addObject(player);
        
        camera.setTarget(player);
        camera.setFollowType(Camera.FollowType.sharpObjFollow);
        
        //// interface 
        //movement 
        BasicIsoMovement BIM = new BasicIsoMovement(this, player, SixAbstractKeyListener.KeyDefaultsType.wasd, 8, 8);
        this.addKeyListener(BIM);
        
        //mouse 
        SixMouseListener SML = new SixMouseListener(this);
        SML.activate();
    }
    
    @Override
    public void mouseAction(MouseEvent e, MouseActionType mat) { 
        int ex = (int) getRealMouseX(e);
        int ey = (int) getRealMouseY(e);
        
        if(mat == MouseActionType.pressed) {
            Tile tile = world.getTileFromRealCords(ex, ey);
            if(tile!=null) { 
                if(tile.getStandable()!=null) { 
                    tile.getStandable().damage(1);
                }
            }
        }
    }
    
}
