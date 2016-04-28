package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file LoadScreenState.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description An application state that loads the game on a background thread and updates a progress bar until the game
 * is fully loaded, and then goes into the MainGameScreenState.
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
import com.jme3.export.binary.BinaryImporter;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import stellarclicker.ship.Ship;
import stellarclicker.util.EAppState;
import stellarclicker.util.ProgressInfo;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class LoadScreenState extends AbstractAppState implements ScreenController
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    private Nifty nifty;
    private Screen screen;
    private Application app;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private ViewPort guiViewPort;
    private AudioRenderer audioRenderer;
    
    private final String PROGRESS_BAR_ID = "loadingBar";
    private final String PROGRESS_BAR_TEXT_ID = "loadText";
    private final String PANEL_BOTTOM_ID = "panel_bottom";
    
    private ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
    private Future loadFuture = null;
    private ProgressInfo progressInfo;
    private Callable<Void> loadingCallable;
    
  
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // APP STATE METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    /**========================================================================================================================== 
    *  INITIALIZE
    * 
    * <br><br> Initializes the application state 
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
        this.progressInfo = MainApplication.app.progressInfo;
        
        MainApplication.app.getNifty().fromXml("Interface/XML/LoadScreen.xml", "load", this);
        
        this.loadingCallable = createLoadCallable(this);
    }

    /**========================================================================================================================== 
    *  CLEAN UP
    * 
    * <br><br> Cleans up the application state
    *///=========================================================================================================================
    @Override
    public void cleanup()
    {
        super.cleanup();
        exec.shutdown();
    }

    /**========================================================================================================================== 
    *  SET ENABLED
    * 
    * <br><br> Make the application state enabled or disabled (i.e. pause and unpause)
    * 
    * @param enabled Whether or not to enabled or disable the application state
    *///=========================================================================================================================
    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
    }
    
    /**========================================================================================================================== 
    *  UPDATE
    * 
    * <br><br> Method called by the main thread (loop) that updates the state of the screen
    * 
    * @param tpf The amount of time per frame
    *///=========================================================================================================================
    @Override
    public void update(float tpf)
    {
        if(this.screen != null)
        {
            try
            {
                // schedule loading
                if (loadFuture == null) 
                {
                    loadFuture = exec.submit(loadingCallable);
                }
                // move to next screen
                else if(loadFuture.isDone())
                {
                    // update the progress bar one last time
                    queueUpdateProgress(this);

                    // start the game
                    MainApplication.app.changeState(EAppState.GAME_STATE);
                }
                // update progress bar
                else
                {
                    queueUpdateProgress(this);
                }
            }
            catch(Exception ex)
            {
                Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                exec.shutdown();
            }
            
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // IMPLEMENTED SCREEN CONTROLLER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    /**========================================================================================================================== 
    *  BIND
    * 
    * <br><br> Method that binds the screen controller with the nifty instance and the screen.
    * 
    * @param nifty The nifty instance
    * @param screen The screen instance
    *///=========================================================================================================================
    public void bind(Nifty nifty, Screen screen)
    {
        this.nifty = nifty;
        this.screen = screen;
        
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
    *  ON END SCREEN
    * 
    * <br><br> Method that is called at the end of the screen's life
    *///=========================================================================================================================
    public void onEndScreen()
    { 
    }
     
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // LOADING SCREEN CONTROLLER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  CREATE LOAD CALLABLE
    * 
    * @param loadScreen The currently running load screen state
    * 
    * @return Callable Creates a callable that will load or create a ship
    *///=========================================================================================================================
    public Callable createLoadCallable(final LoadScreenState loadScreen)
    {
        return new Callable()
        {
            public Object call() throws Exception
            {
                // create or load ship and pause briefly to see loading labels
                Ship myShip = loadScreen.loadShip();
                Thread.sleep(250);

                // update ship info if the ship has been loaded and not created
                if(myShip != null)
                {
                    String lastSaveTime = myShip.getLastSaveTime();
                    if(!lastSaveTime.isEmpty())
                    {
                        // update ship components based on how much time has passed and pause briefly to show loading labels
                        myShip.updateShipComponentsSinceSave( myShip.getSecondsSinceSave(lastSaveTime) );
                        Thread.sleep(250);


                        // update money based on how much time has passed and pause briefly to show loading labels

                        // update officers based on how much time has passed and pause briefly to show loading labels
                    } 
                }

                return null;
            }
        };       
    }
    
    /**========================================================================================================================== 
    *  QUEUE UPDATE PROGRESS
    * 
    * <br><br> Queues a callable to run the jME main update thread that will update the progress bar
    * 
    * @param loadScreen The currently running load screen state
    *///=========================================================================================================================
    public void queueUpdateProgress(final LoadScreenState loadScreen)
    {
        MainApplication.app.enqueue
        (
            new Callable()
            {
                public Object call() throws Exception
                {
                    ProgressInfo progressInfo = MainApplication.app.progressInfo;
                    loadScreen.updateProgressBar(progressInfo.getProgressMade(), progressInfo.getProgressText());
                    
                    return null;
                }
            }
        );
    }
    
    /**========================================================================================================================== 
    *  UPDATE PROGRESS BAR
    * 
    * <br><br> Moves the progress bar given the desired percentage and updates the progress bar text
    * 
    * @param percentComplete The percentage (a number from 0 to 1) that the bar should update to
    * @param progressText Text to show over the progress bar
    *///=========================================================================================================================
    public void updateProgressBar(float percentComplete, String progressText)
    {
        // convert to a whole number percentage
        percentComplete = percentComplete * 100;
        
        // convert to the proper x percentage needed for updating the progress bar
        float progressBarPercent = -120.0f + percentComplete;
        
        // update the progress bar
        Element progressBar = this.screen.findElementByName(PROGRESS_BAR_ID);
        if(progressBar != null)
        {
            progressBar.setConstraintX(new SizeValue(progressBarPercent + "%"));
        }
        
        // update progress bar text control
        Element progressTextElem = this.screen.findElementByName(PROGRESS_BAR_TEXT_ID);
        if(progressTextElem != null)
        {
            progressTextElem.getRenderer(TextRenderer.class).setText(progressText);
        }
        
        // layout the elements
        this.screen.findElementByName(PANEL_BOTTOM_ID).layoutElements();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // PERSISTENCE
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  LOAD SHIP
    * 
    * <br><br> Loads ship from a save file or, if the save file doesn't exist, creates a new ship
    * 
    * @returns Ship The newly loaded / created ship
    *///=========================================================================================================================
    public Ship loadShip() throws Exception
    {
        progressInfo.setProgress(0.0f, "Making the ship...");
        
        File saveFile = MainApplication.app.getSaveFile();
        Ship loadedShip = null;
        
        // retrieve the ship from save
        BinaryImporter importer = BinaryImporter.getInstance();
        
        // creates or loads ship; may cause exception
        if(saveFile.exists())
        {
            loadedShip = (Ship) importer.load(saveFile);
            progressInfo.updateProgress(0.1f, "Save file found! Your ship has been loaded...");
        }
        else
        {
            loadedShip = new Ship();
            progressInfo.updateProgress(1.0f, "No save file found! A new ship has been created...");
        }
        
        // set the new ship
        MainApplication.app.setShip(loadedShip);
        
        return loadedShip;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
