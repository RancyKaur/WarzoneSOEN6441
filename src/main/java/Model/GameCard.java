package Model;
import java.util.*;

/**
 * This class is the entity class that represents "Card" that needs to be assigned to players once they conquer any country
 */
public class GameCard {

    /**
     * d_CardName is the name of the card that is assigned to player
     */
    String d_CardName;
    /**
     * d_ListOfCards contains the type of the cards that can be assigned to the player
     */
    String[] d_ListOfCards = {"Airlift","Blockade", "Bomb","Diplomacy"};

    /**
     * Default Constructor of GameCard
     */
    public GameCard(){}

    /**
     * This constructor is created to assist with the test cases
     * @param p_CardName
     */
    public GameCard(String p_CardName)
    {
        this.d_CardName = p_CardName;
    }

    /**
     * This method is the main method that helps in choosing random card to be assigned
     * @return card name
     */
    public String generateRandomCard()
    {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(d_ListOfCards.length);
        return d_ListOfCards[index];
    }

    /**
     * This method is calls generateRandomCard to create a random card to be assigned to the player
     */
    public void createGameCard()
    {
        d_CardName = generateRandomCard();
    }

    /**
     * This method is helper method for the test cases to test creating card
     * @param p_cardName
     */
    public void createGameCard(String p_cardName)
    {
        d_CardName = p_cardName;
    }

    /**
     * Getter method to return card name
     * @return card's name
     */
    public String getCardName()
    {
        return this.d_CardName;
    }
}
