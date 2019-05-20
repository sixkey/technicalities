/*
*  Created by SixKeyStudios
*  Added in project SixGen_a_9.0.0
*  File SixGen.Utils / Velocity
*  created on 16.10.2018 , 19:24:17 
 */
package SixGen.Utils;

/**
 *
 * @author filip
 */
public class Velocity extends Utils{ 
    private String title = "null";
    private float velX , velY;
    private boolean changed = false;   
    
    public Velocity(float velX , float velY) { 
        this.velX = velX;
        this.velY = velY;
    }
    public Velocity(float velX, float velY, String title) { 
        this.velX = velX;
        this.velY = velY;
        this.title = title;
    }
    public String getTitle() { 
        return title;
    }
    public float getVelX() {
        return velX;
    }
    public void setVelX(float velX) {
        this.velX = velX;
        changed = true;
    }
    public float getVelY() {
        return velY;
    }
    public void setVelY(float velY) {
        this.velY = velY;
        changed = true;
    }
    public Velocity setVels(float velX , float velY) { 
        this.velX = velX;
        this.velY = velY;
        changed = true;
        return this;
    }
    public double getAngle() { 
        return getAngle(velX, velY);
    }

    public void addVels(Velocity[] vel) {
        for(int i = 0 ; i < vel.length ; i++) { 
            this.setVelX(this.getVelX() + vel[i].getVelX());
            this.setVelY(this.getVelY() + vel[i].getVelY());
        }
    }
    
    public Velocity divide(float num) { 
        this.velX = velX / num;
        this.velY = velY / num;
        return this;
    }
    
    public Velocity add(float x, float y) { 
        this.velX += x;
        this.velY += y;
        return this;
    }
    public String toString() { 
        return "VELOCITY X: " + velX + "; Y: " + velY;
    }
    public void setChanged(boolean changed) { 
        this.changed = changed;
    }
    public boolean isChanged() { 
        return changed;
    }
}
