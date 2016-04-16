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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import stellarclicker.util.EShipComponent;
import stellarclicker.util.ESeniorStaff;


public class JsonReader {
    
 //  private JSONArray myArray = new JSONArray();
    
    public JsonReader(){
       // createFile();
      //  System.out.println(readComponents());
    }
    
    public JSONArray readComponents(){
        JSONArray myArray = new JSONArray();
        String path = "components.cfg";
        JSONObject myComponents = readFile(path);
        for(EShipComponent x : EShipComponent.values()){           
            myArray.add((JSONObject) myComponents.get(x.toString()));
        }
        return myArray;
    }
    
    public JSONArray readStaff(){
        String path = "staff.cfg";
        JSONArray myArray = new JSONArray();
        JSONObject myStaff = readFile(path);
        for(EShipComponent x : EShipComponent.values()){           
            myArray.add((JSONObject) myStaff.get(x.toString()));
        }
        return myArray;
    }
    public JSONObject readFile(String path){
    
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
 
            Object obj = parser.parse(new FileReader(path));
 
            jsonObject = (JSONObject) obj;
            
            
            JSONObject json0 = (JSONObject) jsonObject.get("HULL");
            
            System.out.println(json0);
            return jsonObject;
            
            
            
//            JSONObject json1 = (JSONObject) jsonObject.get("otherstuff");
//            myArray.add(json1);
//            System.out.println(myArray.get(1));
            
//            String name = (String) jsonObject.get("Name");
//            String author = (String) jsonObject.get("Author");
//            JSONArray companyList = (JSONArray) jsonObject.get("Company List");
// 
//            System.out.println("Name: " + name);
//            System.out.println("Author: " + author);
//            System.out.println("\nCompany List:");
//            Iterator<String> iterator = companyList.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }
//            String name = (String) jsonObject.get("name");
//		System.out.println(name);
//
//		long age = (Long) jsonObject.get("age");
//		System.out.println(age);
//
//		// loop array
//		JSONArray msg = (JSONArray) jsonObject.get("messages");
//		Iterator<String> iterator = msg.iterator();
//		while (iterator.hasNext()) {
//			System.out.println(iterator.next());
//		}
//
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
}
