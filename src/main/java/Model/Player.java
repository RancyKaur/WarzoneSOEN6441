package Model;

import java.util.*;
import java.util.HashMap;

/**
 * Class representing a player in a game. Players have a name, own countries, continents, armies, orders, cards, and can negotiate with other players.
 */

public class Player {
    private String d_PlayerName;
    private HashMap<String, Continent> d_OwnedContinents;
    private HashMap<String, Country> d_OwnedCountries;
    private int d_OwnedArmies;
    private Order d_Order;
    private Queue<Order> d_OrderList;
    ArrayList<GameCard> d_CardDeck;
    public List<Player> d_NegotiateList;



    /**
     * This constructor assigns a name to the player.
     *
     * @param p_playerName name of the player
     */
    public Player(String p_playerName) {
        d_PlayerName = p_playerName;
        d_OwnedContinents = new HashMap<>();
        d_OwnedCountries = new HashMap<>();
        this.d_OwnedArmies = 0;
        d_OrderList = new ArrayDeque<Order>();
        d_CardDeck = new ArrayList<>();
        d_NegotiateList = new ArrayList<>(); // Initialize the negotiation list
    }

    /**
     * this is the getter method for getting the order.
     * @return d_Order
     */
    public Order getD_Order() {
        return d_Order;
    }

    public void printOwnedCountries() {
        System.out.println("Player's countries:" + this.d_OwnedCountries);
    }

    public void printOwnedContinents() {
        System.out.println("Player's continents:" + this.d_OwnedContinents);
    }

    /**
     * This method sets the name of the player.
     *
     * @param p_playerName name of the player
     */
    public void setPlayerName(String p_playerName) {
        d_PlayerName = p_playerName;
    }

    /**
     * Getter method to return the player name.
     *
     * @return d_PlayerName name of the player
     */
    public String getPlayerName() {
        return d_PlayerName;
    }

    /**
     * This method assigns countries to the player as a HashMap.
     *
     * @param p_countries Countries owned by the player
     */
    public void setOwnedCountries(HashMap<String, Country> p_countries) {
        d_OwnedCountries = p_countries;
    }

    /**
     * Getter method to return the countries owned by a player.
     *
     * @return d_OwnedCountries
     */
    public HashMap<String, Country> getOwnedCountries() {
        return d_OwnedCountries;
    }

    /**
     * This method assigns continents to the player as a HashMap.
     *
     * @param p_continents Continents owned by the player
     */
    public void setOwnedContinents(HashMap<String, Continent> p_continents) {
        d_OwnedContinents = p_continents;
    }

    /**
     * Getter method to return the continents owned by a player.
     *
     * @return d_OwnedContinents
     */
    public HashMap<String, Continent> getOwnedContinents() {
        return d_OwnedContinents;
    }

    /**
     * Getter method to return the initial armies.
     *
     * @return d_OwnedArmies
     */
    public int getOwnedArmies() {
        return d_OwnedArmies;
    }

    /**
     * Setter method to set the initial armies.
     *
     * @param p_ownedArmies number of armies owned by the player
     */
    public void setOwnedArmies(int p_ownedArmies) {
        this.d_OwnedArmies = p_ownedArmies;
    }

    /**
     * This function adds an Order object to the list of Orders.
     */
    public void issue_order() {
        this.d_OrderList.add(this.d_Order);
    }

    /**
     * Getter for the order queue.
     *
     * @return d_OrderList
     */
    public Queue<Order> getD_orderList() {
        return d_OrderList;
    }

    /**
     * This function sets the created Order object for the player.
     *
     * @param p_order created Order
     */
    public void addOrder(Order p_order) {
        this.d_Order = p_order;
    }

    /**
     * This function executes the first Order in the list.
     *
     * @return first Order in the list.
     */
    public Order next_order() {
        return d_OrderList.poll();
    }

    // Add a method to get the negotiateList
    public List<Player> getNegotiateList() {
        return d_NegotiateList;
    }

    // Add a method to add players to the negotiateList
    public void addNegotiatePlayer(Player player) {
        d_NegotiateList.add(player);
    }

    // Add a method to remove players from the negotiateList
    public void removeNegotiatePlayer(Player player) {
        d_NegotiateList.remove(player);
    }

    public void removeAllNegotiators() {
        d_NegotiateList.clear();
    }
    /**
     * Method to assign a random card to the player once they have won any territory.
     */
    public void addCard() {
        GameCard card = new GameCard();
        card.createGameCard();
        d_CardDeck.add(card);
    }

    /**
     * Helper method to test the addCard method.
     *
     * @param testCard
     */
    public void addCard(String testCard) {
        GameCard card = new GameCard();
        card.createGameCard(testCard);
        d_CardDeck.add(card);
    }

    /**
     * Method to remove a card. This would be called when a player uses their card.
     *
     * @param cardName
     */
    public void removeCard(String cardName) {
        // Remove the card from the deck
        Iterator<GameCard> cardIterator = d_CardDeck.iterator();
        while (cardIterator.hasNext()) {
            GameCard card = cardIterator.next();
            if (card.getCardName().equals(cardName)) {
                cardIterator.remove();
                break;
            }
        }
    }

    /**
     * This method helps to check if the player has the specified card.
     * It is called before the player starts playing any card.
     *
     * @param card
     * @return true if the player has the card in the deck; otherwise, false
     */
    public boolean checkCardExists(String card) {
        for (GameCard gameCard : d_CardDeck) {
            if (gameCard.getCardName().equals(card)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to display the deck of cards that the player owns.
     */
    public void showCards() {
        System.out.println("Player owns the following cards:");
        for (GameCard card : d_CardDeck) {
            System.out.println(card.getCardName());
        }
    }

    /**
     * Method to return the list of cards that the player currently owns.
     *
     * @return list of cards
     */
    public ArrayList<GameCard> getCardDeck() {
        return d_CardDeck;
    }
}
