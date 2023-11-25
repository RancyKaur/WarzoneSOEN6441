package Model;

import Controller.GameEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a valid saved game loads or not
 */
public class TestLoadGame {
    /**
     * Represents the state of the game
     */
    GameState d_Game;

    /**
     * To help load the map.
     */
    EngineCommand d_RunG;

    /**
     * Refers to controller
     */
    GameEngine d_Ge;

    /**
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        d_RunG = new EngineCommand();
        d_Game = new GameState();
        d_Ge = new GameEngine();
    }

    /**
     * Tests if valid game phase is maintained or not.
     */
    @Test
    public void testLoadGame(){
        d_Ge.parseCommand(null, "loadgame testabc");

        Assert.assertEquals(GamePhase.ISSUEORDER, d_Ge.getGame().getGamePhase());
    }
    /**
     * Tests if valid game phase is maintained or not.
     */
    @Test
    public void testLoadGame3(){
        d_Ge.parseCommand(null, "loadgame testabc");
        Assert.assertEquals("ronak", d_Ge.getGame().getActivePlayer().getPlayerName().toLowerCase());
    }

}
