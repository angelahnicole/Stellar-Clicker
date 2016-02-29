
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


public class SeniorStaff {
    
    protected ShipComponent managedComponent;
    //protected ShipStatEnum[] shipStatComponentBoostIdx;
    protected int[] shipStatBoost;
    protected BigNumber purchasedCost;
    protected boolean isPurchased;
    
    // Constructor
    SeniorStaff()
    {
        System.out.println("construct things");
    }
    /*
     * Public methods
     */
    public void update()
    {
        System.out.println("Updat the things");
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
    }
    
    private void gainExp()
    {
        System.out.println("X-P");
    }
    
}

