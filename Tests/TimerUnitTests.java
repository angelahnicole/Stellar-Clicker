/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jayway.awaitility.Duration;
import static com.jayway.awaitility.Awaitility.*;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


import org.hamcrest.Matcher.*;
import org.junit.Assert.*;
import stellarclicker.util.Timer;
/**
 *
 * @author Matt
 */
public class TimerUnitTests {
    
    public TimerUnitTests() {
    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testSetTimer()
    {
        Timer timer =  new Timer();
        timer.set(0, 5);
        
        assertEquals(timer.checkCompletion((float)5.1), true);
        
        timer =  new Timer();
        timer.set(0, 5);
        
        assertEquals(timer.checkCompletion(10), true);
        
        timer =  new Timer();
        timer.set(0, 5);
        
        assertEquals(timer.checkCompletion(3), false);
    }
    
    @Test
    public void testPercentComplete()
    {
        Timer timer = new Timer();
        
        timer.set(0, 6);
        assertEquals(timer.getPercentComplete(0),0,.01);
        assertEquals(timer.getPercentComplete(3),.5,.01);
        assertEquals(timer.getPercentComplete(6),1,.01);
    }
    
    @Test 
    public void testTimeRemaining()
    {
        Timer timer = new Timer();
        
        timer.set(0, 10);
        assertEquals(timer.getTimeRemaining((float)5.5),4.5,.01);
        assertEquals(timer.getTimeRemaining(8),2,.01);
        assertEquals(timer.getTimeRemaining(1),9,.01);
        assertEquals(timer.getTimeRemaining(10),0,.01);
    }
}