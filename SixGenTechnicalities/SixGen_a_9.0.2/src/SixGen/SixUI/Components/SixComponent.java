// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.SixUI.Components;

import SixGen.SixUI.Animation.GUIAnimation;
import SixGen.SixUI.SixAction;
import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.SixUI.Animation.GUIAnimation;
import SixGen.SixUI.SixAction;
import SixGen.Utils.Utils;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 *
 * @author Filip
 */
public abstract class SixComponent extends Utils implements GUIAnimation {
    
    protected int fontSize;
    protected Font font;
    protected float x , y;
    protected float width , height;
    protected float textMargin;
    protected float alpha;
    protected float backgroundAlpha;
    protected float foregroundAlpha;
    protected LinkedList<SixAction> actionList = new LinkedList<SixAction>();
    protected boolean visible = true;
    protected AnimationType animationType;
    protected float targetX , targetY;
    
    public SixComponent(float x , float y , float width , float height) { 
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        textMargin = 0;
        backgroundAlpha = 1;
        foregroundAlpha = 1;
        alpha = 1;
    }
    
    public abstract void acRender(Graphics2D g);
    public abstract void acTick();
    
    public void render(Graphics2D g) {
        if(visible) { 
            g.setComposite(getAlphaComposite(alpha));
            acRender(g);
            g.setComposite(getAlphaComposite(1f));
        }
    }
    public void tick() { 
        if(visible) {
            acTick();
        }
    }
    
    public boolean mouseAction(MouseEvent e, MouseActionType mat) {
        if(visible & mat==MouseActionType.pressed) {
            for(int i = 0 ; i < actionList.size() ; i++) { 
                actionList.get(i).action();
            }
        }
        return visible;
    }
    public Rectangle getBounds() {
        return new Rectangle((int)x ,(int)y , (int)width ,(int)height);
    }
    public Rectangle getTextBounds() { 
        return new Rectangle((int)(x + textMargin) , (int) y , (int) width ,(int) height);
    }
    
    public void translate(int x , int y) { 
        setX(this.x + x);
        setY(this.y + y);
    }

    @Override
    public float getX() {
        return x;
    }
    @Override
    public void setX(float x) {
        this.x = x;
    }
    @Override
    public float getY() {
        return y;
    }
    @Override
    public void setY(float y) {
        this.y = y;
    }
    public float getXX() { 
        return x + width;
    }
    public float getYY() { 
        return y + height;
    }
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setBounds(int x , int y , int width, int height) { 
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void setBounds(Rectangle r) { 
        this.x = (float)r.getX();
        this.y = (float)r.getY();
        this.width = (float)r.getWidth();
        this.height = (float)r.getHeight();
    }
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public void toggleVisible() {
        setVisible(!visible);
    }
    public void setFontSize(int fontSize) { 
        this.fontSize = fontSize;
    }
    public int getFontSize() { 
        return fontSize;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
    
    
    public AnimationType getAnimationType() { 
        return animationType;
    }
    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }
    public float getTargetX() {
        return targetX;
    }
    public float getTargetY() { 
        return targetY;
    }
    public void setTargetX(float targetX) {
        this.targetX = targetX;
    } 
    public void setTargetY(float targetY) { 
        this.targetY = targetY;
    }
    
    @Override
    public float getAlpha() {
        return alpha;
    }
    
    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getBackgroundAlpha() {
        return backgroundAlpha;
    }

    public void setBackgroundAlpha(float backgroundAlpha) {
        this.backgroundAlpha = backgroundAlpha;
    }

    public float getForegroundAlpha() {
        return foregroundAlpha;
    }

    public void setForegroundAlpha(float foregroundAlpha) {
        this.foregroundAlpha = foregroundAlpha;
    }
    
    
}
