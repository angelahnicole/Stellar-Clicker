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

import stellarclicker.util.EShipStat;

public class ShipStatistics {
    
    private ShipStat[] shipStats;
    ////////
    /*Constructor
     * 
     */
    ////////
    public ShipStatistics(){
        this.shipStats = new ShipStat[EShipStat.values().length];
        
        for(EShipStat x : EShipStat.values()){
            shipStats[x.ordinal()] = new ShipStat(x.name(),0);
            
        }
        
        
        
    }
    
    

    /////
    /*
     * ShipStat object class
     */
    /////
  private class ShipStat{
      private String name;
      private int stat = 0;
      ShipStat(String name, int stat){
          this.name = name;
          this.stat = stat;
      }
      
      public int getStatValue(){
          return stat;
      }
      public String getStatName(){
          return name;
      }
      public void updateStat(int value){
          this.stat += value;
      }
  }
  
  public void updateStat(EShipStat stat, int value)
  {
   this.shipStats[stat.ordinal()].updateStat(value);
  }
  
   public int getStatValue(EShipStat stat)
  {
   return this.shipStats[stat.ordinal()].getStatValue();
  }
    public String getStatName(EShipStat stat)
  {
   return this.shipStats[stat.ordinal()].getStatName();
  }
}

