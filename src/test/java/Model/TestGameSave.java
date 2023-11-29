package Model;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import Controller.GameEngine;
import static org.junit.Assert.assertTrue;

/**
 *  Test game save functionality
 *
 */
public class TestGameSave {


    EngineCommand d_RunGame;
    WargameMap d_Map;
    GameState d_Game;
    Player d_Player1, d_Player2;
    PlayStrategy d_Pg1, d_Pg2;
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_RunGame= new EngineCommand();
        d_Player1= new Player("wonder");
        d_Player2= new Player("amaze");
        d_Game= new GameState();
        d_Map=d_RunGame.loadMap("demo.map");
        d_Game.setMap(d_Map);
        d_Pg1 = new CheaterStrategy(d_Player1,d_Map);
        d_Game.getPlayers().add(d_Player1);
        d_Pg2 = new CheaterStrategy(d_Player2,d_Map);
        d_Game.getPlayers().add(d_Player2);

        d_Player1.getOwnedCountries().put("india", d_Map.getCountries().get("india"));
        d_Player1.getOwnedCountries().put("china", d_Map.getCountries().get("china"));

        d_Player1.getOwnedCountries().get("india").setcountryOwnerPlayer(d_Player1);
        d_Player1.getOwnedCountries().get("china").setcountryOwnerPlayer(d_Player1);

        d_Player2.getOwnedCountries().put("france",d_Map.getCountries().get("france"));
        d_Player2.getOwnedCountries().put("russia",d_Map.getCountries().get("russia"));
        d_Player2.getOwnedCountries().put("italy", d_Map.getCountries().get("italy"));

        d_Player2.getOwnedCountries().get("france").setcountryOwnerPlayer(d_Player2);
        d_Player2.getOwnedCountries().get("russia").setcountryOwnerPlayer(d_Player2);
        d_Player2.getOwnedCountries().get("italy").setcountryOwnerPlayer(d_Player2);

        d_Player1.getOwnedCountries().get("india").setNumberOfArmies(9);
        d_Player1.getOwnedCountries().get("china").setNumberOfArmies(8);

        d_Player2.getOwnedCountries().get("france").setNumberOfArmies(10);
        d_Player2.getOwnedCountries().get("russia").setNumberOfArmies(9);
        d_Player2.getOwnedCountries().get("italy").setNumberOfArmies(7);

        d_Player1.getOwnedCountries().get("india").getNeighbours().put("russia", d_Player2.getOwnedCountries().get("russia"));
        d_Player1.getOwnedCountries().get("china").getNeighbours().put("russia", d_Player2.getOwnedCountries().get("russia"));
        d_Player1.getOwnedCountries().get("china").getNeighbours().put("france", d_Player2.getOwnedCountries().get("france"));

        d_Player2.getOwnedCountries().get("france").getNeighbours().put("china", d_Player1.getOwnedCountries().get("china"));
        d_Player2.getOwnedCountries().get("russia").getNeighbours().put("india", d_Player1.getOwnedCountries().get("india"));
        d_Player2.getOwnedCountries().get("france").getNeighbours().put("russia", d_Player2.getOwnedCountries().get("italy"));
        d_Player2.getOwnedCountries().get("russia").getNeighbours().put("china", d_Player1.getOwnedCountries().get("china"));
        d_Player2.getOwnedCountries().get("italy").getNeighbours().put("france", d_Player2.getOwnedCountries().get("france"));


    }

    /**
     * Tests whether a game in specific state is saved or not
     */
    @Test
    public void testGameSave() {
        String d_Savegame="GameTestCaseSave";
        try { boolean l_save=d_RunGame.saveGame(this.d_Game,d_Savegame );
            if(l_save) {
                System.out.println("Current Game is saved ");
            }
            assertTrue(l_save);
        }catch(Exception e) {System.out.println("exception");}

    }}



