package Controller;

import Model.StartUp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Model.GameState;



/**
 * Tests tournament related commands.
 */
public class TournamentModeTest {

    /**
     * Represents the state of the game
     */
    GameState d_Game;

    /**
     * Represents gameEngine object
     */
    GameEngine d_Ge;

    /**
     * To help load the map.
     */
    StartUp d_Stup;

    /**
     * Refers to d_Te
     */
    TournamentEngine d_Te;

    /**
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        d_Stup = new StartUp(d_Ge);
        d_Game = new GameState();
        d_Te = new TournamentEngine(d_Ge);
    }

    /**
     * Test if invalid tournament commands are detected or not.
     */
    @Test
    public void testTournamentCommand(){

        String errorMsg="Incorrect command format, the correct format is: 'tournament -M listofmapfiles{1-5} -P listofplayerstrategies{2-4} -G numberofgames{1-5} -D maxnumberofturns{10-50}";
        String message = d_Te.parse(null, "tournament -M -P player  -G 3 -D 5");

        Assert.assertEquals(errorMsg, message);

        message = d_Te.parse(null, "tournament -M dummy.map demo.map Aus.map world.map -P player -G 3 -D 5");
        Assert.assertEquals(errorMsg, message);

        message = d_Te.parse(null, "tournament -M dummy.map demo.map -P human -G 3 -D 15");
        Assert.assertEquals(errorMsg, message);

        message = d_Te.parse(null, "tournament -M dummy.map demo.map -P benevolent benevolent -G 3 -D 15");
        Assert.assertEquals(errorMsg, message);

        message = d_Te.parse(null, "tournament -M dummy.map demo.map -P benevolent random cheater aggressive random  -G 3 -D 15");
        Assert.assertEquals(errorMsg, message);

        message = d_Te.parse(null, "tournament -M dummy.map demo.map -P benevolent random  -G 3 -D 5");
        Assert.assertEquals(errorMsg, message);

        message = d_Te.parse(null, "tournament -M dummy.map demo.map -P benevolent random  -G 3 -D 55");
        Assert.assertEquals(errorMsg, message);
    }


}
