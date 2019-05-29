/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File ui.tui / HealthBar
*  created on 28.5.2019 , 21:30:32 
 */
package technicalities.ui.tui;

import java.awt.Color;
import java.awt.Graphics2D;
import technicalities.variables.idls.UIDL;
import technicalities.world.objects.TObject;

/**
 * HealthBar
 * - ui that displays health 
 * @author filip
 * 
 * TODO:
 * prototype - neeeds major changes
 */
public class HealthBar extends TUI {
    
    public TObject object;
    
    public HealthBar(TObject object) {
        super(UIDL.healthbar);
        this.object = object;
    }
    
    @Override
    public void render(Graphics2D g) { 
        if(object.getMaxHP()!=0) {
            if(object.getHP() < object.getMaxHP()) {
                g.setColor(Color.black);
                g.fillRect((int)object.getCenterX() - 20, (int)object.getCenterY() - 100, 40 , 10);
                g.setColor(Color.red);
                g.fillRect((int)object.getCenterX() - 20, (int)object.getCenterY() - 100, 40 / object.getMaxHP() * object.getHP() , 10);
            }
        }
    }
    
}
