package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file MainApplication.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Serves as a main entry point into the game along with being a state manager and the handler of the ship.
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

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;

import stellarclicker.util.EAppState;
import stellarclicker.ship.Ship;
import stellarclicker.util.JSONLoader;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainApplication extends SimpleApplication
{  
   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static MainApplication app;
    
    private Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    protected EAppState changeState;
    protected AppState currentState;
    public Ship myShip;
    
    private float gameTime;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**========================================================================================================================== 
    * @name MAIN
    * 
    * @description Main entry into the game.
    * 
    * @param args Arguments given to the main method (usually via command line)
    *///=========================================================================================================================
    public static void main(String[] args)
    {
        app = new MainApplication();
        app.start();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // SIMPLE APPLICATION METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
   
    /**========================================================================================================================== 
    * @name SIMPLE INIT APP
    * 
    * @description Initialization for the state
    *///=========================================================================================================================
    @Override
    public void simpleInitApp()
    {
        System.out.println("Initializing...");
        
        // Adding JSON loader so we can read JSON files
        assetManager.registerLoader(JSONLoader.class, "json");

        myShip = new Ship();
        
        // initializing the nifty GUI
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
       
        // always start with the start state
        changeState = EAppState.SPLASH_SCREEN_STATE;
       
        // set stats views to default off
        setDisplayFps(false);
        setDisplayStatView(false);
    }

    /**========================================================================================================================== 
    * @name UPDATE
    * 
    * @description Update method for the state
    *///=========================================================================================================================
    @Override
    public void update()
    {
        super.update();

        // update and render the current state
        float tpf = timer.getTimePerFrame();
        gameTime = timer.getTimeInSeconds();
        stateManager.update(tpf);
        stateManager.render(renderManager);
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();

        // render the viewports
        renderManager.render(tpf, context.isRenderable());
        
        switch(changeState)
        {
            // go to the splash screen state
            case SPLASH_SCREEN_STATE:
            {
                if(stateManager.hasState(currentState))
                {
                    stateManager.detach(currentState);
                }
                
                currentState = new SplashScreenState();
                stateManager.attach(currentState);
                
                break;
            }
            // go to the main game state
            case GAME_STATE:
            {
                if(stateManager.hasState(currentState))
                {
                    stateManager.detach(currentState);
                }
                
                currentState = new MainGameScreenState();
                stateManager.attach(currentState);
                
                break;
            }
            // do nothing- we are staying in the same state
            case STAY_STATE:
            {
                
                break;
            }
        }
        
        // ensure that we don't constantly change states
        changeState = EAppState.STAY_STATE;
        
        //update time across game
        myShip.update(tpf, gameTime);

    }

    /**========================================================================================================================== 
    * @name DESTROY
    * 
    * @description Kill the current state 
    *///=========================================================================================================================
    @Override
    public void destroy()
    {
        super.destroy();

        System.out.println("Destroy");
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // STATE MANAGEMENT METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    * @name CHANGE STATE
    * 
    * @description Notifies the state manager to switch to the new state
    * 
    * @param newState the new app state to switch to
    *///=========================================================================================================================
    public void changeState(EAppState newState)
    {
        changeState = newState;
    }
    
    /**========================================================================================================================== 
    * @name GET NIFTY
    * 
    * @description Returns the main instance of nifty 
    *///=========================================================================================================================
    public Nifty getNifty()
    {
        return nifty;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
