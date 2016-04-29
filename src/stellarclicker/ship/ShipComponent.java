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

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.Random;
import stellarclicker.util.BigNumber;
import stellarclicker.util.Timer;
import stellarclicker.util.EShipComponentState;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ShipComponent implements Savable
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
    protected long eT;
    protected long rT;
    
    protected float experienceTime;
    protected float repairTime;
    
    protected Timer timer;
    protected float gameTime;

    // whether the component is managed by an officer.
    // TODO: use ship component state instead of this bool
    protected boolean managed;
    protected Random rand;
    
    // persistence
    private boolean isTimerSet;
    private float lastTimeElapsed;
    private float lastTimeLeft;
    private OutputCapsule outCapsule;
    private InputCapsule inCapsule;
    
    protected int[] affectedStats;
    
    protected int statBoost;
    protected boolean leveled;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    public ShipComponent(String NAME, int BASE_TIME, int MAX_DUR, int MIN_LEVEL, int MAX_LEVEL, int NUM_STATS, int[] affectedStats, float LEVEL_COST, int[] levelTiers, String basePictureName)
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
        this.isTimerSet = true;
        
        this.statBoost = 0;
        this.leveled = false;
        
        this.affectedStats = affectedStats;
    }
    
    public ShipComponent()
    {
        updateTimeTaken();
        
        this.rand = new Random();
        this.timer = new Timer();
        this.durabilityLossRange = 25;
        this.isTimerSet = true;
        this.statBoost = 0;
        this.leveled = false;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // PERSISTENCE METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  WRITE
    * 
    *  <br><br> Saves a saved ship from file
    * 
    * @param ex A jMonkeyEngine exporter
    *///=========================================================================================================================
    public void write(JmeExporter ex) throws IOException
    {
        outCapsule = ex.getCapsule(this);
        outCapsule.write(levelTiers, "levelTiers", null);
        outCapsule.write(basePictureName, "basePictureName", "");
        outCapsule.write(NAME, "NAME", "");
        outCapsule.write(BASE_TIME, "BASE_TIME", 0);
        outCapsule.write(MAX_DURABILITY, "MAX_DURABILITY", 0);
        outCapsule.write(MIN_LEVEL, "MIN_LEVEL", 0);
        outCapsule.write(MAX_LEVEL, "MAX_LEVEL", 0);
        outCapsule.write(NUM_SHIP_STATS, "NUM_SHIP_STATS", 0);
        outCapsule.write(durability, "durability", MAX_DURABILITY);
        outCapsule.write(level, "level", MIN_LEVEL);
        outCapsule.write(currentState, "currentState", EShipComponentState.INACTIVE);
        outCapsule.write(levelCost, "levelCost", 0);
        outCapsule.write(repairCost, "repairCost", levelCost * 0.1f);
        outCapsule.write(managed, "managed", false);
        outCapsule.write(affectedStats, "affectedStats", null);
        
        
        // update timer information
        outCapsule.write(timer.getActivation(), "isActive", false);
        outCapsule.write(timer.getLastTimeLeft(), "timeLeft", -1);
        outCapsule.write(timer.getLastTimeElapsed(), "timeElapsed", -1);
    }
    
    /**========================================================================================================================== 
    *  READ
    * 
    *  <br><br> Loads a saved ship from file
    * 
    * @param im A jMonkeyEngine importer
    *///=========================================================================================================================
    public void read(JmeImporter im) throws IOException
    {
        inCapsule = im.getCapsule(this);
        this.levelTiers = inCapsule.readIntArray("levelTiers", levelTiers);
        this.basePictureName = inCapsule.readString("basePictureName", "");
        this.NAME = inCapsule.readString("NAME", "");
        this.BASE_TIME = inCapsule.readInt("BASE_TIME", 0);
        this.MAX_DURABILITY = inCapsule.readInt("MAX_DURABILITY", 0);
        this.MIN_LEVEL = inCapsule.readInt("MIN_LEVEL", 0);
        this.MAX_LEVEL = inCapsule.readInt("MAX_LEVEL", 0);
        this.NUM_SHIP_STATS = inCapsule.readInt("NUM_SHIP_STATS", 0);
        this.affectedStats = inCapsule.readIntArray("affectedStats", affectedStats);
        this.durability = inCapsule.readInt("durability", MAX_DURABILITY);
        this.level = inCapsule.readInt("level", MIN_LEVEL);
        this.currentState = inCapsule.readEnum("currentState", EShipComponentState.class, EShipComponentState.INACTIVE);
        this.levelCost = inCapsule.readDouble("levelCost", 0);
        this.repairCost = inCapsule.readDouble("repairCost", levelCost * 0.1f);
        this.managed = inCapsule.readBoolean("managed", false);
        
        boolean isTimerActive = inCapsule.readBoolean("isActive", false);
        this.lastTimeLeft = inCapsule.readFloat("timeLeft", -1);
        this.lastTimeElapsed = inCapsule.readFloat("timeElapsed", -1);
        
        if(isTimerActive)
        {
            this.isTimerSet = false;
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // MAIN LIFECYCLE METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  UPDATE
    * 
    *  <br><br> Runs the components update cycle 
    * 
    * @param gameTime The main game time state
    *///=========================================================================================================================
    public void update(float gameTime)
    {
        this.gameTime = gameTime;

        manageTimers();
    }
    
    /**========================================================================================================================== 
    *  MANAGE TIMERS
    * 
    * <br><br> 
    *///=========================================================================================================================
    public void manageTimers()
    {
        // check if timers need to be reset
        if(!this.isTimerSet)
        {
            // set the timer with what is left
            // note: this has an obvious flaw: the percentages will be completely wrong, but the time keeping would still be correct
            this.timer.set(this.gameTime, this.lastTimeLeft);
            
            // make sure that we don't do it again
            this.isTimerSet = true;
        }
        
        if (this.currentState != EShipComponentState.BROKEN || this.currentState != EShipComponentState.INACTIVE)
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
    *  GAIN EXPERIENCE
    * 
    *  <br><br> Start experience timer and set component state to GAINING EXP
    *///=========================================================================================================================
    public void gainExperience()
    {
        //prevent experience gain if gaining experience.
        if (this.currentState != EShipComponentState.BROKEN)
        {
            this.timer.set(this.gameTime, this.experienceTime);
            this.currentState = EShipComponentState.GAINING_EXP;
        }
        
    }
    
   
    /**========================================================================================================================== 
    *  LEVEL UP
    * 
    *  <br><br> Increases the level of this component, updates the time taken to repair and gain experience, and sets its 
    *  state to inactive.
    *///=========================================================================================================================
    public void levelUp()
    {
        this.level += 1;
        updateTimeTaken();
        this.leveled = true;
        this.currentState = EShipComponentState.INACTIVE;
    }
    
    /**========================================================================================================================== 
    *  CHECK LEVELED
    * 
    *  @return boolean 
    *///=========================================================================================================================
    public boolean checkLeveled()
    {
        boolean temp = this.leveled;
        this.leveled = false;
        return temp;
    }
    /**========================================================================================================================== 
    *  LEVEL UP
    * 
    *  <br><br> Increases the level of this component, updates its state, but does NOT update the time taken to repair and
    *  gain experience if indicated. This is to be used when leveling up components after a save.
    * 
    * @param updateTimeTaken Whether or not to update the time
    * 
    * @return int The new level of the component
    *///=========================================================================================================================
    public int levelUp(boolean updateTimeTaken)
    {
        this.level += 1;
        this.currentState = EShipComponentState.INACTIVE;
        
        if(updateTimeTaken)
        {
            updateTimeTaken();
        }
        
        return this.level;
    }
    
    /**========================================================================================================================== 
    *  GAIN REPAIR
    * 
    *  <br><br> Starts the repair component timer and set its component state to repairing 
    *///=========================================================================================================================
    public void gainRepair()
    {
        this.timer.set(this.gameTime, this.repairTime);
        
        
        this.currentState = EShipComponentState.REPAIRING; 
    }
    
    /**========================================================================================================================== 
    *  DEGRADE COMPONENT
    * 
    *  <br><br> Reduces the components durability 
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
    *  REPAIR COMPONENT
    * 
    *  <br><br> Repairs the component by setting its durability to the max and sets it component state to inactive
    *///=========================================================================================================================
    public void repairComponent()
    {
        this.durability = this.MAX_DURABILITY;
        
        this.currentState = EShipComponentState.INACTIVE;
    }
     
    /**========================================================================================================================== 
    *  BREAK COMPONENT
    * 
    *  <br><Br> Sets the state to broken and the durability to 0 
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
    *  GET FORMATTED LEVEL COST
    * 
    *  @return String A formatted string of the level cost
    *///=========================================================================================================================
    public String getFormattedLevelCost()
    {
        return BigNumber.getNumberString(levelCost);
    }
    
    /**========================================================================================================================== 
    *  GET FORMATTED REPAIR COST
    * 
    *  @return String A formatted string of the repair cost
    *///=========================================================================================================================
    public String getFormattedRepairCost()
    {
        return BigNumber.getNumberString(repairCost);
    }
    
    /**========================================================================================================================== 
    *  GET REPAIR COST
    * 
    *  @return double A formatted string of the repair cost
    *///=========================================================================================================================
    public double getRepairCost()
    {
        return this.repairCost;
    }
    
    /**========================================================================================================================== 
    *  GET LEVEL COST
    * 
    *  @return double A formatted string of the repair cost
    *///=========================================================================================================================
    public double getLevelCost()
    {
        return this.levelCost;
    }
    
    /**========================================================================================================================== 
    *  GET SHIP STATS
    * 
    *  <br><br>Get ship statistics that are affected by this component
    * 
    * @returns int[] An array of ordinal integer values of EShipStat enums
    *///=========================================================================================================================
    public int[] getShipStats()
    {
        return this.affectedStats;
    }
    
    
    /**========================================================================================================================== 
    *  GET NUM SHIP STATS 
    * 
    * @returns int The number of ship stats it affects
    *///=========================================================================================================================
    public int getNumShipStats()
    {
        return this.NUM_SHIP_STATS;
    }
    
    /**========================================================================================================================== 
    *  GET LEVEL
    * 
    *  @return int Returns the current level of this 
    *///=========================================================================================================================
     public int getLevel()
    {
        return this.level;
    }
    
    /**========================================================================================================================== 
    *  GET TIMER PERCENT
    * 
    *  @return double Returns the percent of the timer completion 
    *///=========================================================================================================================
    public double getTimerPercent()
    {
        return this.timer.getPercentComplete(gameTime);
        
    }
    
    /**========================================================================================================================== 
    *  GET TIME REMAINING
    * 
    *  @return String A formatted string of the percent of the timer completion 
    *///=========================================================================================================================
    public String getTimeRemaining()
    {
        Float time = this.timer.getTimeRemaining(gameTime);
        int hours = (int) (time / 3600);
        int remainder = (int)Math.ceil(time - hours * 3600);
        int mins = (int)Math.ceil(remainder / 60);
        remainder = remainder - mins * 60;
        int sec = (int)Math.ceil(remainder);
        
        return String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":" + String.format("%02d", sec);
    }
    
    /**========================================================================================================================== 
    *  GET COMPONENT STATE
    * 
    *  @return EShipComponentState The state of the component 
    *///=========================================================================================================================
    public EShipComponentState getComponentState()
    {
        return this.currentState;
    }
    
    /**========================================================================================================================== 
    *  GET CURRENT TIER
    * 
    * @return int The current tier that the ship component is on
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
    *  GET CURRENT PICTURE NAME
    * 
    *  @return String Returns the name of the picture based on its tier
    *///=========================================================================================================================
    public String getCurrentPictureName()
    {
        return String.format(this.basePictureName, getCurrentTier());
    }
    
    /**========================================================================================================================== 
    *  GET TIME TAKEN
    * 
    * <br><br> Returns the amount of time it will take to level up the component. Will be used to help load saved games.
    * 
    * @param level The current level of the component
    * @param returnExpTime Whether or not to return experience time or repair time
    * 
    * @return float The time taken to level it up or repair it
    *///=========================================================================================================================
    public float getTimeTaken(int level, boolean returnExpTime)
    {
        float timeTaken = (float) this.BASE_TIME / ( ((float)level/10.00f) + 1.00f);
        
        // return time it takes to gain experience
        if(returnExpTime)
        {
            return timeTaken;
        }
        // return time it takes to repair
        else
        {
            return timeTaken * 2;
        }
    }
    
    /**==========================================================================================================================
    *  GET LAST TIME LEFT
    * 
    *  @return float The last saved time left in a running timer on a component
    *///=========================================================================================================================
    public float getLastTimeLeft()
    {
        return this.lastTimeLeft;
    }
    
    /**========================================================================================================================== 
    * GET LAST TIME ELAPSED
    * 
    * @return float The last saved time elapsed in a running timer on a component
    *///=========================================================================================================================
    public float getLastTimeElapsed()
    {
        return this.lastTimeElapsed;
    }
    
    
    /**========================================================================================================================== 
    *  UPDATE TIME TAKEN
    * 
    * <br><br> Updates the current time taken to repair and gain experience
    *///=========================================================================================================================
    public void updateTimeTaken()
    {
        this.experienceTime = this.BASE_TIME / ((this.level/10)+1);
        this.repairTime = this.experienceTime * 2;
        
        if (this.experienceTime < 0.5)
        {
            this.experienceTime = (float)0.5;
        }
        
        if (this.repairTime < 1.0)
        {
            this.repairTime = (float)1.0;
        }
    }
    
    /**========================================================================================================================== 
    *  SET SAVED TIME
    * 
    * <br><br> Updates the time left and time elapsed during experience/repair gain
    * 
    * @param lastTimeLeft Last saved time left in a running timer on a component
    * @param lastTimeElapsed Last saved time elapsed in a running timer on a component
    *///=========================================================================================================================
    public void setSavedTime(float lastTimeLeft, float lastTimeElapsed)
    {
        this.lastTimeLeft = lastTimeLeft;
        this.lastTimeElapsed = lastTimeElapsed;
    }
    
    /**========================================================================================================================== 
    *  SET COMPONENT STATE
    * 
    * <br><br> Updates the component state ont he 
    *///=========================================================================================================================
    public void setComponentState(EShipComponentState newState)
    {
        this.currentState = newState;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}
