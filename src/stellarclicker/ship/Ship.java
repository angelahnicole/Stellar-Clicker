
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
    private ShipComponent[] brokenComponents; 
    private ShipComponent[] activeComponents;  
    private ShipComponent[] inactiveComponents;
    private ShipComponent[] shipComponents;
    private SeniorStaff[] seniorStaff;
    private int[] shipStatistics;
    private double officers;
    private double money;
    private double moneyPerSecond;
    
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
        
        
        
        for (int i = 0; i < shipComponents.length; i++)
        {
            shipComponents[i].update(gameTime);
        }
        
        for (int i = 0; i < seniorStaff.length; i++)
        {
            seniorStaff[i].update(gameTime);
        }
        
       
    }
    
    public void gainComponentExperience(EShipComponent shipComponent)
    {
        String component = shipComponent.toString();
        
        
        shipComponents[shipComponent.ordinal()].initExperienceTimer();
        shipComponents[shipComponent.ordinal()].enable();
        
        
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
     inactiveComponents =  new ShipComponent[EShipComponent.values().length]; 
     activeComponents =  new ShipComponent[EShipComponent.values().length];
     brokenComponents =  new ShipComponent[EShipComponent.values().length];
   
     
     // This for each creates a new component and places it in the array at the index
       for(EShipComponent m : EShipComponent.values()) { 
        shipComponents[m.ordinal()] = new ShipComponent(m.name());
        
     }
       
//       //creates the senior staff which match the number of components
//       for(ESeniorStaff i : ESeniorStaff.values()) { 
//        seniorStaff[i.ordinal()] = new SeniorStaff();
//        
//     }

       for (int j = 0; j < seniorStaff.length; j++)
        {
            seniorStaff[j] = new SeniorStaff();
        }
    }

    /**========================================================================================================================== 
    * @name getComponent
    * 
    * @description Returns a component based on enum value  
    *///=========================================================================================================================
   
    public ShipComponent getComponent(EShipComponent value )
    {   
        return shipComponents[value.ordinal()];
    }
    
    
    /**========================================================================================================================== 
    * @name getComponent
    * 
    * @description Returns a component based on enum value  
    *///=========================================================================================================================
   
    public Enum getComponentState(EShipComponent value )
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
           if(shipComponents[m.ordinal()].isEnabled)
           {
             activeComponents[m.ordinal()] = shipComponents[m.ordinal()];
           }
           else
               activeComponents[m.ordinal()] = null;
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
        if(shipComponents[m.ordinal()].isBroken())
        {   
        brokenComponents[m.ordinal()] = shipComponents[m.ordinal()];
        }
        else
            brokenComponents[m.ordinal()] = null;
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
        if(!shipComponents[m.ordinal()].isEnabled)
        {   
        inactiveComponents[m.ordinal()] = shipComponents[m.ordinal()];
        }
        else
            inactiveComponents[m.ordinal()] = null;
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
