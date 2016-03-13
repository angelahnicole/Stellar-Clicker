
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

public class Ship 
{
    
    /*
     * Private variables for the ship
     */
    private final int COMPONENT_NUM = 7; 
    private ShipComponent[] shipComponents;
    private SeniorStaff[] seniorStaff;
    private int[] shipStatistics;
    private double officers;
    private double money;
    private double moneyPerSecond;
    private EShipComponent componentEnum;
    
    // Ship Constructor
    public Ship()
    {
     shipComponents = new ShipComponent[COMPONENT_NUM];
     int i = 0;
       for(EShipComponent m : EShipComponent.values()) { 
        shipComponents[i] = new ShipComponent(m.name());
        i++;
     }

//     EShipComponent ret;
//     ret = EShipComponent.valueOf("HULL"); 
//     System.out.println("Selected : " + ret);                              
//   }
     
     
    }
    
    /*
     * Public Methods
     */
    
    //Update method
    public void update(float tpf)
    {
        
    }
    
    
    public void purchaseComponentRepair()
    {
        System.out.println("Repair Comp");
    }
    
    
    public void initializeComponents()
    {
        
    }
    
    public void purchaseComponentExperience(int Component)
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
