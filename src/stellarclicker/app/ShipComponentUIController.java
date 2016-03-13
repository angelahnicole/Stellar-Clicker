
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
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;
import stellarclicker.util.EShipComponent;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ShipComponentUIController implements Controller
{
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static final String GREEN_BAR_ID = "#greenBar";
    public static final String RED_BAR_ID = "#redBar";
    public static final String LEVEL_TEXT_ID = "#level";
    public static final String LEVEL_TEXT_ATTR = "compLevel";
    
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
    * @description Method called when the ship component's circle is clicked. Disables the component and will update the 
    * progress bar as progress is being made in gaining experience.
    *///=========================================================================================================================
    public void gainExp()
    {
        if(shipCompElem.isEnabled())
        {
            System.out.println("Gaining experience with " + shipCompElem.getId());

            // move the experience (green) progress bar
            updateProgressBar(1.0, GREEN_BAR_ID); 
            
            // disable the component when we're finished
            disableComponent();
        }
    }
    
    /**========================================================================================================================== 
    * @name UPDATE LEVEL
    * 
    * @description 
    * 
    * @param newLevel The level
    *///=========================================================================================================================
    public void updateLevel(int newLevel)
    {
        if(shipCompElem.isEnabled())
        {
            String levelText = String.valueOf(newLevel);
            
            // update compLevel attribute
            controlDefinitionAttributes.set(LEVEL_TEXT_ATTR, levelText);
            
            // update level text control
            Element levelTextElem = shipCompElem.findElementByName(LEVEL_TEXT_ID);
            if(levelTextElem != null)
            {
                levelTextElem.getRenderer(TextRenderer.class).setText(levelText);
            }
            
        }
    }
    
    /**========================================================================================================================== 
    * @name REPAIR
    * 
    * @description Method called when a component is broken and needs to be repaired. Disables the component and will update the
    * progress bar as progress is being made.
    *///=========================================================================================================================
    public void repair()
    {
        System.out.println("Reparing " + shipCompElem.getId());
        
        updateProgressBar(1.0, RED_BAR_ID);
    }
    
    /**========================================================================================================================== 
    * @name UPDATE PROGRESS BAR
    * 
    * @description Moves the progress bar given the desired percentage and its name
    * 
    * @param percentComplete The percentage (a number from 0 to 1) that the bar should update to
    * @param progressBarID The name (ID) of the element that contains the progress bar
    *///=========================================================================================================================
    public void updateProgressBar(double percentComplete, String progressBarID)
    {
        // convert to a whole number percentage
        percentComplete = percentComplete * 100;
        
        // convert to the proper x percentage needed for updating the progress bar
        double progressBarPercent = -100.0 + percentComplete;
        
        // update the progress bar
        Element progressBar = shipCompElem.findElementByName(progressBarID);
        if(progressBar != null)
        {
            progressBar.setConstraintX(new SizeValue(progressBarPercent + "%"));
        }
        
        // layout the elements
        shipCompElem.layoutElements();
        
    }
    /**========================================================================================================================== 
    * @name REENABLE COMPONENT
    * 
    * @description Re-enables the component by resetting the progress bar, making 
    *///=========================================================================================================================
    private void reenableComponent()
    {
        shipCompElem.enable();
        shipCompElem.findElementByName(GREEN_BAR_ID).setConstraintX(new SizeValue("-100%"));
        shipCompElem.findElementByName(RED_BAR_ID).setConstraintX(new SizeValue("-100%"));
    }
    
    /**========================================================================================================================== 
    * @name REPAIR
    * 
    * @description Method called when a component is broken and needs to be repaired. Disables the component and will update the
    * progress bar as progress is being made.
    *///=========================================================================================================================
    private void disableComponent()
    {
        shipCompElem.disable();
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
