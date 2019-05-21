/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File canvases / MainCanvas
*  created on 20.5.2019 , 19:05:54 
 */
package canvases;

import SixGen.Events.Keyboard.BasicIsoMovement;
import SixGen.Events.Keyboard.SixAbstractKeyListener;
import SixGen.Game.Game;
import SixGen.Handler.Handler.RenderType;
import SixGen.Window.Camera;
import SixGen.Window.SixCanvas;
import variables.idls.CIDL;
import world.World;
import world.handler.TechHandler;
import world.objects.creatures.player.Player;
import world.structure.Layer;

/**
 * MainCanvas
 * 
 * - main canvas
 * 
 * @author filip
 */
public class MainCanvas extends SixCanvas {
    
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
        World world = new World(handler);
        // spawn world
        Layer layer = world.spawnLayer(1000, 1000);
        handler.setLayer(layer);
        
        //// player init 
        Player player = new Player(200, 200, world);
        handler.addObject(player);
        
        camera.setTarget(player);
        camera.setFollowType(Camera.FollowType.sharpObjFollow);
        
        //// interface 
        //movement 
        BasicIsoMovement BIM = new BasicIsoMovement(this, player, SixAbstractKeyListener.KeyDefaultsType.wasd, 5, 5);
        this.addKeyListener(BIM);
        
    }
    
}
