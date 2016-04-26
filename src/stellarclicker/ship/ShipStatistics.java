package stellarclicker.ship;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file ShipStatistics
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

public class ShipStatistics implements Savable
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private ShipStat[] shipStats;
    
    private OutputCapsule outCapsule;
    private InputCapsule inCapsule;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    ////////
    /*Constructor
     * 
     */
    ////////
    public ShipStatistics()
    {
        this.shipStats = new ShipStat[EShipStat.values().length];
        
        for(EShipStat x : EShipStat.values())
        {
            shipStats[x.ordinal()] = new ShipStat(x.name(),0);
            System.out.println(shipStats[x.ordinal()].getStatName());
        }        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // PERSISTENCE METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    public void write(JmeExporter ex) throws IOException
    {
        outCapsule = ex.getCapsule(this);
        outCapsule.write(shipStats, "shipStats", null);
    }
    
    public void read(JmeImporter im) throws IOException
    {
        inCapsule = im.getCapsule(this);
        
        Savable[] savedShipStats = inCapsule.readSavableArray("shipStats", shipStats);
        this.shipStats = Arrays.copyOf(savedShipStats, savedShipStats.length, ShipStat[].class);
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

    /////
    /*
     * ShipStat object class
     */
    /////
    public class ShipStat implements Savable
    {

        private String name;
        private int stat = 0;

        ShipStat(String name, int stat)
        {
            this.name = name;
            this.stat = stat;
        }
        
        ShipStat()
        {
            
        }

        public void write(JmeExporter ex) throws IOException
        {
            OutputCapsule myCapsule = ex.getCapsule(this);
            myCapsule.write(this.name, "name", "");
            myCapsule.write(this.stat, "stat", 0);
        }

        public void read(JmeImporter im) throws IOException
        {
            InputCapsule myCapsule = im.getCapsule(this);
            this.name = myCapsule.readString("name", "");
            this.stat = myCapsule.readInt("stat", 0);
        }

        public int getStatValue()
        {
          return stat;
        }

        public String getStatName()
        {
          return name;
        }

        public void updateStat(int value)
        {
          this.stat += value;
        }
  }
}

