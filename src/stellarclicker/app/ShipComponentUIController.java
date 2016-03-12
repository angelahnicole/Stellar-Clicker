
package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file ShipComponentUIController.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description A UI controller for the ship component.
 *///========================================================================================================================

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
    
    private Nifty nifty;
    private Screen screen;
    private Element shipCompElem;
    private Attributes controlDefinitionAttributes;
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONTROLLER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    
    /**========================================================================================================================== 
    * @name BIND
    * 
    * @description Method that binds the controller with nifty, the screen, the element and its properties/attributes.
    * 
    * @param nifty The nifty instance
    * @param screen The screen instance
    * @param element The element that is controlled by this controller
    * @param parameter The element's parameters
    * @param controlDefinitionAttributes The element's attributes (defined in Nifty XML)
    *///=========================================================================================================================
    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes)
    {
        this.shipCompElem = element;
        this.controlDefinitionAttributes = controlDefinitionAttributes;
        System.out.println("bind() called for element: " + element);
    }
    
    /**========================================================================================================================== 
    * @name INIT
    * 
    * @description Method that initializes the element by passing its properties/attributes.
    * 
    * @param parameter The element's parameters
    * @param controlDefinitionAttributes The element's attributes (defined in Nifty XML)
    *///=========================================================================================================================
    public void init(Properties parameter, Attributes controlDefinitionAttributes)
    {
        System.out.println("init() called for element: " + shipCompElem);
    }

    /**========================================================================================================================== 
    * @name ON START SCREEN
    * 
    * @description Method that is called when the screen has initially started up
    *///=========================================================================================================================
    public void onStartScreen()
    {
        
    }

    /**========================================================================================================================== 
    * @name ON FOCUS
    * 
    * @description Method that is called after a focus change event
    * 
    * @param getFocus Whether or not it has focus
    *///=========================================================================================================================
    public void onFocus(boolean getFocus)
    {
        
    }

    /**========================================================================================================================== 
    * @name INPUT EVENT
    * 
    * @description Method that is called when input is passed from the element to the controller
    * 
    * @param inputEvent A nifty input event that contains information to pass from the UI to the controller
    *///=========================================================================================================================
    public boolean inputEvent(NiftyInputEvent inputEvent)
    {
        return false;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ELEMENT SPECIFIC METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    /**========================================================================================================================== 
    * @name GAIN EXP
    * 
    * @description Method called when the ship component's circle is clicked. Sets off a timer for the associated ship component
    * wait, gain experience, and level up.
    *///=========================================================================================================================
    public void gainExp()
    {
        String compEnum = controlDefinitionAttributes.get("compEnum");
        System.out.println("Gaining experience with " + compEnum);
        

    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // HELPER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    /**========================================================================================================================== 
    * @name STRING TO ENUM
    * 
    * @description Converts a string to an EShipComponent enumeration
    * 
    * @param enumString A string holding the name of the enumeration e.g. "HULL"
    *///=========================================================================================================================
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
