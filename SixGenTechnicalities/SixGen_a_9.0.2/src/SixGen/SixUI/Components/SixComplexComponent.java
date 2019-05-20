// Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.SixUI.Components;

import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Filip
 */
public class SixComplexComponent extends SixComponent {

    public enum GridCompChange{ 
        non , resize , move;
    }
    
    private LinkedList<LinkedList<Cell>> cellRow = new LinkedList<LinkedList<Cell>>();
    
    private Color bg;
    private BufferedImage bgImage;
    private boolean transparent;

    private float marginX , marginY , borderMarginX , borderMarginY;
    private float rows , columns;
    private SixLayout layout;
    private GridCompChange gridCompChange = GridCompChange.non;
    
    public SixComplexComponent(float x, float y) {
        super(x, y, 0, 0);
        layout = SixLayout.free;
        bg = Color.black;
        transparent = false;
    }

    public SixComplexComponent(float x, float y, float width, float height) {
        super(x, y, width, height);
        layout = SixLayout.free;
        bg = Color.black;
        transparent = false;
    }

    public SixComplexComponent(float x, float y, float width, float height, float marginX ,float marginY) {
        super(x, y, width, height);
        layout = SixLayout.free;
        bg = Color.black;
        transparent = false;
        this.marginX = marginX;
        this.marginY = marginY;
    }

    public void addComp(SixComponent comp , int x , int y , int width , int height) {
        if(comp!=null) { 
            SixLayout lay = this.layout;
            if(lay == SixLayout.vertical) {
                checkCompRow(y);
                cellRow.get(y).add(new Cell(comp , x , y , width , height));
                rows = cellRow.size();
                columns = 1;
                refreshGrid();
            } else if(lay == SixLayout.horizontal) { 

            } else if(lay == SixLayout.free) { 
                
            }
        }
    }

    @Override
    public void acRender(Graphics2D g) {
        if(!transparent) { 
            if(bgImage!=null) { 
                g.drawImage(bgImage, (int)x,(int) y, (int)width , (int)height , null);
            } else { 
                g.setColor(bg);
                g.fillRect((int)x, (int)y, (int)width, (int)height);
            }
        }
        for (int i = 0; i < cellRow.size(); i++) {
            for(int f = 0 ; f < cellRow.get(i).size(); f++) { 
                cellRow.get(i).get(f).getComp().render(g);
            }
        }
    }

    @Override
    public void acTick() {
        for (int i = 0; i < cellRow.size(); i++) {
            for(int f = 0; f < cellRow.get(i).size() ; f++) { 
                cellRow.get(i).get(f).getComp().tick();
            }
        }
    }

    public void packAndLocate() {
        float minX, minY, maxX, maxY;

        SixComponent first = cellRow.get(0).get(0).getComp();

        minX = first.getX();
        minY = first.getY();
        maxX = first.getX() + first.getWidth();
        maxY = first.getY() + first.getHeight();

        for (int i = 0; i < cellRow.size(); i++) {
            for(int f = 0; f < cellRow.get(i).size(); f++) {
                SixComponent temp = cellRow.get(i).get(f).getComp();
                if (temp.getX() < minX) {
                    minX = temp.getX();
                } else if (temp.getX() + temp.getWidth() > maxX) {
                    maxX = temp.getX() + temp.getWidth();
                }
                if (temp.getY() < minY) {
                    minY = temp.getY();
                } else if (temp.getY() + temp.getHeight() > maxY) {
                    maxY = temp.getY() + temp.getHeight();
                }
            }
        }
        x = minX;
        y = minY;
        width = maxX - minX;
        height = maxY - minY;
    }

    public void pack() {
        if(cellRow.size() != 0 ) { 
            float minX, minY, maxX, maxY;

            SixComponent first = cellRow.get(0).get(0).getComp();

            minX = first.getX();
            minY = first.getY();
            maxX = first.getX() + first.getWidth();
            maxY = first.getY() + first.getHeight();
            
            for (int i = 0; i < cellRow.size(); i++) {
                for(int f = 0; f < cellRow.get(i).size() ; f++) { 
                    SixComponent temp = cellRow.get(i).get(f).getComp();
                    if (temp.getX() < minX) {
                        minX = temp.getX();
                    } else if (temp.getX() + temp.getWidth() > maxX) {
                        maxX = temp.getX() + temp.getWidth();
                    }
                    if (temp.getY() < minY) {
                        minY = temp.getY();
                    } else if (temp.getY() + temp.getHeight() > maxY) {
                        maxY = temp.getY() + temp.getHeight();
                    }
                }
            }
            width = maxX - minX + borderMarginX * 2;
            height = maxY - minY + borderMarginY * 2;
        }
    }

    public void refreshGridCompChange() { 
        
    }
    
    public void refreshGrid() { 
        if(cellRow.size()!=0) {
            LinkedList<Integer> maxWidths = new LinkedList<Integer>();
            float maxHeight = 0;
            for(int i = 0 ; i < cellRow.size() ; i++) {
                for(int f = 0 ; f < cellRow.get(i).size() ;f++) {
                    Cell tempCell = cellRow.get(i).get(f);
                    SixComponent temp = tempCell.getComp();
                    if(temp.getHeight() > maxHeight) { 
                        maxHeight = temp.getHeight();
                    }
                    int xValue = tempCell.getX() + tempCell.getWidthSpam();
                    for(int x = 0; x < xValue - maxWidths.size();x++) { 
                        maxWidths.add(new Integer(0));
                    }
                }
            }
        }
    }
    
    public void checkCompRow(float row) { 
        row++;
        if(cellRow.size() == 0) { 
            for(int i = 0 ; i < row;i++ ) { 
                cellRow.add(new LinkedList<Cell>());
            }
        } else {
            for(int i = 0 ; i < row - cellRow.size();i++ ) { 
                cellRow.add(new LinkedList<Cell>());
            }
        }
         
    }
    
    public void setBackgroundColor(Color bg) { 
        this.bg = bg;
    }
    public Color getBackgroundColor() { 
        return bg;
    }
    public void setTransparent(boolean transparent) { 
        this.transparent = transparent;
    }
    public boolean isTransparent() { 
        return transparent;
    }

    public float getMarginX() {
        return marginX;
    }

    public void setMarginX(float marginX) {
        this.marginX = marginX;
    }

    public float getMarginY() {
        return marginY;
    }

    public void setMarginY(float marginY) {
        this.marginY = marginY;
    }

    public float getRows() {
        return rows;
    }

    public void setRows(float rows) {
        this.rows = rows;
    }

    public float getColumns() {
        return columns;
    }

    public void setColumns(float columns) {
        this.columns = columns;
    }

    public SixLayout getLayout() {
        return layout;
    }

    public void setLayout(SixLayout layout) {
        this.layout = layout;
    }

    public BufferedImage getBackgroundImage() {
        return bgImage;
    }

    public void setBackgroundImage(BufferedImage bgImage) {
        this.bgImage = bgImage;
    }

    public float getBorderMarginX() {
        return borderMarginX;
    }

    public void setBorderMarginX(float borderMarginX) {
        this.borderMarginX = borderMarginX;
    }

    public float getBorderMarginY() {
        return borderMarginY;
    }

    public void setBorderMarginY(float borderMarginY) {
        this.borderMarginY = borderMarginY;
    }
    @Override
    public boolean mouseAction(MouseEvent e , MouseActionType mat) { 
        Rectangle rect = new Rectangle(e.getX() - 1, e.getY() - 1, 3, 3);
        boolean result = false;
        for (int i = 0; i < cellRow.size(); i++) {
            for(int f = 0 ; f < cellRow.get(i).size();f++ ) {
                SixComponent temp = cellRow.get(i).get(f).getComp();
                if (temp.getBounds().intersects(rect)) {
                    if(temp.mouseAction(e , mat)) { 
                        result = true;
                    }
                }
            }
        }
        return result;
    }
    
    public class Cell { 
        public int x , y , widthSpam , heightSpam;
        public SixComponent comp;
        public Cell(SixComponent comp , int x , int y , int widthSpam ,int heightSpam) { 
            this.comp = comp;
            this.x = x;
            this.y = y;
            this.widthSpam = widthSpam;
            this.heightSpam = heightSpam;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWidthSpam() {
            return widthSpam;
        }

        public void setWidthSpam(int widthSpam) {
            this.widthSpam = widthSpam;
        }

        public int getHeightSpam() {
            return heightSpam;
        }

        public void setHeightSpam(int heightSpam) {
            this.heightSpam = heightSpam;
        }

        public SixComponent getComp() {
            return comp;
        }

        public void setComp(SixComponent comp) {
            this.comp = comp;
        }
        
    }
}
