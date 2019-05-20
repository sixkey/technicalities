/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SixGen.Interfaces.Following;

import SixGen.Window.Camera.FollowType;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author filip
 */
public class Follower{
    
    private FollowingInstance follower, followed;
    private int offsetX;
    private int offsetY;
    private FollowType followType;
    private float followSpeed;
    
    private float targetX, targetY;
    
    public Follower(FollowingInstance follower, FollowingInstance followed) { 
        this.follower = follower;
        this.followed = followed;
        followType = FollowType.sharpObjFollow;
        followSpeed = 7f;
    }
    
    public void tick() {
        if (followed != null && followType == FollowType.smoothObjFollow) {
            //@cameraAdjust#smoothObjFollow
            follower.setX(follower.getX() + ((followed.getCenterX() - follower.getCenterX() + offsetX) * (followSpeed / 100)));
            follower.setY(follower.getY() + ((followed.getCenterY() - follower.getCenterY() + offsetY) * (followSpeed / 100)));
        } else if (followed!= null && followType == FollowType.sharpObjFollow) { 
            //@cameraAdjust#sharpObjFollow
            follower.setX(followed.getCenterX() - (follower.getCenterMassX()) + offsetX);
            follower.setY(followed.getCenterY() - (follower.getCenterMassY()) + offsetY);
        } else if (followType == FollowType.pointFollow) { 
            //@cameraAdjust#opintFollow
            follower.setX(follower.getX() + ((targetX - follower.getCenterX() + offsetX) * (followSpeed / 100)));
            follower.setY(follower.getY() + ((targetY - follower.getCenterY() + offsetY) * (followSpeed / 100)));
        } 
    }
    
    public void render(Graphics2D g) { 
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.green);
        g.drawLine((int)follower.getCenterX(), (int)follower.getCenterY(), (int)followed.getCenterX(), (int)followed.getCenterY());
    }
    
    public void setFollowType(FollowType followType) { 
        this.followType = followType;
    }
    public void setFollowSpeed(float followSpeed) { 
        this.followSpeed = followSpeed;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
    
    public void setTargetX(float targetX) { 
        this.targetX = targetX;
    }
    public void setTargetY(float targetY) { 
        this.targetY = targetY;
    }
    
}
