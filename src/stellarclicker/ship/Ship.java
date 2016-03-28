
package stellarclicker.ship;

import stellarclicker.util.Timer;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * @file Ship.java
 * 
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * 
 * @description This is the ship class that defines the ship object
 * 
 */
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


import stellarclicker.util.BigNumber;
import stellarclicker.util.EShipComponent;
import stellarclicker.util.ESeniorStaff;
import stellarclicker.util.EShipComponentState;

public class Ship 
{
   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    private ShipComponent[] brokenComponents; 
    private ShipComponent[] activeComponents;  
    private ShipComponent[] inactiveComponents;
    private ShipComponent[] shipComponents;
    private SeniorStaff[] seniorStaff;
    private int[] shipStatistics;
    private double officers;
    private double money;
    private double moneyPerSecond;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------------------------------------------------------------------------
    public Ship()
    {
        
        this.initializeComponents();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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
        
        // calls each components' update methods
        for(EShipComponent m : EShipComponent.values()) 
        {
            shipComponents[m.ordinal()].update(gameTime);

        }

        // calls each senior staffs' update methods
        for(ESeniorStaff i : ESeniorStaff.values()) 
        { 
            seniorStaff[i.ordinal()].update(gameTime);
        }

    }
    
    public void gainComponentExperience(EShipComponent shipComponent)
    {
        shipComponents[shipComponent.ordinal()].gainExperience();
    }
    
    public void gainComponentRepair(EShipComponent shipComponent)
    {
        shipComponents[shipComponent.ordinal()].gainRepair();
    }
    
    /**=========================================================================================================================
    * @name purchaseComponentLevel
    * 
    * @description Instantly level up the component if the user has enough money 
    * 
    * @param component the component to level Enum type
    *///=========================================================================================================================
    public void purchaseComponentLevel(EShipComponent component)
    {
        System.out.println("Purchased level for Comp" + component.name());
        
        shipComponents[component.ordinal()].levelUp();
    }

        /**=========================================================================================================================
    * @name getShipComponentLevel
    * 
    * @description Instantly level up the component if the user has enough money 
    * 
    * @param component the component to level from
    *///=========================================================================================================================
    public int getShipComponentLevel(EShipComponent component)
    {
        System.out.println("level for Comp" + component.name());
        
        return shipComponents[component.ordinal()].getLevel();
    }

    /**=========================================================================================================================
    * @name purchaseComponentRepair
    * 
    * @description Instantly repairs component if the user has enough money 
    * 
    * @param component the component to repair Enum type
    *///=========================================================================================================================
    public void purchaseComponentRepair(EShipComponent component)
    {
        System.out.println("Purchased Repair for Comp" + component.name());
        
        shipComponents[component.ordinal()].repairComponent();
    }
    
//        /**=========================================================================================================================
//    * @name getShipComponentState
//    * 
//    * @description Instantly level up the component if the user has enough money 
//    * 
//    * @param component the component state
//    *///=========================================================================================================================
//    public EShipComponentState getComponentState(EShipComponent component)
//    {   
//        return shipComponents[component.ordinal()].getComponentState();
//    }
    /**=========================================================================================================================
    * @name getRepairCost
    * 
    * @description Instantly level up the component if the user has enough money 
    * 
    * @param component the component to level Enum type
    *///=========================================================================================================================
    public double getInstantRepairCost(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getRepairCost();
    }
 /**=========================================================================================================================
    * @name getLevelCost
    * 
    * @description Instantly level up the component if the user has enough money 
    * 
    * @param component the component to level Enum type
    *///=========================================================================================================================
    public double getInstantLevelCost(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getLevelCost();
    }
/**=========================================================================================================================
    * @name getLevelCost
    * 
    * @description Instantly level up the component if the user has enough money 
    * 
    * @param component the component to level Enum type
    *///=========================================================================================================================
    public double getTimerPercent(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getTimerPercent();
    }

//    /**=========================================================================================================================
//    * @name getTimeRemaining
//    * 
//    * @description Instantly level up the component if the user has enough money 
//    * 
//    * @param component the component to level Enum type
//    *///=========================================================================================================================
//    public double getTimeRemaining(EShipComponent component)
//    {
//        return shipComponents[component.ordinal()].getTimeRemaining();
//    }
    
    /**========================================================================================================================== 
    * @name initializeComponents
    * 
    * @description Initializes component array and creates new components from Enum  
    *///=========================================================================================================================
    public void initializeComponents()
    {
        // Initializes the component array 
        shipComponents = new ShipComponent[EShipComponent.values().length];
        seniorStaff = new SeniorStaff[EShipComponent.values().length];
        inactiveComponents =  new ShipComponent[EShipComponent.values().length]; 
        activeComponents =  new ShipComponent[EShipComponent.values().length];
        brokenComponents =  new ShipComponent[EShipComponent.values().length];
     
        // This for each creates a new component and places it in the array at the index
       for(EShipComponent m : EShipComponent.values()) 
       {
           shipComponents[m.ordinal()] = new ShipComponent(m.name());
       }

       //creates the senior staff which match the number of components
       for(ESeniorStaff i : ESeniorStaff.values()) 
       { 
           seniorStaff[i.ordinal()] = new SeniorStaff(i);
       }

    }

    /**========================================================================================================================== 
    * @name getComponent
    * 
    * @description Returns a component based on enum value  
    *///=========================================================================================================================
   
    public ShipComponent getComponent(EShipComponent value)
    {   
        return shipComponents[value.ordinal()];
    }
        
    /**========================================================================================================================== 
    * @name getComponent
    * 
    * @description Returns a component based on enum value  
    *///=========================================================================================================================
   
    public Enum getComponentState(EShipComponent value)
    {   
        return shipComponents[value.ordinal()].getComponentState();
    }
     
    
    /**========================================================================================================================== 
    * @name getActiveComponents
    * 
    * @description Allows user to purchase staff officer for a component  
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
    * @name getActiveComponents
    * 
    * @description Allows user to purchase staff officer for a component  
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
    * @name getInactiveComponents
    * 
    * @description 
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
    * @name purchaseComponentExperience
    * 
    * @description Allows user to purchase more experience for a component  
    * 
    * @param component the component to Upgrade Enum type
    *///=========================================================================================================================
    public void purchaseComponentExperience(EShipComponent component)
    {
        System.out.println("Experienceing Components " + component.name());
    }
    
    /**========================================================================================================================== 
    * @name purchaseSeniorStaff
    * 
    * @description Allows user to purchase staff officer for a component  
    * 
    * @param component the Officer Enum type
    *///=========================================================================================================================
    public void purchaseSeniorStaff(ESeniorStaff officer)
    {
        System.out.println("Purchasing people " + officer.name());
    }
    
    /**========================================================================================================================== 
    * @name resetShip
    * 
    * @description Allows user to reset the ship
    *///=========================================================================================================================
    public void resetShip()
    {
        System.out.println("Resetting all the things");
    }
    
    /**========================================================================================================================== 
    * @name earnMoney
    * 
    * @description Allows user to generate ca$h money  
    * 
    * @param moneyPerSecond amount to increase by
    *///=========================================================================================================================
    private void earnMoney(double moneyPerSecond)
    {
        this.money += moneyPerSecond;
        System.out.println("Ca$h Money: " + this.money);
    }
    
    /**========================================================================================================================== 
    * @name calcMoneyPerSecond
    * 
    * @description calculates the amount of ca$h money to give the player  
    *///=========================================================================================================================
    private void calcMoneyPerSecond()
    {
        System.out.println("Count that cash");
    }
    
    /**========================================================================================================================== 
    * @name claimOfficers
    * 
    * @description not exactly sure what this will be for yet?  
    *///=========================================================================================================================
    private void claimOfficers()
    {
        System.out.println("Gather the people");
    }

    private String getCurrentMoney()
    {
       return BigNumber.getNumberString(money);
    }
}
