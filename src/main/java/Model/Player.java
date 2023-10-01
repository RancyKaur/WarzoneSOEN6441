package Model;


import java.util.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * This class creates a Player and assigns attributes to the player.
 *
 * @author Aarthi
 */
public class Player {

    private String d_PlayerName;
    private HashMap<String, Continent> d_OwnedContinents;
    private HashMap<String, Country> d_OwnedCountries;
    private int d_OwnedArmies;
    private int d_ReinforcementArmies;
    private String d_CountryId;
    private ExecuteOrders d_Order;
    private Queue<ExecuteOrders> d_OrderList;

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
        d_OrderList = new ArrayDeque<ExecuteOrders>();
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
        for (ExecuteOrders l_x : d_OrderList) {
            System.out.println(l_x.getD_player().getPlayerName());
        }
    }

    /**
     * getter for order queue
     *
     * @return d_OrderList
     */
    public Queue<ExecuteOrders> getD_orderList() {
        return d_OrderList;
    }

    /**
     * This function sets the created Object to Players Object
     *
     * @param p_order created Order
     */
    public void addOrder(ExecuteOrders p_order) {
        this.d_Order = p_order;
    }

    /**
     * This function takes the first Order in the List and calls execute() on it.
     *
     * @return first Order in the List.
     */
    public ExecuteOrders next_order() {
        return d_OrderList.poll();
    }

}
