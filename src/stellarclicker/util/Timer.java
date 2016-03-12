


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

public class Timer {
    private Boolean isActive;
    private double start;
    private double stop;
    public Boolean isDone;
    double percent;
    
    
    public Timer()
    {
        this.isActive = false;
        this.start = 0;
        this.stop = 0;
        this.percent = 0;
        
    }
    
    public Boolean getActivation()
    {
            return isActive;
    }
        //starts the timer
    public void set(double gametime, float seconds)
    {
        this.isActive = true;
        this.start = gametime;
        this.stop = this.start + seconds;


    }

    public Boolean checkCompletion(double gametime)
    {
        if (isActive && gametime > this.stop)
        {
            isActive = false;
            this.start = 0;
            this.stop = 0;
            return true;
        }

        return false;
    }
   
    public double getPercentComplete(double gametime)
    {
        
        if (isActive)
        {
         this.percent = (gametime - this.start)/(this.stop - this.start);
         return this.percent;
        }
        
        return 0;
    }
    
    public void cancelTimer()
    {
        this.isActive = false;
    }
    
}
