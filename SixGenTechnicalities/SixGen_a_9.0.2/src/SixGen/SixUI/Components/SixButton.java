package SixGen.SixUI.Components;

import SixGen.SixUI.SixAction;
import SixGen.Utils.Utils.TextLocation;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * SixButton
 * Extends : 
 *  SixComponent
 * Abilities :
 *  Button used in SixUI
 *  inherts basic functions of buttons
*/

public class SixButton extends SixComponent{
    
    //VARIABLES
    
    // text that is rendered on the button 
    protected String text;
    // color of the button background
    protected Color backgroundColor;
    // color of the text
    protected Color foregroundColor;
    // texture of the background
    protected BufferedImage texture;
    // font of the text
    protected Font font;
    // location of the text in the button *see TextLocation for more info
    protected TextLocation textLocation;
    
    //CONSTRUCTORS
    
    public SixButton(SixAction buttonAction ,float x , float y , float width , float height) { 
        //@SixButton
        super(x , y , width , height);
        //@SixButton#constructorSetters
        if(buttonAction!=null) { 
            actionList.add(buttonAction);
        }
        //@SixButton#defaultSetters
        text = "";
        backgroundColor = Color.green;
        foregroundColor = Color.white;
        texture = null;
        font = null;
        textLocation = TextLocation.center;
    }
    
    public SixButton(SixAction buttonAction ,float x , float y , float width , float height , String text) { 
        //@SixButton
        this(buttonAction , x , y , width , height);
        this.text = text;
    }
    
    public SixButton(SixAction buttonAction ,float x , float y , float width , float height , String text , Font font , TextLocation textLocation) {
        //@SixButton
        this(buttonAction , x , y , width , height);
        this.text = text;
        this.font = font;
        this.textLocation = textLocation;
    }
    
    public SixButton(   SixAction buttonAction ,float x , float y , float width , 
                        float height ,Color backgroundColor , Color foregroundColor ,
                        String text , Font font , TextLocation textLocation) {
        //@SixButton
        this(buttonAction , x , y , width , height);
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.text = text;
        this.font = font;
        this.textLocation = textLocation;
    }
    public SixButton(SixAction buttonAction ,float x , float y , float width , float height , String text, Color backgroundColor , Color foregroundColor) {
        //@SixButton
        this(buttonAction, x , y , width , height);
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.textLocation = TextLocation.center;
    
    }
    public SixButton(SixAction buttonAction,float x , float y ,BufferedImage texture) { 
        //@SixButton
        this(buttonAction,x,y,texture.getWidth() , texture.getHeight());
        this.texture = texture;
    }
    
    public SixButton(SixAction buttonAction ,float x , float y , float width , float height , BufferedImage texture) { 
        //@SixButton
        this(buttonAction, x , y , width , height);
        this.texture = rescale(texture, (int)width, (int)height);
    }
    
    //RENDER VOID
    
    @Override
    public void acRender(Graphics2D g) { 
        if(texture!=null) { 
            g.drawImage(texture, (int)x, (int)y, null);
        } else { 
            g.setColor(backgroundColor);
            g.fillRect((int)x ,(int)y , (int)width ,(int)height);
            g.setColor(foregroundColor);
            if(font!=null) { 
            drawTextWithAlign(g, text, font , getBounds(), textLocation);
            } else { 
                drawTextWithAlign(g, text, g.getFont(), getBounds(), textLocation);
            }
        }
    }
    
    //TICK VOID
    
    @Override
    public void acTick() { 
        
    }

    //GETTERS SETTERS
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public TextLocation getTextLocation() {
        return textLocation;
    }

    public void setTextLocation(TextLocation textLocation) {
        this.textLocation = textLocation;
    }

    public LinkedList<SixAction> getActionList() {
        return actionList;
    }

    public void setActionList(LinkedList<SixAction> actionList) {
        this.actionList = actionList;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
}
