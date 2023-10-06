package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To test the functionalities carried out related to Continents
 *
 */
public class TestContinent {

    WargameMap d_Map;
    String d_ContinentName;
    int d_ControlValue;
    EngineCommand engineCommand;

    @Before
    public void before() {
        d_Map = new WargameMap("world.map");
        engineCommand = new EngineCommand();
        d_ContinentName = "africa";
        d_ControlValue = 5;
        engineCommand.addContinentToMap(d_Map, d_ControlValue, d_ContinentName);
    }


    /**
     * Test if continents are added properly
     */
//    @Test
//    public void testAddContinent(){
//        d_Map = engineCommand.editMap("world.map");
//        boolean l_check = engineCommand.addContinentToMap(d_Map,d_ControlValue, d_ContinentName);
//        assertTrue(l_check);
//    }
    /**
     * Test if continents are removed properly
     */
    @Test
    public void testRemoveContinent(){
        boolean l_check = engineCommand.removeContinentFromMap(d_Map, d_ContinentName);
        assertTrue(l_check);

        d_ContinentName = "Europe";
        l_check = engineCommand.removeContinentFromMap(d_Map, d_ContinentName);
        assertFalse(l_check);

    }
}