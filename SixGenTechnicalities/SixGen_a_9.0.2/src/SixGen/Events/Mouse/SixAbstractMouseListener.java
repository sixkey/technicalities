// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.Events.Mouse;

import SixGen.Window.SixCanvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * SixAbstractMouseListener
 * Implements :
 *  MouseListener
 *  MouseMotionListener
 * Abilities : 
 *  Form used for Mouse Listener
 *  Stores MouseActionType enum
 *  Sends MouseEvent and type of action to the canvas (void mouseAction(MouseEvent e , MouseActionType mat))
 */
public abstract class SixAbstractMouseListener implements MouseListener , MouseMotionListener, MouseWheelListener {
    //VARIABLES
    
    // SixCanv that uses this listener. If somebody moves a mouse or clicks a button that canvas receives output
    private SixCanvas canv;

    // values of the buttons on the mouse relative to button value in MouseEvent
    public static final int leftButton = 1;
    public static final int middleButton = 2;
    public static final int rightButton = 3;
    
    // values of activitye of the buttons *see leftButton's middleButton's and rightButton's description for id's of the buttons
    boolean pressed[] = new boolean[4];
    // holds values of the last MouseEvent of the button *see leftButton's middleButton's and rightButton's description for id's of the buttons
    MouseEvent[] event = new MouseEvent[4];
    
    /**
     * MouseActionType describes the mouse action
     */
    public enum MouseActionType { 
        // if button is clicked so pressed and released
        clicked ,
        // if button is pressed
        pressed ,
        // if button is released
        released ,
        // if the cursor enters canvas
        entered ,
        // if the cursor exites canvas
        exited ,
        // if the cursor is moved
        moved ,
        // if the cursor is moving while the button is pressed
        dragged ,
        // if the button is hold
        hold;
    }
    
    //CONSTRUTCOR
    
    public SixAbstractMouseListener(SixCanvas canv) {
        //@SixAbstractMouseListener
        //@SixAbstractMouseListener@constructorSetters
        this.canv = canv;
        //@SixAbstractMouseListener@buttonValuesInit
        for(int i = 0 ; i < pressed.length ; i++) { 
            pressed[i] = false;
        }
    }
    
    //ABSTRACT VOIDS 
    
    public abstract void sixMouseClicked(MouseEvent e); 
    public abstract void sixMousePressed(MouseEvent e);
    public abstract void sixMouseReleased(MouseEvent e);
    public abstract void sixMouseEntered(MouseEvent e);
    public abstract void sixMouseExited(MouseEvent e);
    public abstract void sixMouseDragged(MouseEvent e);
    public abstract void sixMouseMoved(MouseEvent e);
    public abstract void sixMouseHold(MouseEvent e);
    public abstract void sixMouseWheelMoved(MouseWheelEvent e);
    
    //METHODS
    
    public void activate() { 
        canv.addMouseListener(this);
        canv.addMouseMotionListener(this);
        canv.addMouseWheelListener(this);
    }
    public void buttonsActive() { 
        canv.addMouseListener(this);
    }
    public void motionActive() { 
        canv.addMouseMotionListener(this);
    }
    
    //MOUSE VOIDS 
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //@mouseClicked
        sixMouseClicked(e);
        canv.mouseAction(e, MouseActionType.clicked);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //@mousePressed
        sixMousePressed(e);
        event[(int)e.getButton()] = e;
        pressed[(int)e.getButton()] = true;
        canv.mouseAction(e, MouseActionType.pressed);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //@mouseReleased
        sixMouseReleased(e);
        pressed[(int)e.getButton()] = false;
        canv.mouseAction(e, MouseActionType.released);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //@mouseEntered
        sixMouseEntered(e);
        canv.mouseAction(e, MouseActionType.entered);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //@mouseExited
        sixMouseExited(e);
        canv.mouseAction(e, MouseActionType.exited);
    }
    
    @Override
    public void mouseDragged(MouseEvent e) { 
        //@mouseDraged
        sixMouseDragged(e);
        canv.mouseAction(e , MouseActionType.dragged);
    }
    
    @Override
    public void mouseMoved(MouseEvent e) { 
        //@mouseMoved
        sixMouseMoved(e);
        canv.mouseAction(e, MouseActionType.moved);
    }
    
    public void mouseHold(MouseEvent e) { 
        //@mouseHold
        sixMouseHold(e);
        canv.mouseAction(e , MouseActionType.hold);
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //@mouseWheelMoved
        sixMouseWheelMoved(e);
        canv.mouseWheelAction(e);
    }
    
    //VOIDS 
    
    public void tick() { 
        for(int i = 0 ; i < pressed.length ;i++) { 
            if(pressed[i]) {
                mouseHold(event[i]);
            }
        }
    }
}
