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
import stellarclicker.ship.SeniorStaff;
import stellarclicker.util.JSONReader;



public class StaffFactory {
    
    private JSONReader cfgReader = new JSONReader();
    private JSONObject jsonMembers;
    
    public StaffFactory(){
        jsonMembers = cfgReader.readStaff();
   //     System.out.println(jsonMembers);
        testBuild();
    }
    
    public SeniorStaff buildStaff(ESeniorStaff type){
        SeniorStaff newMember = null;
        JSONObject temp = ( JSONObject ) jsonMembers.get(type.toString());
      System.out.println(temp);
        newMember = new SeniorStaff(  (String) temp.get("name"));
        
        return newMember;
    }
    
    public void testBuild(){
        for(ESeniorStaff x : ESeniorStaff.values()){
            buildStaff(x);
        }
    }
}
