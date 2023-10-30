package Model;

import Controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class TestAirlift {
    Order d_DOrder;
    Order d_AOrder;
    Queue<Order> d_OrderList;
    Player d_Player1;
    Player d_Player2;
    WargameMap d_Map;
    ArrayList<Player> d_Players;
    GameStartPhase d_Stup;
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
        d_Player1 = new Player( "jungkook");
        d_Player2 = new Player("janu");
        d_Map = new WargameMap("alekmap.map");
        d_SourceCountryId = "pero";
        d_TargetCountryId = "egypt";
        d_Rge = new  EngineCommand();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = GamePhase.ISSUEORDER;
        l_checkOwnedCountry = true;
        d_DOrder = new Deploy(d_Player1,d_SourceCountryId,d_NumberOfArmies);
        d_AOrder = new Airlift(d_Player1,d_SourceCountryId,d_TargetCountryId, d_NumberOfArmies);
        d_OrderList = new ArrayDeque<>();
    }

    /**
     * Test limits of Player's orders, with checks for country owned and army units.
     * Sample reinforcements assigned for Player1 and tested with unassigned reinforcements for Player2
     */
    @Test
    public void testAirliftEffect() {
        d_Ge = new GameEngine();
        d_Stup = new GameStartPhase();
        d_Map = d_Rge.loadMap("alekmap.map");
        d_Stup.assignCountries(d_Map, d_Players);
        ReinforcePlayers.assignReinforcementArmies(d_Player1);

        //performed checks for owned country and allowed army units.
        boolean l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_SourceCountryId);
        boolean l_checkArmies = (d_Player1.getOwnedArmies() >= d_NumberOfArmies);
        //System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" : "+d_Player1.getOwnedCountries());
        //System.out.println("Countries assigned to "+d_Player2.getPlayerName()+" : "+d_Player2.getOwnedCountries());
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
        l_toRemove.execute();

        Country l_c= d_Player1.getOwnedCountries().get(d_SourceCountryId.toLowerCase());
        //Check if deployed
        System.out.println(l_c.getNumberOfArmies());
        boolean l_checkTargetOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase());
        int l_existingArmies = l_c.getNumberOfArmies();
        boolean l_checkArmiesD = (l_existingArmies >= d_NumberOfArmies);
        d_Player1.addCard("Airlift");
        d_Player1.showCards();
        boolean checkCard = d_Player1.checkCardExists("Airlift");
        if(l_checkOwnedCountry && l_checkTargetOwnedCountry && l_checkArmiesD && checkCard){
            d_Player1.addOrder(d_AOrder);
            d_Player1.issue_order();
            d_Player1.removeCard("Airlift");
        }

        Order l_toRemoveB = d_Player1.next_order();
        l_toRemoveB.execute();

        Country l_cB= d_Player1.getOwnedCountries().get(d_TargetCountryId.toLowerCase());
        //Check if airlifted to target
        System.out.println(l_cB.getNumberOfArmies());
        assertEquals(4 ,l_cB.getNumberOfArmies());
    }

}
