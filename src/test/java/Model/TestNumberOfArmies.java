package Model;


import Controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests if Reinforcements are assigned properly or not
 *
 */

public class TestNumberOfArmies {
    Player d_Player1;
    Player d_Player2;
    WargameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_StartPhase;
    GamePhase d_GamePhase;
    EngineCommand d_Engine;
    ReinforcePlayers d_Arfc;
    GameEngine d_Ge;
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Alekhya");
        d_Player2 = new Player("Janu");
        d_Map = new WargameMap("world.map");
        d_Engine = new EngineCommand();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = GamePhase.ISSUEORDER;
        System.out.println("Before test");
    }

    /**
     * Test if the Reinforcements are assigned or not.
     * Sample reinforcements assigned for Player1 and tested with unassigned reinforcements for Player2
     */
    @Test
    public void testAssignReinforcements() {
        d_Engine = new EngineCommand();
        d_Ge = new GameEngine();
        d_StartPhase = new StartUp(d_Ge);
        d_Map = d_Engine.loadMap("world.map");
        boolean l_check = d_StartPhase.assignCountries(d_Map, d_Players);
        System.out.println(d_Player1.getOwnedCountries());
        if(l_check) {
            ReinforcePlayers.assignReinforcementArmies(d_Player1);
        }
        assertNotEquals(d_Player1.getOwnedArmies(),d_Player2.getOwnedArmies());
        assertEquals(3,d_Player1.getOwnedArmies());
        assertEquals(0,d_Player2.getOwnedArmies());
    }

}
