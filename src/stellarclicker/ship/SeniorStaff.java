
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


public class SeniorStaff 
{
    
    protected ShipComponent managedComponent;
    protected EShipStat[] shipStatComponentBoostIdx;
    protected EShipStat shipStatComponentBoost;
    protected int[] shipStatBoost;
    protected double purchasedCost;
    protected boolean isPurchased;
    
    // Constructor
    SeniorStaff()
    {
        System.out.println("construct things");
        
    }
    /*
     * Public methods
     */
    public void update(float gameTime)
    {
        
        //manage ship component by checking to see if component is idle.
        
    }
    
    public void purchase()
    {
        System.out.println("Buy me!");
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

