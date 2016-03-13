
package stellarclicker.util;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file Persistence.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Handles loading and saving objects for the game
 *///========================================================================================================================

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jme3tools.savegame.SaveGame;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //ALL OF THIS IS EXAMPLE JME CODE FOR THE SAVABLE INTERFACE  
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Persistence extends SimpleApplication {

    public static void main(String[] args) {

//        TestSaveGame app = new TestSaveGame();
//        app.start();
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    public void simpleInitApp() {

        //node that is used to store player data
        Node myShip = new Node();
        myShip.setName("PlayerNode");
        myShip.setUserData("name", "Mario");
        myShip.setUserData("health", 100.0f);
        myShip.setUserData("points", 0);

        //the actual model would be attached to this node
        Spatial model = (Spatial) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        myShip.attachChild(model);

        //before saving the game, the model should be detached so its not saved along with the node
        myShip.detachAllChildren();
        SaveGame.saveGame("mycompany/mygame", "savegame_001", myShip);

        //later the game is loaded again
        Node player = (Node) SaveGame.loadGame("mycompany/mygame", "savegame_001");
        player.attachChild(model);
        rootNode.attachChild(player);

        //and the data is available
        System.out.println("Name: " + player.getUserData("name"));
        System.out.println("Health: " + player.getUserData("health"));
        System.out.println("Points: " + player.getUserData("points"));

        AmbientLight al = new AmbientLight();
        rootNode.addLight(al);
        
        //note you can also implement your own classes that implement the Savable interface.
    }
}
