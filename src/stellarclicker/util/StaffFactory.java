package stellarclicker.util;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file StaffFactory.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Builds senior staff components by reading in JSON
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

import org.json.simple.JSONObject;
import stellarclicker.ship.SeniorStaff;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class StaffFactory 
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    private JSONReader cfgReader = new JSONReader();
    private JSONObject jsonMembers;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------------------------------------------------------------------------
    public StaffFactory()
    {
        jsonMembers = cfgReader.readStaff();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**========================================================================================================================== 
    * @name BUILD STAFF
    * 
    * @description Builds a senior staff based on a file that is in JSON
    * 
    * @param type The senior staff type we would like to create
    * 
    * @return SeniorStaff The newly created senior staff
    *///=========================================================================================================================
    public SeniorStaff buildStaff(ESeniorStaff type)
    {
        SeniorStaff newMember = null;
        
        JSONObject temp = ( JSONObject ) jsonMembers.get(type.toString());
       
        
        newMember = new SeniorStaff(
                (String) temp.get("TITLE"), 
                (String)temp.get("NAME"), 
                (String)temp.get("DESCRIPTION"), 
                Double.parseDouble((String)temp.get("COST")),
                (String)temp.get("ON_PURCHASE")
                
        );
        
        
        
        
        
        
        
        return newMember;
    }
    
    /**========================================================================================================================== 
    * @name TEST BUILD
    * 
    * @description Builds every senior staff by reading in JSON
    *///=========================================================================================================================
    public void testBuild()
    {
        for(ESeniorStaff x : ESeniorStaff.values())
        {
            buildStaff(x);
        }
    }
}
