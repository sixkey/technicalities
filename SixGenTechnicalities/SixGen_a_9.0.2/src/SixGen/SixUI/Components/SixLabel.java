// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.SixUI.Components;

import SixGen.Utils.Utils.TextLocation;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
/**
 *
 * @author Filip
 */
public class SixLabel extends SixComponent{
    private String text;
    private Color backgroundColor;
    private Color foregroundColor;
    private BufferedImage texture;
    private Font font;
    private TextLocation textLocation;
    
    public SixLabel(float x , float y , float width , float height) { 
        super(x , y , width , height);
        text = "";
        backgroundColor = Color.green;
        foregroundColor = Color.white;
        texture = null;
        font = null;
        textLocation = TextLocation.center;
    }
    
    public SixLabel(float x , float y , float width , float height ,String text) { 
        super(x , y , width , height);
        this.text = text;
        backgroundColor = Color.black;
        foregroundColor = Color.white;
        texture = null;
        font = null;
        textLocation = TextLocation.center;
    }
    
    public SixLabel(float x , float y , float width , float height , String text , Font font , TextLocation textLocation) {
        super(x , y , width , height);
        this.text = text;
        backgroundColor = Color.black;
        foregroundColor = Color.white;
        texture = null;
        this.font = font;
        this.textLocation = textLocation;
    }
    
    public SixLabel(float x , float y , float width , float height , String text , Color backgroundColor , Color foregroundColor ,Font font , TextLocation textLocation) {
        super(x , y , width , height);
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        texture = null;
        this.font = font;
        this.textLocation = textLocation;
    }
    public SixLabel(float x , float y , float width , float height , String text, Color backgroundColor , Color foregroundColor) {
        super(x , y , width , height);
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        texture = null;
        this.font = null;
        this.textLocation = TextLocation.center;
    }
    public SixLabel(float x , float y , float width , float height , BufferedImage texture) { 
        super(x , y , width , height);
        text = null;
        backgroundColor = null;
        foregroundColor = null;
        this.texture = texture;
        this.font = null;
        this.textLocation = null;
    }
    public SixLabel(float x , float y ,BufferedImage texture) { 
        super(x,y,texture.getWidth() , texture.getHeight());
        text = null;
        backgroundColor = null;
        foregroundColor = null;
        this.texture = texture;
        this.font = null;
        this.textLocation = null;
    }
    
    @Override
    public void acRender(Graphics2D g) { 
        if(texture!=null) { 
            g.drawImage(texture, (int)x, (int)y, (int)width, (int)height, null);
        } else {
            g.setColor(backgroundColor);
            g.setComposite(getAlphaComposite(backgroundAlpha));
            g.fillRect((int)x, (int)y, (int)width, (int)height);
            g.setComposite(getAlphaComposite(foregroundAlpha));
            g.setColor(foregroundColor);
            if(font!=null) { 
                drawTextWithAlign(g, text, font , getTextBounds(), textLocation);
            } else { 
                drawTextWithAlign(g, text, g.getFont(), getTextBounds(), textLocation);
            }
        }
    }
    @Override
    public void acTick() { 
        
    }

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

    public float getTextMargin() {
        return textMargin;
    }

    public void setTextMargin(float textMargin) {
        this.textMargin = textMargin;
    }
    
    
    
}
