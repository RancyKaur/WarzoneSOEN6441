package Model;

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
        map = new WargameMap("world.map");
        System.out.println("ASDF" + map.getContinents());
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testValidateMap(){
        map = engine.editMap("world.map");
        boolean l_check = engine.validateMap(map);
        assertTrue(l_check);
    }
}