
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
import java.io.*;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import jme3tools.savegame.SaveGame;
import com.jme3.export.Savable;
import stellarclicker.ship.*;
import stellarclicker.util.Timer;
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //ALL OF THIS IS EXAMPLE JME CODE FOR THE SAVABLE INTERFACE  
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Persistence {

    //constructor
    public Persistence()
    {
        

    
    }
    
    public void saveGame(Object o, File file) throws IOException, ClassNotFoundException
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(file))) {
                // no need to specify members individually
                oos.writeObject(o);
                oos.close();
            }
    }
    
    public Object loadGame(File file) throws IOException, ClassNotFoundException
    {
        if (!file.exists())
            return null;
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        Object playerData = in.readObject();
        
        in.close();
        return playerData;
    }
    
    
}
