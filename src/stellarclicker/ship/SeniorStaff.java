
package stellarclicker.ship;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * @file SeniorStaff.java
 * 
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * 
 * @description 
 * 
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import stellarclicker.util.BigNumber;
import stellarclicker.util.EShipStat;
import stellarclicker.util.ESeniorStaff;
import stellarclicker.util.EShipComponentState;
public class SeniorStaff 
{
    
    protected ShipComponent managedComponent;
    protected EShipStat[] shipStatComponentBoostIdx;
    protected EShipStat shipStatComponentBoost;
    protected int[] shipStatBoost;
    protected double purchasedCost;
    protected boolean isPurchased;
    protected String name;
    // Constructor
    SeniorStaff(ESeniorStaff officerType)
    {
        //this.shipStatComponentBoost = EShipStat.values()[officerType.ordinal()];
        //officers default at 100 clatinum
        this.purchasedCost = 100;
        this.name = "Senior Staff Member";
        this.isPurchased = false;
    }
     /**========================================================================================================================== 
    * @name update
    * 
    * @param gameTime updates gametime for officer.
    * 
    * @description checks components for inactive state and manages them. 
    *///=========================================================================================================================
   
    public void update(float gameTime)
    {
       manageComponent();
    }
     /**========================================================================================================================== 
    * @name purchase
    * 
    * @param component the shipComponent to manage
    * @param money the current amount of clatinum the user has. 
    * 
    * @description Purchases a component for management to operate 
    *///=========================================================================================================================
   
    public String purchase(ShipComponent component, double money)
    {
        
        if (money > this.purchasedCost)
        {
            System.out.println("Purhcased!");
            this.isPurchased = true;
            this.managedComponent = component;
            return "Welcome to the crew!";
        }
        
        else
        {
            return "You do not have enough clatinum to purchase this staff member.";
        }
        
        
        
    }
    
    /**========================================================================================================================== 
    * @name manageComponent()
    * 
    
    * 
    * @description manages component timer based on current state
    *///=========================================================================================================================
   
    public void manageComponent()
    {
         if (this.managedComponent != null)
        {
            //manage ship component by checking to see if component is idle.
        if (this.managedComponent.currentState == EShipComponentState.INACTIVE)
        {
            this.managedComponent.gainExperience();
        }
        
        else if (this.managedComponent.currentState == EShipComponentState.BROKEN)
        {
            this.managedComponent.gainRepair();
            
        }
        }
         
         else if (this.isPurchased = true) {
             //System.out.println("Not working, fool");
         }
    }
    
    
    public double getPurchaseCost()
    {
        
        return this.purchasedCost;
    }
    
    public void setPurchaseCost(double cost)
    {
        this.purchasedCost = cost;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    
    
}

