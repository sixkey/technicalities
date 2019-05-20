/*
*  Created by SixKeyStudios
*  Added in project SixGen_a_9.0.0
*  File SixGen.Handler / MTHThread
*  created on 17.8.2018 , 21:43:35 
 */
package SixGen.Handler;

import SixGen.GameObject.GameObject;

/**
 *
 * @author filip
 */
public class MTHThread extends Thread{
    
    private MultyThreadHandler handler;
    
    private boolean locked;
    private boolean running;
    
    private int OIDStart, OIDEnd;
    private int id, threadNumber;
    
    private GameObject[] objects;
    
    public MTHThread(MultyThreadHandler handler,int threadNumber, int id) {
        this.handler = handler;
        this.threadNumber = threadNumber;
        this.id = id;
    }

    @Override
    public void start() { 
        super.start();
        System.out.printf("MTHThraed id: %d has started%n", id);
    }
    
    public void run() {
        System.out.printf("MTHThraed id: %d is running%n", id);
        running = true;
        while(running) {
            if(!isLocked()) { 
                System.out.printf("MTHThraed id: %d tick%n", id);
                tick();
                locked = true;
            }
        }
    }
     
    public synchronized void tick(){ 
        //System.out.printf("Thread tick %d%n", id);
        for(int i = OIDStart; i<=OIDEnd; i++) { 
            objects[i].tickBase();
        }
    }
    
    public synchronized void setTick(GameObject[] objects, int i, int ii) {
        this.objects = objects;
        this.OIDStart = i;
        this.OIDEnd = ii;
        locked = false;
     //   System.out.printf("MTHThread id: %d setTick %b%n", id, locked);
    }
    
    public synchronized boolean isLocked() { 
        return locked;
    }
    public synchronized void setLocked(boolean locked) { 
        this.locked = locked;
    }
    
}
