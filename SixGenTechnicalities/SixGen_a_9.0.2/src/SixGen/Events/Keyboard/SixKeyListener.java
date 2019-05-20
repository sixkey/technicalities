// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.Events.Keyboard;

import SixGen.Window.SixCanvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Filip
 */
public class SixKeyListener extends SixAbstractKeyListener {

    public SixKeyListener(SixCanvas canv) {
        super(canv);
    }

    public void activate() { 
        canv.addKeyListener(this);
        canv.requestFocus();
    }
    @Override
    public void sixKeyTyped(KeyEvent e) {
        
    }
    @Override
    public void sixKeyPressed(KeyEvent e) { 
        
    }
    @Override
    public void sixKeyReleased(KeyEvent e) { 
        
    }
    
}
