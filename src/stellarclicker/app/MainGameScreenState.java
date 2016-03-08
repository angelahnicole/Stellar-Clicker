
package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * @file MainGameScreenState.java
 * 
 * @author Angela Gross, Matthew Dolan, Alex Dunn
 * 
 * @description 
 * 
 */

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
    
    // APP STATE METHODS

    /** 
    * 
    * 
    * @param stateManager 
    * @param app 
    */
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
    }

    /**
     * 
     */
    @Override
    public void cleanup()
    {
        super.cleanup();
    }

    
    /**
     * 
     */
    @Override
    public void setEnabled(boolean enabled)
    {
        // Pause and unpause
        super.setEnabled(enabled);

    }

    
    /**
     * 
     */
    @Override
    public void update(float tpf)
    {
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // SCREEN CONTROLLER METHODS

    
    /**
     * 
     */
    public void bind(Nifty nifty, Screen screen)
    {
        System.out.println("bind( " + screen.getScreenId() + ")");
        
        this.nifty = nifty;
        this.screen = screen;
    }

    
    /**
     * 
     */
    public void onStartScreen()
    {
        System.out.println("onStartScreen");
    }

    
    /**
     * 
     */
    public void onEndScreen()
    {
        
    }

    
    /**
     * 
     */
    public void quitGame()
    {
        app.stop();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // MAIN GUI METHODS
    
    public void openStaffScreen()
    {
        System.out.println("Staff Screen");       
    }

    /**
     * 
     */
    public void openTravelScreen()
    {
        System.out.println("Travel Screen");        
    }
 
     /**
     * 
     */
    public void openOfficerScreen()
    {
        System.out.println("Officer Screen");        
    }

     /**
     * 
     */
    public void openUnlockScreen()
    {
        System.out.println("Unlock Screen");        
    }
    
    

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
}
