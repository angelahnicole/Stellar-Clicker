package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * @file MainApplication.java
 * 
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * 
 * @description 
 * 
 */

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import stellarclicker.app.*;
public class MainApplication extends SimpleApplication
{
    
   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static MainApplication app;
    protected EAppState changeState;
    protected AppState currentState;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // SIMPLE APPLICATION METHODS
   
    /** 
    * Main entry into the game.
    * 
    * @param args Arguments given to the main method (usually via command line)
    */
    public static void main(String[] args)
    {
        app = new MainApplication();
        app.start();
    }

    /**
     * 
     */
    @Override
    public void simpleInitApp()
    {
        System.out.println("Initializing...");
        stellarclicker.util.ThreadManager mythreadmanager = new stellarclicker.util.ThreadManager();
        mythreadmanager.initialize();
        // always start with the start state
        changeState = EAppState.START_STATE;
        
        
    }

    /**
     * 
     */
    @Override
    public void update()
    {
        super.update();

        // do some animation
        float tpf = timer.getTimePerFrame();

        stateManager.update(tpf);
        stateManager.render(renderManager);
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();

        // render the viewports
        renderManager.render(tpf, context.isRenderable());
        
        switch(changeState)
        {
            case START_STATE:
            {
                if(stateManager.hasState(currentState))
                {
                    stateManager.detach(currentState);
                    currentState = new StartState();
                    stateManager.attach(currentState);
                }
                
                currentState = new StartState();
                stateManager.attach(currentState);
                
                break;
            }
            case GAME_STATE:
            {
                if(stateManager.hasState(currentState))
                {
                    stateManager.detach(currentState);
                }
                
                currentState = new GameState();
                stateManager.attach(currentState);
                
                break;
            }
            case STAY_STATE:
            {
                // Do nothing. We are staying here.
                break;
            }
        }
        
        // Go back to not changing state
        changeState = EAppState.STAY_STATE;
    }

    /**
     * 
     */
    @Override
    public void destroy()
    {
        super.destroy();

        System.out.println("Destroy");
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // STATE MANAGEMENT METHODS
    
    /**
     * TODO: This will most likely be moved to stellarclicker.util
     */
    public static enum EAppState
    {
        START_STATE, GAME_STATE, STAY_STATE
    }
    
    /**
     * 
     */
    public void changeState(EAppState newState)
    {
        changeState = newState;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
