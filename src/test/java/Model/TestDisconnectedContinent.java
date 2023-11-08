package Model;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests case where a continent subgraph is disconnected
 */

public class TestDisconnectedContinent {
    ValidateMap d_Mvr;
    EngineCommand d_Rgame;
    WargameMap d_Map;

    /**
     * Set up the context
     */
    @Before
    public void before() {
        d_Mvr = new ValidateMap();
        d_Rgame = new EngineCommand();
        d_Map = new WargameMap("DisconnectedMap.map");
    }

    @Test
    public void testDisconnectedContinent() {
        try{
            d_Map = d_Rgame.editMap("DisconnectedMap.map");
            boolean l_check = d_Mvr.continentConnectivityCheck(d_Map);
            assertFalse(l_check);
        }catch(Exception e){
            assertNotNull(e);
        }
    }
}
