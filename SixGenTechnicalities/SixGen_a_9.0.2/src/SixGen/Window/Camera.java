package SixGen.Window;

import SixGen.GameObject.GameObject;
import SixGen.Interfaces.Following.Follower;
import SixGen.Interfaces.Following.FollowingInstance;
import SixGen.Utils.Utils;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Camera
 * Extends:
 *  Utils
 * Abilities:
 *  Holding x , y and giving it to the canvas for graphics tranlation 
 *  Moving by following target , to point or by vels
 *      to change the type of movement change Camera.FollowType 
 */

public class Camera extends Utils implements FollowingInstance{

    //VARIABLES
    //instance of follower object
    protected Follower follower;
    // x and y position that the camera is currently on
    protected float x, y;
    protected float shakeOffX, shakeOffY, shakeMagX, shakeMagY, shakeCounter;
    // x and y position that the camera will be moved in next render
    protected float virtualX , virtualY;
    // xVelocity and yVelocity
    protected float velX, velY;
    // x and y position that the camera will move to if the Camera.followType is set to FollowType.pointFollow
    protected float targetX , targetY;
    // Object that is followed by camera if the Camera.followType is set to FollowType.smoothObjFollow or FollowType.sharpObjFollo
    protected GameObject target;
    // Width and height of the camera that is used in calculation center point and in following
    protected float cameraWidth, cameraHeight;
    // Camera will move by this speed , speed is used in both pointFollow and smoothObjFollo
    protected float followSpeed;
    // Canvas that is adjustet by this camera
    protected SixCanvas canv;
    // Switch that controls ticking of the camera
    protected boolean tick = true;
    // Dimension of canvas
    protected float canvWidth , canvHeight;
    // Switch that controls if the camera is clamped to some dimensions. Camera is fixed to some bounds and cannot move begind them
    protected boolean clamp;
    
    // FollowType of the camera. See FollowType description for more info
    protected FollowType followType;

    // FollowType defines movement of the camera. See description of values for more info
    public enum FollowType { 
        //Follows Camera.target smoothly by getting speed at the beggining and loosing speed in the end
        smoothObjFollow ,
        //Follows Camera.target by moving it's center to the center of the object every tick.
        sharpObjFollow,
        //Follows targetX and targetY by moving smoothly 
        pointFollow , 
        //Camera is moved by vels (Camera.velX , Camera.velY) 
        vels, 
        //Camera doesn't move
        none;
    }
    
    //CONSTRUCTORS
    
    public Camera(float x, float y, float cameraWidth, float cameraHeight) {
        //@Camera #cameraBase
        this.x = x;
        this.y = y;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
        this.followSpeed = 5f;
        followType = FollowType.vels;
        clamp = true;
        refreshFollower();
    }

    public Camera(float x, float y, float cameraWidth, float cameraHeight, float followSpeed) {
        //@Camera
        this(x, y, cameraWidth, cameraHeight);
        this.followSpeed = followSpeed;
    }
    public Camera(float x , float y , float cameraWidth , float cameraHeight , SixCanvas canv) { 
        //@Camera
        this(x, y, cameraWidth, cameraHeight);
        this.canv = canv;
    }
    public Camera(float x , float y , float cameraWidth , float cameraHeight , SixCanvas canv ,float followSpeed) { 
        //@Camera
        this(x, y, cameraWidth, cameraHeight);
        this.canv = canv;
        this.followSpeed = followSpeed;
    }
    
    //VOIDS
    
    public void cameraAdjust() {
        //@cameraAdjust
        if(tick) {
            if (followType == FollowType.vels) { 
                //@cameraAdjust#vels
                x += velX;
                y += velY;
            } else if (followType == FollowType.none) { 
                //@cameraAdjust#none
                x = virtualX;
                y = virtualY;
            } else {
                follower.tick();
            }
            if(canv!=null && clamp) { 
                //@cameraAdjust#clamp
                x = clamp(0,canvWidth - cameraWidth , x);
                y = clamp(0,canvHeight - cameraHeight, y);
            }
            shakeMagX = shakeMagX / 1.1f;
            shakeMagY = shakeMagY / 1.1f;
            shakeOffX = (float) (Math.sin(Math.toRadians(shakeCounter)) * shakeMagX);
            shakeOffY = (float) (Math.sin(Math.toRadians(shakeCounter)) * shakeMagY);
        }
        shakeCounter = (shakeCounter + 10)%360;
    }
   
    public void refreshFollower() { 
        follower = new Follower(this, target);
        follower.setFollowSpeed(followSpeed);
        follower.setFollowType(followType);
        follower.setTargetX(targetX);
        follower.setTargetY(targetY);
    }
    
    //GETTERS SETTERS
    
    public Rectangle getBounds() { 
        return new Rectangle((int) x , (int) y , (int) cameraWidth , (int) cameraHeight);
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x + shakeOffX;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y + shakeOffY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
        refreshFollower();
    }

    public float getWidth() {
        return cameraWidth;
    }

    public void setWidth(float width) {
        this.cameraWidth = width;
    }

    public float getHeight() {
        return cameraHeight;
    }

    public void setHeight(float height) {
        this.cameraHeight = height;
    }
    public void setSixCanv(SixCanvas canv) { 
        this.canv = canv;
    }
    public SixCanvas getSixCanv() { 
        return canv;
    }
    public void setTick(boolean tick) { 
        this.tick =tick;
    }
    public boolean getTick() { 
        return tick;
    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
        follower.setTargetX(targetX);
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
        follower.setTargetY(targetY);
    }

    public float getCameraWidth() {
        return cameraWidth;
    }

    public void setCameraWidth(float cameraWidth) {
        this.cameraWidth = cameraWidth;
    }

    public float getCameraHeight() {
        return cameraHeight;
    }

    public void setCameraHeight(float cameraHeight) {
        this.cameraHeight = cameraHeight;
    }

    public float getFollowSpeed() {
        return followSpeed;
    }

    public void setFollowSpeed(float followSpeed) {
        this.followSpeed = followSpeed;
        follower.setFollowSpeed(followSpeed);
    }

    public FollowType getFollowType() {
        return followType;
    }

    public void setFollowType(FollowType followType) {
        this.followType = followType;
        follower.setFollowType(followType);
    }
    
    public void setTargetPoint(Point point) { 
        setTargetX((float)point.getX());
        setTargetY((float)point.getY());
    }
    
    public void setTargetPoint(float targetX , float targetY) {
        setTargetX(targetX);
        setTargetY(targetY);
    }
   
    public void setCanvasDimension(Dimension dim) { 
        this.canvWidth = (float)dim.getWidth();
        this.canvHeight = (float)dim.getHeight();
    }

    public void setCanvasDimension(int width , int height) { 
        this.canvWidth = width;
        this.canvHeight = height;
    }
    
    public void refreshDimensions() { 
        this.canvWidth = canv.getWidth();
        this.canvHeight = canv.getHeight();
    }
    
    public float getVirtualX() {
        return virtualX;
    }

    public void setVirtualX(float virtualX) {
        this.virtualX = virtualX;
    }

    public float getVirtualY() {
        return virtualY;
    }

    public void setVirtualY(float virtualY) {
        this.virtualY = virtualY;
    }

    public boolean isClamp() {
        return clamp;
    }

    public void setClamp(boolean clamp) {
        this.clamp = clamp;
    }
    
    public Point getCenterPoint() { 
        return new Point((int)(x + getWidth() / 2), (int)(y + getHeight() / 2));
    }
    
    public float getCenterX() {
        return x + cameraWidth / 2;
    }

    @Override
    public float getCenterY() {
        return y + cameraHeight / 2;
    }

    @Override
    public float getCenterMassX() {
        return cameraWidth / 2;
    }

    @Override
    public float getCenterMassY() {
        return cameraHeight / 2;
    }
    
    public void shake(int x, int y, int time) { 
        this.shakeMagX = x;
        this.shakeMagY = y;
    }
    
}
