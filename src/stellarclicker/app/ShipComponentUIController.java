/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * @file ShipComponentUIController.java
 * 
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * 
 * @description 
 * 
 */


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;
import stellarclicker.util.ShipComponentEnum;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ShipComponentUIController implements Controller
{
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private Element element;
    private Attributes controlDefinitionAttributes;
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 
     */
    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes)
    {
        this.element = element;
        this.controlDefinitionAttributes = controlDefinitionAttributes;
        System.out.println("bind() called for element: " + element);
    }
    
    /**
     * 
     */
    public void init(Properties parameter, Attributes controlDefinitionAttributes)
    {
        System.out.println("init() called for element: " + element);
    }

    /**
     * 
     */
    public void onStartScreen()
    {
        
    }

    /**
     * 
     */
    public void onFocus(boolean getFocus)
    {
        
    }

    /**
     * 
     */
    public boolean inputEvent(NiftyInputEvent inputEvent)
    {
        return false;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void gainExp()
    {
        String compEnum = controlDefinitionAttributes.get("compEnum");
        System.out.println("Gaining experience with " + compEnum);
        

    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    private ShipComponentEnum stringToEnum(String enumString)
    {
        ShipComponentEnum convertedEnum = ShipComponentEnum.HULL;
        
        switch(enumString)
        {
            case "HULL":
            {
                convertedEnum = ShipComponentEnum.HULL;
                break;
            }
            case "SHIELDS":
            {
                convertedEnum = ShipComponentEnum.SHIELDS;
                break;
            }
            case "ENGINES":
            {
                convertedEnum = ShipComponentEnum.ENGINES;
                break;
            }
            case "WEAPONS":
            {
                convertedEnum = ShipComponentEnum.WEAPONS;
                break;
            }
            case "LIFE_SUPPORT":
            {
                convertedEnum = ShipComponentEnum.LIFE_SUPPORT;
                break;
            }
            case "REPLICATORS":
            {
                convertedEnum = ShipComponentEnum.REPLICATORS;
                break;
            }
            case "HOLODECKS":
            {
                convertedEnum = ShipComponentEnum.HOLODECKS;
                break;
            }
        }
        
        return convertedEnum;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
