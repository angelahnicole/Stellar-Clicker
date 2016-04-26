


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * @file Timer.java
 * 
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * 
 * @description Timer object which times & reports progress of specific events
 * 
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package stellarclicker.util;

public class Timer
{
    private Boolean isActive;
    private float start;
    private float stop;
    private float lastGametime;
    
    float percent;
    
    
    public Timer()
    {
        this.isActive = false;
        this.start = 0;
        this.stop = 0;
        this.percent = 0;
        
    }
    
    public Timer(boolean isActive, float start, float stop, float percent)
    {
        this.isActive = isActive;
        this.start = start;
        this.stop = stop;
        this.percent = percent;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**========================================================================================================================== 
    * @name getActivation
    * 
    * @description If timer is activated
   
    *///=========================================================================================================================
    public Boolean getActivation()
    {
            return this.isActive;
    }
    
    /**========================================================================================================================== 
    * @name Set
    * 
    * @description Starts timer (seconds) which is based on the current gametime.
    * 
    * @param gametime The current gametime
    * @param seconds the elapsed seconds before timer is complete
    *///=========================================================================================================================
    public void set(float gametime, float seconds)
    {
        this.isActive = true;
        this.start = gametime;
        this.stop = this.start + seconds;
    }
    
    /**========================================================================================================================== 
    * @name checkCompletion
    * 
    * @description Determines if a timer has elapsed.
    * 
    * @param gametime the current gametimes
    *///=========================================================================================================================
    public Boolean checkCompletion(float gametime)
    {
        this.lastGametime = gametime;
        
        if (isActive && gametime > this.stop)
        {
            isActive = false;
            this.start = 0;
            this.stop = 0;
            return true;
        }

        return false;
    }
    
    /**========================================================================================================================== 
    * @name getPercentComplete
    * 
    * @description returns the percent of how complete a timer is.
    * 
    * @param gametime the current gametime
    *///=========================================================================================================================
    public float getPercentComplete(float gametime)
    {
        
        if (isActive)
        {
         this.percent = (gametime - this.start)/(this.stop - this.start);
         return this.percent;
        }
        
        return 0;
    }
    
    /**========================================================================================================================== 
    * @name getLastGametime
    * 
    * @description Returns the last recorded "game time"
    *///=========================================================================================================================
    public float getLastGametime()
    {
        return this.lastGametime;
    }
    
    /**========================================================================================================================== 
    * @name getLastTimeLeft
    * 
    * @description 
    *///=========================================================================================================================
    public float getLastTimeLeft()
    {
        if(isActive)
        {
            return this.stop - this.lastGametime;
        }
        
        return -1;
    }
    
    /**========================================================================================================================== 
    * @name getLastTimeElapsed
    * 
    * @description 
    *///=========================================================================================================================
    public float getLastTimeElapsed()
    {
        if(isActive)
        {
            return this.lastGametime - this.start;
        }
        
        return -1;
    }
    
    /**========================================================================================================================== 
    * @name getLastPercent
    * 
    * @description 
    *///=========================================================================================================================
    public float getLastPercent()
    {
        if(isActive)
        {
            return this.percent;
        }
        
        return -1;
    }

    /**========================================================================================================================== 
    * @name getTimeRemaining
    * 
    * @description returns how complete a timer is.
    * 
    * @param gametime the current gametime
    *///=========================================================================================================================
    public float getTimeRemaining(float gametime)
    {
        if (isActive)
        {
         return this.stop - gametime;
        }   
        return 0;
    }

    
    /**========================================================================================================================== 
    * @name cancelTimer
    * 
    * @description Resets the timer object to default values.
    * 
    *///=========================================================================================================================
    public void cancelTimer()
    {
        this.isActive = false;
        this.start = 0;
        this.stop = 0;
        this.percent = 0;
    }
    
    
    public void getTime()
    {
      float current = this.stop - this.start;
      
    }
    
}
