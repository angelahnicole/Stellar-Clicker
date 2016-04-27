/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stellarclicker.util;

/**
 *
 * @author evalca
 */
public class ProgressInfo
{
    private float progressMade = 0.0f;
    private String progressText = "Loading...";
    
    public String getProgressText()
    {
        return this.progressText;
    }
    
    public float getProgressMade()
    {
        return this.progressMade;
    }
    
    public synchronized void setProgress(float newProgressMade, String newProgressText)
    {
        this.progressMade = newProgressMade;
        this.progressText = newProgressText;
        
        // always stop at 1
        if(Float.compare(this.progressMade, 1.0f) >= 0)
        {
            this.progressMade = 1.0f;
        }
    }
    
    public synchronized void updateProgress(float moreProgressMade, String newProgressText)
    {
        this.progressMade += moreProgressMade;
        this.progressText = newProgressText;
        
        // always stop at 1
        if(Float.compare(this.progressMade, 1.0f) >= 0)
        {
            this.progressMade = 1.0f;
        }
    }
    
    public synchronized void updateProgress(float moreProgressMade)
    {
        this.progressMade += moreProgressMade;
        
        // always stop at 1
        if(Float.compare(this.progressMade, 1.0f) >= 0)
        {
            this.progressMade = 1.0f;
        }
    }
    
    public synchronized void updateProgress(String newProgressText)
    {
        this.progressText = newProgressText;
    }
}
