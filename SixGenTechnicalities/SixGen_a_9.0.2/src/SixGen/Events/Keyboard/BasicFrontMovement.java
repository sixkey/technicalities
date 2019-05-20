package SixGen.Events.Keyboard;

import SixGen.Events.Keyboard.KeyDefaults;
import SixGen.Window.SixCanvas;
import SixGen.Events.Keyboard.SixAbstractKeyListener;
import SixGen.GameObject.GameObject;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * BasicFrontMovement 
 * Extends: 
 *  SixAbstractKeyListener
 * Abilities: 
 *  Movement used in games with render type front
 *      Key asigned for bottom doesn't work
 *      Movement works for one player
*/

public class BasicFrontMovement extends SixAbstractKeyListener {

    //VARIABLES
    
    //GameObject that is targetet by this movement. All velocity changes caused by keys are translated to this object
    protected GameObject target;
    //Index - Meaning , 0 - left , 1 - right , 2 - up , 3 - down
    //Booleans that signals if the buttons was pressed
    protected boolean[] pressed = new boolean[4];
    //Booleans that signals if the buttons are pressed 
    protected boolean[] activated = new boolean[4];
    //KeyCode for every Button
    protected int[] button = new int[4];
    //Value of speed that is added or subtracted by pressing or releasing button
    protected float speedX, speedY;
    
    protected boolean active;

    //CONTRUSTORS
    
    public BasicFrontMovement(SixCanvas canv, GameObject target, int left, int right, int up, int down, float speedX, float speedY) {
        //@BasicFrontMovement
        super(canv);
        //@BasicFrontMovement#constructorsSetters
        this.speedX = speedX;
        this.speedY = speedY;
        this.target = target;
        //@BasicFrontMovement#pressedActiveInit
        for (int i = 0; i < pressed.length; i++) {
            pressed[i] = false;
            activated[i] = false;
        }
        //@BasixFrontMovement#keyCodesAsign
        button[0] = left;
        button[1] = right;
        button[2] = up;
        button[3] = down;
    }
    
    public BasicFrontMovement(SixCanvas canv, GameObject target, KeyDefaults keyDefaults, float speedX, float speedY) {
        //@BasicFrontMovement
        super(canv);
        //@BasicFrontMovement#constructorSetters
        this.speedX = speedX;
        this.speedY = speedY;
        this.target = target;
        //@BasicFrontMovement#pressedActiveInit
        for (int i = 0; i < pressed.length; i++) {
            pressed[i] = false;
            activated[i] = false;
        }
        //@BasicFrontMovement#keyCodesAsign
        button[0] = keyDefaults.getLeft();
        button[1] = keyDefaults.getRight();
        button[2] = keyDefaults.getUp();
        button[3] = keyDefaults.getDown();
    }
    
    public BasicFrontMovement(SixCanvas canv, GameObject target, KeyDefaultsType keyDefaultsType, float speedX, float speedY) {
        //@BasicFrontMovement
        super(canv);
        //@BasicFrontMovement#constructorSetters
        this.speedX = speedX;
        this.speedY = speedY;
        this.target = target;
        //@BasicFrontMovement#pressedActiveInit
        for (int i = 0; i < pressed.length; i++) {
            pressed[i] = false;
            activated[i] = false;
        }
        //@BasicFrontMovement#creatingKeyDefaults
        KeyDefaults keyDefaults = new KeyDefaults(keyDefaultsType);
        //@BasicFrontMovement#keyCodesAsign
        button[0] = keyDefaults.getLeft();
        button[1] = keyDefaults.getRight();
        button[2] = keyDefaults.getUp();
        button[3] = keyDefaults.getDown();
    }
    
    @Override
    public void sixKeyPressed(KeyEvent e) {
        //@sixKeyPressed
        if (target != null & active) {
            if (target.getHandled()) {
                for (int i = 0; i < button.length; i++) {
                    if (button[i] == e.getKeyCode()) {
                        pressed[i] = true;
                    }
                }
                //@sixKeyPressed#leftButtonCheck
                if (pressed[0] && !activated[0]) {
                    activated[0] = true;
                    target.setVelX(target.getVelX() + (-1 * speedX));
                }
                //@sixKeyPressed#rightButtonCheck
                if (pressed[1] && !activated[1]) {
                    activated[1] = true;
                    target.setVelX(target.getVelX() + (speedX));
                }
                //@sixKeyPressed#upButtonCheck
                if (pressed[2] && !target.isJumping() && target.isGrounded() && !activated[2]) {
                    activated[2] = true;
                    target.setJumping(true);
                    target.setVelY(target.getVelY() - speedY);
                }
                target.keyAction(e, KeyActionType.pressed);
            }
        }
    }

    @Override
    public void sixKeyReleased(KeyEvent e) {
        //@sixKeyReleased
        if (target != null & active) {
            if (target.getHandled()) {
                for (int i = 0; i < button.length; i++) {
                    if (button[i] == e.getKeyCode()) {
                        pressed[i] = false;
                    }
                }
                //@sixKeyReleased#leftButtonCheck
                if (e.getKeyCode() == button[0] && activated[0]) {
                    activated[0] = false;
                    target.setVelX(target.getVelX() + speedX);
                }
                //@sixKeyReleased#rightButtonCheck
                if (e.getKeyCode() == button[1] && activated[1]) {
                    activated[1] = false;
                    target.setVelX(target.getVelX() - speedX);
                }
                //@sixKeyReleased#upButtonCheck
                if (e.getKeyCode() == button[2]) {
                    activated[2] = false;
                }
                target.keyAction(e, KeyActionType.released);
            }
        }
    }
    
    @Override
    public void sixKeyTyped(KeyEvent arg0) {
        //@sixKeyTyped
    }
    
    public void reset() {
        //@reset
        for (int i = 0; i < pressed.length; i++) {
            target = null;
            pressed[i] = false;
            activated[i] = false;
        }
    }
    
    //GETTERS SETTERS
    
    public void setTarget(GameObject obj) {
        target = obj;
    }

    public GameObject getTarget() {
        return target;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean getActive() { 
        return active;
    }
    public void toggleActive() { 
        active = !active;
    }
}
