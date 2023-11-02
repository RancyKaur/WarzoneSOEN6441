package Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test if a new map can be created or existing map can be edited.
 */

public class TestMapEdit {

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
    public void testEditNewMap(){
        d_MapName= "practice.map";
        d_Map = d_RunGame.editMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }

    /**
     * Test to edit the map which exist in folder.
*/
    @Test
    public void testEditExistingMap(){
        d_MapName= "dummy.map";
        d_Map = d_RunGame.editMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }

}

