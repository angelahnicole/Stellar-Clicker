
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
    
    // Constructor
    SeniorStaff(ESeniorStaff officerType)
    {
        this.shipStatComponentBoost = EShipStat.values()[officerType.ordinal()];
        System.out.println("Created Officer " + officerType + " Component Boost " + this.shipStatComponentBoost);
        
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
        if (this.managedComponent != null)
        {
            //manage ship component by checking to see if component is idle.
        if (this.managedComponent.currentState == EShipComponentState.INACTIVE)
        {
            this.managedComponent.gainExperience();
        }
        }
        
    }
     /**========================================================================================================================== 
    * @name purchase
    * 
    * @param component the shipComponent to manage
    * 
    * @description Purchases a component for management to operate 
    *///=========================================================================================================================
   
    public void purchase(ShipComponent component)
    {
        this.isPurchased = true;
        this.managedComponent = component;
        
    }
    
    /*
     * Private methods
     */
    private void repair()
    {
        System.out.println("repair stuff");
        //call component to repair stuff
        
    }
    
    private void gainExp()
    {
        System.out.println("X-P");
    }
    
}

