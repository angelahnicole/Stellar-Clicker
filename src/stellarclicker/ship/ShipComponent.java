
package stellarclicker.ship;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file MainApplication.java
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


import stellarclicker.util.BigNumber;
import stellarclicker.util.Timer;
import stellarclicker.util.EShipComponentState;
import stellarclicker.util.EShipStat;


public class ShipComponent
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    // ship-specific "constants"
    protected int BASE_TIME;
    protected int MAX_DURABILITY;
    protected int MIN_LEVEL;
    protected int MAX_LEVEL; 
    protected int NUM_SHIP_STATS;
    
    protected String name;
    protected int durability;
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
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------------------------------------------------------------------------
    public ShipComponent(String name)
    {
        
        // TODO: should have all of these values be passed in via the constructor
        
        this.BASE_TIME = 10;
        this.MAX_DURABILITY = 100;
        this.MIN_LEVEL = 1;
        this.MAX_LEVEL = 999; 
        this.NUM_SHIP_STATS = 1;

        this.name = name;
        this.durability = MAX_DURABILITY;
        this.level = MIN_LEVEL;
        this.currentState = EShipComponentState.INACTIVE;
        
        this.levelCost = 10000000.0f;
        this.repairCost = this.levelCost * 0.1f;
        
        updateTimeTaken();
        this.timer = new Timer();

        System.out.println("Constructing component " + this.name);
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
        // Matt:  if statement seems messy.  I'd like a better way but can't think at the moment.
        if (this.currentState != EShipComponentState.BROKEN)
        {
            // check for completion of timers
            if (this.timer.checkCompletion(gameTime))
            {
                if(this.currentState == EShipComponentState.GAINING_EXP)
                {
                    levelUp();
                    this.timer.cancelTimer();
                    
                }
                else if(this.currentState == EShipComponentState.REPAIRING)
                {
                    repairComponent();
                    this.timer.cancelTimer();
                }
                
            }
            
        }
    }
    
    /**========================================================================================================================== 
    * @name GAIN EXPERIENCE
    * 
    * @description Start experience timer and set component state to GAINING EXP
    * 
    *///=========================================================================================================================
    public void gainExperience()
    {
        this.timer.set(this.gameTime, this.expTime);
        System.out.println("Starting timer for " + this.name);
        
        this.currentState = EShipComponentState.GAINING_EXP;
         
        
    }
    
    /**========================================================================================================================== 
    * @name levelUp
    * 
    * @description Increases the level of this component, updates the time taken to repair and gain experience, and sets its 
    * state to inactive.
    * 
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
        System.out.println("Starting timer for " + this.name);
        
        this.currentState = EShipComponentState.REPAIRING; 
    }
    
    /**========================================================================================================================== 
    * @name DEGRADE COMPONENT
    * 
    * @description Reduces the components durability 
    * 
    * @param amount the amount to damage by
    *///=========================================================================================================================
    public void degradeComponent(int amount)
    {
        this.durability = this.durability - amount;
        
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
    * @name breakComponent
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
        System.out.println("get stuff");
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
    * @name GET COMPONENT STATE
    * 
    * @description Returns the state of the component 
    * 
    *///=========================================================================================================================
    public EShipComponentState getComponentState()
    {
        return this.currentState;
    }
    
    /**========================================================================================================================== 
    * @name UPDATE TIME TAKEN
    * 
    * @description Updates the current time taken to repair and gain experience
    *///=========================================================================================================================
    private void updateTimeTaken()
    {
        this.expTime = this.BASE_TIME / this.level;
        this.repairTime = this.expTime / 10;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    
}
