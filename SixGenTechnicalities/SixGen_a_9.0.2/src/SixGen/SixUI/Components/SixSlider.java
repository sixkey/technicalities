//tree evolution
package SixGen.SixUI.Components;

import SixGen.Events.Refreshable;
import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.SixUI.SixUI.AligmentType;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 *
 * @author Filip
 */
public class SixSlider extends SixComponent{
    
    private Rectangle sliderBounds;
    private Rectangle barBounds;
    private Color sliderColor;
    private Color barColor;
    private Color textColor;
    private String text;
    private Font font;
    private float sliderRelPosition;
    private boolean active;
    private AligmentType type;
    private Refreshable refreshable;
    
    /**
     * @param refreshable Refresh interface (class implement)
     * @param type (vertical or horizontal)
     * @param x x location of the slider
     * @param y y location of the slider
     * @param barWidth width of the bar (if horizontal - height else width)
     * @param barLength length of the bar (if horizonta - width else height)
     * @param sliderWidth width of the slider (if horizontal - height else width)
     * @param sliderLength length of the bar (if horizontal - width else height)
     * @param barColor color of the bar
     * @param sliderColor color of the slider
     * @param textColor color of the text on the slider
     */
    public SixSlider(  Refreshable refreshable, AligmentType type, int x , int y , int barWidth , 
                    int barLength , int sliderWidth , int sliderLength,
                    Color barColor , Color sliderColor, Color textColor) {
        
        super(x , y , 0 , 0);
        this.type = type;
        if(type == AligmentType.horizontal) { 
            barBounds = new Rectangle(x , y - barWidth / 2 , barLength , barWidth);
            sliderBounds = new Rectangle(x - sliderLength / 2 , y + barWidth / 2 - sliderWidth / 2, sliderLength , sliderWidth);
        } else if(type == AligmentType.vertical) { 
            barBounds = new Rectangle(      x - barWidth / 2, y , barWidth , barLength);
            sliderBounds = new Rectangle(   x - sliderWidth / 2 , y + barLength / 2 - sliderLength / 2 , sliderWidth , sliderLength);
        }
        //colors
        this.sliderColor = sliderColor;
        this.barColor = barColor;
        this.textColor = textColor;
        //bounds
        this.width = getWidth();
        this.height = getHeight();
        
        sliderRelPosition = sliderLength / 2;
        this.refreshable = refreshable;
       
    }
    @Override
    public boolean mouseAction(MouseEvent e , MouseActionType mat) { 
        if(visible) { 
            if(mat == MouseActionType.pressed | mat == MouseActionType.dragged) { 
                if(type == AligmentType.horizontal) { 
                    if  (   
                            ((sliderBounds.contains(e.getPoint()) || barBounds.contains(e.getPoint())) || active) &
                            e.getX() >= barBounds.getX() &
                            e.getX() <= barBounds.getX() + barBounds.getWidth()
                        ) {
                        active = true;
                        setSliderPosition(e.getX());
                        return true;
                    }
                } else { 
                    if  (   
                            ((sliderBounds.contains(e.getPoint()) | barBounds.contains(e.getPoint())) | active) &
                            e.getY() >= barBounds.getY() &
                            e.getY() <= barBounds.getY() + barBounds.getHeight()
                        ) {
                        active = true;
                        setSliderPosition(e.getY());
                        return true;
                    }
                }
            } else if(mat == MouseActionType.released) { 
                active = false;
            }
        } else { 
            active = false;
        }
        return false;
    }
    
    public void acRender(Graphics2D g) {
        g.setColor(barColor);
        g.fill(barBounds);
        g.setColor(sliderColor);
        g.fill(sliderBounds);
        g.setColor(textColor);
        if(text!=null) { 
            g.setFont(font);
            drawCenteredString(g, text, sliderBounds);
        }
    }
    
    public void setSliderPosition(float value) { 
        if(type == AligmentType.horizontal) { 
            if(value < barBounds.getX()) { 
                value = (int) barBounds.getX();
            } else if(value > barBounds.getX() + barBounds.getWidth()) { 
                value = (int)(barBounds.getX() + barBounds.getWidth());
            }
            sliderRelPosition = value - (int)barBounds.getX();
            sliderBounds.x = (int)(value - sliderBounds.getWidth() / 2); 
        } else { 
            if(value < barBounds.getY()) { 
                value = (int) barBounds.getY();
            } else if(value > barBounds.getY() + barBounds.getHeight()) { 
                value = (int)(barBounds.getY() + barBounds.getHeight());
            }
            sliderRelPosition = value - (int)barBounds.getY();
            sliderBounds.y = (int)(value - sliderBounds.getHeight() / 2); 
        }
        refreshable.refresh();
    }
    public void setRelativeSliderPosition(float value) { 
        if(type == AligmentType.horizontal) {
            if(value < 0) { 
                value = 0;
            } else if(value > barBounds.getWidth()) { 
                value = (int)(barBounds.getWidth());
            }
            sliderRelPosition = value;
            sliderBounds.x = (int)(value + barBounds.getX() - sliderBounds.getWidth() / 2);
        } else { 
            if(value < 0) { 
                value = 0;
            } else if(value > barBounds.getHeight()) { 
                value = (int)(barBounds.getHeight());
            }
            sliderRelPosition = value;
            sliderBounds.y = (int)(value + barBounds.getY() - sliderBounds.getHeight() / 2);
        }
        refreshable.refresh();
    }
    public void translateSliderPosition(float value) { 
        setSliderPosition(sliderRelPosition + value);
        refreshable.refresh();
    }
    public void moveLeft(int arraySize) { 
        if(arraySize > 0) { 
            if(type == AligmentType.horizontal) { 
                translateSliderPosition((float)-(barBounds.getWidth() / arraySize));
            } else { 
                translateSliderPosition((float)-(barBounds.getHeight() / arraySize));
            }
        }
        refreshable.refresh();
    }
    public void moveRight(int arraySize) { 
        if(arraySize > 0) {
            if(type == AligmentType.horizontal) { 
                translateSliderPosition((float)(barBounds.getWidth() / arraySize));
            } else { 
                translateSliderPosition((float)(barBounds.getHeight() / arraySize));
            }
        } 
        refreshable.refresh();
    }
    public double getDouble() { 
        if(type == AligmentType.horizontal) { 
            return (double)((int)(sliderRelPosition) / ((float)(int)barBounds.getWidth()));
        } else { 
            return (double)((int)(sliderRelPosition) / ((float)(int)barBounds.getHeight()));
        }
    }
    public void setDouble(double d) {
        if(type == AligmentType.horizontal) { 
            setRelativeSliderPosition((float)(barBounds.getWidth() * d));
        } else { 
            setRelativeSliderPosition((float)(barBounds.getHeight() * d));
        }
    }
    
    public void setText(int id , int amount) { 
        this.text = (id + 1 + " | " + amount);
    }
    public float getX() { 
        return (int)(barBounds.getX() - sliderBounds.getWidth() / 2);
    }
    public float getY() { 
        return (int)(barBounds.getY() - sliderBounds.getHeight() / 2);
    }
    @Override
    public float getHeight() {
        return Math.max((int)(sliderBounds.getHeight()), (int)(barBounds.getHeight()));
    }
    @Override
    public float getWidth() { 
        return (float)Math.max(barBounds.getWidth(), sliderBounds.getWidth());
    }

    @Override
    public void setX(float x) { 
        float relative = x - getX();
        barBounds.x+=relative;
        sliderBounds.x+=relative;
    }
    
    @Override
    public void setY(float y) { 
        float relative = y - this.getY();
        barBounds.y+=relative;
        sliderBounds.y+=relative;
    }

    @Override
    public void acTick() {
    }

    public Color getSliderColor() {
        return sliderColor;
    }

    public void setSliderColor(Color sliderColor) {
        this.sliderColor = sliderColor;
    }

    public Color getBarColor() {
        return barColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Rectangle getSliderBounds() {
        return sliderBounds;
    }

    public void setSliderBounds(Rectangle sliderBounds) {
        this.sliderBounds = sliderBounds;
    }

    public Rectangle getBarBounds() {
        return barBounds;
    }

    public void setBarBounds(Rectangle barBounds) {
        this.barBounds = barBounds;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getSliderRelPosition() {
        return sliderRelPosition;
    }

    public void setSliderRelPosition(float sliderRelPosition) {
        this.sliderRelPosition = sliderRelPosition;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AligmentType getType() {
        return type;
    }

    public void setType(AligmentType type) {
        this.type = type;
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)(getX()), (int)getY(), (int)getWidth(), (int)getHeight());
    }
            
    
    
}
