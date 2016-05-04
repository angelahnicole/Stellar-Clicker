
package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file ShipComponentElementController.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description A UI controller for the ship component.
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

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;
import de.lessvoid.nifty.tools.Color;
import stellarclicker.ship.ShipComponent;
import stellarclicker.util.EShipComponent;
import stellarclicker.util.EShipComponentState;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ShipComponentElementController implements Controller
{
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static final String GREEN_BAR_ID = "#greenBar";
    public static final String RED_BAR_ID = "#redBar";
    public static final String LEVEL_TEXT_ID = "#levelText";
    public static final String LEVEL_TEXT_ATTR = "compLevel";
    public static final String SHIP_COMP_IMAGE_ID = "#compImage";
    public static final String BROKEN_IMAGE_ID = "#brokenImage";
    public static final String BUY_BUTTON_ID = "#buyButton";
    public static final String LEVEL_BUTTON_ID = "#levelButton";
    public static final String MAIN_TEXT_ID = "#mainText";
    public static final String HOVER_TEXT_ID = "#hoverText";
    public static final String MAIN_PANEL_ID = "#mainPanel";
    public static final String STAT_IMAGE_ID = "#statImage";
    public static final String STAT_NAME_ID = "#statName";
    public static final String STAT_VALUE_ID = "#statValue";
    public static final String TIME_LEFT_TEXT_ID = "#timeLeft";
    public static final String NAME_TEXT_ID = "#namePanel";
    
    public static final String HOVER_LEVEL_TEXT = "BUY LEVEL";
    public static final String HOVER_REPAIR_TEXT = "BUY REPAIR";
    
    public static final String COLOR_RED_HEX = "#B8121270";
    public static final String COLOR_GREEN_HEX = "#18B81270";
    
    private Nifty nifty;
    private Screen screen;
    private Element shipCompElem;
    private Attributes controlDefinitionAttributes;
    private boolean appearsBroken;
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CONTROLLER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    /**========================================================================================================================== 
    *  BIND
    * 
    * <br><br> Method that binds the controller with nifty, the screen, the element and its properties/attributes.
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
        
        this.nifty = nifty;
        this.screen = screen;
    }
    
    /**========================================================================================================================== 
    *  INIT
    * 
    * <br><br> Method that initializes the element by passing its properties/attributes.
    * 
    * @param parameter The element's parameters
    * @param controlDefinitionAttributes The element's attributes (defined in Nifty XML)
    *///=========================================================================================================================
    public void init(Properties parameter, Attributes controlDefinitionAttributes)
    {
        appearsBroken = false;
    }

    /**========================================================================================================================== 
    *  ON START SCREEN
    * 
    * <br><br> Method that is called when the screen has initially started up
    *///=========================================================================================================================
    public void onStartScreen()
    {
    }

    /**========================================================================================================================== 
    *  ON FOCUS
    * 
    * <br><br> Method that is called after a focus change event
    * 
    * @param getFocus Whether or not it has focus
    *///=========================================================================================================================
    public void onFocus(boolean getFocus)
    {
    }

    /**========================================================================================================================== 
    *  INPUT EVENT
    * 
    * <br><br> Method that is called when input is passed from the element to the controller
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
    *  INTERACT
    * 
    * <br><br> Method called when the ship component's circle is clicked. If the component is broken, then clicking the 
    * circle will repair it. If the component isn't broken, then clicking the circle will allow it to gain experience.
    *///=========================================================================================================================
    public void interact()
    {
        EShipComponent shipEnum = stringToEnum(shipCompElem.getId());
        ShipComponent shipComp = MainApplication.app.myShip.getComponent(shipEnum);
        EShipComponentState currentState = shipComp.getComponentState();
        
        if(currentState == EShipComponentState.INACTIVE || currentState == EShipComponentState.GAINING_EXP)
        {
            gainExp();
        }
        else
        {
            repair();
        }
    }
    
    /**========================================================================================================================== 
    *  PURCHASE
    * 
    * <br><br> Method called when the ship component's buy button is clicked. If the component is broken, then clicking this 
    * will purchase a repair. If the component isn't broken, then clicking it will purchase a level.
    *///=========================================================================================================================
    public void purchase()
    {
        EShipComponent shipEnum = stringToEnum(shipCompElem.getId());
        ShipComponent shipComp = MainApplication.app.myShip.getComponent(shipEnum);
        EShipComponentState currentState = shipComp.getComponentState();
        
        if(currentState == EShipComponentState.INACTIVE || currentState == EShipComponentState.GAINING_EXP)
        {
            purchaseLevel();
        }
        else
        {
            purchaseRepair();
        }
    }

    /**========================================================================================================================== 
    *  GAIN EXP
    * 
    * <br><br> Method called when the ship component's circle is clicked. Disables the component and will update the 
    * progress bar as progress is being made in gaining experience.
    *///=========================================================================================================================
    public void gainExp()
    {
        if(this.shipCompElem.isEnabled())
        {
            // start to gain experience and set off timing events
            EShipComponent shipEnum = stringToEnum(shipCompElem.getId());
            MainApplication.app.myShip.gainComponentExperience(shipEnum);

            disableLevelButton();
        }
    }
    
    /**========================================================================================================================== 
    *  PURCHASE LEVEL
    * 
    * <br><br> Method called when the "Buy" button is clicked. Instantly levels a component up
    *///=========================================================================================================================
    public void purchaseLevel()
    {
        if(this.shipCompElem.isEnabled())
        {
            EShipComponent shipEnum = stringToEnum(shipCompElem.getId());
            ShipComponent shipComp = MainApplication.app.myShip.getComponent(shipEnum);
            
            // purchase a level instantly
            MainApplication.app.myShip.purchaseComponentLevel(shipEnum);
            
            // update level and level cost
            int newLevel = MainApplication.app.myShip.getComponent(shipEnum).getLevel();
            String levelCost = shipComp.getFormattedLevelCost();
            updateLevel(newLevel);
            updateCost(levelCost);
        }
    }
    
    /**========================================================================================================================== 
    *  REPAIR
    * 
    * <br><br> Method called when a component is broken and needs to be repaired. Disables the component and will update the
    * progress bar as progress is being made.
    *///=========================================================================================================================
    public void repair()
    {
        // purchase experience and set off timing events
        EShipComponent shipEnum = stringToEnum(shipCompElem.getId());
        MainApplication.app.myShip.gainComponentRepair(shipEnum);
        
        disableLevelButton();
    }
    
     /**========================================================================================================================== 
    *  PURCHASE REPAIR
    * 
    * <br><br> Method called when a component is broken and needs to be repaired. Instantly repairs the component.
    *///=========================================================================================================================
    public void purchaseRepair()
    {
        // purchase experience and set off timing events
        EShipComponent shipEnum = stringToEnum(shipCompElem.getId());
        ShipComponent shipComp =  MainApplication.app.myShip.getComponent(shipEnum);
        MainApplication.app.myShip.purchaseComponentRepair(shipEnum);
        
        // make component appear fixed and update text
        fixComponent(shipComp.getFormattedLevelCost());
    }
    
    /**========================================================================================================================== 
    *  UPDATE LEVEL
    * 
    * <br><br> Updates the level text of the ship component
    * 
    * @param newLevel The new level of the ship component
    *///=========================================================================================================================
    public void updateLevel(int newLevel)
    {
        String levelText = String.valueOf(newLevel);

        // update level text control
        Element levelTextElem = shipCompElem.findElementByName(LEVEL_TEXT_ID);
        if(levelTextElem != null)
        {
            levelTextElem.getRenderer(TextRenderer.class).setText(levelText);
        }
        
        // updates the photo tier
        if(this.nifty != null && this.screen != null)
        {
            EShipComponent shipEnum = stringToEnum(shipCompElem.getId());
            String compPictureName = MainApplication.app.myShip.getShipComponentCurrentPictureName(shipEnum);
            NiftyImage newImage = this.nifty.getRenderEngine().createImage(this.screen, "Textures/ShipComponents/" + compPictureName, false);
            Element compImage = this.shipCompElem.findElementByName(SHIP_COMP_IMAGE_ID);
            compImage.getRenderer(ImageRenderer.class).setImage(newImage);
        }
    }
    
    /**========================================================================================================================== 
    *  UPDATE TIME LEFT
    * 
    * <br><br> Updates the time remaining when repairing/gaining experience
    * 
    * @param newTimeLeft A formatted string hh:mm:ss for the time remaining
    *///=========================================================================================================================
    public void updateTimeLeft(String newTimeLeft)
    {
        // update time left text control
        Element timeTextElem = shipCompElem.findElementByName(TIME_LEFT_TEXT_ID);
        if(timeTextElem != null)
        {
            timeTextElem.getRenderer(TextRenderer.class).setText(newTimeLeft);
        }
    }
    
    /**========================================================================================================================== 
    *  UPDATE COST
    * 
    * <br><br> Updates the new cost of the ship component
    * 
    * @param newCost The new cost of the ship component
    *///=========================================================================================================================
    public void updateCost(String newCost)
    {
        if(shipCompElem.isEnabled())
        {
            // update cost text control
            Element costTextElem = shipCompElem.findElementByName(MAIN_TEXT_ID);
            if(costTextElem != null)
            {
                costTextElem.getRenderer(TextRenderer.class).setText(newCost);
            }
        }
    }
    
    /**========================================================================================================================== 
    *  UPDATE PROGRESS BAR
    * 
    * <br><br> Moves the progress bar given the desired percentage and its name
    * 
    * @param percentComplete The percentage (a number from 0 to 1) that the bar should update to
    * @param progressBarID The name (ID) of the element that contains the progress bar
    *///=========================================================================================================================
    public void updateProgressBar(double percentComplete, String progressBarID)
    {
        // convert to a whole number percentage
        percentComplete = percentComplete * 100;
        
        // convert to the proper x percentage needed for updating the progress bar
        double progressBarPercent = -120.0 + percentComplete;
        
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
    *  BREAK COMPONENT
    * 
    * <br><br> Overlays a broken icon to indicate to the user that it is broken along with disallowing the user to buy any
    * levels.
    * 
    * @param repairCost Formatted cost to repair the component
    *///=========================================================================================================================
    public void breakComponent(String repairCost)
    {
        // start fix component event
        this.shipCompElem.findElementByName(LEVEL_BUTTON_ID).startEffect(EffectEventId.onCustom, new BreakComp(), "breakComp");
        
        // change color and text of button
        switchToRepairButton(repairCost);
        
        this.appearsBroken = true;
    }
    
    /**========================================================================================================================== 
    *  FIX COMPONENT
    * 
    * <br><br> Hides the broken icon and enables the buy button.
    * 
    * @param levelCost Formatted cost to level the component
    *///=========================================================================================================================
    public void fixComponent(String levelCost)
    {
        // start fix component event
        this.shipCompElem.findElementByName(LEVEL_BUTTON_ID).startEffect(EffectEventId.onCustom, new FixComp(), "fixComp");
        
        // change color and text of button
        switchToLevelButton(levelCost);
        
        this.appearsBroken = false;
    }
    
    /**========================================================================================================================== 
    *  IS ELEMENT ENABLED
    * 
    * @return boolean Whether or not the ship element is enabled
    *///=========================================================================================================================
    public boolean isElementEnabled()
    {
        return this.shipCompElem.isEnabled();
    }
    
    /**========================================================================================================================== 
    *  IS LEVEL BUTTON ENABLED
    * 
    * @return boolean Whether or not the level button element is enabled
    *///=========================================================================================================================
    public boolean isLevelButtonEnabled()
    {
        return this.shipCompElem.findElementByName(LEVEL_BUTTON_ID).isEnabled();
    }
    
    /**========================================================================================================================== 
    *  IS BUY BUTTON ENABLED
    * 
    * @return boolean Whether or not the buy button element is enabled
    *///=========================================================================================================================
    public boolean isBuyButtonEnabled()
    {
        return this.shipCompElem.findElementByName(BUY_BUTTON_ID).isEnabled();
    }
    
    /**========================================================================================================================== 
    *  REENABLE COMPONENT
    * 
    * <br><br> Re-enables the component by resetting the progress bar, making 
    *///=========================================================================================================================
    public void reenableComponent()
    {
        this.shipCompElem.enable();
        this.shipCompElem.findElementByName(LEVEL_BUTTON_ID).enable();
        
        // move back the bar
        this.shipCompElem.findElementByName(GREEN_BAR_ID).setConstraintX(new SizeValue("-120%"));
        this.shipCompElem.findElementByName(RED_BAR_ID).setConstraintX(new SizeValue("-120%"));
        
        // layout the elements
        shipCompElem.layoutElements();
    }
    
    /**========================================================================================================================== 
    *  DISABLE BUYING
    * 
    * <br><br> Disables the buy button for the ship component
    * 
    * @param currentCost Formatted cost
    *///=========================================================================================================================
    public void disableBuying(String currentCost)
    {
        // update cost
        Element mainText = this.shipCompElem.findElementByName(MAIN_TEXT_ID);
        if(mainText != null)
        {
            mainText.getRenderer(TextRenderer.class).setText(currentCost);
        }
        
        disableBuyButton();
    }
    
    /**========================================================================================================================== 
    *  ENABLE BUYING
    * 
    * <br><br> Enables the button for buying- has to make sure to have the correct color and labeling
    * 
    * @param currentCost Formatted cost
    *///=========================================================================================================================
    public void enableBuying(String currentCost)
    {
        // update cost
        Element mainText = this.shipCompElem.findElementByName(MAIN_TEXT_ID);
        if(mainText != null)
        {
            mainText.getRenderer(TextRenderer.class).setText(currentCost);
        }
        
        enableBuyButton();
    }
    
    /**========================================================================================================================== 
    *  ENABLE BUYING
    * 
    * <br><br> Enables the button for buying- has to make sure to have the correct color and labeling
    * 
    * @param currentCost Formatted cost
    *///=========================================================================================================================
    public void updateState(EShipComponentState currentState, String currentCost)
    {
        if(currentState == EShipComponentState.INACTIVE || currentState == EShipComponentState.GAINING_EXP)
        {
            fixComponent(currentCost);
        }
        else
        {
            breakComponent(currentCost);
        }
    }
    
    /**========================================================================================================================== 
    *  SWTICH TO REPAIR BUTTON
    * 
    * <br><br> Changes the buy button to have repair text and repair color
    * 
    * @param repairCost Formatted cost of repairing the component
    *///=========================================================================================================================
    private void switchToRepairButton(String repairCost)
    {
        // change buy button to show repair info
        Element mainText = this.shipCompElem.findElementByName(MAIN_TEXT_ID);
        Element hoverText = this.shipCompElem.findElementByName(HOVER_TEXT_ID);
        if(mainText != null && hoverText != null)
        {
            mainText.getRenderer(TextRenderer.class).setText(repairCost);
            hoverText.getRenderer(TextRenderer.class).setText(HOVER_REPAIR_TEXT);
        }

        // change buy button to have a red overlay
        Element mainPanel = this.shipCompElem.findElementByName(MAIN_PANEL_ID);
        if(mainPanel != null)
        {
            mainPanel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(COLOR_RED_HEX));
        }
    }
    
    /**========================================================================================================================== 
    *  SWTICH TO LEVEL BUTTON
    * 
    * <br><br> Changes the buy button to have level text and level color
    * 
    * @param repairCost Formatted cost of leveling the component
    *///=========================================================================================================================
    private void switchToLevelButton(String levelCost)
    {
        // change buy button to show level info
        Element mainText = this.shipCompElem.findElementByName(MAIN_TEXT_ID);
        Element hoverText = this.shipCompElem.findElementByName(HOVER_TEXT_ID);
        if(mainText != null && hoverText != null)
        {
            mainText.getRenderer(TextRenderer.class).setText(levelCost);
            hoverText.getRenderer(TextRenderer.class).setText(HOVER_LEVEL_TEXT);
        }

        // change buy button to have a green overlay
        Element mainPanel = this.shipCompElem.findElementByName(MAIN_PANEL_ID);
        if(mainPanel != null)
        {
            mainPanel.getRenderer(PanelRenderer.class).setBackgroundColor(new Color(COLOR_GREEN_HEX));
        }
    }
    
    /**========================================================================================================================== 
    *  DISABLE LEVEL BUTTON
    * 
    * <br><br> Disables the level button of the component
    *///=========================================================================================================================
    public void disableLevelButton()
    {
        this.shipCompElem.findElementByName(LEVEL_BUTTON_ID).disable();
    }
    
    /**========================================================================================================================== 
    *  DISABLE BUY BUTTON
    * 
    * <br><br> Disables the buy button of the component
    *///=========================================================================================================================
    public void disableBuyButton()
    {
        this.shipCompElem.findElementByName(BUY_BUTTON_ID).disable();
    }
    
    /**========================================================================================================================== 
    *  ENABLE BUY BUTTON
    * 
    * <br><br> Enables the buy button of the component
    *///=========================================================================================================================
    public void enableBuyButton()
    {
        this.shipCompElem.findElementByName(BUY_BUTTON_ID).enable();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // HELPER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    public boolean appearsBroken()
    {
        return this.appearsBroken;
    }

    /**========================================================================================================================== 
    *  STRING TO ENUM
    * 
    * <br><br> Converts a string to an EShipComponent enumeration
    * 
    * @param enumString A string holding the name of the enumeration e.g. "HULL"
    * 
    * @return EShipComponent The converted EShipComponent enum
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
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // CUSTOM EVENTS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
     class FixComp implements EndNotify 
     {

        @Override
        public void perform()
        {
            System.out.println("FixComp has ended.");
        }
         
     }
     
     class BreakComp implements EndNotify 
     {

        @Override
        public void perform()
        {
            System.out.println("BreakComp has ended.");
        }
         
     }
     
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
