package stellarclicker.app;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * @file GameState.java
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
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


public class GameState extends AbstractAppState implements ScreenController
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private MainApplication app;
    private Node rootNode;
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
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.guiViewPort = this.app.getGuiViewPort();
        this.audioRenderer = this.app.getAudioRenderer();
        
//        Box b = new Box(1, 1, 1);
//        Geometry geom = new Geometry("Box", b);
//        
//
//        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.Blue);
//        geom.setMaterial(mat);
//        rootNode.attachChild(geom); 
// 
    }

    /** 
    * 
    * 
    * 
    */
    @Override
    public void cleanup()
    {
        super.cleanup();
    }

    /** 
    * 
    * 
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
    * 
    * 
    */
    @Override
    public void update(float tpf)
    {
        System.out.println("Updating all the things!");        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // SCREEN CONTROLLER METHODS
    
    /** 
    * 
    * 
    * 
    */
    public void bind(Nifty nifty, Screen screen)
    {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }
    
    /** 
    * 
    * 
    * 
    */
  
    /**
     * 
     */
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

    
    public void onStartScreen()
    {
        System.out.println("onStartScreen");
    }

    /** 
    * 
    * 
    * 
    */
    public void onEndScreen()
    {
        System.out.println("onEndScreen");
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
