
package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file SplashScreenState.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description An application state that is ran at the beginning of the game. It gives credits to the makers of the game and
 * allows the user to start or exit the game.
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
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import stellarclicker.util.EAppState;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class SplashScreenState extends AbstractAppState implements ScreenController
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static final String CREDITS_LAYER_ID = "creditsUI";
    public static final String CREDITS_WINDOW_ID = "creditsWindow";
    
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
    
    // APP STATE METHODS

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
        
        this.app.setPauseOnLostFocus(false);
        this.inputManager.setCursorVisible(true);
        
        MainApplication.app.getNifty().fromXml("Interface/XML/SplashScreen.xml", "splash");
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
    * <br><br> Method called by the main thread (loop) that updates the state of the game.
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
        // start music
        MainApplication.app.startMusic();
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
    // MAIN SCREEN CONTROLLER METHODS
    // --------------------------------------------------------------------------------------------------------------------------------------------

    
    /**========================================================================================================================== 
    *  START GAME
    * 
    * <br><br> Switches the application to the starting state
    *///=========================================================================================================================
    public void startGame()
    {
        MainApplication.app.changeState(EAppState.GAME_STATE);
    }
    
    /**========================================================================================================================== 
    *  LOAD GAME
    * 
    * <br><br> Switches the application to the loading state
    *///=========================================================================================================================
    public void loadGame()
    {
        MainApplication.app.changeState(EAppState.LOAD_STATE);
    }
    
    /**========================================================================================================================== 
    *  OPEN CREDITS WINDOW
    * 
    * <br><br> Opens the credits window in the main screen
    *///=========================================================================================================================
    public void openCreditsWindow()
    {
        Element window = this.screen.findElementByName(CREDITS_WINDOW_ID);
        if(window != null)
        {
            window.setVisible(true);
        }
    }

    /**========================================================================================================================== 
    *  QUIT GAME
    * 
    * <br><br> Terminates the application
    *///=========================================================================================================================
    public void quitGame()
    {
        MainApplication.app.stop();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
