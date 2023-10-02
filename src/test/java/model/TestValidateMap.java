package model;

import Model.EngineCommand;
import Model.WargameMap;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests if map is validated or not
 */
public class TestValidateMap {
    EngineCommand engine;
    WargameMap map;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        engine = new EngineCommand();
        map = new WargameMap("test1.map");
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testValidateMap(){
        boolean l_check = engine.validateMap(map);
        assertTrue(l_check);
    }
}
