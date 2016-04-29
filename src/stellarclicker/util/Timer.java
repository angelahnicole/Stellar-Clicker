package stellarclicker.util;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file Timer.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Timer object which times & reports progress of specific events
 * --------------------------------------------------------------------------------------------------------------------------
    JME LICENSE
    ******************************************************************************
    Copyright (c) 2003-2016 jMonkeyEngine
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are
    met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.

    * Neither the name of 'jMonkeyEngine' nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
    TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
    PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
    CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *///========================================================================================================================

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.lang.*;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Timer
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    private Boolean isActive;
    private float start;
    private float stop;
    private float lastGametime;
    private int comparison;
    float percent;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    public Timer()
    {
        this.isActive = false;
        this.start = 0;
        this.stop = 0;
        this.percent = 0;
        this.comparison = 1;
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
    *  GET ACTIVATION
    * 
    * @return Boolean Whether or not the timer is activated
    *///=========================================================================================================================
    public Boolean getActivation()
    {
        return this.isActive;
    }
    
    /**========================================================================================================================== 
    *  SET
    * 
    * <br><br> Starts timer (seconds) which is based on the current gametime.
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
    *  CHECK COMPLETION
    * 
    * @param gametime the current gametimes
    * 
    * @return Boolean Whether or not the timer has fully completed
    *///=========================================================================================================================
    public Boolean checkCompletion(float gametime)
    {
        this.lastGametime = gametime;
        this.comparison = Float.compare(gametime, this.stop);
        if (isActive && this.comparison > 0)
        {
            isActive = false;
            this.start = 0;
            this.stop = 0;
            return true;
        }

        return false;
    }
    
    /**========================================================================================================================== 
    *  GET PERCENT COMPLETE
    * 
    * @param gametime the current gametime
    * 
    * @return float The timer's percentage of completion
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
    *  GET LAST GAME TIME
    * 
    * @return float The last recorded "game time"
    *///=========================================================================================================================
    public float getLastGametime()
    {
        return this.lastGametime;
    }
    
    /**========================================================================================================================== 
    *  GET LAST TIME LEFT
    * 
    * @return float The time left since it last saved
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
    *  GET LAST TIME ELAPSED
    * 
    * @return float The time elapsed since it last saved
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
    *  GET LAST PERCENT
    * 
    * @return float The percent complete since it last saved
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
    *  GET TIME REMAINING
    * 
    * @param gametime The current gametime
    * 
    * @return float How much time is left in the timer (if active)
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
    *  CANCEL TIMER
    * 
    * <br><br> Resets the timer object to default values.
    *///=========================================================================================================================
    public void cancelTimer()
    {
        this.isActive = false;
        this.start = 0;
        this.stop = 0;
        this.percent = 0;
    }
    
    
    /**========================================================================================================================== 
    *  GET TIME
    * 
    * 
    *///=========================================================================================================================
    public void getTime()
    {
      float current = this.stop - this.start;
      
    }
    
}
