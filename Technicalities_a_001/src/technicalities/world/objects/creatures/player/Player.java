/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File world.objects.creatures.player / Player
*  created on 20.5.2019 , 21:33:16 
 */
package technicalities.world.objects.creatures.player;

import java.awt.Color;
import java.awt.Graphics2D;
import technicalities.variables.idls.OIDL;
import technicalities.world.World;
import technicalities.world.objects.creatures.Creature;

/**
 * Player
 * - player object
 * @author filip
 */
public class Player extends Creature {
    
    ////// VARIABLES ////// 
    
    ////// CONSTRUCTORS ////// 
    public Player(float centerX, float centerY, World world) { 
        super(centerX, centerY, OIDL.player, world);
        setBoundsABottom(PLAYER_SIZE, PLAYER_SIZE);
        color = Color.red;
    }
    
    public void tick() { 
        super.tick();
    }
}
