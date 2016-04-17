package stellarclicker.util;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file JSONReader.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Helps read JSON files
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

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetKey;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import stellarclicker.app.MainApplication;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class JSONReader 
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------------------------------------------------------------------------
    public JSONReader()
    {
       // createFile();
      //  System.out.println(readComponents());
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**========================================================================================================================== 
    * @name READ COMPONENTS
    * 
    * @description Reads in the components as JSON
    * 
    * @return JSONObject Object that has the read in components
    *///=========================================================================================================================
    public JSONObject readComponents()
    {
        String assetKey = "Configuration/components.json";
        JSONObject myComponents = getAsset(assetKey);
     
        return myComponents;
    }
    
    /**========================================================================================================================== 
    * @name READ STAFF
    * 
    * @description Reads in the staff members as JSON
    * 
    * @return JSONObject Object that has the read in staff members
    *///=========================================================================================================================
    public JSONObject readStaff()
    {
        String assetKey = "Configuration/staff.json";
        JSONObject myStaff = getAsset(assetKey);
       
        return myStaff;
    }
    
    /**========================================================================================================================== 
    * @name GET ASSET
    * 
    * @description Returns a the asset as a file object
    * 
    * @return JSONObject Object that has the read in staff members
    *///=========================================================================================================================
    private JSONObject getAsset(String assetKey)
    {
        return (JSONObject) MainApplication.app.getAssetManager().loadAsset(new AssetKey<>(assetKey));
    }
    
    /**========================================================================================================================== 
    * @name READ FILE
    * 
    * @description Reads in the given file and tries to parse it as JSON
    * 
    * @param path The path to the file
    * 
    * @return JSONObject Object that has the retrieved JSON
    *///=========================================================================================================================
    private JSONObject readFile(File myFile)
    {
    
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        
        try 
        {
            Object obj = parser.parse( new FileReader(myFile) );
 
            jsonObject = (JSONObject)obj;
            
            return jsonObject;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return jsonObject;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}
