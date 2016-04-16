/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stellarclicker.util;

/**
 *
 * @author Alex
 */
import org.json.simple.JSONObject;
import stellarclicker.ship.ShipComponent;
import stellarclicker.util.JsonReader;



public class ComponentFactory {
    
    private JsonReader cfgReader = new JsonReader();
    private JSONObject jsonComponents;
    
    public ComponentFactory(){
        jsonComponents = cfgReader.readComponents();
        testBuild();
    }
    
    public ShipComponent buildComponent(Enum type){
        ShipComponent newComponent = null;
        JSONObject temp = ( JSONObject ) jsonComponents.get(type.toString());
      
        newComponent = new ShipComponent(  (String) temp.get("name"), Integer.parseInt((String) temp.get("BASE_TIME")), Integer.parseInt((String) temp.get("MAX_DUR")), Integer.parseInt((String) temp.get("MIN_LEVEL")), Integer.parseInt((String) temp.get("MAX_LEVEL")), Integer.parseInt((String) temp.get("NUM_STATS")), Float.parseFloat((String) temp.get("LEVEL_COST")));
        
        return newComponent;
    }
    
    public void testBuild(){
        for(EShipComponent x : EShipComponent.values()){
            buildComponent(x);
        }
    }
}
