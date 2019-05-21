package SixGen.Events.Keyboard;

import SixGen.Events.Keyboard.KeyDefaults;
import SixGen.Window.SixCanvas;
import SixGen.Events.Keyboard.SixAbstractKeyListener;
import SixGen.GameObject.GameObject;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * BasicIsoMovement 
 * Extends: 
 *  SixAbstractKeyListener
 * Abilities: 
 *  Movement used in games with render type iso
 *      All buttons active
 *      Movement works for one player
*/

public class BasicIsoMovement extends SixAbstractKeyListener {

    //VARIABLES
    
    //GameObject that is targetet by this movement. All velocity changes caused by keys are translated to this object
    protected GameObject target;
    //Index - Meaning , 0 - left , 1 - right , 2 - up , 3 - down
    //Booleans that signals if the buttons was pressed
    protected boolean pressed[] = new boolean[4];
    //Booleans that signals if the buttons are pressed 
    protected boolean active[] = new boolean[4];
    //KeyCode for every Button
    protected int button[] = new int[4];
    //Value of speed that is added or subtracted by pressing or releasing button
    protected float speedX, speedY;

    public BasicIsoMovement(SixCanvas canv, GameObject target, int left, int right, int up, int down, float speedX, float speedY) {
        //@BasicIsoMovement
        super(canv);
        //@BasicIsoMovement#constructorSetters
        this.speedX = speedX;
        this.speedY = speedY;
        this.target = target;
        //@BasicIsoMovement#pressedActiveInit
        for (int i = 0; i < pressed.length; i++) {
            pressed[i] = false;
            active[i] = false;
        }
        //@BasicIsoMovement#keyCodesAsign
        button[0] = left;
        button[1] = right;
        button[2] = up;
        button[3] = down;
    }

    public BasicIsoMovement(SixCanvas canv, GameObject target, KeyDefaults keyDef, float speedX, float speedY) {
        //@BasicIsoMovement
        super(canv);
        //@BasicIsoMovement#constructorSetters
        this.speedX = speedX;
        this.speedY = speedY;
        this.target = target;
        //@BasicIsoMovement#pressedActiveInit
        for (int i = 0; i < pressed.length; i++) {
            pressed[i] = false;
            active[i] = false;
        }
        //@BasicIsoMovement#keyCodesAsign
        button[0] = keyDef.getLeft();
        button[1] = keyDef.getRight();
        button[2] = keyDef.getUp();
        button[3] = keyDef.getDown();
    }
    
    public BasicIsoMovement(SixCanvas canv, GameObject target, KeyDefaultsType keyDefType, float speedX, float speedY) {
        //@BasicIsoMovement
        super(canv);
        //@BasicIsoMovement#constructorSetters
        this.speedX = speedX;
        this.speedY = speedY;
        this.target = target;
        //@BasicIsoMovement#pressedActiveInit
        for (int i = 0; i < pressed.length; i++) {
            pressed[i] = false;
            active[i] = false;
        }
        //@BasicIsoMovement#creatingKeyDefaults
        KeyDefaults keyDef = new KeyDefaults(keyDefType);
        //@BasicIsoMovement#keyCodesAsign
        button[0] = keyDef.getLeft();
        button[1] = keyDef.getRight();
        button[2] = keyDef.getUp();
        button[3] = keyDef.getDown();
    }
    
    @Override
    public void sixKeyPressed(KeyEvent e) {
        //@sixKeyPressed
        if(target!=null) {
            for (int i = 0; i < button.length; i++) {
                if (button[i] == e.getKeyCode()) {
                    pressed[i] = true;
                }
            }
            //@sixKeyPressed#leftButtonCheck
            if (pressed[0] && !active[0]) {
                active[0] = true;
                target.setVelX(target.getVelX() + (-1 * speedX));
            }
            //@sixKeyPressed#rightButtonCheck
            if (pressed[1] && !active[1]) {
                active[1] = true;
                target.setVelX(target.getVelX() + (speedX));
            }
            //@sixKeyPressed#upButtonCheck
            if (pressed[2] && !active[2]) {
                active[2] = true;
                target.setVelY(target.getVelY() - speedY);
            }
            //@sixKeyPressed#downButtonCheck
            if (pressed[3] && !active[3]) {
                active[3] = true;
                target.setVelY(target.getVelY() + (speedY));
            }
            target.keyAction(e , KeyActionType.pressed);
        }
    }

    @Override
    public void sixKeyReleased(KeyEvent e) {
        //@sixKeyReleased
        if(target!=null) { 
            for (int i = 0; i < button.length; i++) {
                if (button[i] == e.getKeyCode()) {
                    pressed[i] = false;
                }
            }
            //@sixKeyReleased#leftButtonCheck
            if (!pressed[0] && active[0]) {
                active[0] = false;
                target.setVelX(target.getVelX() + speedX);
            }
            //@sixKeyReleased#rightButtonCheck
            if (!pressed[1] && active[1]) {
                active[1] = false;
                target.setVelX(target.getVelX() - speedX);
            }
            //@sixKeyReleased#upButtonCheck
            if (!pressed[2] && active[2]) {
                active[2] = false;
                target.setVelY(target.getVelY() + speedX);
            }
            //@sixKeyReleased#downButtonCheck
            if (!pressed[3] && active[3]) {
                active[3] = false;
                target.setVelY(target.getVelY() - speedX);
            }
            target.keyAction(e , KeyActionType.released);
        }
    }

    @Override
    public void sixKeyTyped(KeyEvent arg0) {
        //@sixKeyTyped
    }

    public void reset() {
        //@reset
        for (int i = 0; i < pressed.length; i++) {
            pressed[i] = false;
            active[i] = false;
        }
    }
    
    //GETTERS SETTERS
    
    public void setTarget(GameObject target) {
        this.target = target;
    }

    public GameObject getTarget() {
        return target;
    }

    

}
