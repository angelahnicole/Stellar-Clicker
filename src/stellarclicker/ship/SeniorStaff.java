
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

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;
import stellarclicker.util.BigNumber;
import stellarclicker.util.EShipStat;
import stellarclicker.util.ESeniorStaff;
import stellarclicker.util.EShipComponentState;
import stellarclicker.util.Timer;
import stellarclicker.util.*;

public class SeniorStaff implements Savable
{
    protected ShipComponent managedComponent;
    protected double purchasedCost;
    protected boolean isPurchased;
    protected String staffType;
    protected String name;
    protected String description;
    protected String onPurchase;
    
    private OutputCapsule outCapsule;
    private InputCapsule inCapsule;
    
    // Constructor
    public SeniorStaff(String officerType, String name, String description, double cost, String onPurchase)
    {
        this.staffType = officerType;
        this.name = name;
        this.description = description;
        this.purchasedCost = cost;
        this.onPurchase = onPurchase;

    }
    
    public SeniorStaff()
    {
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // PERSISTENCE METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    public void write(JmeExporter ex) throws IOException
    {
        outCapsule = ex.getCapsule(this);
        outCapsule.write(managedComponent, "managedComponent", null);
        outCapsule.write(purchasedCost, "purchasedCost", 0);
        outCapsule.write(isPurchased, "isPurchased", false);
        outCapsule.write(staffType, "staffType", "");
        outCapsule.write(name, "name", "");
        outCapsule.write(description, "description", "");
        outCapsule.write(onPurchase, "onPurchase", "");
        
    }

    public void read(JmeImporter im) throws IOException
    {
        inCapsule = im.getCapsule(this);
        this.managedComponent = (ShipComponent) inCapsule.readSavable("managedComponent", managedComponent);
        this.purchasedCost = inCapsule.readDouble("purchasedCost", purchasedCost);
        this.isPurchased = inCapsule.readBoolean("isPurchased", isPurchased);
        this.staffType = inCapsule.readString("staffType", staffType);
        this.name = inCapsule.readString("name", "");
        this.description = inCapsule.readString("description", "");
        this.onPurchase = inCapsule.readString("onPurchase", "");
    }
    
    public OutputCapsule getExporterCapsule()
    {
        return outCapsule;
    }
    
    public InputCapsule getImporterCapsule()
    {
        return inCapsule;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

     /**========================================================================================================================== 
    * @name update
    * 
    * @param gameTime updates gametime for officer.
    * 
    * @description checks components for inactive state and manages them. 
    *///=========================================================================================================================
   
    public void update(float gameTime)
    {
       if (this.isPurchased)
       {
       manageComponent(gameTime);
       }
    }
     /**========================================================================================================================== 
    * @name purchase
    * 
    * @param component the shipComponent to manage
    * @param money the current amount of clatinum the user has. 
    * 
    * @description Purchases a component for management to operate 
    *///=========================================================================================================================
   
    public String purchase(ShipComponent component, double money)
    {
        
        if (money > this.purchasedCost)
        {
            
            this.isPurchased = true;
            this.managedComponent = component;
            
            return "Welcome to the crew!";
        }
        
        else
        {
            return "You do not have enough clatinum to purchase this staff member.";
        }
        
        
        
    }
    
    /**========================================================================================================================== 
    * @name manageComponent()
    * 
    
    * 
    * @description manages component timer based on current state
    *///=========================================================================================================================
   
    public void manageComponent(float gameTime)
    {
        
        
         if (this.managedComponent != null)
        {
            //manage ship component by checking to see if component is idle.
        if (this.managedComponent.currentState == EShipComponentState.INACTIVE)
        {
            this.managedComponent.gainExperience();
            
        }
        
        else if (this.managedComponent.currentState == EShipComponentState.BROKEN)
        {
            this.managedComponent.gainRepair();
            
        }
        }
         
        
    }
    
    public boolean isPurchased()
    {
        return this.isPurchased;
    }
    
    
    public double getPurchaseCost()
    {
        
        return this.purchasedCost;
    }
    
    public void setPurchaseCost(double cost)
    {
        this.purchasedCost = cost;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getOnPurchase()
    {
        return this.onPurchase;
    }
    
    public void setOnPurchase(String onPurchase)
    {
        this.onPurchase = onPurchase;
    }
    
    
}

