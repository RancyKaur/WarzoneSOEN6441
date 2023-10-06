package Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * To test the functionalities carried out related to Country
 *
 */
public class TestCountry {

    WargameMap d_Map;
    String d_CountryId;
    String d_ContinentName;
    EngineCommand engineCommand;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_Map = new WargameMap("world.map");
        d_CountryId="iceland";
        engineCommand = new EngineCommand();
        d_ContinentName = "asia";
        engineCommand.addContinentToMap(d_Map, 5, d_ContinentName);
        engineCommand.addCountryToContinent(d_Map, d_CountryId, d_ContinentName);
    }

    /**
     * Test if the country is added to the continent
     */
    @Test
    public void testAddCountry(){
        System.out.println(d_Map.getMapName());
        System.out.println(d_Map.getContinents());
        d_CountryId="India";
        boolean l_check = engineCommand.addCountryToContinent(d_Map, d_CountryId, d_ContinentName);
        assertEquals(true,l_check);
    }


    /**
     * Test if the country is removed from map
     */
    @Test
    public void testRemoveCountry(){
        System.out.println(d_Map.getCountries());
        boolean l_check = engineCommand.removeCountry(d_Map, d_CountryId);
        assertEquals(true,l_check);

        d_CountryId="brazil";
        l_check = engineCommand.removeCountry(d_Map, d_CountryId);
        assertEquals(false,l_check);
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