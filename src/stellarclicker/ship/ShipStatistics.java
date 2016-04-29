package stellarclicker.ship;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file ShipStatistics.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description 
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

import java.util.Arrays;
import java.io.IOException;

import stellarclicker.util.EShipStat;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ShipStatistics implements Savable
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    protected ShipStat[] shipStats;
    
    private OutputCapsule outCapsule;
    private InputCapsule inCapsule;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    public ShipStatistics()
    {
        this.shipStats = new ShipStat[EShipStat.values().length];
        
        for(EShipStat x : EShipStat.values())
        {
            shipStats[x.ordinal()] = new ShipStat(x.name(),0);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // PERSISTENCE
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  WRITE
    * 
    * <br><br> Saves a saved ship from file
    * 
    * @param ex A jMonkeyEngine exporter
    *///=========================================================================================================================
    public void write(JmeExporter ex) throws IOException
    {
        outCapsule = ex.getCapsule(this);
        outCapsule.write(shipStats, "shipStats", null);
    }
    
    /**========================================================================================================================== 
    *  READ
    * 
    * <br><br> Loads a saved ship from file
    * 
    * @param im A jMonkeyEngine importer
    *///=========================================================================================================================
    public void read(JmeImporter im) throws IOException
    {
        inCapsule = im.getCapsule(this);
        
        Savable[] savedShipStats = inCapsule.readSavableArray("shipStats", shipStats);
        this.shipStats = Arrays.copyOf(savedShipStats, savedShipStats.length, ShipStat[].class);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    public void updateStat(EShipStat stat, int value)
    {
        if(this.shipStats[stat.ordinal()] != null)
        {
            this.shipStats[stat.ordinal()].updateStat(value);
        }
    }
    
    public void updateStat(int statOrdinal, int value)
    {
        if(statOrdinal < this.shipStats.length && this.shipStats[statOrdinal] != null)
        {
            this.shipStats[statOrdinal].updateStat(value);
        }
    }

    public int getStatValue(EShipStat stat)
    {
        if(this.shipStats[stat.ordinal()] != null)
        {
            return this.shipStats[stat.ordinal()].getStatValue();
        }
        else
        {
            return -1;
        }
    }
    
    public int getStatValue(int statOrdinal)
    {
        if(statOrdinal < this.shipStats.length && this.shipStats[statOrdinal] != null)
        {
            return this.shipStats[statOrdinal].getStatValue();
        }
        else
        {
            return -1;
        }
    }
    
    public String getStatName(EShipStat stat)
    {
        if(this.shipStats[stat.ordinal()] != null)
        {
            return this.shipStats[stat.ordinal()].getStatName();
        }
        else
        {
            return "";
        }
    }
     
     public String getStatName(int statOrdinal)
     {
         if(statOrdinal < this.shipStats.length && this.shipStats[statOrdinal] != null)
         {
            return this.shipStats[statOrdinal].getStatName();
         }
         else
         {
             return "";
         }
     }
      
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}