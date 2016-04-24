
package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file MainGameScreenState.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description It's an application state that handles the interaction between the user, UI, and the ship.
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

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import stellarclicker.ship.SeniorStaff;

import stellarclicker.ship.ShipComponent;
import stellarclicker.util.ESeniorStaff;
import stellarclicker.util.EShipComponent;
import stellarclicker.util.EShipComponentState;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainGameScreenState extends AbstractAppState implements ScreenController
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static final String OFFICER_LAYER_ID = "officerUI";
    public static final String OFFICER_WINDOW_ID = "officerWindow";
    public static final String SENIOR_STAFF_LAYER_ID = "seniorStaff";
    public static final String SENIOR_STAFF_WINDOW_ID = "seniorStaffWindow";
    public static final String UNLOCKS_LAYER_ID = "unlocksUI";
    public static final String UNLOCKS_WINDOW_ID = "unlocksWindow";
    public static final String TRAVEL_LAYER_ID = "travelUI";
    public static final String TRAVEL_WINDOW_ID = "travelWindow";
    public static final String SHIP_IMAGE_ID = "mainShipImage";
    public static final String AUDIO_IMAGE_ID = "audioButton#iconPanel#iconImage";
    public static final String MONEY_COMP_ID = "money";
    public static final String MONEY_TEXT_ID = "#compMoney";
    
    private Nifty nifty;
    private Screen screen;
    private Application app;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private ViewPort guiViewPort;
    private AudioRenderer audioRenderer;
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // APP STATE METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    /**========================================================================================================================== 
    * @name INITIALIZE
    * 
    * @description Initializes the application state 
    * 
    * @param stateManager An instance of the main application's state manager
    * @param app The main application
    *///=========================================================================================================================
    @Override
    public void initialize(AppStateManager stateManager, Application app)
    {
        super.initialize(stateManager, app);
        this.app = app;
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.guiViewPort = this.app.getGuiViewPort();
        this.audioRenderer = this.app.getAudioRenderer();
        
        MainApplication.app.getNifty().fromXml("Interface/XML/MainGameScreen.xml", "mainGame", this);
    }

    /**========================================================================================================================== 
    * @name CLEAN UP
    * 
    * @description Cleans up the application state
    *///=========================================================================================================================
    @Override
    public void cleanup()
    {
        super.cleanup();
    }

    /**========================================================================================================================== 
    * @name SET ENABLED
    * 
    * @description Make the application state enabled or disabled (i.e. pause and unpause)
    * 
    * @param enabled Whether or not to enabled or disable the application state
    *///=========================================================================================================================
    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
    }
    
    /**========================================================================================================================== 
    * @name UPDATE
    * 
    * @description Method called by the main thread (loop) that updates the state of the game.
    * 
    * @param tpf The amount of time per frame
    *///=========================================================================================================================
    @Override
    public void update(float tpf)
    {
        if(this.screen != null)
        {
            updateActiveShipComponents();
            updateInactiveShipComponents();
            updateBrokenShipComponents();
            updateMoneyInfo();
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // IMPLEMENTED SCREEN CONTROLLER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    /**========================================================================================================================== 
    * @name BIND
    * 
    * @description Method that binds the screen controller with the nifty instance and the screen.
    * 
    * @param nifty The nifty instance
    * @param screen The screen instance
    *///=========================================================================================================================
    public void bind(Nifty nifty, Screen screen)
    {
        System.out.println("bind( " + screen.getScreenId() + ")");
        
        this.nifty = nifty;
        this.screen = screen;
        
        // we initialize components and staff here since the screen cannot be non-null
        initShipComponents();
        initSeniorStaff();
    }

    /**========================================================================================================================== 
    * @name ON START SCREEN
    * 
    * @description Method that is called when the screen has initially started up
    *///=========================================================================================================================
    public void onStartScreen()
    {
        System.out.println("onStartScreen");
    }

    
    /**========================================================================================================================== 
    * @name ON END SCREEN
    * 
    * @description Method that is called at the end of the screen's life
    *///=========================================================================================================================
    public void onEndScreen()
    { 
    }
     
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // MAIN GAME SCREEN CONTROLLER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    * @name INIT SHIP COMPONENTS    
    * 
    * @description Grabs a list of inactive ship components from the Ship and updates the level text
    *///=========================================================================================================================
    private void initShipComponents()
    {
        ShipComponent[] inactiveComponents = MainApplication.app.myShip.getInactiveComponents();
        
        for(int i = 0; i < inactiveComponents.length; i++)
        {
            ShipComponent shipComp = inactiveComponents[i];
            
            if(shipComp != null)
            {
                // get associated enum
                EShipComponent shipEnum = EShipComponent.values()[i];
                
                // get ship element from GUI
                ShipComponentElementController shipElem = this.screen.findControl(shipEnum.toString(), ShipComponentElementController.class);
                
                // update basic information about the ship component
                shipElem.updateLevel(shipComp.getLevel());
                shipElem.updateCost(shipComp.getFormattedLevelCost());
                
                updateShipTier();
            }
            
            // discard element
            inactiveComponents[i] = null;
        }
    }
    
    /**========================================================================================================================== 
    * @name INIT SENIOR STAFF 
    * 
    * @description Grabs a list of senior staff and updates the costs
    *///=========================================================================================================================
    private void initSeniorStaff()
    {
        SeniorStaff[] seniorStaff = MainApplication.app.myShip.getAllStaff();
        for(int i = 0; i < seniorStaff.length; i++)
        {
            // get staff element from GUI
            ESeniorStaff staffEnum = ESeniorStaff.values()[i];
            StaffElementController staffElem = this.screen.findControl(staffEnum.toString(), StaffElementController.class);
            
            // get and update cost
            staffElem.updateCost( MainApplication.app.myShip.getSeniorStaffCostStr(staffEnum) );
        }
    }
    
    /**========================================================================================================================== 
    * @name UPDATE ACTIVE SHIP COMPONENTS
    * 
    * @description Grabs a list of active ship components from the Ship and updates each of their progress bars accordingly.
    *///=========================================================================================================================
    private void updateActiveShipComponents()
    {
        ShipComponent[] activeComponents = MainApplication.app.myShip.getActiveComponents();
        
        for(int i = 0; i < activeComponents.length; i++)
        {
            ShipComponent shipComp = activeComponents[i];
            
            if(shipComp != null)
            {
                // get associated enum and get element from GUI
                EShipComponent shipEnum = EShipComponent.values()[i];
                ShipComponentElementController shipElem = this.screen.findControl(shipEnum.toString(), ShipComponentElementController.class);
                
                // get percentage complete and time left
                double percentComplete = shipComp.getTimerPercent();
                String timeLeft = MainApplication.app.myShip.getTimeLeft(shipEnum);

                // update bar (color depends on which activity)
                if(shipComp.getComponentState() == EShipComponentState.GAINING_EXP)
                {
                    shipElem.updateProgressBar(percentComplete, ShipComponentElementController.GREEN_BAR_ID);
                    shipElem.updateProgressBar(0, ShipComponentElementController.RED_BAR_ID);
                    
                    shipElem.updateCost(shipComp.getFormattedLevelCost());
                }
                else if(shipComp.getComponentState() == EShipComponentState.REPAIRING)
                {
                    
                    shipElem.updateProgressBar(percentComplete, ShipComponentElementController.RED_BAR_ID);
                    shipElem.updateProgressBar(0, ShipComponentElementController.GREEN_BAR_ID);
                    
                    shipElem.updateCost(shipComp.getFormattedRepairCost());
                }
                
                // update the level and time left labels
                shipElem.updateLevel(shipComp.getLevel());
                shipElem.updateTimeLeft(timeLeft);
                
                updateShipTier();
                
                // disable the component and discard element from list
                shipElem.disableLevelButton();
                activeComponents[i] = null;
            }
        }
    }
    
    /**========================================================================================================================== 
    * @name UPDATE INACTIVE SHIP COMPONENTS
    * 
    * @description Grabs a list of inactive ship components from the Ship and, if the components aren't already enabled again,
    * we enable them.
    *///=========================================================================================================================
    private void updateInactiveShipComponents()
    {
        ShipComponent[] inactiveComponents = MainApplication.app.myShip.getInactiveComponents();
        
        for(int i = 0; i < inactiveComponents.length; i++)
        {
            ShipComponent shipComp = inactiveComponents[i];
            
            if(shipComp != null)
            {
                // get associated enum
                EShipComponent shipEnum = EShipComponent.values()[i];
                
                // get ship element from GUI
                ShipComponentElementController shipElem = this.screen.findControl(shipEnum.toString(), ShipComponentElementController.class);
                
                // reinitialize the component by enabling it, updating its info, and making sure that it doesn't look broken
                if(!shipElem.isLevelButtonEnabled())
                {
                    shipElem.reenableComponent();
                    shipElem.fixComponent(shipComp.getFormattedLevelCost());
                }
                
                // update the level and time left labels
                shipElem.updateLevel(shipComp.getLevel());
                shipElem.updateTimeLeft("00:00:00");
                
                updateShipTier();
            }
            
            // discard element
            inactiveComponents[i] = null;
        }
    }
    
    /**========================================================================================================================== 
    * @name UPDATE BROKEN SHIP COMPONENTS
    * 
    * @description Grabs a list of the broken ship components from the Ship and, if the component isn't showing the user that it
    * is broken, then we show them that it is.
    *///=========================================================================================================================
    private void updateBrokenShipComponents()
    {
        ShipComponent[] brokenComponents = MainApplication.app.myShip.getBrokenComponents();
        
        for(int i = 0; i < brokenComponents.length; i++)
        {
            ShipComponent shipComp = brokenComponents[i];
            
            if(shipComp != null)
            {
                // get associated enum
                EShipComponent shipEnum = EShipComponent.values()[i];
                
                // get ship element from GUI
                ShipComponentElementController shipElem = this.screen.findControl(shipEnum.toString(), ShipComponentElementController.class);
                
                // enable it if it needs it
                if(!shipElem.isLevelButtonEnabled())
                {
                    shipElem.reenableComponent();
                    shipElem.updateLevel(shipComp.getLevel());
                    
                    updateShipTier();
                }
                
                // make it appear broken
                if(!shipElem.appearsBroken())
                {
                    shipElem.breakComponent(shipComp.getFormattedRepairCost());
                }
            }
            
            // discard element
            brokenComponents[i] = null;
        }
    }
    
    /**========================================================================================================================== 
    * @name UPDATE SHIP TIER
    * 
    * @description Updates the ship's tier based on the ship components' tiers
    *///=========================================================================================================================
    private void updateShipTier()
    {
        // updates the photo tier of the ship
        if(this.nifty != null && this.screen != null)
        {
            String shipPictureName = MainApplication.app.myShip.getShipCurrentPictureName();
            NiftyImage newImage = this.nifty.getRenderEngine().createImage(this.screen, "Textures/Ships/" + shipPictureName, false);
            Element shipImage = this.screen.findElementByName(SHIP_IMAGE_ID);
            shipImage.getRenderer(ImageRenderer.class).setImage(newImage);
        }
    }
    
    /**========================================================================================================================== 
    * @name UPDATE MONEY INFO
    * 
    * @description Updates cash and buttons to reflect money
    *///=========================================================================================================================
    private void updateMoneyInfo()
    {
   
        if(this.nifty != null && this.screen != null)
        {
            // update current money
            String currentMoney = MainApplication.app.myShip.getCurrentMoneyStr();
            Element moneyCompElem = this.screen.findElementByName(MONEY_COMP_ID);
            if(moneyCompElem != null)
            {
                moneyCompElem.findElementByName(MONEY_TEXT_ID).getRenderer(TextRenderer.class).setText(currentMoney);
            }
            
            // update ship component buttons
            ShipComponent[] shipComponents = MainApplication.app.myShip.getAllComponents();
            for(int i = 0; i < shipComponents.length; i++)
            {
                ShipComponent shipComp = shipComponents[i];
                if(shipComp != null)
                {
                    // get ship element from GUI
                    EShipComponent shipEnum = EShipComponent.values()[i];
                    ShipComponentElementController shipElem = this.screen.findControl(shipEnum.toString(), ShipComponentElementController.class);
                    
                    if( !MainApplication.app.myShip.canAfford(shipEnum) )
                    {
                        shipElem.disableBuying();
                    }
                    else
                    {
                        shipElem.enableBuying( MainApplication.app.myShip.getShipComponentCostStr(shipEnum) );
                    }
                }
            }
            
            // update staff buttons
            SeniorStaff[] seniorStaff = MainApplication.app.myShip.getAllStaff();
            for(int i = 0; i < seniorStaff.length; i++)
            {
                // get staff element from GUI
                ESeniorStaff staffEnum = ESeniorStaff.values()[i];
                StaffElementController staffElem = this.screen.findControl(staffEnum.toString(), StaffElementController.class);
                
                if( !MainApplication.app.myShip.canAfford(staffEnum) )
                {
                    staffElem.disableBuying();
                }
                else
                {
                    staffElem.enableBuying();
                }
            }
            
            
        }
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // WINDOW GAME SCREEN CONTROLLER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    * @name TOGGLE MUSIC
    * 
    * @description Toggles the music volume on and off along with changing the icon picture
    *///=========================================================================================================================
    public void toggleMusic()
    {
        String iconKey;
        
        // Turns it off if it's on
        if(MainApplication.app.isMusicOn())
        {
            iconKey = "Interface/Images/Icons/audioOffIcon.png";
            MainApplication.app.setMusicVolume(true);
        }
        // Turns it on if it's off
        else
        {
            iconKey = "Interface/Images/Icons/audioOnIcon.png";
            MainApplication.app.setMusicVolume(false);
        }
        
        NiftyImage newImage = this.nifty.getRenderEngine().createImage(this.screen, iconKey, false);
        Element iconImage = this.screen.findElementByName(AUDIO_IMAGE_ID);
        iconImage.getRenderer(ImageRenderer.class).setImage(newImage);
    }
    
    /**========================================================================================================================== 
    * @name OPEN OFFICER WINDOW
    * 
    * @description Opens the officer window in the main screen
    *///=========================================================================================================================
    public void openOfficerWindow()
    {
        System.out.println("Officer Window Opened"); 
        
        Element window = this.screen.findElementByName(OFFICER_WINDOW_ID);
        if(window != null)
        {
            window.setVisible(true);
        }
    }
    
    /**========================================================================================================================== 
    * @name OPEN SENIOR STAFF WINDOW
    * 
    * @description Opens the senior staff window in the main screen
    *///=========================================================================================================================
    public void openSeniorStaffWindow()
    {
        System.out.println("Staff Window Opened");   
        
        Element window = this.screen.findElementByName(SENIOR_STAFF_WINDOW_ID);
        if(window != null)
        {
            window.setVisible(true);
        }
    }
    
    /**========================================================================================================================== 
    * @name OPEN UNLOCKS WINDOW
    * 
    * @description Opens the unlocks window in the main screen
    *///=========================================================================================================================
    public void openUnlocksWindow()
    {
        System.out.println("Unlock Screen Window Opened");
        
        Element window = this.screen.findElementByName(UNLOCKS_WINDOW_ID);
        if(window != null)
        {
            window.setVisible(true);
        }
    }

    /**========================================================================================================================== 
    * @name OPEN TRAVEL WINDOW
    * 
    * @description Opens the travel window in the main screen
    *///=========================================================================================================================
    public void openTravelWindow()
    {
        System.out.println("Travel Window Opened");
        
        Element window = this.screen.findElementByName(TRAVEL_WINDOW_ID);
        if(window != null)
        {
            window.setVisible(true);
        }
    }
 
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
}
