package Model;

import Model.Player;
import Model.StartUp;
import Controller.GameEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test if given player has won the game
 */
public class TestGameWin{
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
        d_Player1 = new Player("Player1");
        d_Player2 = new Player("Player2");
        d_Map = new WargameMap("test2.map");
        d_Rge = new EngineCommand();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = GamePhase.ISSUEORDER;
        l_checkOwnedCountry = true;
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("test2.map");
        d_Stup.assignCountries(d_Map, d_Players);
        System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" : "+d_Player1.getOwnedCountries());
        System.out.println("Countries assigned to "+d_Player2.getPlayerName()+" : "+d_Player2.getOwnedCountries());
        d_Player2.getOwnedCountries().putAll(d_Player1.getOwnedCountries());
        d_Player1.getOwnedCountries().clear();
    }

    /**
     * Tests if a specific player won the Game
     */
    @Test
    public void testGameWin() {
        System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" : "+d_Player1.getOwnedCountries());
        System.out.println("Countries assigned to "+d_Player2.getPlayerName()+" : "+d_Player2.getOwnedCountries());

        boolean ifWon = false;
        for (Player l_p : d_Players){
            if(l_p.getOwnedCountries().size() == d_Map.getCountries().size()){
                System.out.println(l_p.getPlayerName()+" has Won the Game!");
                ifWon = true;
            }
        }
        assertTrue(ifWon);
    }

}


