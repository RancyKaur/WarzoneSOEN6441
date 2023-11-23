package Model;

import Controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import static org.junit.Assert.*;


/**
 * Tests Random Strategy for non-human player
 */
public class TestRandomStrategy{
    Order d_DOrder;
    Queue<Order> d_OrderList;
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
    int d_NumberOfArmies = 3;
    GameCard d_Card;
    String[] d_CardsList;


    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Wonder");
        d_Player2 = new Player("Amaze");
        //d_Map = new GameMap("dummy.map");
        d_Ge = new GameEngine();
        d_Rge = new EngineCommand();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("demo.map");
        d_SourceCountryId = "china";
        d_TargetCountryId = "china";
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = GamePhase.ISSUEORDER;
        l_checkOwnedCountry = true;
        d_DOrder = new Deploy(d_Player1,d_SourceCountryId,d_NumberOfArmies);
        d_OrderList = new ArrayDeque<>();
        d_Card = new GameCard();
        d_CardsList = new String[]{"Bomb", "Airlift", "Blockade", "Diplomacy"};
    }

    /**
     * Tests Random Order
     */
    @Test
    public void testRandomStrat() {
        d_Stup.assignCountries(d_Map, d_Players);
        ReinforcePlayers.assignReinforcementArmies(d_Player1);
        ReinforcePlayers.assignReinforcementArmies(d_Player2);

        d_Player1.setStrategy(new RandomStrategy(d_Player1, d_Map));
        d_Player1.set_isHuman(false);
        d_Player1.addCard("Bomb");
        d_Player1.addCard("Airlift");
        d_Player1.addCard("Blockade");
        d_Player1.addCard("Diplomacy");


        boolean ord = false;
        ord = d_Player1.issueAutoOrder();
        System.out.println(ord);
        if(!ord){
            //Case when deploy Order
            assertFalse(ord);
            assertEquals(0, d_Player1.getD_orderList().size());
        }else{
            assertTrue(ord);
            assertEquals(1, d_Player1.getD_orderList().size());
        }
    }

    /**
     * Tests Random Country Owner
     */
    @Test
    public void testRandomCountryPlayerOwner() {
        d_Stup.assignCountries(d_Map, d_Players);
        ReinforcePlayers.assignReinforcementArmies(d_Player1);
        ReinforcePlayers.assignReinforcementArmies(d_Player2);

        Random rand = new Random();
        Object[] values = d_Map.getCountries().values().toArray();
        Object randomValue = values[rand.nextInt(values.length)];
        Country d_RandomCountry = (Country) randomValue;
        System.out.println(d_RandomCountry.getCountryName());

        Player l_targetPlayer = null;
        for (Player temp : d_Players) {
            //check which player has target countryID
            if (temp.getOwnedCountries().containsKey(d_RandomCountry.getCountryName().toLowerCase())) {
                l_targetPlayer = temp;
                break;
            }
        }
        System.out.println(d_RandomCountry.getcountryOwnerPlayer().getPlayerName());
        assertEquals(l_targetPlayer, d_RandomCountry.getcountryOwnerPlayer());

    }

    /**
     * Tests Random Country with Armies deployed
     */
    @Test
    public void testCountryWithArmies() {
        d_Stup.assignCountries(d_Map, d_Players);
        d_Stup.showMap(d_Players,d_Map);
        ReinforcePlayers.assignReinforcementArmies(d_Player1);
        ReinforcePlayers.assignReinforcementArmies(d_Player2);

        String d_CountryId = "germany";
        d_DOrder = new Deploy(d_Player1,d_CountryId,d_NumberOfArmies);
        //performed checks for owned country and allowed army units.
        boolean l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_CountryId);
        boolean l_checkArmies = (d_Player1.getOwnedArmies() >= d_NumberOfArmies);

        if(l_checkOwnedCountry && l_checkArmies){
            d_Player1.addOrder(d_DOrder);
            d_Player1.issue_order();
            d_Player1.setOwnedArmies(d_Player1.getOwnedArmies()-d_NumberOfArmies);
            System.out.println("After Deploy reinforcement Pool:"+d_Player1.getOwnedArmies());
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
        //Check if num of armies deployed correctly
        assertEquals(d_NumberOfArmies ,l_c.getNumberOfArmies());


        Country d_RandomCountryWithArmies = null;
        int l_maxArmies = 0, l_numArmies;
        for (Country l_countryDetails : d_Player1.getOwnedCountries().values()) {
            l_numArmies = l_countryDetails.getNumberOfArmies();
            System.out.println(l_countryDetails.getCountryName());
            if (l_numArmies > l_maxArmies) {
                System.out.println(l_countryDetails.getNumberOfArmies());
                System.out.println(l_countryDetails.getCountryName());
                d_RandomCountryWithArmies = l_countryDetails;
                break;
            }
        }
        assertEquals("germany", d_RandomCountryWithArmies.getCountryName().toLowerCase());
    }


    /**
     * Tests Random Country which is unbound to random Generation
     */
    @Test
    public void testRandomCountry() {
        d_Stup.assignCountries(d_Map, d_Players);
        ReinforcePlayers.assignReinforcementArmies(d_Player1);
        ReinforcePlayers.assignReinforcementArmies(d_Player2);

        Random rand = new Random();
        Object[] values = d_Player1.getOwnedCountries().values().toArray();
        int totalC = values.length - 1;
        Object randomValue = values[rand.nextInt(totalC + 1)];
        Country d_RandomCountry = (Country) randomValue;
        System.out.println(d_RandomCountry);

        assertNotEquals(null,d_RandomCountry);
    }

}

