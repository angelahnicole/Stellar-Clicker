
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

public class Ship 
{
    
    /*
     * Private variables for the ship
     */
    private ShipComponent[] shipComponents;
    private SeniorStaff[] seniorStaff;
    private int[] shipStatistics;
    private double officers;
    private double money;
    private double moneyPerSecond;
    private EShipComponent componentEnum;
    
 /**========================================================================================================================== 
    * @name Ship Constructor
    * 
    * @description Creates and calls initailize functions for the components 
    * 
    *///=========================================================================================================================
       public Ship()
    {
        
     this.initializeComponents();
     
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
        
        
        //sync gameTime across game objects
        //update components
        for (int i = 0; i < shipComponents.length; i++)
        {
            shipComponents[i].update(gameTime);
        }
        //update staff
        for (int i = 0; i < seniorStaff.length; i++)
        {
            seniorStaff[i].update(gameTime);
        }
        //if staff available to manage component && 
        
       
    }
    
     /**========================================================================================================================== 
    * @name purchaseComponentRepair
    * 
    * @description Calls repair method on component  
    * 
    * @param component the component to repair Enum type
    *///=========================================================================================================================
   
    public void purchaseComponentRepair(EShipComponent component)
    {
        System.out.println("Repair Comp" + component.name());
        
    }
    
    /**========================================================================================================================== 
    * @name initializeComponents
    * 
    * @description Initializes component array and creates new components from Enum  
    * 
    *///=========================================================================================================================
     
    public void initializeComponents()
    {
        // Initializes the component array 
     shipComponents = new ShipComponent[EShipComponent.values().length];
     seniorStaff = new SeniorStaff[EShipComponent.values().length];
     int i = 0; //index for component 
     
     // This for each creates a new component and places it in the array at the index
       for(EShipComponent m : EShipComponent.values()) { 
        shipComponents[i] = new ShipComponent(m.name());
        i++;
     }
       
       //creates the senior staff which match the number of components
        for (int j = 0; j < seniorStaff.length; j++)
        {
            seniorStaff[j] = new SeniorStaff();
        }
    }
    
        /**========================================================================================================================== 
    * @name getActiveComponents
    * 
    * @description Allows user to purchase staff officer for a component  
    *///=========================================================================================================================
   
    public ShipComponent[] getActiveComponents()
    {
        ShipComponent[] activeComponents =  new ShipComponent[EShipComponent.values().length];
   
       for(EShipComponent m : EShipComponent.values()) 
       {
           if(shipComponents[m.ordinal()].isEnabled)
           {
             activeComponents[m.ordinal()] = shipComponents[m.ordinal()];
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
        ShipComponent[] brokenComponents =  new ShipComponent[EShipComponent.values().length];
   
       for(EShipComponent m : EShipComponent.values()) 
       { 
        if(shipComponents[m.ordinal()].isBroken())
        {   
        brokenComponents[m.ordinal()] = shipComponents[m.ordinal()];
        }
       }
        return brokenComponents;
    }
    
    /**========================================================================================================================== 
    * @name getInactiveComponents
    * 
    * @description Allows user to purchase staff officer for a component  
    *///=========================================================================================================================
   
    public ShipComponent[] getInactiveComponents(ESeniorStaff officer)
    {
        ShipComponent[] inactiveComponents =  new ShipComponent[EShipComponent.values().length];
   
       for(EShipComponent m : EShipComponent.values()) 
       { 
        if(!shipComponents[m.ordinal()].isEnabled)
        {   
        inactiveComponents[m.ordinal()] = shipComponents[m.ordinal()];
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
}
