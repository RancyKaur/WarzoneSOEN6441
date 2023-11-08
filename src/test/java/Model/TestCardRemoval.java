package Model;

import Model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Class to validate removal of card assigned to the player
 */
public class TestCardRemoval {
    Player d_player;
    String d_Card;

    /**
     * Creating the player object
     */
    @Before
    public void before(){
        d_player = new Player("Vignesh");
        d_Card="Diplomacy";
    }

    /**
     * To check the card exist and is removed or not
     */
    @Test
    public void TestCard(){
        d_player.addCard("Airlift");
        d_player.addCard("Bomb");
        d_player.addCard("Diplomacy");
        Boolean l_cardExist=d_player.checkCardExists(d_Card);
        assertEquals(true,l_cardExist);
        d_player.showCards();
        d_player.removeCard("Diplomacy");
        System.out.println("After Removing Diplomacy card from the Deck");
        d_player.showCards();
    }
}

