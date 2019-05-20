package SixGen.SixUI.Animation;

/**
 *
 * @author Filip
 */
public interface GUIAnimation {
    
    public enum AnimationType { 
        flying , fadeIn , fadeOut;
    }
    
    public AnimationType getAnimationType();
    public void setAnimationType(AnimationType animationType);
    
    public float getTargetX();
    public float getTargetY();
    
    public void setTargetX(float targetX);
    public void setTargetY(float targetY);
    
    public float getX();
    public float getY();
    
    public void setX(float x);
    public void setY(float y);
    
    public void setAlpha(float alpha);
    public float getAlpha();
}

