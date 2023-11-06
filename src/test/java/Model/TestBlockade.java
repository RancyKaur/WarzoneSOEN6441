package Model;

import Controller.GameEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
/**
 * Test blockade operation for a specific player in Game
 * @author Rahul
 */
public class TestBlockade{
    Order d_DOrder;
    Order d_BOrder;
    Queue<Order> d_OrderList;
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
    int d_NumberOfArmies = 3;
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Alekhya");
        d_Player2 = new Player("Jahnavi");
        d_Map = new WargameMap("world.map");
        d_CountryId = "india";
        d_Rge = new EngineCommand();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = GamePhase.ISSUEORDER;
        l_checkOwnedCountry = true;
        d_DOrder = new Deploy(d_Player1,d_CountryId,d_NumberOfArmies);
        d_BOrder = new Blockade(d_Player1,d_CountryId);
        d_OrderList = new ArrayDeque<>();
    }

    /**
     * Test limits of Player's orders, with checks for country owned and army units.
     * Sample reinforcements assigned for Player1 and tested with unassigned reinforcements for Player2
     */
    @Test
    public void testBlockadeEffect() {
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("world.map");
        d_Stup.assignCountries(d_Map, d_Players);
        ReinforcePlayers.assignReinforcementArmies(d_Player1);

        //performed checks for owned country and allowed army units.
        System.out.println(d_Player1.getOwnedArmies());
        boolean l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_CountryId);
        boolean l_checkArmies = (d_Player1.getOwnedArmies() >= d_NumberOfArmies);
        System.out.println(l_checkOwnedCountry+"   "+ l_checkArmies);
        if(l_checkOwnedCountry && l_checkArmies){
            d_Player1.addOrder(d_DOrder);
            d_Player1.issue_order();
            d_Player1.setOwnedArmies(d_Player1.getOwnedArmies()-d_NumberOfArmies);
            System.out.println("After :"+d_Player1.getOwnedArmies());
        }
        else{
            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
        }
        System.out.println(d_Player1.getD_orderList());
        Order l_toRemove = d_Player1.next_order();
        System.out.println("Order: " +l_toRemove+ " executed for player: "+d_Player1.getPlayerName());
        System.out.println(d_Player1.getD_orderList());
        l_toRemove.execute();

        Country l_c= d_Player1.getOwnedCountries().get(d_CountryId.toLowerCase());
        System.out.println(l_c.getNumberOfArmies());

        d_Player1.addCard("Blockade");
        d_Player1.showCards();
        boolean checkCard = d_Player1.checkCardExists("Blockade");
        if(l_checkOwnedCountry && checkCard){
            d_Player1.addOrder(d_BOrder);
            d_Player1.issue_order();
            d_Player1.removeCard("Blockade");
        }
        else{
            System.out.println("Country not owned by player or Card not owned | please pass to next player");
        }
        Order l_toRemoveB = d_Player1.next_order();
        l_toRemoveB.execute();

        d_Player1.getOwnedCountries().put(l_c.getCountryName().toLowerCase(),l_c);

        Country l_cB= d_Player1.getOwnedCountries().get(d_CountryId.toLowerCase());
        System.out.println(l_cB.getNumberOfArmies());

        assertEquals(9 ,l_cB.getNumberOfArmies());
    }

}
