package SixGen.SixUI;

import SixGen.SixUI.Components.SixComponent;
import SixGen.SixUI.Components.SixLabel;
import SixGen.SixUI.Components.SixButton;
import SixGen.Utils.ID;
import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class SixUI extends SixComponent{
    
    protected LinkedList<SixComponent> components = new LinkedList<SixComponent>();
    protected ID Id;
    protected Color backgroundColor;
    
    public enum AligmentType {
        horizontal, vertical;
    }

    public SixUI(ID uiId) {
        super(0 , 0 , 0 , 0);
        this.Id = uiId;
        backgroundAlpha = 1;
    }
    @Override
    public void acTick() { 
        if(visible) { 
            for(int i = 0; i < components.size();i++) { 
                components.get(i).tick();
            }
            addTick();
        }
    }
    public void acRender(Graphics2D g) {
        if(visible) { 
            Graphics2D g2d = (Graphics2D) g;
            if(backgroundColor!=null) { 
                g2d.setComposite(getAlphaComposite(backgroundAlpha * alpha));
                g.setColor(backgroundColor);
                g.fillRect((int)x , (int)y, (int)width, (int)height);
                g2d.setComposite(getAlphaComposite(1f));
            }
            for (int i = 0; i < components.size(); i++) {
                components.get(i).getAlphaComposite(alpha);
                components.get(i).render(g);
                components.get(i).getAlphaComposite(1);
            }
            addRender(g);
        }
    }
    
    
    public void addRender(Graphics g) {
        
    }
    public void addTick() { 
        
    }
    
    public void addComponent(SixComponent comp) {
        components.add(comp);
    }
    public void addComponentRelative(SixComponent comp) { 
        comp.setX(comp.getX() + this.getX());
        comp.setY(comp.getY() + this.getY());
        components.add(comp);
    }
    public void addComponent(SixComponent comp , boolean centered) { 
        if(centered) { 
            comp.setX(comp.getX() - comp.getWidth() / 2);
            comp.setY(comp.getY() - comp.getHeight() / 2);
        } 
        components.add(comp);
    }
    
    public void addComponent(SixComponent comp , boolean centeredX , boolean centeredY) { 
        if(centeredX) { 
            comp.setX(comp.getX() - comp.getWidth() / 2);
        }
        if(centeredY) {
            comp.setY(comp.getY() - comp.getHeight() / 2);
        } 
        components.add(comp);
    }

    public SixComponent getComponent(int i) {
        return components.get(i);
    }
    
    public SixComponent getComponent(Rectangle bounds) { 
        for(int i = 0 ; i < components.size() ; i++) { 
            if(components.get(i).getBounds().intersects(bounds)) { 
                return components.get(i);
            }
        }
        return null;
    }

    public int getComponentListSize() {
        return components.size();
    }
    
    public boolean mouseInBounds(MouseEvent e) { 
        return getBounds().contains(e.getPoint());
    }
    
    public boolean mouseAction(MouseEvent e, MouseActionType mat) {
        if(mat==MouseActionType.pressed || mat==MouseActionType.dragged) { 
            return realMouseAction(e , mat);
        } else {
            return false;
        }
    }

    public boolean realMouseAction(MouseEvent e , MouseActionType mat) { 
        Rectangle rect = new Rectangle(e.getX() - 1, e.getY() - 1, 3, 3);
        boolean result = false;
        for (int i = 0; i < components.size(); i++) {
            SixComponent temp = components.get(i);
            if (temp.getBounds().intersects(rect)) {
                if(temp.mouseAction(e , mat)) {
                    result = true;
                }
            }
        }
        return result;
    }
    
    public void AlignComponents(SixComponent component[], AligmentType type, int x, int y, int width, int height, int marginWidth, int marginHeight) {
        int amount = component.length;
        int buttonWidth, buttonHeight;
        if (type == AligmentType.vertical) {
            buttonWidth = width;
            buttonHeight = (height - marginHeight * (amount - 1)) / amount;
            for (int i = 0; i < amount; i++) {
                SixComponent temp = component[i];
                temp.setX(x);
                temp.setY((i * (buttonHeight + marginHeight)));
                temp.setWidth(buttonWidth);
                temp.setHeight(buttonHeight);
            }
        } else if (type == AligmentType.horizontal) {
            buttonWidth = (width - marginWidth * (amount - 1)) / amount;
            buttonHeight = height;
            for (int i = 0; i < amount; i++) {
                SixComponent temp = component[i];
                temp.setX((i * (buttonWidth + marginWidth)));
                temp.setY(y);
                temp.setWidth(buttonWidth);
                temp.setHeight(buttonHeight);
            }
        }
    }

    public void addButtonsWithAligment(AligmentType type, SixAction action[], int x, int y, int width, int height, int margin, boolean centered) {
        int amount = action.length;
        int buttonWidth, buttonHeight;
        if (type == AligmentType.vertical) {
            buttonWidth = width;
            buttonHeight = (height - margin * (amount - 1)) / amount;
            if (!centered) {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x, i * (buttonHeight + margin), buttonWidth, buttonHeight);
                    addComponent(button);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x - width / 2, i * (buttonHeight + margin) - height / 2, buttonWidth, buttonHeight);
                    addComponent(button);
                }
            }
        } else if (type == AligmentType.horizontal) {
            buttonWidth = (width - margin * (amount - 1)) / amount;
            buttonHeight = height;
            if (!centered) {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x + i * (buttonWidth + margin), y, buttonWidth, buttonHeight);
                    addComponent(button);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x + i * (buttonWidth + margin) - width / 2, y - height / 2, buttonWidth, buttonHeight);
                    addComponent(button);
                }
            }
        }
    }

    public void addButtonsWithAligment(AligmentType type, SixAction action[], int x, int y, float buttonWidth, float buttonHeight, int margin, boolean centered) {
        int amount = action.length;
        float width, height;
        if (type == AligmentType.vertical) {
            width = buttonWidth;
            height = buttonHeight * amount + (amount - 1) * margin;
            if (centered) {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x - width / 2, y + i * (buttonHeight + margin) - height / 2, buttonWidth, buttonHeight);
                    addComponent(button);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x, y + i * (buttonHeight + margin), buttonWidth, buttonHeight);
                    addComponent(button);
                }
            }
        } else if (type == AligmentType.horizontal) {
            width = buttonWidth * amount + (amount - 1) * margin;
            height = buttonHeight;
            if (centered) {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i],x + i * (buttonWidth + margin) - width / 2, y - height / 2, buttonWidth, buttonHeight);
                    addComponent(button);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i],x + i * (buttonWidth + margin), y, buttonWidth, buttonHeight);
                    addComponent(button);
                }
            }
        }
    }

    public void addButtonsWithAligment(AligmentType type, SixAction action[], String text[], int x, int y, int width, int height, int margin, boolean centered) {
        int amount = action.length;
        int buttonWidth, buttonHeight;
        if (type == AligmentType.vertical) {
            buttonWidth = width;
            buttonHeight = (height - margin * (amount - 1)) / amount;
            if (centered) {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x - width / 2, i * (buttonHeight + margin) + y - height /2, buttonWidth, buttonHeight, text[i]);
                    addComponent(button);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x, i * (buttonHeight + margin) + y, buttonWidth, buttonHeight, text[i]);
                    addComponent(button);
                }
            }
        } else if (type == AligmentType.horizontal) {
            buttonWidth = (width - margin * (amount - 1)) / amount;
            buttonHeight = height;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin) + x - width / 2, y - height /2, buttonWidth, buttonHeight, text[i]);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin) + x, y, buttonWidth, buttonHeight, text[i]);
                    addComponent(button);
                }
            }
        }
    }

    public void addButtonsWithAligment(AligmentType type, SixAction action[], String text[], int x, int y, float buttonWidth, float buttonHeight, int margin , boolean centered) {
        int amount = action.length;
        float width , height;
        if (type == AligmentType.vertical) {
            width = buttonWidth;
            height = buttonHeight * amount + (amount - 1) * margin;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x - width / 2, i * (buttonHeight + margin) + y - height /2 , buttonWidth, buttonHeight, text[i]);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x, i * (buttonHeight + margin) + y, buttonWidth, buttonHeight, text[i]);
                    addComponent(button);
                }
            }
        } else if (type == AligmentType.horizontal) {
            width = amount * buttonWidth + (amount - 1) * margin;
            height = buttonHeight;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin) + x - width / 2, y - height/2, buttonWidth, buttonHeight, text[i]);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin) + x, y, buttonWidth, buttonHeight, text[i]);
                    addComponent(button);
                }
            }
        }
    }

    public void addButtonsWithAligment(AligmentType type, SixAction action[], String text[], Color backgroundColor, Color foregroundColor, int x, int y, int width, int height, int margin, boolean centered) {
        int amount = action.length;
        int buttonWidth, buttonHeight;
        if (type == AligmentType.vertical) {
            buttonWidth = width;
            buttonHeight = (height - margin * (amount - 1)) / amount;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x - width / 2, i * (buttonHeight + margin) + y - height / 2, buttonWidth, buttonHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x, i * (buttonHeight + margin) + y, buttonWidth, buttonHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(button);
                }
            }
        } else if (type == AligmentType.horizontal) {
            buttonWidth = (width - margin * (amount - 1)) / amount;
            buttonHeight = height;
            if(centered ) { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin) + x - width /2 , y - height / 2, buttonWidth, buttonHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin) + x, y, buttonWidth, buttonHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(button);
                }
            }
        }
    }

    public void addButtonsWithAligment(AligmentType type, SixAction action[], String text[], Color backgroundColor, Color foregroundColor, int x, int y, float buttonWidth, float buttonHeight, int margin , boolean centered) {
        int amount = action.length;
        float width  , height;
        if (type == AligmentType.vertical) {
            width = buttonWidth;
            height = amount * buttonHeight + (amount - 1) * margin;
            if(centered ) {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x - width / 2, i * (buttonHeight + margin) + y - height / 2, buttonWidth, buttonHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x, i * (buttonHeight + margin) + y, buttonWidth, buttonHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(button);
                }
            }
        } else if (type == AligmentType.horizontal) {
            width = amount * buttonWidth + (amount - 1) * margin;
            height = buttonHeight;
            if(centered ) {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin) + x - width / 2, y - height / 2, buttonWidth, buttonHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin) + x, y, buttonWidth, buttonHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(button);
                }
            }
        }
    }

    public void addButtonsWithAligment(AligmentType type, SixAction action[], BufferedImage texture[], int x, int y, int width, int height, int margin , boolean centered) {
        int amount = action.length;
        int buttonWidth, buttonHeight;
        if (type == AligmentType.vertical) {
            buttonWidth = width;
            buttonHeight = (height - margin * (amount - 1)) / amount;
            if(centered ) { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x - width / 2, i * (buttonHeight + margin) - height / 2, buttonWidth, buttonHeight, texture[i]);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x, i * (buttonHeight + margin), buttonWidth, buttonHeight, texture[i]);
                    addComponent(button);
                }
            }
        } else if (type == AligmentType.horizontal) {
            buttonWidth = (width - margin * (amount - 1)) / amount;
            buttonHeight = height;
            if(centered) {
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin) - width / 2, y - height / 2, buttonWidth, buttonHeight, texture[i]);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], i * (buttonWidth + margin), y, buttonWidth, buttonHeight, texture[i]);
                    addComponent(button);
                }
            }
        }
    }

    public void addButtonsWithAligment(AligmentType type, SixAction action[], BufferedImage texture[], int x, int y, float buttonWidth, float buttonHeight, int margin , boolean centered ) {
        int amount = action.length;
        float width , height;
        if (type == AligmentType.vertical) {
            width = buttonWidth;
            height = amount * buttonHeight + (amount - 1) * margin;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x - width /2 , i * (buttonHeight + margin) - height / 2, buttonWidth, buttonHeight, texture[i]);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x, i * (buttonHeight + margin), buttonWidth, buttonHeight, texture[i]);
                    addComponent(button);
                }
            }
        } else if (type == AligmentType.horizontal) {
            width = amount * buttonWidth + (amount - 1) * margin;
            height = buttonHeight;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x + i * (buttonWidth + margin) - width / 2, y - height / 2, buttonWidth, buttonHeight, texture[i]);
                    addComponent(button);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixButton button = new SixButton(action[i], x + i * (buttonWidth + margin), y, buttonWidth, buttonHeight, texture[i]);
                    addComponent(button);
                }
            }
        }
    }

    public void addLabelsWithAligment(AligmentType type, int amount, int x, int y, int width, int height, int margin , boolean centered) {
        int labelWidth, labelHeight;
        if (type == AligmentType.vertical) {
            labelWidth = width;
            labelHeight = (height - margin * (amount - 1)) / amount;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x - width / 2, i * (labelHeight + margin) - height / 2, labelWidth, labelHeight);
                    addComponent(label);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x, i * (labelHeight + margin), labelWidth, labelHeight);
                    addComponent(label);
                }
            }
            
        } else if (type == AligmentType.horizontal) {
            labelWidth = (width - margin * (amount - 1)) / amount;
            labelHeight = height;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) - width / 2, y - height / 2, labelWidth, labelHeight);
                    addComponent(label);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin), y, labelWidth, labelHeight);
                    addComponent(label);
                }
            }
            
        }
    }
    
    public void addLabelsWithAligment(AligmentType type, int amount, int x, int y, float labelWidth , float labelHeight, int margin , boolean centered) {
        float width , height;
        if (type == AligmentType.vertical) {
            width = labelWidth;
            height = amount * labelHeight + (amount - 1) * margin;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x - width / 2, i * (labelHeight + margin) - height / 2, labelWidth, labelHeight);
                    addComponent(label);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x, i * (labelHeight + margin), labelWidth, labelHeight);
                    addComponent(label);
                }
            }
            
        } else if (type == AligmentType.horizontal) {
            width = amount * labelWidth + (amount -1 ) * margin;
            height = labelHeight;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x + i * (labelWidth + margin) - width / 2, y - height / 2, labelWidth, labelHeight);
                    addComponent(label);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x + i * (labelWidth + margin), y, labelWidth, labelHeight);
                    addComponent(label);
                }
            }
            
        }
    }

    public void addLabelsWithAligment(AligmentType type, String text[], int x, int y, int width, int height, int margin , boolean centered) {
        int amount = text.length;
        int labelWidth, labelHeight;
        if (type == AligmentType.vertical) {
            labelWidth = width;
            labelHeight = (height - margin * (amount - 1)) / amount;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x - width /2 , i * (labelHeight + margin) + y - height / 2    , labelWidth, labelHeight, text[i]);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x, i * (labelHeight + margin) + y, labelWidth, labelHeight, text[i]);
                    addComponent(label);
                }
            }
        } else if (type == AligmentType.horizontal) {
            labelWidth = (width - margin * (amount - 1)) / amount;
            labelHeight = height;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) + x - width / 2, y - height / 2, labelWidth, labelHeight, text[i]);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) + x, y, labelWidth, labelHeight, text[i]);
                    addComponent(label);
                }
            }
        }
    }
    
    public void addLabelsWithAligment(AligmentType type, String text[], int x, int y, float labelWidth , float labelHeight, int margin , boolean centered) {
        int amount = text.length;
        float width  , height;
        if (type == AligmentType.vertical) {
            width = labelWidth;
            height = labelHeight * amount + (amount - 1) * margin;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x - width /2 , i * (labelHeight + margin) + y - height / 2    , labelWidth, labelHeight, text[i]);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x, i * (labelHeight + margin) + y, labelWidth, labelHeight, text[i]);
                    addComponent(label);
                }
            }
        } else if (type == AligmentType.horizontal) {
            width = labelWidth * amount + (amount - 1) * margin;
            height = labelHeight;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) + x - width / 2, y - height / 2, labelWidth, labelHeight, text[i]);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) + x, y, labelWidth, labelHeight, text[i]);
                    addComponent(label);
                }
            }
        }
    }

    public void addLabelsWithAligment(AligmentType type, String text[], Color backgroundColor, Color foregroundColor, int x, int y, int width, int height, int margin , boolean centered) {
        int amount = text.length;
        int labelWidth, labelHeight;
        if (type == AligmentType.vertical) {
            labelWidth = width;
            labelHeight = (height - margin * (amount - 1)) / amount;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x - width / 2, i * (labelHeight + margin) + y - height / 2, labelWidth, labelHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x , i * (labelHeight + margin) + y, labelWidth, labelHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(label);
                }
            }
        } else if (type == AligmentType.horizontal) {
            labelWidth = (width - margin * (amount - 1)) / amount;
            labelHeight = height;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) + x - width / 2, y - height / 2, labelWidth, labelHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) + x, y, labelWidth, labelHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(label);
                }
            }
        }
    }

     public void addLabelsWithAligment(AligmentType type, String text[], Color backgroundColor, Color foregroundColor, int x, int y, float labelWidth , float labelHeight, int margin , boolean centered) {
        int amount = text.length;
        float width , height;
        if (type == AligmentType.vertical) {
            width = labelWidth;
            height = labelHeight * amount + (amount - 1) * margin;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x - width / 2, i * (labelHeight + margin) + y - height / 2, labelWidth, labelHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x , i * (labelHeight + margin) + y, labelWidth, labelHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(label);
                }
            }
        } else if (type == AligmentType.horizontal) {
            width = labelWidth * amount + (amount - 1) * margin;
            height = labelHeight;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) + x - width / 2, y - height / 2, labelWidth, labelHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) + x, y, labelWidth, labelHeight, text[i], backgroundColor, foregroundColor);
                    addComponent(label);
                }
            }
        }
    }
    
    public void addLabelsWithAligment(AligmentType type, BufferedImage texture[], int x, int y, int width, int height, int margin , boolean centered) {
        int amount = texture.length;
        int labelWidth, labelHeight;
        if (type == AligmentType.vertical) {
            labelWidth = width;
            labelHeight = (height - margin * (amount - 1)) / amount;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x - width / 2, i * (labelHeight + margin) - height / 2, labelWidth, labelHeight, texture[i]);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x, i * (labelHeight + margin), labelWidth, labelHeight, texture[i]);
                    addComponent(label);
                }
            }
        } else if (type == AligmentType.horizontal) {
            labelWidth = (width - margin * (amount - 1)) / amount;
            labelHeight = height;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) - width / 2, y - height / 2, labelWidth, labelHeight, texture[i]);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin), y, labelWidth, labelHeight, texture[i]);
                    addComponent(label);
                }
            }
        }
    }
    
    public void addLabelsWithAligment(AligmentType type, BufferedImage texture[], int x, int y, float labelWidth , float labelHeight, int margin , boolean centered) {
        int amount = texture.length;
        float width , height;
        if (type == AligmentType.vertical) {
            width = labelWidth;
            height = labelHeight * amount + (amount - 1 ) * margin; 
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x - width / 2, i * (labelHeight + margin) - height / 2, labelWidth, labelHeight, texture[i]);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(x, i * (labelHeight + margin), labelWidth, labelHeight, texture[i]);
                    addComponent(label);
                }
            }
        } else if (type == AligmentType.horizontal) {
            width = labelWidth * amount + (amount - 1) * margin;
            height = labelHeight;
            if(centered) { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin) - width / 2, y - height / 2, labelWidth, labelHeight, texture[i]);
                    addComponent(label);
                }
            } else { 
                for (int i = 0; i < amount; i++) {
                    SixLabel label = new SixLabel(i * (labelWidth + margin), y, labelWidth, labelHeight, texture[i]);
                    addComponent(label);
                }
            }
        }
    }

    public void setFontSize(int size) { 
        for(SixComponent c : components) { 
            c.setFontSize(size);
        }
    }
    
    public void setFont(Font font) { 
        for(SixComponent c : components) { 
            c.setFont(font);
        }
    }
    
    public void translateContent(int x , int y) { 
        for(SixComponent c : components) { 
            c.setX(c.getX() + x);
             c.setY(c.getY() + y);
        }
    }
    @Override
    public void setX(float x) {
        float delta = x - this.x;
        for(SixComponent c : components) { 
            c.setX(c.getX() + delta);
        }
        this.x = x;
    }
    @Override
    public void setY(float y) {
        float delta = y - this.y;
        for(SixComponent c : components) { 
            c.setY(c.getY() + delta);
        }
        this.y = y;
    }
    public int getWidthFromComponents() { 
        float xx = components.get(0).getXX();
        for(int i = 1 ; i < components.size() ; i++) { 
            SixComponent temp = components.get(i);
            if(temp.getXX() > xx) { 
                xx = temp.getXX();
            }
        }
        return (int)Math.abs(x - xx);
    }
    public int getHeightFromComponents() { 
        float yy = components.get(0).getYY();
        for(int i = 1 ; i < components.size() ; i++) { 
            SixComponent temp = components.get(i);
            if(temp.getYY() > yy) { 
                yy = temp.getYY();
            }
        }
        return (int)Math.abs(y - yy);
    }
    public int getXFromComponents() { 
        float x = components.get(0).getX();
        for(int i = 1 ; i < components.size() ; i++) { 
            SixComponent temp = components.get(i);
            if(temp.getX() < x) { 
                x = temp.getX();
            }
        }
        return (int)x;
    }
    public int getYFromComponents() { 
        float x = components.get(0).getY();
        for(int i = 1 ; i < components.size() ; i++) { 
            SixComponent temp = components.get(i);
            if(temp.getX() < x) { 
                x = temp.getX();
            }
        }
        return (int)x;
    }
    public Rectangle getBoundsFromComponents() { 
        SixComponent c = components.get(0);
        float x = c.getX();
        float y = c.getY();
        float xx = c.getXX();
        float yy = c.getYY();
        for(int i = 1 ; i < components.size() ; i++) { 
            SixComponent temp = components.get(i);
            if(temp.getX() < x) { 
                x = temp.getX();
            }
            if(temp.getY() < y) { 
                y = temp.getY();
            }
            if(temp.getXX() > xx) { 
                xx = temp.getXX();
            }
            if(temp.getYY() > yy) { 
                yy = temp.getYY();
            }
        }
        return new Rectangle((int)x, (int)y, (int)Math.abs(xx - x), (int)Math.abs(yy - y));
    }
    @Override
    public boolean isVisible() {
        return visible;
    }
    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        for(int i = 0 ; i < components.size() ; i++) { 
            components.get(i).setVisible(visible);
        }
    }
    public ID getId() {
        return Id;
    }
    
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    @Override
    public float getAlpha() {
        return alpha;
    }
    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
