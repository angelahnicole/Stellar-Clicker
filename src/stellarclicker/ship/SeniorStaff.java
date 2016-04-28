
package stellarclicker.ship;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file SeniorStaff.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * <br><br> 
 * --------------------------------------------------------------------------------------------------------------------------
    JME LICENSE
    ******************************************************************************
    Copyright (c) 2003-2016 jMonkeyEngine
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are
    met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.

    * Neither the NAME of 'jMonkeyEngine' nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
    TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
    PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
    CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *///========================================================================================================================

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;

import java.io.IOException;
import stellarclicker.app.MainApplication;

import stellarclicker.util.EShipComponentState;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class SeniorStaff implements Savable
{
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    protected ShipComponent managedComponent;
    protected double purchasedCost;
    protected boolean isPurchased;
    protected String staffType;
    protected String name;
    protected String description;
    protected String onPurchase;
    
    private OutputCapsule outCapsule;
    private InputCapsule inCapsule;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
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

    /**========================================================================================================================== 
    *  WRITE
    * 
    * <br><br> Saves a saved senior staff from file
    * 
    * @param ex A jMonkeyEngine exporter
    *///=========================================================================================================================
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

    /**========================================================================================================================== 
    *  READ
    * 
    * <br><br> Loads a saved senior staff from file
    * 
    * @param im A jMonkeyEngine importer
    *///=========================================================================================================================
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

     /**========================================================================================================================== 
    * UPDATE
    * 
    * <br><br> checks components for inactive state and manages them. 
    * 
    * @param gameTime updates gametime for officer.
    * 
    * 
    *///=========================================================================================================================
    public void update(float gameTime)
    {
        if (this.isPurchased)
        {
            manageComponent(gameTime);
        }
    }
     /**========================================================================================================================== 
    *  PURCHASE
    * 
    * @param component the shipComponent to manage
    * @param money the current amount of clatinum the user has. 
    * 
    * <br><br> Purchases a component for management to operate 
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
    *  MANAGE COMPONENT
    * 
    * <br><br> manages component timer based on current state
    * 
    * @param gameTime
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
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
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
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
}

