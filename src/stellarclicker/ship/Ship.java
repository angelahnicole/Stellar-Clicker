
package stellarclicker.ship;

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

public class Ship {
    
    /*
     * Private variable for the ship
     */
    private ShipComponent[] shipComponenets;
    private SeniorStaff[] seniorStaff;
    private int[] shipStatistics;
    private BigNumber officers;
    private BigNumber money;
    private BigNumber moneyPerSecond;
    
    // Ship Constructor
    Ship()
    {
        System.out.println("You created a ship");
    }
    
    /*
     * Public Methods
     */
    
    //Update method
    public void update(float tpf)
    {
        System.out.println("Updating!");
    }
    
    public void purchaseComponentRepair()
    {
        System.out.println("Repair Comp");
    }
    
    public void purchaseComponenetExperience()
    {
        System.out.println("Experienceing Components");
    }
    
    public void purchaseSeniorStaff()
    {
        System.out.println("Purchasing people");
    }
    
    public void resetShip()
    {
        System.out.println("Resetting all the things");
    }
    
    /*
     * Private Methods
     */
    
    private void earnMoney()
    {
        System.out.println("Cash Money");
    }
    
    private void calcMoneyPerSecond()
    {
        System.out.println("Count that cash");
    }
    
    private void claimOfficers()
    {
        System.out.println("Gather the people");
    }
}
