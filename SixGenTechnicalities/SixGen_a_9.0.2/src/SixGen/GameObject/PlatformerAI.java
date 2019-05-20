    // Based on SixGenEngine version 1.2
// Created by SixKeyStudios
package SixGen.GameObject;

import SixGen.Handler.Handler;
import SixGen.Utils.Utils.Direction;
import SixGen.Utils.ID;
import SixGen.Utils.Utils;
import SixGen.SixUI.SixAction;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

/**
 * PlatformerAI
 * Abilities:
 *  Has a basic AI (Artificial Inteligence) for platforemer Games
 *      Ai that can run towards player , from palyer or don't care about the player
 */

public class PlatformerAI extends Utils {

    //VARIABLES
    
    public enum PlatformerAIType {
        passive, defensive, aggressive;
    }

    public enum PlatformerAIMoveType {
        moving , randomMoving, jumping, stay, horizontalFlying , verticalFlying , verHorFlying , randomFlying;
    }
    
    protected Handler handler;
    protected GameObject parent;
    protected GameObject target;

    protected PlatformerAIType AIType;
    protected PlatformerAIMoveType AIMoveType;
    protected AIDef aiDef;

    protected float blindnessRad = 200;
    protected float ranMovRad = 200;
    protected float ranMovAccMiss = 3;
    protected float ranMovStayTimeMax = 2000;
    protected float ranMovStayTimeMin = 1000;
    protected float velX, velY;

    protected boolean chasing = false;
    
    protected Direction direction;

    protected float checkRectsWidth;
    protected float checkRectsHeight;

    protected Rectangle leftCheck, rightCheck, topCheck , bottomCheck , leftBottomCheck, rightBottomCheck, leftTopCheck, rightTopCheck;

    protected float connectionX, connectionY, connectionXX, connectionYY;
    protected boolean connectionSwc;

    protected LinkedList<ID> ignoreList;
    protected LinkedList<SixAction> actions = new LinkedList<SixAction>();
    
    
    public PlatformerAI(GameObject parent, Handler handler, PlatformerAIType AIType, PlatformerAIMoveType AIMoveType) {
        this.AIType = AIType;
        this.AIMoveType = AIMoveType;
        this.handler = handler;
        this.parent = parent;
        this.target = null;
        checkRectsWidth = parent.getWidth();
        checkRectsHeight = parent.getHeight();
        this.ignoreList = new LinkedList<ID>();
        AIInit();
    }

    public PlatformerAI(GameObject parent, GameObject target, Handler handler, PlatformerAIType AIType, PlatformerAIMoveType AIMoveType) {
        this.AIType = AIType;
        this.AIMoveType = AIMoveType;
        this.handler = handler;
        this.parent = parent;
        this.target = target;
        checkRectsWidth = parent.getWidth();
        checkRectsHeight = parent.getHeight();
        this.ignoreList = new LinkedList<ID>();
        AIInit();
    }

    public void refreshCheckRectsBoundsToParent() { 
        checkRectsWidth = parent.getWidth();
        checkRectsHeight = parent.getHeight();
    }
    
    private void AIInit() {
        
        updateFull();
        
        if (AIMoveType == PlatformerAIMoveType.moving) {
            initMoving();
        } else if(AIMoveType == PlatformerAIMoveType.horizontalFlying) {
            initHFlying();
        } else if(AIMoveType == PlatformerAIMoveType.verticalFlying) { 
            initVFlying();
        } else if(AIMoveType == PlatformerAIMoveType.randomFlying) { 
            initRandomFlying();
        }
    }
    
    public void initMoving() { 
        aiDef = new AIDef() {
            @Override
            public void tick() {
                updateCorners();
                updateHSides();
                boolean chasing = false;
                if (AIType == PlatformerAIType.aggressive && target != null && handler.isHandled(target)) {
                    float d = getDiagonal(parent, target);
                    if (d < blindnessRad) {
                        chasing = true;
                        connectionSwc = true;
                        connectionX = parent.getCenterX();
                        connectionY = parent.getCenterY();
                        connectionXX = target.getCenterX();
                        connectionYY = target.getCenterY();

                        if (connectionX - connectionXX > 0) {
                            parent.setVelX(velX * -1);
                            direction = Direction.left;
                        } else {
                            parent.setVelX(velX);
                            direction = Direction.right;
                        }
                    } else {
                        connectionSwc = false;
                    }
                } else if (AIType == PlatformerAIType.defensive && target != null && handler.isHandled(target)) {
                    float d = getDiagonal(parent, target);
                    if (d < blindnessRad) {
                        chasing = true;
                        connectionSwc = true;
                        connectionX = parent.getCenterX();
                        connectionY = parent.getCenterY();
                        connectionXX = target.getCenterX();
                        connectionYY = target.getCenterY();

                        if (connectionX - connectionXX > 0) {
                            parent.setVelX(velX);
                            direction = Direction.right;
                        } else {
                            parent.setVelX(velX * -1);
                            direction = Direction.left;
                        }
                    } else {
                        connectionSwc = false;
                        chasing = false;
                    }
                }
                if (direction == Direction.left) {
                    GameObject leftBottomCheckObject = handler.findObjectViaBounds(leftBottomCheck);
                    GameObject leftCheckObject = handler.findObjectViaBounds(leftCheck);
                    if (isIgnored(leftCheckObject)) {
                        leftCheckObject = null;
                    }
                    if (isIgnored(leftBottomCheckObject)) {
                        leftBottomCheckObject = null;
                    }
                    if ((leftBottomCheckObject == null | leftCheckObject != null) & leftBottomCheckObject != leftCheckObject & leftCheckObject != target) {
                        if (chasing) {
                            parent.setVelX(0);
                        } else {
                            direction = Direction.right;
                            parent.setVelX(velX);
                        }
                    }
                } else if (direction == Direction.right) {
                    GameObject rightBottomCheckObject = handler.findObjectViaBounds(rightBottomCheck);
                    GameObject rightCheckObject = handler.findObjectViaBounds(rightCheck);
                    if (isIgnored(rightCheckObject)) {
                        rightCheckObject = null;
                    }
                    if (isIgnored(rightBottomCheckObject)) {
                        rightBottomCheckObject = null;
                    }
                    if ((rightBottomCheckObject == null | rightCheckObject != null) & rightBottomCheckObject != rightCheckObject & rightCheckObject != target) {
                        if (chasing) {
                            parent.setVelX(0);
                        } else {
                            direction = Direction.left;
                            parent.setVelX(velX * -1);
                        }
                    }
                }
            }
        };
    }

    public void initHFlying() { 
        aiDef = new AIDef() {
            @Override
            public void init() { 
                parent.setVelX(5);
                parent.setGravityLock(true);
            }
            @Override
            public void tick() { 
                updateHSides();
                if (AIType == PlatformerAIType.aggressive && target != null && handler.isHandled(target)) {
                    float d = getDiagonal(parent, target);
                    if (d < blindnessRad) {
                        chasing = true;
                        connectionSwc = true;
                        connectionX = parent.getCenterX();
                        connectionY = parent.getCenterY();
                        connectionXX = target.getCenterX();
                        connectionYY = target.getCenterY();
                        parent.moveTowards(connectionXX, connectionYY, velX);
                    } else {
                        connectionSwc = false;
                        chasing = false;
                    }
                } else if (AIType == PlatformerAIType.defensive && target != null && handler.isHandled(target)) {
                    float d = getDiagonal(parent, target);
                    if (d < blindnessRad) {
                        chasing = true;
                        connectionSwc = true;
                        connectionX = parent.getCenterX();
                        connectionY = parent.getCenterY();
                        connectionXX = target.getCenterX();
                        connectionYY = target.getCenterY();
                        float targetX = connectionX + (connectionX - connectionXX);
                        float targetY = connectionY + (connectionY - connectionYY);
                        parent.moveTowards(targetX, targetY, velX);
                    } else {
                        connectionSwc = false;
                        chasing = false;
                    }
                } else {
                    chasing = false;
                    connectionSwc = false;
                }
                if(!chasing) { 
                    if (direction == Direction.left) {
                        GameObject leftCheckObject = handler.findObjectViaBounds(leftCheck);
                        if (isIgnored(leftCheckObject)) {
                            leftCheckObject = null;
                        }
                        if (leftCheckObject != null & leftCheckObject != target) {
                            direction = Direction.right;
                            parent.setVelX(velX);
                        }
                    } else if (direction == Direction.right) {
                        GameObject rightCheckObject = handler.findObjectViaBounds(rightCheck);
                        if (isIgnored(rightCheckObject)) {
                            rightCheckObject = null;
                        }
                        if (rightCheckObject != null & rightCheckObject != target) {
                            direction = Direction.left;
                            parent.setVelX(velX * -1);
                        }
                    }
                }
            }
        };
    }
    
    public void initVFlying() {
        aiDef = new AIDef() {
            @Override
            public void init() { 
                parent.setGravityLock(true);
                parent.setVelY(5);
                direction = Direction.down;
            }
            @Override
            public void tick() { 
                topCheck = new Rectangle((int) (parent.getX()) , (int) (parent.getY() - checkRectsHeight) , (int)(checkRectsWidth),(int)(checkRectsHeight));
                bottomCheck = new Rectangle((int) (parent.getX()) , (int)(parent.getY() + parent.getHeight()) ,(int) (checkRectsWidth) , (int)(checkRectsHeight));
                if (AIType == PlatformerAIType.aggressive && target != null && handler.isHandled(target)) {
                    float d = getDiagonal(parent, target);
                    if (d < blindnessRad) {
                        chasing = true;
                        connectionSwc = true;
                        connectionX = parent.getCenterX();
                        connectionY = parent.getCenterY();
                        connectionXX = target.getCenterX();
                        connectionYY = target.getCenterY();
                        parent.moveTowards(connectionXX, connectionYY, velX);
                    } else {
                        connectionSwc = false;
                        chasing = false;
                    }
                } else if (AIType == PlatformerAIType.defensive && target != null && handler.isHandled(target)) {
                    float d = getDiagonal(parent, target);
                    if (d < blindnessRad) {
                        chasing = true;
                        connectionSwc = true;
                        connectionX = parent.getCenterX();
                        connectionY = parent.getCenterY();
                        connectionXX = target.getCenterX();
                        connectionYY = target.getCenterY();
                        float targetX = connectionX + (connectionX - connectionXX);
                        float targetY = connectionY + (connectionY - connectionYY);
                        parent.moveTowards(targetX, targetY, velX);
                    } else {
                        connectionSwc = false;
                        chasing = false;
                    }
                } else {
                    chasing = false;
                    connectionSwc = false;
                }
                if(!chasing) { 
                    if (direction == Direction.up) {
                        GameObject topCheckObject = handler.findObjectViaBounds(topCheck);
                        if (isIgnored(topCheckObject)) {
                            topCheckObject = null;
                        }
                        if (topCheckObject != null & topCheckObject != target) {
                            direction = Direction.down;
                            parent.setVelY(velY);
                        }
                    } else if (direction == Direction.down) {
                        GameObject bottomCheckObject = handler.findObjectViaBounds(bottomCheck);
                        if (isIgnored(bottomCheckObject)) {
                            bottomCheckObject = null;
                        }
                        if (bottomCheckObject != null & bottomCheckObject != target) {
                            direction = Direction.up;
                            parent.setVelY(velY * -1);
                        }
                    }
                }
            }
        };
    }
    
    public void initRandomFlying() { 
        aiDef = new AIDef() { 
            float targetX , targetY;
            float lastX , lastY;
            double lastTime , waitTime , now;
            boolean waiting;
            boolean chasing;
            boolean returning;
            @Override
            public void init() { 
                parent.setGravityLock(true);
                parent.setVelX(0);
                parent.setVelY(0);
                targetX = 0;
                targetY = 0;
                lastTime = System.currentTimeMillis();
                generateTarget();
                generateWaitTime();
                waiting = true;
                chasing = false;
                System.out.println(lastTime);
            }
            @Override
            public void tick() {
                updateSides();
                if (AIType == PlatformerAIType.aggressive && target != null && handler.isHandled(target)) {
                    float d = getDiagonal(parent, target);
                    if (d < blindnessRad) {
                        chasing = true;
                        waiting = false;
                        connectionSwc = true;
                        connectionX = parent.getCenterX();
                        connectionY = parent.getCenterY();
                        connectionXX = target.getCenterX();
                        connectionYY = target.getCenterY();
                        parent.moveTowards(connectionXX, connectionYY, velX);
                    } else {
                        chasing = false;
                        connectionSwc = false;
                    }
                } else if (AIType == PlatformerAIType.defensive && target != null && handler.isHandled(target)) {
                    float d = getDiagonal(parent, target);
                    if (d < blindnessRad) {
                        chasing = true;
                        connectionSwc = true;
                        connectionX = parent.getCenterX();
                        connectionY = parent.getCenterY();
                        connectionXX = target.getCenterX();
                        connectionYY = target.getCenterY();
                        float targetX = connectionX + (connectionX - connectionXX);
                        float targetY = connectionY + (connectionY - connectionYY);
                        parent.moveTowards(targetX, targetY, velX);
                    } else {
                        connectionSwc = false;
                        chasing = false;
                    }
                } else {
                    chasing = false;
                    connectionSwc = false;
                }
                if (AIType == PlatformerAIType.defensive && target != null && handler.isHandled(target)) {
                    float d = getDiagonal(parent, target);
                    if (d < blindnessRad) {
                        chasing = true;
                        waiting = false;
                        connectionSwc = true;
                        connectionX = parent.getCenterX();
                        connectionY = parent.getCenterY();
                        connectionXX = target.getCenterX();
                        connectionYY = target.getCenterY();
                        parent.moveTowards(connectionXX, connectionYY, velX);
                    } else {
                        chasing = false;
                        connectionSwc = false;
                    }
                } else {
                    chasing = false;
                    connectionSwc = false;
                }
                if(waiting) { 
                    if((intersectsSides())){
                        returning = true;
                        waiting = false;
                        targetX = (lastX);
                        targetY = (lastY);
                    } else { 
                        now = System.currentTimeMillis();
                        if(now - lastTime > waitTime) { 
                            lastX = parent.getCenterX();
                            lastY = parent.getCenterY();
                            returning = false;
                            waiting = false;
                            generateTarget();
                        }
                    }
                } else if(!chasing) {
                    if(parent.checkCenterWithMiss(targetX, targetY, ranMovAccMiss)) {
                        generateWaitTime();
                        lastTime = System.currentTimeMillis();
                        parent.setVelY(0);
                        parent.setVelX(0);
                        waiting = true;
                    } else { 
                        if(!(intersectsSides()) || returning) {
                            parent.moveTowards(targetX, targetY, velX);
                        } else {
                            returning = true;
                            targetX = (lastX);
                            targetY = (lastY);
                        }
                    }
                }
            }
            public void generateWaitTime() { 
                Random r = new Random();
                waitTime = r.nextInt((int)(ranMovStayTimeMax - ranMovStayTimeMin)) + ranMovStayTimeMin;
            }
            public void generateTarget() { 
                Random r = new Random();
                targetX = (int)(r.nextInt((int)ranMovRad * 2) - ranMovRad + parent.getCenterX());
                targetY = (int)(r.nextInt((int)ranMovRad * 2) - ranMovRad + parent.getCenterY());
                if(handler.findObjectViaBounds(new Rectangle ((int)(targetX - 1) , (int)(targetY - 1) , 3 , 3))!=null) { 
                    generateTarget();
                }
            }
        };
    }
    
    public void tick() {
        aiDef.tick();
        if(actions.size() > 0) { 
            for(int i = 0 ; i < actions.size() ;i++) { 
                actions.get(i).action();
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(leftBottomCheck);
        g2d.draw(rightBottomCheck);
        g2d.draw(leftTopCheck);
        g2d.draw(rightTopCheck);
        g.setColor(Color.red);
        g2d.draw(rightCheck);
        g2d.draw(leftCheck);
        g.setColor(Color.blue);
        g2d.draw(topCheck);
        g2d.draw(bottomCheck);
        if (connectionSwc) {
            g2d.drawLine((int) connectionX, (int) connectionY, (int) connectionXX, (int) connectionYY);
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    public PlatformerAIType getAIType() {
        return AIType;
    }

    public void setAIType(PlatformerAIType AIType) {
        this.AIType = AIType;
        AIInit();
    }

    public PlatformerAIMoveType getAIMoveType() {
        return AIMoveType;
    }

    public void setAIMoveType(PlatformerAIMoveType AIMoveType) {
        this.AIMoveType = AIMoveType;
    }

    public AIDef getAiDef() {
        return aiDef;
    }

    public void setAiDef(AIDef aiDef) {
        this.aiDef = aiDef;
    }

    public float getBlindnessRad() {
        return blindnessRad;
    }

    public void setBlindnessRad(float blindnessRad) {
        this.blindnessRad = blindnessRad;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        if(this.velX == 0)
            parent.setVelX(velX);
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getCheckRectsWidth() {
        return checkRectsWidth;
    }

    public void setCheckRectsWidth(float checkRectsWidth) {
        this.checkRectsWidth = checkRectsWidth;
    }

    public float getCheckRectsHeight() {
        return checkRectsHeight;
    }

    public void setCheckRectsHeight(float checkRectsHeight) {
        this.checkRectsHeight = checkRectsHeight;
    }

    public Rectangle getLeftCheck() {
        return leftBottomCheck;
    }

    public void setLeftCheck(Rectangle leftCheck) {
        this.leftBottomCheck = leftCheck;
    }

    public Rectangle getRightCheck() {
        return rightBottomCheck;
    }

    public void setRightCheck(Rectangle rightCheck) {
        this.rightBottomCheck = rightCheck;
    }

    public void addIgnoreID(ID id) {
        this.ignoreList.add(id);
    }

    public void removeIgnoreID(ID id) {
        this.ignoreList.remove(id);
    }

    protected abstract class AIDef {

        public AIDef() {
            parent.setVelX(velX);
            direction = Direction.right;
            init();
        }
        public void init() { 
            
        }
        public abstract void tick();
    }

    public boolean isIgnored(GameObject temp) {
        if (temp != null) {
            for (int i = 0; i < ignoreList.size(); i++) {
                if (temp.checkObj(ignoreList.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void addAction(SixAction action) { 
        actions.add(action);
    }
    public void removeAction(SixAction action) { 
        actions.remove(action);
    }

    public float getRanMovRad() {
        return ranMovRad;
    }

    public void setRanMovRad(float ranMovRad) {
        this.ranMovRad = ranMovRad;
    }
    public void updateLeft() { 
        leftCheck = new Rectangle((int) (parent.getX() - checkRectsWidth), (int) (parent.getY() + parent.getHeight() / 2 - checkRectsHeight / 2), (int) (checkRectsWidth), (int) (checkRectsHeight));
    }
    public void updateRight() { 
        rightCheck = new Rectangle((int) (parent.getX() + parent.getWidth()), (int) (parent.getY() + parent.getHeight() / 2 - checkRectsHeight / 2), (int) (checkRectsWidth), (int) (checkRectsHeight));
    }
    public void updateBottom() { 
        bottomCheck = new Rectangle((int) (parent.getX()) , (int)(parent.getY() + parent.getHeight()) ,(int) (checkRectsWidth) , (int)(checkRectsHeight));
    }
    public void updateTop() { 
        topCheck = new Rectangle((int) (parent.getX()) , (int) (parent.getY() - checkRectsHeight) , (int)(checkRectsWidth),(int)(checkRectsHeight));
    }
    public void updateBottomLeft() { 
        leftBottomCheck = new Rectangle((int) (parent.getX() - checkRectsWidth), (int) (parent.getY() + parent.getHeight() / 2 + checkRectsHeight / 2), (int) (checkRectsWidth), (int) (checkRectsHeight));
    }
    public void updateBottomRight() { 
        rightBottomCheck = new Rectangle((int) (parent.getX() + parent.getWidth()), (int) (parent.getY() + parent.getHeight() / 2 + checkRectsHeight / 2), (int) (checkRectsWidth), (int) (checkRectsHeight));
    }
    public void updateTopRight() { 
        rightTopCheck = new Rectangle((int) (parent.getX() + parent.getWidth()), (int) (parent.getY() + parent.getHeight() / 2 - checkRectsHeight * 3 / 2), (int) (checkRectsWidth), (int) (checkRectsHeight));
    }
    public void updateTopLeft() { 
        leftTopCheck = new Rectangle((int) (parent.getX() - checkRectsWidth), (int) (parent.getY() + parent.getHeight() / 2 - checkRectsHeight * 3 / 2), (int) (checkRectsWidth), (int) (checkRectsHeight));
    }
    public void updateHSides() { 
        updateRight();
        updateLeft();
    }
    public void updateVSides() { 
        updateTop();
        updateBottom();
    }
    public void updateSides() { 
        updateRight();
        updateLeft();
        updateTop();
        updateBottom();
    }
    public void updateCorners() { 
        updateBottomLeft();
        updateBottomRight();
        updateTopLeft();
        updateTopRight();
    }
    public void updateFull() {
        updateRight();
        updateLeft();
        updateTop();
        updateBottom();
        updateBottomLeft();
        updateBottomRight();
        updateTopLeft();
        updateTopRight();
    }
    public boolean intersectsHSides(Rectangle bounds) { 
        return bounds.intersects(rightCheck) || bounds.intersects(leftCheck);
    }
    public boolean intersectsVSides(Rectangle bounds) { 
        return bounds.intersects(topCheck) || bounds.intersects(bottomCheck);
    }
    public boolean intersectsSides(Rectangle bounds) { 
        return intersectsHSides(bounds) || intersectsVSides(bounds);
    }
    public boolean intersectsCorners(Rectangle bounds) { 
        return  bounds.intersects(rightBottomCheck) || bounds.intersects(rightTopCheck) ||
                bounds.intersects(leftBottomCheck) || bounds.intersects(leftTopCheck);
    }
    public boolean intersectsFull(Rectangle bounds) { 
        return intersectsSides(bounds) || intersectsCorners(bounds);
    }
    public boolean intersectsHSides() { 
        return !(handler.findObjectViaBounds(rightCheck) == null && handler.findObjectViaBounds(leftCheck) == null);
    }
    public boolean intersectsVSides() { 
        return !(handler.findObjectViaBounds(topCheck) == null && handler.findObjectViaBounds(bottomCheck) == null);
    }
    public boolean intersectsSides() { 
        return intersectsHSides() || intersectsVSides();
    }
    public boolean intersectsCorners() { 
        return  !(  handler.findObjectViaBounds(rightTopCheck) == null && handler.findObjectViaBounds(rightBottomCheck) == null &&
                    handler.findObjectViaBounds(leftTopCheck) == null && handler.findObjectViaBounds(leftBottomCheck) == null);
    }
    public boolean intersectsFull() { 
        return intersectsSides() || intersectsCorners();
    }

    public boolean isChasing() {
        return chasing;
    }

    public void setChasing(boolean chasing) {
        this.chasing = chasing;
    }
    
}
