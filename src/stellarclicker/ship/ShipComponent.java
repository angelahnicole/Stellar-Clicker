
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
  
    public int NUM_SHIP_STAT_COMPONENETS;
    protected String name;
    protected int MAX_DURABILITY;
    protected int MAX_LEVEL;
    protected float BASE_RANK;
    protected double levelCost;
    protected double repairCost;    
    protected int durability;
    protected int level;
    protected int currentExp;
    protected int nextLevelExp;
    protected boolean isEnabled;
    protected EShipComponentState currentState;
    
    protected EShipStat[] shipStatCompUnlocksIdx;
    
    //rate at which a component upgrades per experience gain
    protected int expGain;

    
    //protected ShipStatEnum[] shipStatCompUnlocksIdx;

    protected int[][] shipStatUnlocks;
    
    //Timers to change components.
    protected long expTime;
    protected long repairTime;
    protected Timer timer;
    
    protected float gameTime;
    
    //whether the component is managed by an officer.
    protected boolean managed;
   //Constructor 
    public ShipComponent(String name)
    {
        this.name = name;
        this.timer = new Timer();
        this.isEnabled = false;
        this.expTime = 5;
        this.repairTime =3;
        this.durability = 100;
        this.currentState = EShipComponentState.INACTIVE;
     System.out.println("Constructing component " + this.name);
       
    }
    
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
        
        
        if (this.currentExp > this.nextLevelExp)
        {
            levelUp();
            
        }
    }
    /**========================================================================================================================== 
    * @name GETCOMPONENTSTATE
    * 
    * @description Returns the state of the component 
    * 
    *///=========================================================================================================================

    public Enum getComponentState()
    {
        
            return this.currentState;
    }
      
    /**========================================================================================================================== 
    * @name manageTimers
    * 
    * @description Self explanatory  
    * 
    *///=========================================================================================================================
    
    public void manageTimers()
    {
        
        //Matt:  if statement seems messy.  I'd like a better way but can't think at the moment.
        if (!isBroken())
        {
        //check for completion of timers
        if (this.timer.checkCompletion(gameTime))
        {
            //experience gain
            levelUp();
            System.out.println("Stopping timer for " + this.name);
            isEnabled = false;
            
            if (this.managed)
            {
                 //start a new expTimer
                this.timer.cancelTimer();
                this.timer.set(gameTime, this.expTime);
                System.out.println("Resetting timer for " + this.name);
            }
            
        }  
           
        }
       
        
    }
     /**========================================================================================================================== 
    * @name TIMERPERCENT
    * 
    * @description Returns the percent of the timer completion 
    * 
    * @param gameTime The main application time
    *///=========================================================================================================================
   
    public double timerPercent()
    {
        return this.timer.getPercentComplete(gameTime);
        
    }
    
     /**========================================================================================================================== 
    * @name INITEXPERIENCETIMER
    * 
    * @description Starts exp timer for this component
    * 
    * 
    *///=========================================================================================================================
   
    public void initExperienceTimer()
    {
        
        this.timer.set(this.gameTime, this.expTime);
        System.out.println("Starting timer for " + this.name);
    }
    
    
      /**========================================================================================================================== 
    * @name INITREPAIRTIMER
    * 
    * @description Starts repair timer for this component
    * 
    * 
    *///=========================================================================================================================
   
    public void initRepairTimer()
    {
        
        this.timer.set(this.gameTime, this.repairTime);
    }
    
    
    
    public void degradeComponent()
    {
        System.out.println("this is degrading");
    }
    
    /**========================================================================================================================== 
    * @name repairComponent
    * 
    * @description Sets the repair state 
    * 
    *///=========================================================================================================================

    public void repairComponent()
    {
        System.out.println("repair");
        this.durability = 100;
        this.currentState = EShipComponentState.ACTIVE;
        
    }
    
    /**========================================================================================================================== 
    * @name damageComponent
    * 
    * @description Reduces the components durability 
    * 
    * @param amount the amount to damage by
    *///=========================================================================================================================

    public void damageComponent(int amount)
    {
        this.durability = this.durability - amount;
        //check if broken
        
        if (this.durability < 0)
        {
            breakComponent();
        }
    }
    
    /**========================================================================================================================== 
    * @name isBroken
    * 
    * @description Returns wheather the compnnent is in a broken state 
    * 
    *///=========================================================================================================================

    public boolean isBroken()
    {
        if(this.durability <= 0)
        {
        
        return true;
        }
        else
            return false;
    }
    
    
    /**========================================================================================================================== 
    * @name getShipstatistic
    * 
    * @description Get the Statistic 
    * 
    *///=========================================================================================================================

    public void getShipStatistic()
    {
        System.out.println("get stuff");
    }
    
    /**========================================================================================================================== 
    * @name enable
    * 
    * @description Put this component in the enabled state 
    * 
    *///=========================================================================================================================

    public void enable()
    {
        
        this.isEnabled = true;
        this.currentState = EShipComponentState.ACTIVE;
    }
    
    /**========================================================================================================================== 
    * @name GainExperience
    * 
    * @description Add exp to this  
    * 
    *///=========================================================================================================================

    public void gainExperience()
    {
        
        
        this.currentExp += this.expGain;
        this.currentState = EShipComponentState.GAINING_EXP;
          
    }
    /**========================================================================================================================== 
    * @name getLevel
    * 
    * @description Returns the current level of this 
    * 
    *///=========================================================================================================================
     public int getLevel()
    {
        return this.level;
    }
    
    /**========================================================================================================================== 
    * @name levelUp
    * 
    * @description Increases the level of this 
    * 
    *///=========================================================================================================================

    private void levelUp()
    {
        this.level += 1;
        this.currentExp = this.currentExp - this.nextLevelExp;
        this.currentState = EShipComponentState.ACTIVE;
    }
    
    /**========================================================================================================================== 
    * @name breakComponent
    * 
    * @description Sets the state to broken and the durability to 0 
    * 
    *///=========================================================================================================================

   private void breakComponent()
    {
        this.isEnabled = false;
        this.durability = 0;
        this.currentState = EShipComponentState.BROKEN;
    }
}
