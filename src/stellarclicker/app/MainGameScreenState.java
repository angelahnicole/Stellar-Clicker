
package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file MainGameScreenState.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description It's an application state that handles the interaction between the user, UI, and the ship.
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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainGameScreenState extends AbstractAppState implements ScreenController
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private Nifty nifty;
    private Screen screen;
    private MainApplication app;
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
        this.app = (MainApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.guiViewPort = this.app.getGuiViewPort();
        this.audioRenderer = this.app.getAudioRenderer();
        
        this.app.getNifty().fromXml("Interface/XML/MainGameScreen.xml", "mainGame", this);
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
