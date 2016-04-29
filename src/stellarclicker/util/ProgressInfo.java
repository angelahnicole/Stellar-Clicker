package stellarclicker.util;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file ProgressInfo.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Simple container class that allows threads to update the progress percentage and progress text.
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

    * Neither the NAME of 'jMonkeyEngine' nor the names of its contributors
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

public class ProgressInfo
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    private float progressMade = 0.0f;
    private String progressText = "Loading...";
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  GET PROGRESS TEXT
    * 
    * @return String The text that is shown on the progress bar
    *///=========================================================================================================================
    public String getProgressText()
    {
        return this.progressText;
    }
    
    /**========================================================================================================================== 
    *  GET PROGRESS MADE
    * 
    * @return float The percentage of the progress bar that is shown
    *///=========================================================================================================================
    public float getProgressMade()
    {
        return this.progressMade;
    }
    
    /**========================================================================================================================== 
    *  SET PROGRESS
    * 
    * <br><br> Safely overwrites the text that is shown on the progress bar and the percentage of the bar that is shown
    * 
    * @param newProgressMade The percentage of the bar that is shown (this overwrites it)
    * @param newProgressText The text that is shown on the progress bars
    *///=========================================================================================================================
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
    
    /**========================================================================================================================== 
    *  UPDATE PROGRESS
    * 
    * <br><br> Safely overwrites the text that is shown on the progress bar and adds to the percentage of the bar that is 
    * shown
    * 
    * @param newProgressMade The percentage of the bar that is shown (this adds to it)
    * @param newProgressText The text that is shown on the progress bars
    *///=========================================================================================================================
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
    
    /**========================================================================================================================== 
    *  UPDATE PROGRESS
    * 
    * <br><br> Safely adds to the percentage of the bar that is shown
    * 
    * @param newProgressMade The percentage of the bar that is shown (this adds to it)
    *///=========================================================================================================================
    public synchronized void updateProgress(float moreProgressMade)
    {
        this.progressMade += moreProgressMade;
        
        // always stop at 1
        if(Float.compare(this.progressMade, 1.0f) >= 0)
        {
            this.progressMade = 1.0f;
        }
    }
    
    /**========================================================================================================================== 
    *  UPDATE PROGRESS
    * 
    * <br><br> Safely overwrites the text that is shown on the progress bar
    * 
    * @param newProgressText The text that is shown on the progress bars
    *///=========================================================================================================================
    public synchronized void updateProgress(String newProgressText)
    {
        this.progressText = newProgressText;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
