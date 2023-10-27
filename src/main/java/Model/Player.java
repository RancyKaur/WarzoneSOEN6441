package Model;


import java.util.*;


import java.util.HashMap;


/**
 * This class creates a Player and assigns attributes to the player.
 *
 * @author Sujith
 */
public class Player {

    private String d_PlayerName;
    private HashMap<String, Continent> d_OwnedContinents;
    private HashMap<String, Country> d_OwnedCountries;
    private int d_OwnedArmies;
    private Order d_Order;
    private Queue<Order> d_OrderList;
    ArrayList<GameCard> d_CardDeck;

    /**
     * This constructor assigns name to the player.
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
    }

    public void printOwnedCountries() {
        System.out.println("player's countries:" + this.d_OwnedCountries);
    }

    public void printOwnedContinents() {
        System.out.println("player's continents:" + this.d_OwnedContinents);
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
     * @return d_playerName name of the player
     */
    public String getPlayerName() {
        return d_PlayerName;
    }

    /**
     * This method assigns countries to the player as a HashMap.
     *
     * @param p_countries Countries owned by player
     */
    public void setOwnedCountries(HashMap<String, Country> p_countries) {
        d_OwnedCountries = p_countries;
    }

    /**
     * Getter method to return to countries owned by a player.
     *
     * @return d_ownedCountries
     */
    public HashMap<String, Country> getOwnedCountries() {
        return d_OwnedCountries;
    }

    /**
     * This method assigns continents to the player as a HashMap.
     *
     * @param p_continents Countries owned by player
     */
    public void setOwnedContinents(HashMap<String, Continent> p_continents) {
        d_OwnedContinents = p_continents;
    }

    /**
     * Getter method to return to Continents owned by a player.
     *
     * @return d_ownedContinents
     */
    public HashMap<String, Continent> getOwnedContinents() {
        return d_OwnedContinents;
    }

    /**
     * Getter method to return the initial armies.
     *
     * @return d_ownedArmies
     */
    public int getOwnedArmies() {
        return d_OwnedArmies;
    }

    /**
     * Setter method to set the initial armies
     *
     * @param p_ownedArmies number of armies owned by players
     */
    public void setOwnedArmies(int p_ownedArmies) {
        this.d_OwnedArmies = p_ownedArmies;
    }

    /**
     * This function adds Order object to the list of Orders.
     * It has no parameters.
     */
    public void issue_order() {
        this.d_OrderList.add(this.d_Order);
    }

    /**
     * getter for order queue
     *
     * @return d_OrderList
     */
    public Queue<Order> getD_orderList() {
        return d_OrderList;
    }

    /**
     * This function sets the created Object to Players Object
     *
     * @param p_order created Order
     */
    public void addOrder(Order p_order) {
        this.d_Order = p_order;
    }

    /**
     * This function takes the first Order in the List and calls execute() on it.
     *
     * @return first Order in the List.
     */
    public Order next_order() {
        return d_OrderList.poll();
    }


    /**
     * Method to assign random card to player once he has won any territory
     */
    public void addCard() {
        GameCard l_card = new GameCard();
        l_card.createGameCard();
        d_CardDeck.add(l_card);
    }

    /**
     * Helper method to test addCard method
     * @param test card
     */
    public void addCard(String test){
        GameCard l_card = new GameCard();
        l_card.createGameCard(test);
        d_CardDeck.add(l_card);
    }

    /**
     * Method to remove card, this would be called when player uses their card
     * @param p_cardName
     */
    public void removeCard(String p_cardName)
    {
        //remove card from deck
        Iterator l_Carditer = d_CardDeck.iterator();
        while (l_Carditer.hasNext()) {
            GameCard l_card = (GameCard) l_Carditer.next();
            if (l_card.getCardName().equals(p_cardName)) {
                d_CardDeck.remove(l_card);
                break;
            }
        }
    }

    /**
     * This method helps to check if player has the specified card
     * It is called before the player starts playing any card
     * @param p_card
     * @return true if player has card in the deck otherwise false
     */
    public boolean checkCardExists(String p_card) {

        int l_cardCount = 0;
        Iterator l_iter = d_CardDeck.iterator();
        while (l_iter.hasNext()) {
            GameCard l_card = (GameCard) l_iter.next();
            if (l_card.getCardName() == p_card) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to display deck of cards that player owns
     */
    public void showCards()
    {
        Iterator l_iter = d_CardDeck.iterator();
        System.out.println("Player owns following cards:");
        while (l_iter.hasNext()) {
            GameCard l_card = (GameCard) l_iter.next();
            System.out.println(l_card.getCardName());
        }
    }

    /**
     * Method to return list of cards that player currently owns
     * @return cards' list
     */
    public ArrayList<GameCard> get_CardDeck() {
        return d_CardDeck;
    }
}
