package Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test if the map is loading or not
 *
 */
public class TestMapLoad {

    EngineCommand d_RunGame;
    WargameMap d_Map;
    String d_MapName;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_RunGame = new EngineCommand();
    }

    /**
     * Test to edit the map which is not existing and build it from scratch.
     */
    @Test
    public void testLoadMap(){
        d_MapName= "dummy.map";
        d_Map = d_RunGame.loadMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }

}

