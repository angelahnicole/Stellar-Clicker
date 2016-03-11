package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**========================================================================================================================== 
 * @file MainApplication.java
 * --------------------------------------------------------------------------------------------------------------------------
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * --------------------------------------------------------------------------------------------------------------------------
 * @description Serves as a main entry point into the game along with being a state manager and the handler of the ship.
 *///========================================================================================================================

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import stellarclicker.app.*;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import stellarclicker.util.EAppState;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainApplication extends SimpleApplication
{  
   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private Nifty nifty;
    private NiftyJmeDisplay niftyDisplay;
    public static MainApplication app;
    protected EAppState changeState;
    protected AppState currentState;
    
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
