package Model;
import Controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Test case to confirm if country is turned Neurtal
 */
public class TestNeutralCountry {

    Player d_Player1;
    Player d_Player2;
    WargameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    GamePhase d_GamePhase;
    GameEngine d_Ge;
    EngineCommand d_Rge;
    String d_CountryId = null;
    boolean l_checkOwnedCountry;
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Vignesh");
        d_Player2 = new Player("Rancy");
        d_Map = new WargameMap("test2.map");
        d_CountryId = "germany";
        d_Rge = new EngineCommand();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = GamePhase.ISSUEORDER;
        l_checkOwnedCountry = true;
    }

    /**
     * Test case to check if country is neutral for specific player
     */
    @Test
    public void testNeutralTerritory() {
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("test2.map");
        d_Stup.assignCountries(d_Map, d_Players);
        System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" are : "+d_Player1.getOwnedCountries());
        System.out.println("Countries assigned to "+d_Player2.getPlayerName()+" are : "+d_Player2.getOwnedCountries());

        Country l_c= d_Player1.getOwnedCountries().get(d_CountryId.toLowerCase());
        d_Player1.getOwnedCountries().remove(l_c.getCountryName().toLowerCase());
        System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" are : "+d_Player1.getOwnedCountries());
        //Check if country owned by player 1
        l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_CountryId.toLowerCase());
        System.out.println(l_checkOwnedCountry);
        //testing if above territory is neutral
        assertFalse(l_checkOwnedCountry);
    }

}
