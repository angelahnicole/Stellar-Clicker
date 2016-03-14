
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
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import stellarclicker.util.EShipComponent;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainGameScreenState extends AbstractAppState implements ScreenController
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private Nifty nifty;
    private Screen screen;
    private Application app;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private ViewPort guiViewPort;
    private AudioRenderer audioRenderer;
    //private float percent = 0.0f;
    //private int level = 1;
    
    
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
        // Prototype code to get a ship component's bar and move it 
        /*if(this.screen != null)
        {
            ShipComponentUIController weapon = this.screen.findControl(EShipComponent.WEAPONS.toString(), ShipComponentUIController.class);
            
            if(percent < 1)
            {
                percent += 0.01f;
            }
            else
            {
                percent = 0.0f;
                level++;
                
                if(weapon != null)
                {
                    weapon.updateLevel(level);
                }
            }

            if(weapon != null)
            {
                weapon.updateProgressBar(percent, ShipComponentUIController.RED_BAR_ID);
            }
        }*/
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
    // MAIN SCREEN CONTROLLER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    * @name QUIT GAME
    * 
    * @description Stops the application
    *///=========================================================================================================================
    public void quitGame()
    {
        app.stop();
    }
    
    /**========================================================================================================================== 
    * @name OPEN STAFF WINDOW
    * 
    * @description Opens the staff window in the main screen
    *///=========================================================================================================================
    public void openStaffWindow()
    {
        System.out.println("Staff Window Opened");       
    }

    /**========================================================================================================================== 
    * @name OPEN TRAVEL WINDOW
    * 
    * @description Opens the travel window in the main screen
    *///=========================================================================================================================
    public void openTravelWindow()
    {
        System.out.println("Travel Window Opened");        
    }
 
     /**========================================================================================================================== 
    * @name OPEN OFFICER WINDOW
    * 
    * @description Opens the officer window in the main screen
    *///=========================================================================================================================
    public void openOfficerWindow()
    {
        System.out.println("Officer Window Opened");        
    }

     /**========================================================================================================================== 
    * @name OPEN UNLOCK WINDOW
    * 
    * @description Opens the unlock window in the main screen
    *///=========================================================================================================================
    public void openUnlockWindow()
    {
        System.out.println("Unlock Screen");        
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
}
