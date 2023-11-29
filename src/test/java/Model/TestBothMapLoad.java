package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Test Class checks the loading of the map.
 */
public class TestBothMapLoad {

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
     * Test to load the domination map
     */
    @Test
    public void testDominationLoadMap(){
        d_MapName= "demo.map";
        System.out.println(d_MapName);
        d_Map = d_RunGame.loadMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }


    /**
     * Test to load the conquest map
     */
    @Test
    public void testConquestLoadMap(){
        d_MapName= "Australia.map";
        System.out.println(d_MapName);
        d_Map = d_RunGame.loadMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }

}
