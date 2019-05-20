package SixGen.Handler.Managers;

import SixGen.GameObject.GameObject;
import SixGen.Handler.Handler;
import SixGen.Utils.ID;
import SixGen.Utils.IDException;
import SixGen.Utils.Utils;
import static SixGen.Utils.Utils.getLineIntersection;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.LinkedList;

/**
 * CollisionManager
 * Extends:
 *  Utils
 * Abilities:
 *  Takes care of collision
 *      If bounds of two objects intersetcs the checkedObject is moved and theese two objects are checked by objectCheck
 */

public class CollisionManager extends Utils implements HandlerManager{

    // VARIABLES
    
    // Switch that controls grounded statement of the objects that is currently under check
    protected boolean grounded;
    // Switch that controls jumping statement of the objects that is currently under check
    protected boolean jumping;

    //CONSTRUCTORS
    
    // if tow IDs are both on the collisionExcpetionList and objectCheckExceptionList collision manager is completely ignoring it
    // This list of IDEException (*see IDException for more information) that is used in collision
    // If two IDs are on this list than they don't collide so they can be at the same space but can be checked by objectCheck
    public LinkedList<IDException> collisionExceptionList = new LinkedList<IDException>();
    // This list of IDEException (*see IDException for more information) that is used in objectCheck
    // If two IDs are on this list than they are not checked by objectCheck but can collide
    public LinkedList<IDException> objectCheckExceptionList = new LinkedList<IDException>();
    
    // Boolean that signals if there is object in the top bounds of the object that is checked
    protected boolean topColl = false;
    // Boolean that signals if there is object in the bottom bounds of the object that is checked
    protected boolean bottomColl = false;
    // Boolean that signals if there is object in the left bounds of the object that is checked
    protected boolean leftColl = false;
    // Boolean that signals if there is object in the right bounds of the object that is checked
    protected boolean rightColl = false;
    
    protected LinkedList<Line2D.Double> renderLines;
    protected LinkedList<Rectangle> renderRects;
    protected Handler handler;
    
    //CONSTRUCTORS
    
    public CollisionManager(Handler handler) { 
        this.handler = handler;
        renderLines = new LinkedList<>();
        renderRects = new LinkedList<>();
    }
    
    //METHODS
    
    @Override
    public void tick(GameObject objects[]) {
        //@tick
        grounded = false;
        jumping = true;
        for (int i = 0; i < objects.length; i++) {
            // this object is checked ,if checkedObject and target collide this one is moved
            GameObject checkedObject = objects[i];
            for (int f = 0; f < objects.length; f++) {
                // this object is checked but if they collide this won't change
                GameObject target = objects[f];
                if(checkedObject!=target) {
//                    if (checkedObject.getBounds().intersects(target.getBounds())) {
                        if(checkedObject.isCollisionLock() == false && target.isCollisionFree() == false) { 
                            // checking IDs with the collistionCheckExceptioList
                            // if the duo of temp's and target's ID is on the list theese objects won't collide
                            boolean collisionSwitch = true;
                            for (int x = 0; x < collisionExceptionList.size(); x++) {
                                IDException CollExc = collisionExceptionList.get(x);
                                if (CollExc.checkObj(checkedObject , target)) {
                                    collisionSwitch = false;
                                    break;
                                }
                            }
                            if(collisionSwitch) {
                                collision(checkedObject, target);
                            }
                        }
                        if(checkedObject.isCollObjCheckFree() == false && target.isCollisionFree() == false) { 
                            // checking IDs with the objectCheckExceptioList
                            // if the duo of temp's and target's ID is on the list theese objects won't be checked
                            boolean objectSwitch = true;
                            for (int x = 0; x < objectCheckExceptionList.size(); x++) {
                                IDException CollExc = objectCheckExceptionList.get(x);
                                if (CollExc.checkObj(checkedObject,target)) {
                                    objectSwitch = false;
                                    break;
                                }
                            }
                            if(objectSwitch) {
                                objectCheck(checkedObject , target);
                            }
                        }
                    }
//                }
            }
            if(!jumping) {
                checkedObject.setJumping(jumping);
                checkedObject.setGrounded(grounded);
            }
        }
    }
    
    @Override
    public void render(Graphics2D g) {
        //@render
        g.setColor(Color.blue);
//        System.out.println("@CollisionManager" + renderLines.size());
        renderLines.forEach((l) -> { 
            g.draw(l);
        });
        g.setColor(Color.green);
        renderRects.forEach((r) -> { 
            g.draw(r);
        });
        renderRects = new LinkedList<>();
    }

    public void collision(GameObject checkedObject, GameObject target) {
        if(checkedObject!=null || target!=null ) {
            //@collision
            topColl = false;
            bottomColl = false;
            rightColl = false;
            leftColl = false;
            
            float x , y;
            
            if(checkedObject.getSafeState()!=null) {
                x = checkedObject.getSafeState().getCenterX();
                y = checkedObject.getSafeState().getCenterY();
            } else { 
                x = checkedObject.getCenterX();
                y = checkedObject.getCenterY();
            }
            
//            System.out.println("@CollisionManager : " + x);
            
            Line2D.Double trajectory = new Line2D.Double(   x ,
                                                            y ,
                                                            checkedObject.getX() + checkedObject.getWidth() / 2 ,   
                                                            checkedObject.getY() + checkedObject.getHeight() / 2);
            Rectangle newRect = floatRectangle (target.getX() - checkedObject.getWidth() / 2,
                                                target.getY() - checkedObject.getHeight() / 2,
                                                target.getWidth() + checkedObject.getWidth(),
                                                target.getHeight() + checkedObject.getHeight());
            
            renderRects.add(newRect);
            renderLines.add(trajectory);
            
            double rX = newRect.getX() + 1f;
            double rY = newRect.getY() + 1f;
            double rXX = newRect.getX() + newRect.getWidth() - 2;
            double rYY = newRect.getY() + newRect.getHeight() - 2;

            //top of the object
            if(target.isBounds(Direction.down)) {
                if(getLineIntersection(trajectory,new Line2D.Double(rX , rYY , rXX , rYY)) !=null) { 
                    topColl = true;
                }
            }
            //bottom of the object
            if(target.isBounds(Direction.up)) {
                if(getLineIntersection(trajectory,new Line2D.Double(rX, rY , rXX , rY)) !=null) { 
                    bottomColl = true;
                }
            }
            
            //right side of the object
            if(target.isBounds(Direction.left)) { 
                if(getLineIntersection(trajectory,new Line2D.Double(rX , rY , rX , rYY)) !=null) {
                    rightColl = true;
                }
            }
            //left side of the object
            if(target.isBounds(Direction.right)) { 
                if(getLineIntersection(trajectory,new Line2D.Double(rXX , rY , rXX , rYY)) !=null) { 
                    leftColl = true;
                }
            }
            
            
            // if the target intersetcs both top or one of the sides than it is considered 
//            collisionElimination(checkedObject , target);

            if (bottomColl) {
                bottomCollision(checkedObject , target);
            }
            if (topColl) {
                topCollision(checkedObject , target);
            }
            if (leftColl) {
                leftCollision(checkedObject , target);
            }
            if (rightColl) {
                rightCollision(checkedObject , target);
            }
        }
    }
    
    public void collisionElimination(GameObject checkedObject , GameObject target) {
        //@collisionElimination
        if(topColl & rightColl) { 
            if(     Math.abs(checkedObject.getSafeState().getCenterX() - (target.getX() + target.getWidth() / 2)) > 
                    Math.abs(checkedObject.getSafeState().getCenterY() - (target.getY() + target.getHeight() / 2))) { 
                topColl = false;
            } else { 
                rightColl = false;
            }
        }
        if(topColl & leftColl) { 
            if(     Math.abs(checkedObject.getSafeState().getCenterX() - (target.getX() + target.getWidth() / 2)) > 
                    Math.abs(checkedObject.getSafeState().getCenterY() - (target.getY() + target.getHeight() / 2))) { 
                topColl = false;
            } else { 
                leftColl = false;
            }
        }
        if(bottomColl & rightColl) { 
            if(     Math.abs(checkedObject.getSafeState().getCenterX() - (target.getX() + target.getWidth() / 2)) > 
                    Math.abs(checkedObject.getSafeState().getCenterY() - (target.getY() + target.getHeight() / 2))) { 
                bottomColl = false;
            } else { 
                rightColl = false;
            }
        }
        if(bottomColl & leftColl) {
            if(     Math.abs(checkedObject.getSafeState().getCenterX() - (target.getX() + target.getWidth() / 2)) > 
                    Math.abs(checkedObject.getSafeState().getCenterY() - (target.getY() + target.getHeight() / 2))) { 
                bottomColl = false;
            } else { 
                leftColl = false;
            }
        }
        
        if(topColl & bottomColl) { 
            if(checkedObject.getSafeState().getCenterY() < target.getY() + target.getHeight() / 2) { 
                topColl = false;
            } else { 
                bottomColl =false;
            }
        }
        if(leftColl & rightColl) { 
            if(checkedObject.getSafeState().getCenterX() < target.getX() + target.getWidth() / 2) { 
                rightColl = false;
            } else { 
                leftColl = false;
            }
        }
    }
    
    public void objectCheck(GameObject checkedObject , GameObject target) { 
        //@objectCheck
    }
    
    //COLLISIONS
    
    public void leftCollision(GameObject checkedObject , GameObject target) { 
        checkedObject.setX(target.getX() + target.getWidth());
    }
    public void rightCollision(GameObject checkedObject , GameObject target) { 
//        System.out.println("@CollisionManager : rightCollision #" + Math.random());
        checkedObject.setX(target.getX() - checkedObject.getWidth());
    }
    public void topCollision(GameObject checkedObject, GameObject target) { 
//        System.out.println("@CollisionManager : topCollision" + Math.random());
        checkedObject.setY(target.getY() + target.getHeight());
    }
    public void bottomCollision(GameObject checkedObject , GameObject target) {
//        System.out.println("@CollisionManager : bottomCollision" + Math.random());
        checkedObject.setY(target.getY() - checkedObject.getHeight());
    }
    
    // STANDARD COLLISION TEMPLATES
    
    public void stanLeftCollision(GameObject checkedObject , GameObject target) { 
        checkedObject.setX(target.getX() + target.getWidth());
    }
    public void stanRightCollision(GameObject checkedObject , GameObject target) { 
        checkedObject.setX(target.getX() - checkedObject.getWidth());
    }
    public void stanTopCollision(GameObject checkedObject, GameObject target) { 
        checkedObject.setY(target.getY() + target.getHeight());
    }
    public void stanBottomCollision(GameObject checkedObject , GameObject target) {
        checkedObject.setY(target.getY() - checkedObject.getHeight());
    }
    
    //GETTERS SETTERS
    
    public boolean checkObjectsDuo(GameObject checkedObject , GameObject target , ID id1 , ID id2) { 
        return target.getId() == id1 && checkedObject.getId() == id2 || target.getId() == id2 && checkedObject.getId() == id1; 
    }
    public boolean checkObjects(GameObject checkedObject , GameObject target , ID checkedObjectID , ID targetID) { 
        return checkedObject.getId() == checkedObjectID && target.getId() == targetID;
    }
    public void addCollExc(IDException gravExc) {
        collisionExceptionList.add(gravExc);
    }

    public void addCollExc(ID id1, ID id2) {
        collisionExceptionList.add(new IDException(id1, id2));
    }

    public void removeCollExc(IDException gravExc) {
        collisionExceptionList.remove(gravExc);
    }

    public void removeCollExc(ID id1, ID id2) {
        for(int i = 0 ; i < collisionExceptionList.size() ; i++) { 
            IDException e = collisionExceptionList.get(i);
            if(e.getId1() == id1 && e.getId2() == id2) { 
                collisionExceptionList.remove(e);
                i--;
            }
        }
    }

    public void addCollExcDuo(ID id1, ID id2) {
        collisionExceptionList.add(new IDException(id1, id2));
        collisionExceptionList.add(new IDException(id2, id1));
    }

    public void removeCollExcDuo(ID id1, ID id2) {
        for(int i = 0 ; i < collisionExceptionList.size() ; i++) { 
            IDException e = collisionExceptionList.get(i);
            if((e.getId1() == id1 && e.getId2() == id2) | (e.getId1() == id2 && e.getId2() == id1)) { 
                collisionExceptionList.remove(e);
                i--;
            }
        }
    }
    
    public void addObjChExc(IDException objExc) {
        objectCheckExceptionList.add(objExc);
    }

    public void addObjChExc(ID id1, ID id2) {
        objectCheckExceptionList.add(new IDException(id1, id2));
    }

    public void removeObjChExc(IDException objExc) {
        objectCheckExceptionList.remove(objExc);
    }

    public void removeObjChExc(ID id1, ID id2) {
        for(int i = 0 ; i < objectCheckExceptionList.size() ; i++) { 
            IDException e = objectCheckExceptionList.get(i);
            if(e.getId1() == id1 && e.getId2() == id2) { 
                objectCheckExceptionList.remove(e);
                i--;
            }
        }
    }

    public void addObjChExcDuo(ID id1, ID id2) {
        objectCheckExceptionList.add(new IDException(id1, id2));
        objectCheckExceptionList.add(new IDException(id2, id1));
    }

    public void removeObjChExcDuo(ID id1, ID id2) {
        for(int i = 0 ; i < objectCheckExceptionList.size() ; i++) { 
            IDException e = objectCheckExceptionList.get(i);
            if((e.getId1() == id1 && e.getId2() == id2) | (e.getId1() == id2 && e.getId2() == id1)) { 
                objectCheckExceptionList.remove(e);
                i--;
            }
        }
    }

}
