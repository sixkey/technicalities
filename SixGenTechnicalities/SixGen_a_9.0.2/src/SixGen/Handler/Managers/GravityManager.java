package SixGen.Handler.Managers;

import SixGen.GameObject.GameObject;
import SixGen.Utils.ID;
import SixGen.Utils.IDException;
import SixGen.Utils.Utils;
import java.awt.Graphics2D;
import java.util.LinkedList;

/**
* GravityManager
* Extends:
*   
* Abilities:
*   Grabs objects from handler and applyies gravity on them
*       If the type is side then the objects will be attracted to the chosen side
*       If the type is net gravity then the objects will be attracted to ano another
*       If the type is both then both gravity applyies
*/
public class GravityManager extends Utils implements HandlerManager{

    //VARIABLES
    
    // This boolean turns on and of the whole manager
    protected boolean gravitySwitch = true;
    // Type of the gravity that this gravityManager provides
    protected GravityType gravityType;
    // If the gravityType is bottom or both , this value is used in the bottom gravity method
    protected float dirGravityStrength = 0.1f;
    // Direction of the attraction by the bottomgravity method
    protected Direction gravityDirection = Direction.down;
    // This is list of IDException that the gravity manager is ignoring
    protected LinkedList<IDException> gravityExceptionList = new LinkedList<IDException>();

    // GravityType is enum that holds 3 types of gravity
    public enum GravityType {
        // objects are attracted to the side
        side,
        // objects are attracted to one another
        net,
        // both
        both;
    }

    //CONSTRUCTORS
    
    public GravityManager(GravityType gravityType) {
        //@GravityManager
        //@GravityManager#constructorSetters
        this.gravityType = gravityType;
    }

    public GravityManager(GravityType gravityType, float dirGravityStrength, Direction gravityDirection) {
        //@GravityManager
        //@GravityManager#constructorSetters
        this.gravityType = gravityType;
        this.dirGravityStrength = dirGravityStrength;
        this.gravityDirection = gravityDirection;
    }

    //VOIDS
    
    @Override
    public void tick(GameObject[] objects) {
        //@tick
        if (gravitySwitch) {
            if (gravityType == GravityType.side) {
                directionGravity(objects);
            } else if (gravityType == GravityType.net) {
                netGravity(objects);
            } else if (gravityType == GravityType.both) {
                directionGravity(objects);
                netGravity(objects);
            }
        }
    }
    
    @Override
    public void render(Graphics2D g) { 
        
    }

    public void directionGravity(GameObject[] objects) {
        //@directionGravity
        // list of every ticking object in handler
        for (int i = 0; i < objects.length; i++) {
            GameObject temp = objects[i];
            if (!temp.isGravityLock()) {
                if (gravityDirection == Direction.down) {
                    temp.setVelY(temp.getVelY() + dirGravityStrength);
                }
                if (gravityDirection == Direction.up) {
                    temp.setVelY(temp.getVelY() - dirGravityStrength);
                }
                if (gravityDirection == Direction.left) {
                    temp.setVelX(temp.getVelX() - dirGravityStrength);
                }
                if (gravityDirection == Direction.right) {
                    temp.setVelX(temp.getVelX() + dirGravityStrength);
                }
            }
        }
    }

    public void netGravity(GameObject[] objects) {
        //@netGravity
        // list of objects that will attract
        LinkedList<GameObject> attractersList = new LinkedList<GameObject>();
        // list of objects that will be attracted 
        LinkedList<GameObject> attractedList = new LinkedList<GameObject>();
        //@netGravity#filtering
        for (int i = 0; i < objects.length; i++) {
            attractedList.add(objects[i]);
            attractersList.add(objects[i]);
        }
        // filtering the attractersList
        for (int i = 0; i < attractersList.size(); i++) {
            GameObject temp = attractersList.get(i);
            if (temp.isGravityLock()) {
                attractedList.remove(temp);
            }
        }
        // filtering the attractedList
        for (int i = 0; i < attractersList.size(); i++) { 
            GameObject temp = attractersList.get(i);
            if (temp.isGravityProducerLock()) {
                attractersList.remove(temp);
            }
        }
        //@netGraivity#attracting
        for (int i = 0; i < attractersList.size(); i++) {
            GameObject attracter = attractersList.get(i);
            for (int f = 0; f < attractedList.size(); f++) {
                boolean exceptionSwitch = false;
                GameObject attracted = attractedList.get(f);
                // checking if the objects are not in the exceptionList
                for (int ex = 0; ex < gravityExceptionList.size(); ex++) {
                    IDException exception = gravityExceptionList.get(ex);
                    if (exception.checkID(attracter.getId(), attracted.getId())) {
                        exceptionSwitch = true;
                    }
                }
                // if the objects aren't in the exceptionList then attract them
                if (!exceptionSwitch) {
                    if (attracted != attracter && !attracted.isGrounded()) {
                        //@netGraivity#finalAttraction
                        float vels[] = getSidesFromDiagonal(attracted, attracter, 3, attracter.getGravityPull(getDiagonal(attracter, attracted), attracted.getMass()));
                        attracted.setVelX(attracted.getVelX() + vels[0]);
                        attracted.setVelY(attracted.getVelY() + vels[1]);
                    }
                }
            }
        }
    }

    //GETTERS SETTERS
    
    public void setGravitySwitch(boolean gravitySwitch) {
        this.gravitySwitch = gravitySwitch;
    }

    public boolean isGravitySwitch() {
        return gravitySwitch;
    }

    public GravityType getGravityType() {
        return gravityType;
    }

    public void setGravityType(GravityType gravityType) {
        this.gravityType = gravityType;
    }

    public float getDirGravityStrength() {
        return dirGravityStrength;
    }

    public void setDirGravityStrength(float dirGravityStrength) {
        this.dirGravityStrength = dirGravityStrength;
    }

    public Direction getGravityDirection() {
        return gravityDirection;
    }

    public void setGravityDirection(Direction gravityDirection) {
        this.gravityDirection = gravityDirection;
    }

    public void addGravExc(IDException gravExc) {
        gravityExceptionList.add(gravExc);
    }
    public void addGravExc(ID id1 , ID id2) {
        gravityExceptionList.add(new IDException(id1, id2));
    }
    public void removeGravExc(IDException gravExc) {
        gravityExceptionList.remove(gravExc);
    }
    public void removeGravExc(ID id1, ID id2) { 
        gravityExceptionList.remove(new IDException(id1 , id2));
    }
    public void addGravExcDuo(ID id1, ID id2) {
        gravityExceptionList.add(new IDException(id1, id2));
        gravityExceptionList.add(new IDException(id2, id1));
    }
    public void removeGravExcDuo(ID id1, ID id2) {
        gravityExceptionList.remove(new IDException(id1, id2));
        gravityExceptionList.remove(new IDException(id2, id1));
    }
}
