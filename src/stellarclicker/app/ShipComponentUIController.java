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
 * @description Controllers for the ship components  
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
import stellarclicker.util.EShipComponent;

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
     * This function is run on a focus change event
     * @param getFocus 
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
    
    
    private EShipComponent stringToEnum(String enumString)
    {
        EShipComponent convertedEnum = EShipComponent.HULL;
        
        switch(enumString)
        {
            case "HULL":
            {
                convertedEnum = EShipComponent.HULL;
                break;
            }
            case "SHIELDS":
            {
                convertedEnum = EShipComponent.SHIELDS;
                break;
            }
            case "ENGINES":
            {
                convertedEnum = EShipComponent.ENGINES;
                break;
            }
            case "WEAPONS":
            {
                convertedEnum = EShipComponent.WEAPONS;
                break;
            }
            case "LIFE_SUPPORT":
            {
                convertedEnum = EShipComponent.LIFE_SUPPORT;
                break;
            }
            case "REPLICATORS":
            {
                convertedEnum = EShipComponent.REPLICATORS;
                break;
            }
            case "HOLODECKS":
            {
                convertedEnum = EShipComponent.HOLODECKS;
                break;
            }
        }
        
        return convertedEnum;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
