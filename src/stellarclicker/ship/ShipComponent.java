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
 * @description 
 * 
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import stellarclicker.util.BigNumber;
import java.util.concurrent.*;
public class ShipComponent implements Runnable{
    
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
    protected int expGain;
    protected long expTimer;
    //protected ShipStatEnum[] shipStatCompUnlocksIdx;
    protected int[][] shipStatUnlocks;
    
    //threading
    private ScheduledExecutorService executor;
    private ScheduledFuture<?> task;
   //Constructor 
    public ShipComponent()
    {
        System.out.println("Constructing component");
        
        initialize();
        
    }
    
    @Override //threading
    public void run()
    {
        
        
    }
    
    final Runnable startExpGain = new Runnable () {
        
        public void run() {
            System.out.println("Starting experience of component");
            gainExp();
        }
    };
    
    /* Load a component at game start and setup appropriate 
     * data for the component.
     * 
     * Load the type and then load what the user save data is.
     * 
     * 
     */
    public void initialize()
    {
        this.isEnabled = true; // TODO: create triggers to enable and disable 
        if (this.isEnabled)
        {
            //start thread 
            executor = Executors.newScheduledThreadPool(1);
            
            //start worker to gain experience. Initially, a 0 second delay to start with expTimer second period in between. 
            //This will change when we nail down specifics for balance, reasons.
            //also, note that this needs to be cleaned up upon disable of experience gain or closure of the game.
            executor.scheduleAtFixedRate(startExpGain, 0, expTimer, TimeUnit.SECONDS);
            
        }
        
        
    }
    
    public void closeThreads()
    {
        if (this.task != null)
        {
            this.task.cancel(true);
        }
    }
    
    //change the time it takes to gain 
    public void changeThreadInterval(long time)
    {
        if (time > 0)
        {
            if (this.task != null)
            {
                this.task.cancel(true);
            }
            this.task = executor.scheduleAtFixedRate(startExpGain, time, time, TimeUnit.DAYS);
        }
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
