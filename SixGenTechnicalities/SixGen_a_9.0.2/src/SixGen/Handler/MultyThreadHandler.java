/*
*  Created by SixKeyStudios
*  Added in project SixGen_a_9.0.0
*  File SixGen.Handler / MultyThreadHandler
*  created on 17.8.2018 , 21:40:34 
 */
package SixGen.Handler;

import SixGen.GameObject.GameObject;
import SixGen.Window.SixCanvas;

/**
 *
 * @author filip
 */
public class MultyThreadHandler extends Handler {
    
    private int threadNumber;
    
    MTHThread threads[];
    
    private boolean running;
    
    public MultyThreadHandler(SixCanvas sixCanv, RenderType renderType, int threadNumber) {
        super(sixCanv, renderType);
        this.threadNumber = threadNumber;
        running = false;
    }
    
    @Override
    public void tick() {
        if(running) {
            GameObject[] to = getTickObjects();
            for(int i = 0; i < threads.length; i++) {
                int start = (int)Math.floor((to.length * i) / threadNumber);
                int end = (int)Math.floor((to.length * (start+1)) / threadNumber) - 1;
                threads[i].setTick(to, start, end);
            }
        }
    }
    
    public void start() { 
        
        threads = new MTHThread[threadNumber];
        
        for(int i = 0; i < threadNumber; i++) {
            MTHThread temp = new MTHThread(this, threadNumber, i);
            temp.setLocked(true);
            threads[i] = temp;
            threads[i].start();
            
        }
        running = true;
    }
    
}
