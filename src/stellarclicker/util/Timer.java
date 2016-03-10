
package stellarclicker.util;

/**
 *
 * @author matt
 * 
 * This class will be used as an option for timers. If threading does not work out.
 */

public class Timer {
    private Boolean isActive;
    private double start;
    private double stop;
    public Boolean isDone;
    public Timer()
    {
        this.isActive = false;
        this.start = 0;
        this.stop = 0;
        
        
    }
    
    public Boolean getActivation()
    {
            return isActive;
    }
        //starts the timer
    public void set(double gametime, int seconds)
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
}
