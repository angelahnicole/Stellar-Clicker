
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
import stellarclicker.util.EShipStat;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import stellarclicker.util.ComponentFactory;
import stellarclicker.util.StaffFactory;

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
    private ShipStatistics[] shipStats;
    private SeniorStaff[] seniorStaff;
    private int[] shipStatistics;
    private double officers;
    private double money;
    private double moneyPerSecond;
    private ComponentFactory compFactory;
    private StaffFactory staffFactory;
    
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
        
        //testing remaining time method
     //   System.out.println(getTimeLeft(EShipComponent.HULL));
    }
    
    /**=========================================================================================================================
    * @name gainComponentExperience
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
    * @name gainComponentRepair
    * 
    * @description Instantly repair component 
    * 
    * @param component the component to repair
    *///=========================================================================================================================
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
        
        
        shipComponents[component.ordinal()].repairComponent();
    }
    
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
    * @name getTimerPercent
    * 
    * @description  
    * 
    * @param component the component to get from Enum type
    *///=========================================================================================================================
    public double getTimerPercent(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getTimerPercent();
    }

    /**=========================================================================================================================
    * @name getTimeLeft
    * 
    * @description  
    * 
    * @param component the component to get from
    *///=========================================================================================================================
    public String getTimeLeft(EShipComponent component)
    {
        return shipComponents[component.ordinal()].getTimeRemaining();
    }
    
    /**========================================================================================================================== 
    * @name initializeComponents
    * 
    * @description Initializes component array and creates new components from Enum  
    *///=========================================================================================================================
    public void initializeComponents()
    {
        this.money = 200000000;
        // Initializes the component array 
        shipStats = new ShipStatistics[EShipStat.values().length];
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

       int initialCost = 100;
       
       
        // This for each creates ship stats 
       for(EShipStat m : EShipStat.values()) 
       {
           shipStats[m.ordinal()] = new ShipStatistics();
       }
       
       //creates the senior staff which match the number of components
       for(ESeniorStaff i : ESeniorStaff.values()) 
       { 
           //seniorStaff[i.ordinal()] = new SeniorStaff(i);
           seniorStaff[i.ordinal()] = staffFactory.buildStaff(i);
           
           seniorStaff[i.ordinal()].setPurchaseCost(initialCost);
           
           initialCost = Math.round(initialCost*10);
           
           
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
        
    }
    
    /**========================================================================================================================== 
    * @name purchaseSeniorStaff
    * 
    * @description Allows user to purchase staff officer for a component  
    * 
    * 
    * @param component the Officer Enum type
    * @param money the current clatinum the player has.
    *///=========================================================================================================================
    public void purchaseSeniorStaff(ESeniorStaff officer)
    {System.out.println(officer.ordinal());
        seniorStaff[officer.ordinal()].purchase(shipComponents[officer.ordinal()], this.money);
         System.out.println(shipComponents[officer.ordinal()].name);
    }  
    
    /**========================================================================================================================== 
    * @name getSeniorStaffCost
    * 
    * @description reports the cost of a staff member
    * 
    * 
    * @param officer the enumerated officer
    *///=========================================================================================================================
    public String getSeniorStaffCost(ESeniorStaff officer)
    {
        double cost = seniorStaff[officer.ordinal()].getPurchaseCost();
        DecimalFormat moneyFormat = new DecimalFormat("$0.00");
        
        return moneyFormat.format(cost);
    }
    
    /**========================================================================================================================== 
    * @name getSeniorStaffName
    * 
    * @description reports the name of the senior staff member
    * 
    * 
    * @param officer the enumerated officer
    *///=========================================================================================================================
    public String getSeniorStaffName(ESeniorStaff officer)
    {
        String name = seniorStaff[officer.ordinal()].getName();
        
        
        return name;
    }
    /**========================================================================================================================== 
    * @name resetShip
    * 
    * @description Allows user to reset the ship
    *///=========================================================================================================================
    public void resetShip()
    {
        
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
        
    }
    
    /**========================================================================================================================== 
    * @name calcMoneyPerSecond
    * 
    * @description calculates the amount of ca$h money to give the player  
    *///=========================================================================================================================
    private void calcMoneyPerSecond()
    {
       
    }
    
    /**========================================================================================================================== 
    * @name claimOfficers
    * 
    * @description Increases the amount of officers joined to the ship.
    *///=========================================================================================================================
    private void claimOfficers()
    {
        
    }

    /**========================================================================================================================== 
    * @name getCurrentMoney
    * 
    * @description returns money value  
    *///=========================================================================================================================
    private String getCurrentMoney()
    {
       return BigNumber.getNumberString(money);
    }
}
