// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.Events.Keyboard;

import SixGen.Utils.Utils;
import SixGen.Window.SixCanvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * SixAbstractKeyListener
 * Implements :
 *  KeyListener
 * Abilities : 
 *  Form used for Keyboard Listener
 *  Stores info about keyDefaults
 *  Stores KeyActionType enum
 *  Sends KeyEvent and type of action to the canvas (void keyAction(KeyEvent e , KeyActionType kat))
 */
public abstract class SixAbstractKeyListener implements KeyListener {
    
    //VARIABLES
    
    // SixCanv that uses this listener. If somebody types something on keyboard that canvas receives output
    protected SixCanvas canv;

    // KeyActionType holds two types of Keyboard input
    public enum KeyActionType {
        //if the key is pressed and released so it's activated and then deactivated
        typed,
        //if the key is pressed down so it's activated 
        pressed,
        //if the key is released so it's deactivated
        released;
    }

    // KeyDefaultsType holds two Keyboard default presets
    public enum KeyDefaultsType {
        // standard wasd preset : w - up , a - left , s - down , d - right
        wasd, 
        // standard arrow preset : arrow up - up , arrow left - left , arrow down - down , arrow right - right
        arrows;
    }
    
    //CONSTRUCTORS
    
    public SixAbstractKeyListener(SixCanvas canv) {
        //@SixAbstractKeyListener
        //@SixAbstractKeyListener@constructorSetters
        this.canv = canv;
    }

    //ABSTRACT VOIDS 
    
    public abstract void sixKeyTyped(KeyEvent e);

    public abstract void sixKeyPressed(KeyEvent e);

    public abstract void sixKeyReleased(KeyEvent e);

    //TYPING VOIDS 
    
    @Override
    public void keyTyped(KeyEvent e) {
        //@keyTyped
        sixKeyTyped(e);
        canv.keyAction(e, KeyActionType.typed);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //@keyPressed
        sixKeyPressed(e);
        canv.keyAction(e, KeyActionType.pressed);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //@keyReleased
        sixKeyReleased(e);
        canv.keyAction(e, KeyActionType.released);
    }

}
