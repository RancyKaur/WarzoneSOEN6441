package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test Class to checks wheteher card is getting added or not.
 */
public class TestCard {
    ArrayList<GameCard> d_Listofcards;
    GameCard d_Card;
    String d_CardType;
    /**
     * Initializes the array and create the custom card Bomb.
     */
    @Before
    public void before(){
        d_Listofcards = new ArrayList<>();
        d_CardType="Bomb";
        d_Card=new GameCard(d_CardType);
    }
    /**
     * Test to check whether the card is getting added to the deck.
     */
    @Test
    public void TestCard(){
        d_Listofcards.add(d_Card);
        assertEquals(d_Listofcards.get(0).getCardName(),d_CardType);
    }
}

