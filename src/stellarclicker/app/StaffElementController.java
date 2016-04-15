
package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file StaffElementController.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description A UI controller for the senior staff element.
 * --------------------------------------------------------------------------------------------------------------------------
    JME LICENSE
    ******************************************************************************
    Copyright (c) 2003-2016 jMonkeyEngine
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are
    met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.

    * Neither the name of 'jMonkeyEngine' nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
    TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
    PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
    CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
    EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
    PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
    PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *///========================================================================================================================

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;
import de.lessvoid.nifty.tools.Color;
import stellarclicker.ship.ShipComponent;
import stellarclicker.util.ESeniorStaff;
import stellarclicker.util.EShipComponent;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class StaffElementController implements Controller
{
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static final String COLOR_RED_HEX = "#B8121270";
    public static final String COLOR_GREEN_HEX = "#18B81270";
    
    private Nifty nifty;
    private Screen screen;
    private Element staffElem;
    private Attributes controlDefinitionAttributes;
    private boolean appearsBroken;
    
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
        this.staffElem = element;
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
        System.out.println("init() called for element: " + staffElem);
        
        appearsBroken = false;
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
    * @name PURCHASE
    * 
    * @description 
    *///=========================================================================================================================
    public void purchase()
    {
        // purchase the senior staff
        ESeniorStaff staffEnum = stringToEnum(staffElem.getId());
        MainApplication.app.myShip.purchaseSeniorStaff(staffEnum);
        
        // disable the component
        disableComponent();
    }
    
    /**========================================================================================================================== 
    * @name DISABLE COMPONENT
    * 
    * @description Disables the component
    *///=========================================================================================================================
    public void disableComponent()
    {
        staffElem.disable();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // HELPER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    * @name STRING TO ENUM
    * 
    * @description Converts a string to an ESeniorStaff enumeration
    * 
    * @param enumString A string holding the name of the enumeration e.g. "BARKEEP"
    *///=========================================================================================================================
    private ESeniorStaff stringToEnum(String enumString)
    {
        ESeniorStaff convertedEnum = ESeniorStaff.BARKEEP;
        
        switch(enumString)
        {
            case "WARP_ENGINEER":
            {
                convertedEnum = ESeniorStaff.WARP_ENGINEER;
                break;
            }
            case "SHIELDS_ENGINEER":
            {
                convertedEnum = ESeniorStaff.SHIELDS_ENGINEER;
                break;
            }
            case "HOLODECK_PROGRAMMER":
            {
                convertedEnum = ESeniorStaff.HOLODECK_PROGRAMMER;
                break;
            }
            case "TACTICAL_OFFICER":
            {
                convertedEnum = ESeniorStaff.TACTICAL_OFFICER;
                break;
            }
            case "BARKEEP":
            {
                convertedEnum = ESeniorStaff.BARKEEP;
                break;
            }
            case "CHIEF_MEDICAL_OFFICER":
            {
                convertedEnum = ESeniorStaff.CHIEF_MEDICAL_OFFICER;
                break;
            }
            case "HULL_ENGINEER":
            {
                convertedEnum = ESeniorStaff.HULL_ENGINEER;
                break;
            }
        }
        
        return convertedEnum;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}