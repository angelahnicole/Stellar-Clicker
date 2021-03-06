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
import com.jme3.audio.AudioSource.Status;
import com.jme3.audio.AudioNode;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import stellarclicker.util.EAppState;
import stellarclicker.ship.Ship;
import stellarclicker.util.JSONLoader;
import stellarclicker.util.ProgressInfo;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainApplication extends SimpleApplication
{  
   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // main application singleton
    public static MainApplication app;
    
    // game states and nifty instances
    private Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    protected EAppState changeState;
    protected AppState currentState;
    
    // music
    private AudioNode audioMusic;
    private String musicKey = "Sounds/Music/Cycles Looped.ogg";
    private boolean musicOn = false;
    
    // game attributes
    public Ship myShip;
    private float gameTime;
    
    private boolean beginTime = false;
    
    // persistence information
    public ProgressInfo progressInfo;
    private File saveFile;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**========================================================================================================================== 
    *  MAIN
    * 
    * <br><br> Main entry into the game.
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
    *  SIMPLE INIT APP
    * 
    * <br><br> Initialization for the state
    *///=========================================================================================================================
    @Override
    public void simpleInitApp()
    {
        // Adding JSON loader so we can read JSON files (needs to happen before ship is created)
        assetManager.registerLoader(JSONLoader.class, "json");
        
        // initialize background music
        initMusic();
        
        // initialize the save file
        initSaveFile();
        
        // initialize progress info
        progressInfo = new ProgressInfo();
        
        // initializing the nifty GUI
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
       
        // always start with the start state
        changeState = EAppState.SPLASH_STATE;
       
        // set stats views to default off
        setDisplayFps(false);
        setDisplayStatView(false);
    }

    /**========================================================================================================================== 
    *  UPDATE
    * 
    * <br><br> Update method for the state
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
        
        // check music so we will restart it if it's done
        checkMusic();
        
        switch(changeState)
        {
            // go to the splash screen state
            case SPLASH_STATE:
            {
                if(stateManager.hasState(currentState))
                {
                    stateManager.detach(currentState);
                }
                
                currentState = new SplashScreenState();
                stateManager.attach(currentState);
                
                break;
            }
            // go to the load screen state
            case LOAD_STATE:
            {
                if(stateManager.hasState(currentState))
                {
                    stateManager.detach(currentState);
                }
                
                currentState = new LoadScreenState();
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
                startShip();
                
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
        if (this.myShip != null && this.beginTime == true)
        {
            this.myShip.update(tpf, gameTime);
        }
    }

    
    /**========================================================================================================================== 
    *  DESTROY
    * 
    * <br><br> Kill the current state and save the game.
    *///=========================================================================================================================
    @Override
    public void destroy()
    {
        super.destroy();
        saveGame();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // STATE MANAGEMENT METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  CHANGE STATE
    * 
    * <br><br> Notifies the state manager to switch to the new state
    * 
    * @param newState the new app state to switch to
    *///=========================================================================================================================
    public void changeState(EAppState newState)
    {
        changeState = newState;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // SOUND MANAGEMENT METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  CHECK MUSIC
    * 
    * <br><br> Restarts the music once it has stopped (you can't loop streamed music)
    *///=========================================================================================================================
    private void checkMusic()
    {
        if(musicOn && audioMusic.getStatus() == Status.Stopped)
        {
            initMusic();
            startMusic();
        }
    }
    
    /**========================================================================================================================== 
    *  INIT MUSIC
    * 
    * <br><br> Creates a new audio node with the desired settings for background music
    *///=========================================================================================================================
    private void initMusic()
    {
        audioMusic = new AudioNode(assetManager, musicKey, true, false);
        audioMusic.setPositional(false);
        audioMusic.setLooping(false);
        audioMusic.setVolume(1);
    }
    
    /**========================================================================================================================== 
    *  START MUSIC
    * 
    * @return boolean Whether or not the music is "on", or if the volume is on
    *///=========================================================================================================================
    public boolean isMusicOn()
    {
        return musicOn;
    }
    
    /**========================================================================================================================== 
    *  START MUSIC
    * 
    * <br><br> Starts the background music for the game
    *///=========================================================================================================================
    public void startMusic()
    {
        if(audioMusic.getStatus() != Status.Playing)
        {
            audioMusic.play();
            musicOn = true;
        }
    }
    
    /**========================================================================================================================== 
    *  STOP MUSIC
    * 
    * <br><br> Stops the background music for the game
    *///=========================================================================================================================
    public void stopMusic()
    {
        if(audioMusic.getStatus() != Status.Stopped)
        {
            audioMusic.stop();
            musicOn = false;
        }
    }
    
    /**========================================================================================================================== 
    *  SET MUSIC VOLUME
    * 
    * <br><br> Toggles the volume of the music.
    * 
    * @param mute Whether or not you want to mute the music
    *///=========================================================================================================================
    public void setMusicVolume(boolean mute)
    {
        if(mute)
        {
            audioMusic.setVolume(0);
            musicOn = false;
        }
        else
        {
            audioMusic.setVolume(1);
            musicOn = true;
        }
    }
    
    public void startShip()
    {
        
        this.beginTime = true;
        
    }
    
    /**========================================================================================================================== 
    *  SET MUSIC VOLUME
    * 
    * <br><br> Changes the volume of the music.
    * 
    * @param volume The volume of the music.
    *///=========================================================================================================================
    public void setMusicVolume(float volume)
    {
        audioMusic.setVolume(volume);
        
        if(volume == 0)
        {
            musicOn = false;
        }
        else
        {
            musicOn = true;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // PERSISTENCE
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  INIT SAVE FILE
    * 
    * <br><br> Initializes the save file with the correct location
    *///=========================================================================================================================
    public void initSaveFile()
    {
        String userHome = System.getProperty("user.home");
        this.saveFile = new File(userHome + "/StellarClicker/Saves/ship.j3o");
    }
    
    /**========================================================================================================================== 
    *  SAVE GAME
    * 
    * <br><br> Saves the game (or ship) using jME
    *///=========================================================================================================================
    public void saveGame()
    {
        // get the file to save
        BinaryExporter exporter = BinaryExporter.getInstance();
        
        // save the whole ship!
        try
        {
            exporter.save(this.myShip, this.saveFile);
        }
        catch(Exception ex)
        {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, "Error: Failed to save game!", ex);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // GETTERS AND SETTERS
    // --------------------------------------------------------------------------------------------------------------------------------------------
    
    /**========================================================================================================================== 
    *  GET NIFTY
    * 
    * @return Nifty The main instance of nifty 
    *///=========================================================================================================================
    public Nifty getNifty()
    {
        return nifty;
    }
    
    /**========================================================================================================================== 
    *  GET SAVE FILE
    * 
    * @return File The file object for the save file
    *///=========================================================================================================================
    public File getSaveFile()
    {
        return this.saveFile;
    }
    
    /**========================================================================================================================== 
    *  SET SHIP
    * 
    * <br><br> Set the ship object with a new instance
    * 
    * @param newShip The new ship instance
    *///=========================================================================================================================
    public void setShip(Ship newShip)
    {
        this.myShip = newShip;
    }
     /**========================================================================================================================== 
    *  CLAIM OFFICERS
    * 
    * <br><br> Set the ship object with previous instance with new officers
    *///=========================================================================================================================
    public void claimOfficers()
    {
        double officers = this.myShip.getClaimableOfficers();
        Ship newShip = new Ship(officers);
        this.myShip = newShip;
        
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
