/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stellarclicker.util;

/**
 *
 * @author Alex
 */

import java.io.FileReader;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import stellarclicker.util.EShipComponent;
import stellarclicker.util.ESeniorStaff;


public class JsonReader {
    
    
    public JsonReader(){
       // createFile();
      //  System.out.println(readComponents());
    }
    
    public JSONObject readComponents(){
       
        String path = "components.cfg";
        JSONObject myComponents = readFile(path);
     
        return myComponents;
    }
    
    public JSONObject readStaff(){
        String path = "staff.cfg";
        JSONObject myStaff = readFile(path);
       
        return myStaff;
    }
    public JSONObject readFile(String path){
    
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
 
            Object obj = parser.parse(new FileReader(path));
 
            jsonObject = (JSONObject) obj;
            
            return jsonObject;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
}
