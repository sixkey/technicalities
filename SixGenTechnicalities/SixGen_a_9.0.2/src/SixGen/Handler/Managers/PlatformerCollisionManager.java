package SixGen.Handler.Managers;

import SixGen.GameObject.GameObject;
import SixGen.Handler.Handler;


public class PlatformerCollisionManager extends CollisionManager{
    
    public PlatformerCollisionManager(Handler handler) {
        super(handler);
    }
    
     public void topCollision(GameObject checkedObject, GameObject target) { 
//        System.out.println("@CollisionManager : topCollision" + Math.random());
        checkedObject.setVelY(0);
        checkedObject.setY(target.getY() + target.getHeight());
    }
     public void bottomCollision(GameObject checkedObject , GameObject target) {
//        System.out.println("@CollisionManager : bottomCollision" + Math.random());
        checkedObject.setY(target.getY() - checkedObject.getHeight());
        checkedObject.setVelY(0);
        grounded = true;
        jumping = false;
    }
}