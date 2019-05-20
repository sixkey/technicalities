/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SixGen.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Filip
 */
public class MovingPath {
    
    private Point[] points;
    private Point targetPoint;
    private boolean[] reached;
    private boolean done;
    private int accMiss;
    
    public MovingPath(Point[] points) { 
        this.points = points;
        this.reached = new boolean[points.length];
        done = false;
        accMiss = 10;
        for(int i = 0 ; i < reached.length ; i++) { 
            reached[i] = false;
        }
        if(points.length > 0) {
            targetPoint = points[0];
        } else {
            done = true;
        }
    }
    public void tick(GameObject object) {
        if(!done) {
            if(isReached(targetPoint , object.getCenterPoint())) { 
                int id = findPointID(targetPoint);
                reached[id] = true;
                if(id < points.length - 1) { 
                    targetPoint = points[id + 1];
                } else {
                    done = true;
                }
            } 
            object.moveTowards(targetPoint);
        } else { 
            object.stop();
        }
    }
    
    public void render(Graphics g) { 
        g.setColor(Color.RED);
        if(points.length>1) { 
            for(int i = 0 ; i < points.length - 1 ; i++) { 
                g.drawLine((int)points[i].getX(), (int)points[i].getY(), (int)points[i+1].getX(), (int)points[i+1].getY());
            }  
        }
    }
    
    private Point findPoint(Point point) { 
        for(Point p : points) {
            if(p == point) { 
                return p;
            }
        }
        return null;
    }
    
    private int findPointID(Point point) { 
        for(int i = 0 ; i < points.length ; i++) {
            if(points[i] == point) { 
                return i;
            }
        }
        return -1;
    }
    
    private boolean isReached(Point t , Point o) { 
        return o.getX() > t.getX() - accMiss && o.getX() < t.getX() + accMiss 
               &&
               o.getY() > t.getY() - accMiss && o.getY() < t.getY() + accMiss;
    }
    
    public Point getPoint(int i) { 
        return points[i];
    }
    public void setPoint(int i , Point point) { 
        this.points[i] = point;
    }
    public void setReached(int i , boolean reached) { 
        this.reached[i] = reached;
    }
    public boolean getReached(int i) { 
        return reached[i];
    }

    public Point[] getPoint() {
        return points;
    }

    public void setPoint(Point[] point) {
        this.points = point;
    }

    public int getAccMiss() {
        return accMiss;
    }

    public void setAccMiss(int accMiss) {
        this.accMiss = accMiss;
    }
    
    public boolean isDone() { 
        return done;
    }
    public String toString() { 
        String result = "MOVING PATH\n";
        for(int i = 0 ; i < points.length; i++) { 
            result+=points[i].toString() + "\n";
        }
        return result;
    }
    
    public MovingPath translate(int x, int y) { 
        for(Point p: points) { 
            p.x = p.x + x;
            p.y = p.y + y;
        }
        return new MovingPath(points);
    }
}
