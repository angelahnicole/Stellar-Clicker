/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainGameScreenState extends AbstractAppState implements ScreenController
{

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private Nifty nifty;
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
        
        this.app.setPauseOnLostFocus(false);
        
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/XML/screen.xml", "start", this);
        guiViewPort.addProcessor(niftyDisplay);
        inputManager.setCursorVisible(true);
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
        nifty.gotoScreen("empty");
    }

    
    /**
     * 
     */
    public void startGame()
    {
        nifty.fromXml("Interface/XML/MainGameScreen.xml", "mainGame", this);
        MainApplication.app.changeState(MainApplication.EAppState.GAME_STATE); // switch to new state
    }

    
    /**
     * 
     */
    public void quitGame()
    {
        app.stop();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
