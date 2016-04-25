package stellarclicker.ship;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file Ship.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description This is the ship class that defines the ship object
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
import java.text.DateFormat;

import stellarclicker.util.BigNumber;
import stellarclicker.util.EShipComponent;
import stellarclicker.util.ESeniorStaff;
import stellarclicker.util.EShipComponentState;
import stellarclicker.util.EShipStat;
import stellarclicker.util.ComponentFactory;
import stellarclicker.util.StaffFactory;
import stellarclicker.util.*;

import java.util.Arrays;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Ship implements Savable
{
   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    // The ship's level tiers that corresponds to the different pictures it can upgrade to. 
    // All of its ship components will have to be at the level for each tier for it to upgrade
    private final int[] allLevelTiers = { 1, 5, 10, 15, 20 };
    private final String basePictureName = "mainShip_%s.png";
    
    private ShipComponent[] brokenComponents; 
    private ShipComponent[] activeComponents;  
    private ShipComponent[] inactiveComponents;
    private ShipComponent[] shipComponents;
    private ShipStatistics shipStats;
    private SeniorStaff[] seniorStaff;
    private double officers;
    private double claimableOfficers;
    private double money;
    private double moneyPerSecond;
    private ComponentFactory compFactory;
    private StaffFactory staffFactory;
    private int previousMoneyTime;
    
    private OutputCapsule outCapsule;
    private InputCapsule inCapsule;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------------------------------------------------------------------------
    public Ship()      
    {    
        compFactory = new ComponentFactory();
        staffFactory = new StaffFactory();
        this.initializeComponents();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // PERSISTENCE METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    public void write(JmeExporter ex) throws IOException
    {
        outCapsule = ex.getCapsule(this);
        outCapsule.write(shipComponents, "shipComponents", null);
        outCapsule.write(shipStats, "shipStats", null);
        outCapsule.write(seniorStaff, "seniorStaff", null);
        outCapsule.write(officers, "officers", 0);
        outCapsule.write(claimableOfficers, "claimableOfficers", 0);
        outCapsule.write(money, "money", 0);
        outCapsule.write(moneyPerSecond, "moneyPerSecond", 0);
        outCapsule.write(previousMoneyTime, "previousMoneyTime", 0);
        outCapsule.write(new Date().toString(), "saveDate", new Date().toString());
    }
    
    public void read(JmeImporter im) throws IOException
    {
        inCapsule = im.getCapsule(this);
        
        // cast savable array to ship component array
        Savable[] savedShipComps = inCapsule.readSavableArray("shipComponents", shipComponents);
        this.shipComponents = Arrays.copyOf(savedShipComps, savedShipComps.length, ShipComponent[].class);
        
        // cast savable array to senior staff array
        Savable[] savedSeniorStaff = inCapsule.readSavableArray("seniorStaff", seniorStaff);
        this.seniorStaff = Arrays.copyOf(savedSeniorStaff, savedSeniorStaff.length, SeniorStaff[].class);

        this.shipStats = (ShipStatistics) inCapsule.readSavable("shipStats", shipStats);
        this.officers = inCapsule.readDouble("officers", officers);
        this.claimableOfficers = inCapsule.readDouble("claimableOfficers", claimableOfficers);
        this.money = inCapsule.readDouble("money", money);
        this.moneyPerSecond = inCapsule.readDouble("moneyPerSecond", moneyPerSecond);
        this.previousMoneyTime = inCapsule.readInt("previousMoneyTime", previousMoneyTime);
        
        
        String lastSave = inCapsule.readString("saveDate", "");
        if(!lastSave.isEmpty())
        {
            long secondsSinceSave = getSecondsSinceSave(lastSave);
            
            // update ship components here
            
        }
    }
    
    private long getSecondsSinceSave(String lastSave)
    {
        DateFormat df = new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy", Locale.ENGLISH);
        Date saveDate = null;
        Date nowDate = new Date();
        long secondsSinceSave = 0;

        try
        {
            saveDate = df.parse(lastSave);
        }
        catch (ParseException ex)
        {
            Logger.getLogger(Ship.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(saveDate != null)
        {
           secondsSinceSave = (nowDate.getTime() - saveDate.getTime()) / 1000;
        }
        
        return secondsSinceSave;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**========================================================================================================================== 
    * @name INITIALIZE COMPONENTS
    * 
    * @description Initializes component array and creates new components from Enum  
    *///=========================================================================================================================
    private void initializeComponents()
    {
        //TODO: THIS SHOULDN'T BE STARTING MONEY
        this.money = 1.0;
        
        // Initializes the component array 
        shipComponents = new ShipComponent[EShipComponent.values().length];
        seniorStaff = new SeniorStaff[ESeniorStaff.values().length];
        inactiveComponents =  new ShipComponent[EShipComponent.values().length]; 
        activeComponents =  new ShipComponent[EShipComponent.values().length];
        brokenComponents =  new ShipComponent[EShipComponent.values().length];
        
        // This for each creates a new component and places it in the array at the index
        for(EShipComponent m : EShipComponent.values()) 
        {
           shipComponents[m.ordinal()] = compFactory.buildComponent(m);
           //shipComponents[m.ordinal()] = new ShipComponent(m.name());
        }

        // This for each creates ship stats 
        this.shipStats = new ShipStatistics();

        //creates the senior staff which match the number of components
        for(ESeniorStaff i : ESeniorStaff.values()) 
        { 
           //seniorStaff[i.ordinal()] = new SeniorStaff(i);
           seniorStaff[i.ordinal()] = staffFactory.buildStaff(i);
        }
        
        //money checkers
        this.officers = 6;
        this.previousMoneyTime = 0;
        
        //money checkers
    }
    
     /**========================================================================================================================== 
    * @name UPDATE
    * 
    * @description Runs the components update cycle 
    * 
    * @param tpf The main game time state
    * @param gameTime The main game time in total seconds
    *///=========================================================================================================================
    public void update(float tpf, float gameTime)
    {  
        // calls each senior staffs' update methods
        for(ESeniorStaff i : ESeniorStaff.values()) 
        { 
            seniorStaff[i.ordinal()].update(gameTime);
        }
        
        // calls each components' update methods
        for(EShipComponent m : EShipComponent.values()) 
        {
            shipComponents[m.ordinal()].update(gameTime);
        }
        
        
        
        //TODO: TEST
        //check officer happiness & update money per second
        calcMoneyPerSecond(2000000);
        
        //increases money by money per second.
        if ((int)gameTime > this.previousMoneyTime)
        {
            earnMoney(this.moneyPerSecond);
            this.previousMoneyTime = (int)gameTime;
            
            System.out.println(getCashFormat());
                    
        }
    }
    
    /**=========================================================================================================================
    * @name GAIN COMPONENT EXPERIENCE
    * 
    * @description  
    * 
    * @param component the component to gain experience
    *///=========================================================================================================================   
    public void gainComponentExperience(EShipComponent shipComponent)
    {
        shipComponents[shipComponent.ordinal()].gainExperience();
    }
    
    /**=========================================================================================================================
    * @name GAIN COMPONENT REPAIR
    * 
    * @description Instantly repair component 
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
     public void gainComponentRepair(EShipComponent shipComponent)
    {
        shipComponents[shipComponent.ordinal()].gainRepair();
    }
    
    /**=========================================================================================================================
    * @name PURCHASE COMPONENT LEVEL
    * 
    * @description Instantly level up the component if the user has enough money 
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public void purchaseComponentLevel(EShipComponent component)
    {
        double temp = shipComponents[component.ordinal()].getLevelCost();
        if (this.money > temp)
        {
        this.money = this.money - temp;
        shipComponents[component.ordinal()].levelUp();
        }
    }

    /**=========================================================================================================================
    * @name PURCHASE COMPONENT REPAIR
    * 
    * @description Instantly repairs component if the user has enough money 
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public void purchaseComponentRepair(EShipComponent component)
    {
        double temp = shipComponents[component.ordinal()].getRepairCost();
        if (this.money > temp)
        {
        this.money = this.money - temp;
        shipComponents[component.ordinal()].repairComponent();
        }
    }
    
    /**========================================================================================================================== 
    * @name PURCHASE COMPONENT EXPERIENCE
    * 
    * @description Allows user to purchase more experience for a component  
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public void purchaseComponentExperience(EShipComponent component)
    {
        
    }
    
    /**========================================================================================================================== 
    * @name PURCHASE SENIOR STAFF
    * 
    * @description Allows user to purchase staff officer for a component  
    * 
    * @param officer The senior staff enum that describes the desired senior officer
    * @param money the current clatinum the player has.
    *///=========================================================================================================================
    public void purchaseSeniorStaff(ESeniorStaff officer)
    {
        System.out.println(officer.ordinal());
        
        seniorStaff[officer.ordinal()].purchase(shipComponents[officer.ordinal()], this.money);
        this.money = this.money - seniorStaff[officer.ordinal()].getPurchaseCost();
        System.out.println(shipComponents[officer.ordinal()].NAME);
    }  

    /**========================================================================================================================== 
    * @name EARN MONEY
    * 
    * @description Allows user to generate ca$h money  
    * 
    * @param moneyPerSecond amount to increase by
    *///=========================================================================================================================
    private void earnMoney(double moneyPerSecond)
    {
        this.money += moneyPerSecond;
        
    }
    
    /**========================================================================================================================== 
    * @name CALCULATE MONEY PER SECOND
    * 
    * @description Calculates the amount of ca$h money to give the player  
    *///=========================================================================================================================
    private void calcMoneyPerSecond(int multiplier)
    {
        //need more statistics for this calculation.
       double change = this.officers*multiplier;
       this.moneyPerSecond = change;
    }
    
    /**========================================================================================================================== 
    * @name RESET SHIP
    * 
    * @description Allows user to reset the ship
    *///=========================================================================================================================
    public void resetShip()
    {
        this.money = 0.0;
        this.initializeComponents();
        System.out.println("Resetting the ship");
        
    }
    
    /**========================================================================================================================== 
    * @name INCREASE CLAIMABLE OFFICERS
    * 
    * @description Increases the amount of officers joined to the ship.
    * 
    * @param count The number of officers to increase by
    *///=========================================================================================================================
    public void increaseClaimableOfficers(int count)
    {
        this.claimableOfficers += count;
    }
    
    /**========================================================================================================================== 
    * @name CLAIM OFFICERS
    * 
    * @description Increases the amount of officers joined to the ship.
    *///=========================================================================================================================
    private void claimOfficers()
    {
        //check component levels
        this.officers += this.claimableOfficers;
    }
    
    /**========================================================================================================================== 
    * @name GET MONEY
    * 
    * @description reports cash amount in formatted string
    * 
    * @param officer the enumerated officer
    *///=========================================================================================================================
    public Double getCash()
    {
        return this.money;
        
    }
    
    public String getCashFormat()
    {
        return BigNumber.getNumberString(this.money);
    }
   
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // GETTERS/SETTERS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**=========================================================================================================================
    * @name GET INSTANT REPAIR COST
    * 
    * @description Instantly level up the component if the user has enough money 
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public double getInstantRepairCost(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getRepairCost();
    }
    
    /**=========================================================================================================================
    * @name GET INSTANT LEVEL COST
    * 
    * @description Instantly level up the component if the user has enough money 
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public double getInstantLevelCost(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getLevelCost();
    }
    
    /**=========================================================================================================================
    * @name GET SHIP COMPONENT COST
    * 
    * @description Returns the cost of the ship depending on its state
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public double getShipComponentCost(EShipComponent component)
    {
        ShipComponent shipComp = shipComponents[component.ordinal()];
        EShipComponentState shipCompState = shipComp.getComponentState();
        double cost = Double.MAX_VALUE;
        
        // user can buy repairs if it's broken or repairing
        if(shipCompState == EShipComponentState.BROKEN || shipCompState == EShipComponentState.REPAIRING)
        {
            cost = this.getInstantRepairCost(component);
        }
        // user can buy levels if it's gaining experience or inactive
        else if(shipCompState == EShipComponentState.GAINING_EXP || shipCompState == EShipComponentState.INACTIVE)
        {
            cost = this.getInstantLevelCost(component);
        }
        
        return cost;
    }
    
    /**=========================================================================================================================
    * @name GET SHIP COMPONENT COST STRING
    * 
    * @description Returns the formatted cost of the ship depending on its state
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public String getShipComponentCostStr(EShipComponent component)
    {
        return BigNumber.getNumberString( getShipComponentCost(component) );
    }
    
    /**=========================================================================================================================
    * @name GET SHIP COMPONENT LEVEL
    * 
    * @description Instantly level up the component if the user has enough money 
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public int getShipComponentLevel(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getLevel();
    }
    
    /**=========================================================================================================================
    * @name GET SHIP COMPONENT CURRENT PICTURE NAME
    * 
    * @description Returns the name of the picture based on its tier of the ship component
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public String getShipComponentCurrentPictureName(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getCurrentPictureName();
    }
   
    /**=========================================================================================================================
    * @name GET TIMER PERCENT
    * 
    * @description Returns a percentage of how much time is remaining if the component is gaining experience or repariing
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public double getTimerPercent(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getTimerPercent();
    }

    /**=========================================================================================================================
    * @name GET TIME LEFT
    * 
    * @description Returns a formatted string of how much time is remaining if the component is gaining experience or repariing
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public String getTimeLeft(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getTimeRemaining();
    }

    /**========================================================================================================================== 
    * @name GET COMPONENT
    * 
    * @description Returns a component based on enum value  
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public ShipComponent getComponent(EShipComponent component)
    {   
        return shipComponents[component.ordinal()];
    }
        
    /**========================================================================================================================== 
    * @name GET COMPONENT STATE
    * 
    * @description Returns the state that the desired ship component is in
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public EShipComponentState getComponentState(EShipComponent component)
    {   
        return shipComponents[component.ordinal()].getComponentState();
    }
    
    /**========================================================================================================================== 
    * @name CAN AFFORD
    * 
    * @description Returns whether or not the user can afford purchasing repairs or levels
    * 
    * @param component The ship component enum that describes the desired ship component
    *///=========================================================================================================================
    public boolean canAfford(EShipComponent component)
    {
        return getShipComponentCost(component) <= this.money;
    }
    
    /**========================================================================================================================== 
    * @name CAN AFFORD
    * 
    * @description Returns whether or not the user can afford purchasing senior staff
    * 
    * @param officer The senior staff enum that describes the desired senior staff
    *///=========================================================================================================================
    public boolean canAfford(ESeniorStaff officer)
    {
        return this.getSeniorStaffCost(officer) <= this.money;
    }
    
    /**========================================================================================================================== 
    * @name GET ALL COMPONENTS
    * 
    * @description Returns all ship components
    *///=========================================================================================================================
    public ShipComponent[] getAllComponents()
    {
        return shipComponents;
    }
     
    /**========================================================================================================================== 
    * @name GET ACTIVE COMPONENTS
    * 
    * @description Returns the components that are either being repaired or gaining experience
    *///=========================================================================================================================
    public ShipComponent[] getActiveComponents()
    {
        
        for(EShipComponent m : EShipComponent.values()) 
        {
            EShipComponentState compState = shipComponents[m.ordinal()].getComponentState();
            if(compState == EShipComponentState.GAINING_EXP || compState == EShipComponentState.REPAIRING)
            {
                 activeComponents[m.ordinal()] = shipComponents[m.ordinal()];
            }
            else
            {
                 activeComponents[m.ordinal()] = null;
            }
        }
        
        return activeComponents;
    }
    
    /**========================================================================================================================== 
    * @name GET BROKEN COMPONENTS
    * 
    * @description Returns the components that are broken and need to be repaired
    *///=========================================================================================================================
    public ShipComponent[] getBrokenComponents()
    {
     
        for(EShipComponent m : EShipComponent.values()) 
        { 
            EShipComponentState compState = shipComponents[m.ordinal()].getComponentState();
            if(compState == EShipComponentState.BROKEN)
            {   
                brokenComponents[m.ordinal()] = shipComponents[m.ordinal()];
            }
            else
            {
                brokenComponents[m.ordinal()] = null;
            }
        }
        
        return brokenComponents;
    }
    
    /**========================================================================================================================== 
    * @name GET INACTIVE COMPONENTS
    * 
    * @description Returns the components that aren't being repaired or gaining experience. (Note: Even automated components
    * need to be inactive for a brief period of time in order to properly reset and update information about them)
    *///=========================================================================================================================
    public ShipComponent[] getInactiveComponents()
    {
        for(EShipComponent m : EShipComponent.values()) 
        { 
            EShipComponentState compState = shipComponents[m.ordinal()].getComponentState();
            if(compState == EShipComponentState.INACTIVE)
            {   
                inactiveComponents[m.ordinal()] = shipComponents[m.ordinal()];
            }
            else
            {
                inactiveComponents[m.ordinal()] = null;
            }
        }
        
        return inactiveComponents;
    }
    
    /**========================================================================================================================== 
    * @name GET ALL STAFF
    * 
    * @description Get all of the ship's senior staff
    *///=========================================================================================================================
    public SeniorStaff[] getAllStaff()
    {
        return seniorStaff;
    }
    
    /**========================================================================================================================== 
    * @name GET SENIOR STAFF NAME
    * 
    * @description reports the NAME of the senior staff member
    * 
    * @param officer the enumerated officer
    *///=========================================================================================================================
    public String getSeniorStaffName(ESeniorStaff officer)
    {
        return seniorStaff[officer.ordinal()].getName();
    }
    /**========================================================================================================================== 
    * @name GET SENIOR STAFF ON PURCHASE
    * 
    * @description Reports the event On_Purchase for the officer;
    * 
    * @param officer the enumerated officer
    *///=========================================================================================================================
    public String getSeniorStaffOnPurchase(ESeniorStaff officer)
    {
        return seniorStaff[officer.ordinal()].getOnPurchase();
    }
    /**========================================================================================================================== 
    * @name GET SENIOR STAFF DESCRIPTION
    * 
    * @description Reports the description of the officer.
    * 
    * @param officer the enumerated officer
    *///=========================================================================================================================
    public String getSeniorStaffDescription(ESeniorStaff officer)
    {
        return seniorStaff[officer.ordinal()].getDescription();
    }
    
    /**========================================================================================================================== 
    * @name GET SENIOR STAFF COST STR
    * 
    * @description Reports the formatted cost of a staff member
    * 
    * @param officer the enumerated officer
    *///=========================================================================================================================
    public String getSeniorStaffCostStr(ESeniorStaff officer)
    {
        return BigNumber.getNumberString( seniorStaff[officer.ordinal()].getPurchaseCost() );
    }
    
    /**========================================================================================================================== 
    * @name GET SENIOR STAFF COST
    * 
    * @description Reports the numerical cost of a staff member
    * 
    * @param officer the enumerated officer
    *///=========================================================================================================================
    public double getSeniorStaffCost(ESeniorStaff officer)
    {
        return seniorStaff[officer.ordinal()].getPurchaseCost();
    }
    
    /**========================================================================================================================== 
    * @name IS SENIOR STAFF PURCHASED
    * 
    * @description Whether or not the senior staff is purchased
    * 
    * @param officer the enumerated officer
    *///=========================================================================================================================
    public boolean isSeniorStaffPurchased(ESeniorStaff officer)
    {
        return seniorStaff[officer.ordinal()].isPurchased();
    }
    
    /**========================================================================================================================== 
    * @name GET CURRENT MONEY STR
    * 
    * @description Returns formatted money value
    *///=========================================================================================================================
    public String getCurrentMoneyStr()
    {
       return BigNumber.getNumberString(this.money);
    }
    
    /**========================================================================================================================== 
    * @name GET SHIP CURRENT PICTURE NAME
    * 
    * @description Returns the name of the picture based on its tier
    *///=========================================================================================================================
    public String getShipCurrentPictureName()
    {
        return String.format(this.basePictureName, getShipCurrentTier());
    }
    
    /**========================================================================================================================== 
    * @name GET SHIP CURRENT TIER
    * 
    * @description Returns the smallest tier of all of the ship components (which is the ship's current tier)
    *///=========================================================================================================================
    private int getShipCurrentTier()
    {
        int minTier = 6; // only 5 tiers, so basically infinity

        for(ShipComponent shipComp : shipComponents) 
        {
            int shipTier = this.getCurrentTier(shipComp.getLevel());
            
            if(shipTier < minTier)
            {
                minTier = shipTier;
            }
        }
        
        return minTier;
    }
    
    /**========================================================================================================================== 
    * @name GET CURRENT TIER
    * 
    * @description Returns the current tier from the ship's tiers based on the given level
    * 
    * @param level The level of the ship component we want to check against
    *///=========================================================================================================================
    private int getCurrentTier(int level)
    {
        int currentTier = 1;
        
        for(int i = 0; i < allLevelTiers.length; i++)
        {
            if(level >= allLevelTiers[i])
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
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
