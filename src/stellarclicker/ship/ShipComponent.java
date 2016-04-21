
package stellarclicker.ship;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file ShipComponent.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description This class defines a ship component for the ship 
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

import java.util.Random;
import stellarclicker.util.BigNumber;
import stellarclicker.util.Timer;
import stellarclicker.util.EShipComponentState;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ShipComponent
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    //  The ship component's level tiers that corresponds with the different pictures it can upgrade to.
    protected int[] levelTiers;
    protected String basePictureName;
    
    // ship-specific "constants"
    protected String NAME;
    protected int BASE_TIME;
    protected int MAX_DURABILITY;
    protected int MIN_LEVEL;
    protected int MAX_LEVEL; 
    protected int NUM_SHIP_STATS;
    
    protected int durability;
    protected int durabilityLossRange;
    protected int level;
    protected EShipComponentState currentState;
    
    // money related 
    protected double levelCost;
    protected double repairCost;    
    
    // timers to change components.
    protected long expTime;
    protected long repairTime;
    protected Timer timer;
    protected float gameTime;

    // whether the component is managed by an officer.
    // TODO: use ship component state instead of this bool
    protected boolean managed;
    
    protected Random rand;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------------------------------------------------------------------------
    public ShipComponent(String NAME, int BASE_TIME, int MAX_DUR, int MIN_LEVEL, int MAX_LEVEL, int NUM_STATS, float LEVEL_COST, int[] levelTiers, String basePictureName)
    {
        this.levelTiers = levelTiers;
        this.basePictureName = basePictureName;
        
        this.NAME = NAME;
        this.BASE_TIME = BASE_TIME;
        this.MAX_DURABILITY = MAX_DUR;
        this.MIN_LEVEL = MIN_LEVEL;
        this.MAX_LEVEL = MAX_LEVEL; 
        this.NUM_SHIP_STATS = NUM_STATS;

        this.durability = MAX_DURABILITY;
        this.level = MIN_LEVEL;
        this.currentState = EShipComponentState.INACTIVE;
        
        this.levelCost = LEVEL_COST;
        this.repairCost = this.levelCost * 0.1f;
        
        updateTimeTaken();
        this.timer = new Timer();

        this.rand = new Random();
        this.durabilityLossRange = 25;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // MAIN LIFECYCLE METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    * @name UPDATE
    * 
    * @description Runs the components update cycle 
    * 
    * @param gameTime The main game time state
    *///=========================================================================================================================
    public void update(float gameTime)
    {
        this.gameTime = gameTime;

        manageTimers();
    }
    
    /**========================================================================================================================== 
    * @name MANAGE TIMERS
    * 
    * @description 
    *///=========================================================================================================================
    public void manageTimers()
    {
        
        if (this.currentState != EShipComponentState.BROKEN)
        {
            // check for completion of timers
            if (this.timer.checkCompletion(gameTime))
            {
                if(this.currentState == EShipComponentState.GAINING_EXP)
                {
                    this.timer.cancelTimer();
                    levelUp();
                    
                    //degrade component
                    degradeComponent();
                    
                }
                else if(this.currentState == EShipComponentState.REPAIRING)
                {
                    this.timer.cancelTimer();
                    repairComponent();
                }
            }
        }
    }
    
    /**========================================================================================================================== 
    * @name GAIN EXPERIENCE
    * 
    * @description Start experience timer and set component state to GAINING EXP
    *///=========================================================================================================================
    public void gainExperience()
    {
        //prevent experience gain if gaining experience.
        if (this.currentState != EShipComponentState.BROKEN)
        {this.timer.set(this.gameTime, this.expTime);
        
        
        this.currentState = EShipComponentState.GAINING_EXP;
        }
        
    }
    
    /**========================================================================================================================== 
    * @name LEVEL UP
    * 
    * @description Increases the level of this component, updates the time taken to repair and gain experience, and sets its 
    * state to inactive.
    *///=========================================================================================================================
    public void levelUp()
    {
        this.level += 1;
        updateTimeTaken();
        
        this.currentState = EShipComponentState.INACTIVE;
    }
    
    /**========================================================================================================================== 
    * @name GAIN REPAIR
    * 
    * @description Starts the repair component timer and set its component state to repairing 
    *///=========================================================================================================================
    public void gainRepair()
    {
        this.timer.set(this.gameTime, this.repairTime);
        
        
        this.currentState = EShipComponentState.REPAIRING; 
    }
    
    /**========================================================================================================================== 
    * @name DEGRADE COMPONENT
    * 
    * @description Reduces the components durability 
    *///=========================================================================================================================
    public void degradeComponent()
    {
        this.durability = this.durability - (rand.nextInt(this.durabilityLossRange) + 1);
        
        if (this.level % 20 == 0 && this.durabilityLossRange > 1)
        {
            this.durabilityLossRange = this.durabilityLossRange - 1;
        }
        if (this.durability < 0)
        {
            breakComponent();
        }
    }
    
    /**========================================================================================================================== 
    * @name REPAIR COMPONENT
    * 
    * @description Repairs the component by setting its durability to the max and sets it component state to inactive
    *///=========================================================================================================================
    public void repairComponent()
    {
        this.durability = this.MAX_DURABILITY;
        
        this.currentState = EShipComponentState.INACTIVE;
    }
     
    /**========================================================================================================================== 
    * @name BREAK COMPONENT
    * 
    * @description Sets the state to broken and the durability to 0 
    *///=========================================================================================================================
    private void breakComponent()
    {
        this.durability = 0;
        this.currentState = EShipComponentState.BROKEN;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // GETTERS/SETTERS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    * @name GET FORMATTED LEVEL COST
    * 
    * @description Returns a formatted string of the level cost
    *///=========================================================================================================================
    public String getFormattedLevelCost()
    {
        return BigNumber.getNumberString(levelCost);
    }
    
    /**========================================================================================================================== 
    * @name GET FORMATTED REPAIR COST
    * 
    * @description Returns a formatted string of the repair cost
    *///=========================================================================================================================
    public String getFormattedRepairCost()
    {
        return BigNumber.getNumberString(repairCost);
    }
    
    /**========================================================================================================================== 
    * @name GET REPAIR COST
    * 
    * @description Returns a formatted string of the repair cost
    *///=========================================================================================================================
    public double getRepairCost()
    {
        return this.repairCost;
    }
    
    /**========================================================================================================================== 
    * @name GET LEVEL COST
    * 
    * @description Returns a formatted string of the repair cost
    *///=========================================================================================================================
    public double getLevelCost()
    {
        return this.levelCost;
    }
    /**========================================================================================================================== 
    * @name GET SHIP STATISTIC
    * 
    * @description Get the Statistic 
    *///=========================================================================================================================
    public void getShipStatistic()
    {
        
    }
    
    /**========================================================================================================================== 
    * @name GET LEVEL
    * 
    * @description Returns the current level of this 
    * 
    *///=========================================================================================================================
     public int getLevel()
    {
        return this.level;
    }
    
    /**========================================================================================================================== 
    * @name GET TIMER PERCENT
    * 
    * @description Returns the percent of the timer completion 
    * 
    * @param gameTime The main application time
    *///=========================================================================================================================
    public double getTimerPercent()
    {
        return this.timer.getPercentComplete(gameTime);
        
    }
    
    /**========================================================================================================================== 
    * @name GET TIME Remaining
    * 
    * @description Returns the percent of the timer completion 
    * 
    * @param gameTime The main application time
    *///=========================================================================================================================
    public String getTimeRemaining()
    {
        Float time = this.timer.getTimeRemaining(gameTime);
        int hours = (int) (time / 3600);
        int remainder = (int)Math.ceil(time - hours * 3600);
        int mins = (int)Math.ceil(remainder / 60);
        remainder = remainder - mins * 60;
        int sec = (int)Math.ceil(remainder);
        return hours + ":" + mins + ":" + sec;
    }
    
    /**========================================================================================================================== 
    * @name GET COMPONENT STATE
    * 
    * @description Returns the state of the component 
    *///=========================================================================================================================
    public EShipComponentState getComponentState()
    {
        return this.currentState;
    }
    
    /**========================================================================================================================== 
    * @name GET CURRENT TIER
    * 
    * @description Returns the current tier that the ship component is on
    *///=========================================================================================================================
    public int getCurrentTier()
    {
        int currentTier = 1;
        
        for(int i = 0; i < levelTiers.length; i++)
        {
            if(this.level >= levelTiers[i])
            {
                currentTier = i + 1;
            }
            else
            {
                break;
            }
        }
        
        return currentTier;
    }
    
    /**========================================================================================================================== 
    * @name GET CURRENT PICTURE NAME
    * 
    * @description Returns the name of the picture based on its tier
    *///=========================================================================================================================
    public String getCurrentPictureName()
    {
        return String.format(this.basePictureName, getCurrentTier());
    }
    
    /**========================================================================================================================== 
    * @name UPDATE TIME TAKEN
    * 
    * @description Updates the current time taken to repair and gain experience
    *///=========================================================================================================================
    private void updateTimeTaken()
    {
        this.expTime = this.BASE_TIME / ((this.level/10)+1);
        this.repairTime = this.expTime * 2;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}
