package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test case to confirm type of map
 *
 */
public class TestMapTypes {

    LoadGraph d_LoadMap;
    String d_MapName, d_MapType;

    /**
     * set up context
     */
    @Before
    public void before(){
        d_LoadMap= new LoadGraph();
    }

    /**
     * Test for type conquest
     */
    @Test
    public void testConquestMap(){
        d_MapName= "Australia.map";
        d_MapType= d_LoadMap.readMap("src/main/resources/maps/"+ d_MapName);
        assertEquals("conquest", d_MapType);
    }

    /**
     * Test for type domination
     */
    @Test
    public void testDominationMap(){
        d_MapName= "Aus.map";
        d_MapType= d_LoadMap.readMap("src/main/resources/maps/"+ d_MapName);
        assertEquals("domination", d_MapType);
    }
}
