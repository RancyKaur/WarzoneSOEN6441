package Model;


import static org.junit.Assert.*;

import java.util.ArrayList;

import Controller.GameEngine;
import Model.Player;
import Controller.GameEngine;
import org.junit.Before;
import org.junit.Test;
/**
 * Tests if countries are assigned properly or not
 *
 */

public class TestCountryAssignment {
    Player d_Player1;
    Player d_Player2;
    WargameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    EngineCommand d_Rge;
    GamePhase d_GamePhase;
    GameEngine d_Ge;
    ReinforcePlayers d_Arfc;

    /**
     * Set up the map and add players
     */
    @Before
    public void before() {

        d_Player1 = new Player("ronak");
        d_Player2 = new Player("alekhya");
        d_Map = new WargameMap("new.map");
        d_Rge = new EngineCommand();
        d_Players = new ArrayList<>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = GamePhase.ISSUEORDER;
    }

    /**
     * Test if the countries are assign properly or not
     */
    @Test
    public void testPopulateCountries() {
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("new.map");
        boolean l_check = d_Stup.assignCountries(d_Map, d_Players);
        assertEquals(true,l_check);
        assertEquals(d_Player1.getOwnedCountries().size(),d_Player2.getOwnedCountries().size());
    }

}


