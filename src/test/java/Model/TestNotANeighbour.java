package Model;

import Controller.GameEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Test whether a territory is not a neighbour for given player
 */
public class TestNotANeighbour{
    Player d_Player1;
    Player d_Player2;
    WargameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    GamePhase d_GamePhase;
    GameEngine d_Ge;
    EngineCommand d_Rge;
    String d_SourceCountryId = null;
    String d_TargetCountryId = null;
    boolean l_checkOwnedCountry;
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Vignesh");
        d_Player2 = new Player("Sujith");
        d_Map = new WargameMap("test2.map");
        d_SourceCountryId = "germany";
        d_TargetCountryId = "india";
        d_Rge = new EngineCommand();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = GamePhase.ISSUEORDER;
        l_checkOwnedCountry = true;
    }

    /**
     * Player 1 deploys specific army units
     * Player 2 bombs the territory where the armies wee deployed by Player 1
     */
    @Test
    public void testNotNeighbour() {
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("test2.map");
        d_Stup.assignCountries(d_Map, d_Players);

        //check if sluci is of player2
        boolean l_checkOwnedCountryCurrent = d_Player2.getOwnedCountries().containsKey(d_SourceCountryId.toLowerCase());
        //Check if tungu is of player 2
        boolean l_checkOwnedCountryNotOfCurrent = !d_Player2.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase());
        boolean targetCountryNeighbour = false;
        //checks if target country id is neighbour to one of current player's country
        for (Country cD : d_Player2.getOwnedCountries().values()) {
            System.out.println(cD.getCountryName());
            System.out.println(cD.getNeighbours().containsKey(d_TargetCountryId));
            if (cD.getNeighbours().containsKey(d_TargetCountryId.toLowerCase()) && l_checkOwnedCountryNotOfCurrent) {
                targetCountryNeighbour = true;
                break;
            }
        }
        //if Tungu was neighbour of Sluci
        //Negative test should pass as not a neighbour
        assertFalse(targetCountryNeighbour);
    }
}

