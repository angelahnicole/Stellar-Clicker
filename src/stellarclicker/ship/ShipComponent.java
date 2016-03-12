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
    
    //rate at which a component upgrades per experience gain
    protected int expGain;

    
    //protected ShipStatEnum[] shipStatCompUnlocksIdx;

    protected int[][] shipStatUnlocks;
    
    protected long expTime;
    protected long repairTime;
    protected Timer expTimer;
    protected Timer repairTimer;
    
    //whether the component is managed by an officer.
    protected boolean managed;
   //Constructor 
    public ShipComponent()
    {
        System.out.println("Constructing component");
        
        this.expTimer = new Timer();
        this.repairTimer = new Timer();
        
    }
    
    /*
     * Public Methods
     */
    public void update(double gameTime)
    {
        
        //check for completion of timers
        if (this.expTimer.checkCompletion(gameTime))
        {
            //start a new expTimer
            this.expTimer.cancelTimer();
            this.expTimer.set(gameTime, this.expTime);
        
        }
        if (this.repairTimer.checkCompletion(gameTime))
        {
            
        }
        
        
        //check if leveled up
        
        if (this.currentExp > this.nextLevelExp)
        {
            levelUp();
            
        }
    }
    
    
    public double experienceTimerPercent(double gameTime)
    {
        return this.expTimer.getPercentComplete(gameTime);
        
    }
    
    public double repairTimerPercent(double gameTime)
    {
        
        
        return this.repairTimer.getPercentComplete(gameTime);
    }
    
    public void degradeComponent()
    {
        System.out.println("this is degrading");
    }
    
    public void repairComponent()
    {
        System.out.println("repair");
        this.durability = 100;
        
    }
    
    public void damageComponent(int amount)
    {
        this.durability = this.durability - amount;
        //check if broken
        
        if (this.durability < 0)
        {
            breakComponent();
        }
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
        
        this.isEnabled = true;
    }
    
    
    public void gainExperience()
    {
        this.currentExp += this.expGain;
        
        
    }
    
     public int getLevel()
    {
        return this.level;
    }
     
     /*
     * Private Methods
     */
    private void levelUp()
    {
        this.level += 1;
        this.currentExp = this.currentExp - this.nextLevelExp;
        
    }
    
   private void breakComponent()
    {
        this.isEnabled = false;
        this.durability = 0;
    }
}
