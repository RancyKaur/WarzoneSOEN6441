package Model;

import java.io.Serializable;
import java.util.Random;

/**
 * Class handles implementation related to cards.
 */
public class Card implements Serializable {

    /**
     * Represents type of Card from Bomb, Airlift, Blockade and Diplomacy
     */
    private String d_CardType;

    /**
     * This list contains the cards issued to players
     */
    private static final String[] d_ListOfCards = { "Bomb", "Airlift", "Blockade", "Diplomacy" };

    /**
     * Default constructor of Card to access the methods of this class.
     */
    public Card() {
    }

    /**
     * This constructor will assign type of cards
     * 
     * @param p_cardType Card Type that is assigned
     */
    public Card(String p_cardType) {
        this.d_CardType = p_cardType;
    }

    /**
     * The method gives type of card.
     * 
     * @return string card type
     */
    String getCardType() {
        return d_CardType;
    }

    /**
     * Stores the random card picked in the CardType String
     */
    public void createCard() {
        d_CardType = randomCard();
    }

    /**
     * Stores the temp card picked in the CardType String
     * 
     * @param temp specific card
     */
    public void createCard(String temp) {
        d_CardType = temp;
    }

    /**
     * Picks a random card from the Cards List using random generator
     * 
     * @return The card from the Cards List
     */
    public String randomCard() {
        Random randomCardGenerator = new Random();
        int idx = randomCardGenerator.nextInt(d_ListOfCards.length);
        return d_ListOfCards[idx];
    }

}
