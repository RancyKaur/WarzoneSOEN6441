package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To test the removal of continent
 *
 */
public class TestRemoveContinent {

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