package stellarclicker.util;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file ComponentFactory.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Builds ship components by reading in JSON
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

    * Neither the name of 'jMonkeyEngine' nor the names of its contributors
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

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import stellarclicker.ship.ShipComponent;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ComponentFactory 
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    private JSONReader cfgReader = new JSONReader();
    private JSONObject jsonComponents;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------------------------------------------------------------------------
    public ComponentFactory()
    {
        jsonComponents = cfgReader.readComponents();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**========================================================================================================================== 
    * @name BUILD COMPONENT
    * 
    * @description Builds a component based on a file that is in JSON
    * 
    * @param type The ship component we would like to create
    * 
    * @return ShipComponent The newly created ship component
    *///=========================================================================================================================
    public ShipComponent buildComponent(EShipComponent type)
    {
        ShipComponent newComponent = null;
        
        // create JSON object 
        JSONObject temp = (JSONObject) jsonComponents.get(type.toString());
        
        // create JSON array object to get the tiers
        JSONArray tierArray = (JSONArray) temp.get("LEVEL_TIERS");
        int[] levelTiers = 
        { 
            Integer.parseInt( (String)tierArray.get(0) ), 
            Integer.parseInt( (String)tierArray.get(1) ), 
            Integer.parseInt( (String)tierArray.get(2) ), 
            Integer.parseInt( (String)tierArray.get(3) ), 
            Integer.parseInt( (String)tierArray.get(4) )
        };
        
        // create array from JSON array
        int numStats = Integer.parseInt( (String)temp.get("NUM_STATS") );
        JSONArray statArray = (JSONArray) temp.get("AFFECTED_STATS");
        int[] affectedStats = new int[numStats];
        for(int i = 0; i < statArray.size(); i++)
        {
            affectedStats[i] = EShipStat.valueOf( (String)statArray.get(i) ).ordinal();
        }
        
        newComponent = new ShipComponent
        (  
            type.toString(), 
            Integer.parseInt( (String)temp.get("BASE_TIME") ), 
            Integer.parseInt( (String)temp.get("MAX_DUR") ), 
            Integer.parseInt( (String)temp.get("MIN_LEVEL") ), 
            Integer.parseInt( (String)temp.get("MAX_LEVEL") ), 
            numStats, 
            affectedStats,
            Float.parseFloat( (String)temp.get("LEVEL_COST") ),
            levelTiers,
            (String) temp.get("BASE_PICTURE_NAME")
        );
        
        return newComponent;
    }
    
    /**========================================================================================================================== 
    * @name TEST BUILD
    * 
    * @description Builds every component by reading in JSON
    *///=========================================================================================================================
    public void testBuild()
    {
        for(EShipComponent x : EShipComponent.values())
        {
            buildComponent(x);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
