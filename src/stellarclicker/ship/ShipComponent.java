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
import stellarclicker.util.Timer;


import stellarclicker.util.EShipStat;


public class ShipComponent
{

    
    public int NUM_SHIP_STAT_COMPONENETS;
    
    protected int MAX_DURABILITY;
    protected int MAX_LEVEL;
    protected float BASE_RANK;
    protected double levelCost;
    protected double repairCost;
    protected int durability;
    protected int level;
    protected int currentExp;
    protected int nextLevelExp;
    protected boolean isEnabled;
    
    protected EShipStat[] shipStatCompUnlocksIdx;
    
    protected int expGain;

    
    //protected ShipStatEnum[] shipStatCompUnlocksIdx;

    protected int[][] shipStatUnlocks;
    
    protected long expTime;
    protected Timer expTimer;
    
    
   //Constructor 
    public ShipComponent()
    {
        System.out.println("Constructing component");
        
        this.expTimer = new Timer();
        
    }
    
    /*
     * Public Methods
     */
    public void update(double gameTime)
    {
        
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
