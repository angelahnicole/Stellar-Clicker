package stellarclicker.util;

/**========================================================================================================================== 
 * @file JSONLoader.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author https://github.com/MultiverseKing/MultiverseKing_JME
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Helps load JSON files through the jMonkeyEngine asset manager
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
import com.jme3.asset.AssetLoader; 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
/**
 * @todo Use for game configuration 
 * @author roah 
 */ 
public class JSONLoader implements AssetLoader 
{ 
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
 
    private final static JSONParser parser = new JSONParser(); 
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
    /**========================================================================================================================== 
     * @name READ LOAD
     * 
     * @description Load the file using the binary importer. 
     * 
     * @param assetInfo 
     * 
     * @return JSONObject
     * 
     * @throws IOException 
     *///=========================================================================================================================
    @Override 
    public Object load(AssetInfo assetInfo) throws IOException 
    { 
        InputStream is = assetInfo.openStream(); 
        JSONObject data = null; 
        BufferedReader bufferedReader = null; 
        StringBuilder stringBuilder = new StringBuilder();
        
        try 
        { 
            bufferedReader = new BufferedReader(new InputStreamReader(is)); 
            String s; 
            while ((s = bufferedReader.readLine()) != null) 
            { 
                    stringBuilder.append(s); 
            } 
            data = (JSONObject) parser.parse(stringBuilder.toString()); 
        } 
        catch (ParseException ex) 
        { 
            Logger.getLogger(JSONLoader.class.getName()).log(Level.SEVERE, null, ex); 
        } 
        finally 
        { 
            if (bufferedReader != null) 
            { 
                try 
                { 
                    bufferedReader.close(); 
                } catch (IOException ex) { 
                    Logger.getLogger(JSONLoader.class.getName()).log(Level.SEVERE, null, ex); 
                } 
            } 
            is.close(); 
        } 
 
        if (data != null) 
        { 
            return data; 
        } 
        else 
        { 
            Logger.getGlobal().log(Level.WARNING, "{0} : Data couldn't be loaded.", new Object[]{getClass().getName()}); 
            return null; 
        } 
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
