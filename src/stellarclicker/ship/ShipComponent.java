/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stellarclicker.ship;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * @file ShipComponenet.java
 * 
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * 
 * @description This class defines a ship component for the ship 
 * 
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import stellarclicker.util.BigNumber;

public class ShipComponent {
    
    public int NUM_SHIP_STAT_COMPONENETS;
    
    protected int MAX_DURABILITY;
    protected int MAX_LEVEL;
    protected float BASE_RANK;
    protected BigNumber levelCost;
    protected BigNumber repairCost;
    protected int durability;
    protected int level;
    protected int currentExp;
    protected int nextLevelExp;
    protected boolean isEnabled;
    
    //protected ShipStatEnum[] shipStatCompUnlocksIdx;
    protected int[][] shipStatUnlocks;
    
   //Constructor 
    ShipComponent()
    {
        System.out.println("Constructing component");
    }
    
    /*
     * Public Methods
     */
    public void update()
    {
        System.out.println("Updating Component");
    }
    
    public void gainExp()
    {
        System.out.println("Gaining xp");
    }
    
    public void degradeComponent()
    {
        System.out.println("this is degrading");
    }
    
    public void repairComponent()
    {
        System.out.println("repair");
    }
    
    public void isBroken()
    {
        System.out.println("yep it is");
    }
    
    public void getShipStatistic()
    {
        System.out.println("get stuff");
    }
    
    public void enable()
    {
        System.out.println("set enabled");
    }
    /*
     * Private Methods
     */
    
    public void levelUp()
    {
        System.out.println("1 up");
    }
    
    
}
