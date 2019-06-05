/*
*  Created by SixKeyStudios
*  Added in project SixGen_a_9.0.1
*  File SixGen.Utils / Timer
*  created on 19.10.2018 , 14:04:31 
 */
package SixGen.Utils;

/**
 *
 * @author filip
 */
public class Timer {
    
    private long duration;
    private long start;
    private boolean active;
    
    private long pauseStart;
    private boolean paused;
    private long pauseDelta;
    
    public Timer(long duration) { 
        this.start = System.currentTimeMillis();
        this.duration = duration;
        active = false;
    }
    
    public boolean isFinished() { 
        return 0 >= getDelta();
    }
    
    public void reset() {
        this.start = System.currentTimeMillis();
    }
    
    public void setActive(boolean active) { 
        this.active = active;
    }
    public boolean isActive() { 
        return active;
    }
    
    public long getDelta() {
        if(!paused) { 
            return duration - (System.currentTimeMillis() - start);
        } else { 
            return pauseDelta;
        }
    }
    
    public long getDelta(long time) { 
        return duration - time + start;
    }
    
    public String toString() { 
        return "TIMER [" + getDelta() + "; " + duration + "; " + active + "]: " + isFinished();
    }
    
    public void setDuration(long duration) { 
        this.duration = duration;
    }
    
    public void pause() { 
        pause(System.currentTimeMillis());
    }
    
    public void pause(long time) { 
        if(!paused) {
            pauseStart = time;
            pauseDelta = getDelta(time);
            paused = true;
        }
    }
    
    public void unpause() { 
        unpause(System.currentTimeMillis());
    }
    
    public void unpause(long time) { 
        if(paused) {
            this.start += time - pauseStart;
            paused = false;
        }
    }
}
