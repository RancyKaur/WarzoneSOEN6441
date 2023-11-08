package Model;

import Controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Test Class for confirming functionality of the advance order when the other country is owned by the player
 *
 */
public class TestAdvanceOnly{
    Order d_DOrder;
    Queue<Order> d_OrderList;
    Player d_Player1, d_Player2, d_TargetPlayer;
    WargameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    GamePhase d_GamePhase;
    GameEngine d_Ge;
    EngineCommand d_Rge;
    String d_SourceCountryId = null;
    String d_TargetCountryId = null;
    boolean l_checkOwnedCountry;
    int d_NumberOfArmies = 4;
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("p1");
        d_Player2 = new Player("p2");
        d_Map = new WargameMap("test2.map");
        d_SourceCountryId = "pak";
        d_TargetCountryId = "iran";
        d_Rge = new EngineCommand();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player1);
        d_GamePhase = GamePhase.ISSUEORDER;
        l_checkOwnedCountry = true;
        d_DOrder = new Advance(d_Player1,d_SourceCountryId,d_TargetCountryId,d_NumberOfArmies,d_TargetPlayer);
        d_OrderList = new ArrayDeque<>();
    }

    /**
     * test the effect of advance order
     */
    @Test
    public void testAdvanceEffect() {
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("test2.map");
        d_Stup.assignCountries(d_Map, d_Players);
        for(Player tmp: d_Players) {
            if(tmp.getOwnedCountries().containsKey(d_TargetCountryId)) {
                d_TargetPlayer= tmp;
                break;
            }
        }
        ReinforcePlayers.assignReinforcementArmies(d_Player1);

        //performed checks for owned country and allowed army units.
        boolean l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_SourceCountryId);
        Country attackingCountry = d_Player1.getOwnedCountries().get(d_SourceCountryId.toLowerCase());
        Country defendingCountry = attackingCountry.getNeighbours().get(d_TargetCountryId.toLowerCase());
        attackingCountry.setNumberOfArmies(8);
        defendingCountry.setNumberOfArmies(1);

        boolean l_checkNeighbourCountry = (d_TargetCountryId.equals(defendingCountry.getCountryName()));
        boolean l_checkExistingArmies= attackingCountry.getNumberOfArmies()>= d_NumberOfArmies;
        if(l_checkOwnedCountry && l_checkNeighbourCountry && l_checkExistingArmies){
            d_Player1.addOrder(new Advance(d_Player1,d_SourceCountryId,d_TargetCountryId, d_NumberOfArmies,d_TargetPlayer ));
            d_Player1.issue_order();
        }
        else{
            System.out.println("Country not owned by player or sufficient army not present | please pass to next player");
        }
        System.out.println(d_Player1.getD_orderList());
        Order l_toRemove = d_Player1.next_order();
        System.out.println("Order: " +l_toRemove+ " executed for player: "+d_Player1.getPlayerName());
        System.out.println(d_Player1.getD_orderList());
        l_toRemove.execute();

        //Check if armies advance correctly
        System.out.println(d_SourceCountryId + " and "+ d_TargetCountryId + " are neighbour to each other \n"
                + d_TargetCountryId + " is owned by "+ d_Player1 );
        System.out.println("Armies are transfer from "+ d_SourceCountryId + " to "+ d_TargetCountryId);
        assertEquals(4 ,attackingCountry.getNumberOfArmies()); //8-4= 4
        assertEquals(5, defendingCountry.getNumberOfArmies()); //1+4= 5
    }

}

