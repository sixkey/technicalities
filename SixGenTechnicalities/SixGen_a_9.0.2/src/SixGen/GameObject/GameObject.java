package SixGen.GameObject;

import SixGen.Utils.ID;
import SixGen.Utils.Utils;
import SixGen.SixUI.Animation.Animation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import SixGen.Events.Keyboard.SixAbstractKeyListener.KeyActionType;
import SixGen.Events.Mouse.SixAbstractMouseListener.MouseActionType;
import SixGen.Handler.Managers.GravityObject;
import SixGen.Interfaces.Following.FollowingInstance;
import SixGen.Utils.Velocity;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * GameObject
 * Extends:
 *  Utils
 * Abilities :
 *  Template for all game objects
 *  holds lots of values about position , dimensions , gravity and everything
*/

public abstract class GameObject extends Utils implements GravityObject, FollowingInstance{

    //VARIALBES
    // texture that this object has
    protected BufferedImage renderImage;
    // state in whitch the object was one update before
    protected SafeState safeState;
    // position of the object center
    // centerX = x + centerMassX always 
    // centerY = y + centerMassY always
    protected float centerX, centerY;
    // position of the object
    protected float x, y;
    // position of the object center relative to the position of the object
    // if the centerMassX is equal to 3 the distance between x and centerX is 3. Same width y
    protected float centerMassX, centerMassY;
    // dimensions of object
    protected float width, height;
    // velocity of the object
    protected ArrayList <Velocity> velComplex;
    protected Velocity vel;
    // movement path of the object
    protected MovingPath movingPath;
    // speed of the object. used in move towards 
    protected float angSpeed;
    // location of the chunk that this object is currently in. If the handler is normal not ChunkHandler this value is null
    protected Point chunkPoint;
    // ID number of the layer in Handler that this object is currently in
    protected int layer;
    // for example ID can be 9mm bullet but the former will be ammo
    // ID of the object
    protected ID id;
    // second ID that refers to the parent object
    protected ID former;
    // boolean that controls if the object is handled by handler
    protected boolean handled = false;
    // if gravityLock is true then the object can't be moved by gravity manager
    protected boolean gravityLock = false;
    // this boolean can turns off attracting of this object in GravityManager. This object can be still moved by GravityManager
    protected boolean gravityProducerLock = false;
    // if collisionLock is true then the object can't be moved by collision manager
    protected boolean collisionLock = false;
    // if collisionFree is true then object's can move trought
    protected boolean collisionFree = false;
    // if collisionObjCheckFree is true then the object is not checked in objectCheck *see CollisionManager for more info
    protected boolean collObjCheckFree = false;
    // size of the corners in the bounds system that aren't recognized by collisionManager
    protected float boundsGridMarg = 5;
    protected boolean movBlock = false;
    // if the object is staying on other object. Mainly used in platformer games 
    protected boolean grounded = false;
    // if the object is jumping. Mainly used in platformer games
    protected boolean jumping = false;
    // strenght of the gravity. The object that is attracted to this object will be attracted by speed with this value. The speed also depends on the distance between objects
    protected float mass;
    // the radius in whitch the objects are attracted to this one
    protected float gravityRadius;
    // the animation that is rendered. *see Animation description for more info
    protected Animation renderAnimation;
    // textureBlock is spritesheet with blocks 
    protected BufferedImage textureBlock;
    // renderSwitch controls if the object is rendered
    private boolean renderSwitch;
    // tickSwitch controsl if the object does tick
    private boolean tickSwitch;
    // leftBoundsColl controls if the left bounds of the object is recognized by collision manager
    private boolean leftBoundsColl = true;
    // rightBoundsColl controls if the right bounds of the object is recognized by collision manager
    private boolean rightBoundsColl = true;
    // topBoundsColl controls if the top bounds of the object is recognized by collision manager
    private boolean topBoundsColl = true;
    // bottomBoundsColl controls if the bottom bounds of the object is recognized by collision manager
    private boolean bottomBoundsColl = true;
    
    // color is udes in all automated fill and draw bounds methods
    protected Color color;
    // spawncolor is used in spawning this object
    protected Color spawnColor;
    // list of rectangles that rendered and form line. This line is drawn by this object and the position of the pen is centerX and centerY
    private LinkedList<Rectangle> linePixels = new LinkedList<Rectangle>();
    protected int lineMaxLength = 100;
    // 
    private TexturePosition texturePosition = null;
    
    public enum TexturePosition {
        topLeft, top, topRight, left, center, right, bottomLeft, bottom, bottomRight;
    }
    
    public GameObject(float centerX, float centerY, ID id) {
        //@GameObject
        //@GameObject#constructorSetters
        this.centerX = centerX;
        this.centerY = centerY;
        this.x = centerX;
        this.y = centerY;
        this.id = id;
        this.former = id;
        this.color = Color.white;
        //@GameObject#defaultSetters
        vel = new Velocity(0 , 0);
        velComplex = new ArrayList<Velocity>();
        
    }
    public GameObject(float centerX , float centerY , float width , float height , ID id) { 
        //@GameObject
        this(centerX , centerY , id);
        setBoundsACenter(width , height);
    }
    
    //RENDERING AND TICKING VOIDS
    
    public void renderBase(Graphics2D g) { 
        if(renderSwitch) {
            render(g);
        }
    }

    public void tickBase() { 
        if(tickSwitch) { 
            tick();
        }
    }
    
    /**
     * basic render preset, Override for more options
     * @param g graphics element
     */
    public void render(Graphics2D g) { fillBounds(g); }
    
    /**
     * basic tick preset, Override for more options
     */
    public void tick() { addVels(); }
    
    public void renderMassCenter(Graphics g) {
        g.setColor(Color.green);
        g.drawRect((int) centerX - 2, (int) centerY - 2, (int) 4, (int) 4);
    }
    
    public void drawBounds(Graphics g) { 
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(getBounds());
    }
    
    public void fillBounds(Graphics g) {
        if(color!=null)
        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(getBounds());
    }
    
    public void fillBounds(Graphics g , Color c){ 
        Graphics2D g2d = (Graphics2D) g;
        if(c!=null) {
            g2d.setColor(c);
        }
        g2d.fill(getBounds());
    }
    
    public void fillRotatedBounds(Graphics g , Color c) { 
        if(c != null) { 
            g.setColor(c);
        }
        drawRotatedRect(g, getBounds(),centerX , centerY , vel.getAngle());
    }
    
    public void fillRotatedBounds(Graphics g , Color c , float a) { 
        if(c != null) { 
            g.setColor(c);
        }
        drawRotatedRect(g ,getBounds() ,centerX , centerY, a);
    }
    
    public void fillOvalBounds(Graphics g) { 
        g.fillOval((int)x, (int)y, (int)width, (int)height);
    }
    
    public void fillOvalBounds(Graphics g , Color c) {
        if(c!=null) {
            g.setColor(c);
        }
        g.fillOval((int)x, (int)y, (int)width, (int)height);
    }
    
    public void renderBounds(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color color = g.getColor();
        g.setColor(Color.green);
        if(bottomBoundsColl)
        g2d.draw(getBottomBounds());
        if(topBoundsColl)
        g2d.draw(getTopBounds());
        g.setColor(Color.red);
        if(leftBoundsColl)
        g2d.draw(getLeftBounds());
        if(rightBoundsColl)
        g2d.draw(getRightBounds());
        g.setColor(Color.blue);
        g.fillRect((int)centerX - 2, (int)centerY - 2, 4, 4);
        g.setColor(color);
    }
   
    public void drawLine(Graphics g , Color c) { 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        for(int i = 0 ; i < linePixels.size() - 1 ;i++) {
            
            g2d.drawLine((int)linePixels.get(i).getX(), (int)linePixels.get(i).getY(), (int)linePixels.get(i + 1).getX(), (int)linePixels.get(i + 1).getY());
//            g2d.fill(linePixels.get(i));
        }
        linePixels.add(floatRectangle(centerX , centerY , 1 , 1));
        while(linePixels.size()>lineMaxLength) { 
            linePixels.removeFirst();
        }
    }
    
    public void drawLine(Graphics g , Color c , int lastRectsAmount) { 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        int size = linePixels.size();
        if(lastRectsAmount > size) { 
            lastRectsAmount = size;
        }
        if(size > 0) {
            for(int i = 0 ; i < lastRectsAmount ;i++) { 
                g2d.fill(linePixels.get(linePixels.size() - i - 1));
            }
        }
        linePixels.add(floatRectangle(centerX , centerY , 1 , 1));
        while(linePixels.size()>lineMaxLength) { 
            linePixels.removeFirst();
        }
    }
    
    public void drawFadedLine(Graphics g , Color c) { 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        int size = linePixels.size();
        if(size > 0) {
            for(int i = 0 ; i < size - 1;i++) {
                float temp = (float)i / (float)size;
                temp = clamp(0 , 1 , temp);
                g2d.setComposite(getAlphaComposite(temp));
                
                
                g2d.drawLine((int)linePixels.get(i).getX(), (int)linePixels.get(i).getY(), (int)linePixels.get(i + 1).getX(), (int)linePixels.get(i + 1).getY());
                
                
                g2d.setComposite(getAlphaComposite(1));
            }
        }
        linePixels.add(floatRectangle(centerX , centerY , 1 , 1));
        while(linePixels.size()>lineMaxLength) { 
            linePixels.removeFirst();
        }
    }
    
    public void drawFadedLine(Graphics g , Color c , int lastRectsAmount , int penSize) { 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        int size = linePixels.size();
        if(lastRectsAmount > size) { 
            lastRectsAmount = size;
        }
        if(size > 0) {
            for(int i = 0 ; i < lastRectsAmount ;i++) {
                float temp = 1 - (float)i / (float)lastRectsAmount;
                g2d.setComposite(getAlphaComposite(temp));
                fillOval(g , linePixels.get(linePixels.size() - i - 1));
                g2d.setComposite(getAlphaComposite(1));
            }
        }
        if(size == 10) { 
            }
        linePixels.add(floatRectangle(centerX - penSize / 2, centerY - penSize / 2, penSize , penSize));
        while(linePixels.size()>lineMaxLength) { 
            linePixels.removeFirst();
        }
    }
    
    public void drawLine(Graphics g , Color c , float penSize) { 
        g.setColor(c);
        for(int i = 0 ; i < linePixels.size() ;i++) { 
            fillOval(g , linePixels.get(i));
        }
        linePixels.add(floatRectangle(centerX - penSize / 2, centerY - penSize / 2, penSize , penSize));
        while(linePixels.size()>lineMaxLength) { 
            linePixels.removeFirst();
        }
    }
    
    public void destroyLine() { 
        while(!linePixels.isEmpty()) { 
            linePixels.removeLast();
        }
    }
    
    //MOVING VOIDS 
    
    public void moveTo(float xx , float yy) { 
        Point[] point = {new Point((int)xx , (int)yy)}; 
        movingPath = new MovingPath(point);
    }
    public void moveTo(Point point) { 
        Point[] points = {point};
        movingPath = new MovingPath(points);
    }
    public void moveTowards(float xx , float yy , float speed) {
        float[] vels = getSidesFromDiagonal(centerX-xx, centerY-yy, 3, speed);
        vel.setVels(vels[0] , vels[1]);
    }
    public void moveTowards(Point point , float speed) {
        moveTowards((float)point.getX() , (float)point.getY() , speed);
    }
    public void moveTowards(Point point) { 
        moveTowards(point , angSpeed);
    }
    public void moveInAngle(float angle , float speed) { 
        float newAngle = 180 + angle;
        int nez = (int)(Math.floor(newAngle/90)) % 4;
        
        float y = (int)(Math.tan(Math.toRadians(newAngle)) * 1000000);
        float x = 1000000;
        
        if((nez == 1 || nez == 2 || angle % 180 == 0) && angle % 360 != 0) { 
            y *=-1;
            x *=-1;
        }
        y*=-1;
        float[] vels = getSidesFromDiagonal(x , y , 3 , speed);
        vel.setVels(vels[0] , vels[1]);
    }
    
    //COLISION VOIDS
    
    public Direction collisionDirection(Rectangle bounds) {
        if (this.getBottomBounds().intersects(bounds)) {
            return Direction.down;
        } else if (this.getTopBounds().intersects(bounds)) {
            return Direction.up;
        } else if (this.getLeftBounds().intersects(bounds)) {
            return Direction.left;
        } else if (this.getRightBounds().intersects(bounds)) {
            return Direction.right;
        } else {
            return null;
        }
    }
    
    public Direction[] collisionDirections(Rectangle bounds) { 
        LinkedList<Direction> directions = new LinkedList<Direction>();
        if (this.getBottomBounds().intersects(bounds)) {
            directions.add(Direction.down);
        }
        if (this.getTopBounds().intersects(bounds)) {
            directions.add(Direction.up);
        } 
        if (this.getLeftBounds().intersects(bounds)) {
            directions.add(Direction.left);
        } 
        if (this.getRightBounds().intersects(bounds)) {
            directions.add(Direction.right);
        }
        return directions.toArray(new Direction[directions.size()]);
    }
    
    public boolean collision(Rectangle bounds) {
        if (this.getBounds().intersects(bounds)) {
            return true;
        } else {
            return false;
        }
    }
    
    
    //USER INPUT VOIDS 
    
    public void mouseAction(MouseEvent e , MouseActionType mouseActionType) {

    }
  
    public void keyAction(KeyEvent e ,KeyActionType actionType) {

    }
    
    //GETTERS SETTERS
    
    public String toString() { 
        return String.valueOf(id) + " " + centerX + " " + centerY;
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.x = centerX - centerMassX; 
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.y = centerY - centerMassY; 
        this.centerY = centerY;
    }
    
    public Point getCenterPoint() { 
        return new Point((int)centerX , (int)centerY);
    }
    public void setCenterPoint(Point point) { 
        centerX = (float)point.getX();
        centerY = (float)point.getY();
        x = centerX - centerMassX;
        y = centerY - centerMassY;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        centerX = x + centerMassX;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        centerY = y + centerMassY;
    }
    
    public float getXX() { 
        return this.getX() + this.getWidth();
    }
    
    public float getYY() { 
        return this.getY() + this.getHeight();
    }
    
    public void setCenterMass(float x, float y) { 
        this.centerX = x;
        this.centerY = y;
    }
    
    public void setCenterMass(float xy) { 
        this.centerX = xy;
        this.centerY = xy;
    }
    
    public float getCenterMassX() {
        return centerMassX;
    }
    public void setCenterMassX(float centerMassX) {
        this.centerMassX = centerMassX;
    }

    public void setCenterMassXref(float centerMassX) {
        this.centerMassX = centerMassX;
        refreshX();
    }

    public void setCenterMassYref(float centerMassY) {
        this.centerMassY = centerMassY;
        refreshY();
    }

    public float getCenterMassY() {
        return centerMassY;
    }

    public void setCenterMassY(float centerMassY) {
        this.centerMassY = centerMassY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setWidthACenter(float width) {
        this.width = width;
        centerMassX = width / 2;
        refreshX();
    }

    public void setHeightACenter(float height) {
        this.height = height;
        centerMassY = height / 2;
        refreshY();
    }

    public void setBounds(float width , float height) { 
        this.width = width;
        this.height = height;
    }
    
    public void setBoundsACenter(float width, float height) {
        this.width = width;
        this.height = height;
        centerMassX = width / 2;
        centerMassY = height / 2;
        refreshX();
        refreshY();
    }
    
    public void setBounds(float size) { 
        this.width = size;
        this.height = size;
    }
    
    public void setBoundsACenter(float size) { 
        this.width = size;
        this.height = size;
        centerMassX = size / 2;
        centerMassY = size / 2;
        refreshX();
        refreshY(); 
    }
    public void setBoundsABottom(float width , float height) { 
        this.width = width;
        this.height = height;
        centerMassX = width / 2;
        centerMassY = height;
        refreshX();
        refreshY(); 
    }

    public void refreshX() {
        x = centerX - centerMassX;
    }

    public void refreshY() {
        y = centerY - centerMassY;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    public void setRenderImage(BufferedImage renderImage) {
        this.renderImage = renderImage;
    }

    public BufferedImage getRenderImage() {
        return renderImage;
    }

    public void stop() { 
        setVel(new Velocity(0, 0));
    }
    
    public void setVelX(float velX) {
        vel.setVelX(velX);
    }

    public void setVelY(float velY) {
        vel.setVelY(velY);
    }
    
    public void setVel(Velocity vel) { 
        this.vel = vel;
    }
  
    public Velocity getVel() { 
        return vel;
    }
    
    public void setVels(float velX , float velY) { 
        vel.setVelX(velX);
        vel.setVelY(velY);
    }

    public float getVelX() {
        return vel.getVelX();
    }

    public float getVelY() {
        return vel.getVelY();
    }

    public void refreshCenterX() {
        centerX = x + centerMassX;
    }

    public void refreshCenterY() {
        centerY = y + centerMassY;
    }


    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public boolean getHandled() {
        return handled;
    }

    public void addComplexVel(Velocity velocity) { 
        velComplex.add(velocity);
    }
    
    public Velocity removeComplexVel(String title) { 
        for(int i = 0 ; i < velComplex.size(); i++) { 
            Velocity temp = velComplex.get(i);
            if(temp.getTitle().equals(title)) {
                velComplex.remove(temp);
                return temp;
            }
        }
        return null;
    }
    
    public Velocity setComplexVel(String title, Velocity v) { 
        for(int i = 0 ; i < velComplex.size(); i++) { 
            Velocity temp = velComplex.get(i);
            if(temp.getTitle().equals(title)) {
                temp.setVelX(v.getVelX());
                temp.setVelY(v.getVelY());
                return temp;
            }
        }
        return null;
    }
    
     public Velocity setComplexVel(Velocity v) { 
        for(int i = 0 ; i < velComplex.size(); i++) { 
            Velocity temp = velComplex.get(i);
            if(temp.getTitle().equals(v.getTitle())) {
                temp.setVelX(v.getVelX());
                temp.setVelY(v.getVelY());
                return temp;
            }
        }
        return null;
    }
    
    public Velocity getComplexVel(String title) {
        for(int i = 0 ; i < velComplex.size(); i++) { 
            Velocity temp = velComplex.get(i);
            if(temp.getTitle().equals(title)) {
                return temp;
            }
        }
        return null;
    }
    
    public void addVels() {
        safeState = new SafeState(x , y , width , height);
//        System.out.println("addVels()");
//        System.out.println(y);
        x += vel.getVelX();
        y += vel.getVelY();
//        System.out.println(y);
        refreshCenterX();
        refreshCenterY();
    }
    public void addComplexVels() { 
        float velX = 0, velY = 0;
        for(int i = 0; i < velComplex.size() ; i++) { 
            velX+=velComplex.get(i).getVelX();
            velY+=velComplex.get(i).getVelY();
        }
        this.vel.setVelX(velX);
        this.vel.setVelY(velY);
        addVels();
    }

    public void addVels(float extender) { 
        safeState = new SafeState(x , y , width , height);
        x += vel.getVelX() * extender;
        y += vel.getVelY() * extender;
        refreshCenterX();
        refreshCenterY();
    }
    
    public void addVels(float xExtender , float yExtender) { 
        safeState = new SafeState(x , y , width , height);
        x += vel.getVelX() * xExtender;
        y += vel.getVelY() * yExtender;
        refreshCenterX();
        refreshCenterY();
    }
    
    public void updateMovingPath() { 
        if(movingPath!=null) { 
            movingPath.tick(this);
        }
    }
    
    public float getGravityPull(float distance, float mass2) {
        float d = distance / 1.5f;
        return G_CONSTANT * ((mass) / (d * d));
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getGravityRadius() {
        return gravityRadius;
    }

    public void setGravityRadius(float gravityRadius) {
        this.gravityRadius = gravityRadius;
    }

    public boolean isGravityLock() {
        return gravityLock;
    }

    public void setGravityLock(boolean gravityLock) {
        this.gravityLock = gravityLock;
    }

    public boolean isGravityProducerLock() {
        return gravityProducerLock;
    }

    public void setGravityProducerLock(boolean gravityProducerLock) {
        this.gravityProducerLock = gravityProducerLock;
    }

    public Rectangle getTopBounds() {
        return new Rectangle((int) (x), (int) (y - boundsGridMarg), (int) (width), (int) (height / 3));
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int) (x), (int) (y + height / 3 * 2 + boundsGridMarg), (int) (width), (int) (height / 3));
    }

    public Rectangle getLeftBounds() {
        return new Rectangle((int) (x - boundsGridMarg), (int) (y), (int) (width / 3), (int) (height));
    }

    public Rectangle getRightBounds() {
        return new Rectangle((int) (x + width / 3 * 2 + boundsGridMarg), (int) (y), (int) (width / 3), (int) (height));
    }

    public boolean isGrounded() {
        return grounded;
    }

    public boolean isMovBlock() {
        return movBlock;
    }

    public void setMovBlock(boolean movBlock) {
        this.movBlock = movBlock;
    }
    
    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
    
    public boolean isJumping() { 
        return jumping;
    }
    public void setJumping(boolean jumping) { 
        this.jumping = jumping;
    }

    public boolean isCollisionLock() {
        return collisionLock;
    }

    public void setCollisionLock(boolean collisionLock) {
        this.collisionLock = collisionLock;
    }

    public boolean isCollisionFree() {
        return collisionFree;
    }

    public void setCollisionFree(boolean collisionFree) {
        this.collisionFree = collisionFree;
    }

    public Animation getRenderAnimation() {
        return renderAnimation;
    }

    public void setRenderAnimation(Animation renderAnimation) {
        this.renderAnimation = renderAnimation;
    }

    public void renderRenderImage(Graphics g) {
        g.drawImage(renderImage, (int) x, (int) y, (int) width, (int) height, null);
    }
    public void renderRenderImage(Rectangle rectangle, Graphics g) {
        g.drawImage(renderImage, (int)rectangle.getX() , (int)rectangle.getY() , (int)rectangle.getWidth() , (int)rectangle.getHeight(), null);
    }
    public void renderImage(BufferedImage image, Rectangle rectangle, Graphics g) {
        g.drawImage(image, (int)rectangle.getX() , (int)rectangle.getY() , (int)rectangle.getWidth() , (int)rectangle.getHeight(), null);
    }
    public void renderRenderAnimation(Graphics g) { 
        renderAnimation.render(g , getBounds());
    }

    public TexturePosition getTexturePosition() {
        return texturePosition;
    }

    public void setTexturePosition(TexturePosition texturePosition) {
        this.texturePosition = texturePosition;
        renderImage = getTextureFromTextureBlock(getImageIDFromBlock());
    }

    public int getImageIDFromBlock() {
        if (texturePosition == TexturePosition.topLeft) {
            return 0;
        } else if (texturePosition == TexturePosition.top) {
            return 1;
        } else if (texturePosition == TexturePosition.topRight) {
            return 2;
        } else if (texturePosition == TexturePosition.left) {
            return 3;
        } else if (texturePosition == TexturePosition.center) {
            return 4;
        } else if (texturePosition == TexturePosition.right) {
            return 5;
        } else if (texturePosition == TexturePosition.bottomLeft) {
            return 6;
        } else if (texturePosition == TexturePosition.bottom) {
            return 7;
        } else if (texturePosition == TexturePosition.bottomRight) {
            return 8;
        }
        return 100;
    }

    public BufferedImage getTextureFromTextureBlock(int id) {
        int texWidth = textureBlock.getWidth() / 3;
        int texHeight = textureBlock.getHeight() / 3;
        int counter = 0;
        for (int yy = 0; yy < 3; yy++) {
            for (int xx = 0; xx < 3; xx++) {
                if (counter == id) {
                    return textureBlock.getSubimage(xx * texWidth, yy * texHeight, texWidth, texHeight);
                }
                counter++;
            }
        }
        return null;
    }

    public boolean isRenderSwitch() {
        return renderSwitch;
    }

    public void setRenderSwitch(boolean renderSwitch) {
        this.renderSwitch = renderSwitch;
    }

    public boolean isTickSwitch() {
        return tickSwitch;
    }

    public void setTickSwitch(boolean tickSwitch) {
        this.tickSwitch = tickSwitch;
    }
    
    public float getCollisionAccMiss() { 
        return boundsGridMarg;
    }
    
    public boolean isBounds(Direction dir) { 
        if(null != dir) switch (dir) {
            case left:
                return leftBoundsColl;
                
            case right:
                return rightBoundsColl;
                
            case up:
                return topBoundsColl;
                
            case down:
                return bottomBoundsColl;
                
            default:
                return true;
        } else { 
            return true;
        }
    }
    public void setBoundsSwitch(Direction dir , boolean swc) { 
        if(null != dir) switch (dir) {
            case left:
                leftBoundsColl = swc;
                break;
            case right:
                rightBoundsColl = swc;
                break;
            case up:
                topBoundsColl = swc;
                break;
            case down:
                bottomBoundsColl = swc;
                break;
            default:
        }
    }
    
    public void setColor(Color color) { 
        this.color = color;
    }
    public Color getColor() { 
        return color;
    }

    public boolean isCollObjCheckFree() {
        return collObjCheckFree;
    }

    public void setCollObjCheckFree(boolean collObjCheckFree) {
        this.collObjCheckFree = collObjCheckFree;
    }

    public ID getFormer() {
        return former;
    }

    public void setFormer(ID former) {
        this.former = former;
    }
    public boolean checkObj(ID id ) {
        return id == this.id || id == this.former;
    }
    public boolean checkCenterWithMiss(float xx , float yy , float accMiss) { 
        return
            centerX <= xx + accMiss && centerX >= xx - accMiss &&
            centerY <= yy + accMiss && centerY >= yy - accMiss
        ;
    }
    public Point getChunkPoint() {
        return chunkPoint;
    }

    public void setChunkPoint(Point chunkPoint) {
        this.chunkPoint = chunkPoint;
    }
    
    public void setChunkPoint(int x , int y) { 
        this.chunkPoint = new Point(x , y);
    }
    
    public SafeState getSafeState() { 
        return safeState;
    }

    public Color getSpawnColor() {
        return spawnColor;
    }

    public void setSpawnColor(Color spawnColor) {
        this.spawnColor = spawnColor;
    }

    public float getSpeed() {
        return angSpeed;
    }

    public void setSpeed(float speed) {
        this.angSpeed = speed;
    }

    public MovingPath getMovingPath() {
        return movingPath;
    }

    public void setMovingPath(MovingPath movingPath) {
        this.movingPath = movingPath;
    }
    
    
    
    public class SafeState { 
        
        //VARIABLES
        
        private float x;
        private float y;
        private float width;
        private float height;
        
        //CONSTRUCTORS
        
        public SafeState(float x , float y , float width , float height) { 
            //@SafeState
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        //GETTERS SETTERS
        
        public float getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
        public float getCenterX() { 
            return x + width / 2;
        }
        public float getCenterY() { 
            return y + height / 2;
        }
    }
}
