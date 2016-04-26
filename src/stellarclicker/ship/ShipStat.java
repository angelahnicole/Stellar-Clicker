/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stellarclicker.ship;

/////

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;

/*
 * ShipStat object class
 */
/////
public class ShipStat implements Savable
{

    private String name;
    private int stat = 0;

    ShipStat(String name, int stat)
    {
        this.name = name;
        this.stat = stat;
    }

    ShipStat()
    {

    }

    public void write(JmeExporter ex) throws IOException
    {
        OutputCapsule myCapsule = ex.getCapsule(this);
        myCapsule.write(this.name, "name", "");
        myCapsule.write(this.stat, "stat", 0);
    }

    public void read(JmeImporter im) throws IOException
    {
        InputCapsule myCapsule = im.getCapsule(this);
        this.name = myCapsule.readString("name", "");
        this.stat = myCapsule.readInt("stat", 0);
    }

    public int getStatValue()
    {
      return stat;
    }

    public String getStatName()
    {
      return name;
    }

    public void updateStat(int value)
    {
      this.stat += value;
    }
}
