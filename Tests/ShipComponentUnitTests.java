/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



import java.util.concurrent.TimeUnit;

import stellarclicker.ship.ShipComponent;
import stellarclicker.util.EShipComponentState;
/**
 *
 * @author Matt
 */
public class ShipComponentUnitTests {
    
    private ShipComponent testComponent = null;
    
    public ShipComponentUnitTests() {
    }
    
    
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void finishExperienceTimer()
    {
        testComponent = new ShipComponent();
        testComponent.update(0);
        testComponent.setExperienceTime(3);
        
        testComponent.gainExperience();
        
        testComponent.update(4);
        
        testComponent.manageTimers();
        
        int level = testComponent.getLevel();
        
        assertEquals(1, level);
    }
    
    @Test
    public void finishRepairTimer()
    {
        //setup
        testComponent = new ShipComponent();
        testComponent.setMaxDurability(100);
        testComponent.update(0);
        testComponent.setRepairTime(6);
        
        //changes
        testComponent.gainRepair();
        testComponent.update(7);
        testComponent.manageTimers();
        
        int durability = testComponent.getDurability();
        
        assertEquals(100, durability);
    }
     
    @Test
    public void unfinishedExperienceTimer()
    {
        testComponent = new ShipComponent();
        testComponent.update(0);
        testComponent.setExperienceTime(3);
        
        testComponent.gainExperience();
        
        testComponent.update(2);
        
        testComponent.manageTimers();
        
        int level = testComponent.getLevel();
        
        assertEquals(0, level);
    }
    
     @Test
    public void unfinishRepairTimer()
    {
         //setup
        testComponent = new ShipComponent();
        testComponent.setMaxDurability(100);
        testComponent.update(0);
        testComponent.setRepairTime(6);
        
        //changes
        testComponent.gainRepair();
        testComponent.update(4);
        testComponent.manageTimers();
        
        int durability = testComponent.getDurability();
        
        assertFalse(durability == 100);
    }
}