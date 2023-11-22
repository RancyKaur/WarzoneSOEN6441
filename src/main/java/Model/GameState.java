package Model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class holds the data required to maintain the state of the game.
 * This is implemented for helping with saving and loading game state
 */

public class GameState extends Observable implements Serializable {

    /**
     * Stores the map being edited or the map being played on depending on the user's choice.
     */
    private WargameMap d_Map;

    /**
     * Represents the type of the map.
     */
    private String d_MapType;

    /**
     * Stores the current phase of the game.
     */
    private GamePhase d_GamePhase;

    /**
     * Stores the list of players playing the game.
     */
    private ArrayList<Player> d_Players;

    /**
     * Stores currently active player.
     */
    private Player d_ActivePlayer;

    /**
     * Stores the deck of cards.
     */
    private GameCard d_Card;

    /**
     * Represents number of cards dealt.
     */
    private int d_CardsDealt;

    /**
     * Logs the information related to the game.
     */

    private Phase d_Phase;

    /**
     *Stores the current phase name
     */
    private String d_PhaseName;

    /**
     * Get the current phase of the game.
     * @return returns the current phase of the game.
     */
    public Phase get_Phase() {
        return d_Phase;
    }
    /**
     * sets current phase of the game
     * @param d_Phase phase of the game.
     */
    public void set_Phase(Phase d_Phase) {
        this.d_Phase = d_Phase;
    }

    /**
     * Constructor to initialize the game data.
     */
    public GameState(){
        d_Map = new WargameMap();
        d_GamePhase = GamePhase.STARTPLAY;
        d_Players = new ArrayList<Player>();
        d_ActivePlayer = null;
        d_Card = new GameCard();
        d_CardsDealt = 0;


    }

    /**
     * Constructor to initialize the game data.
     * @param p_map Represents the state of the game.
     * @param p_mapType Domination/Conquest type map
     * @param p_gamePhase Phase the game is currently in
     * @param p_players List of players involved in the game
     * @param p_phase determine the phase of game
     * @param p_activePlayer Player who's turn is going on
     * @param p_card Card of cards.
     * @param p_phaseName its saves the name of the phase
     */
    public GameState(WargameMap p_map, String p_mapType, GamePhase p_gamePhase,Phase p_phase, ArrayList<Player> p_players, Player p_activePlayer, GameCard p_card,String p_phaseName){
        this.d_Map = p_map;
        this.d_MapType = p_mapType;
        this.d_GamePhase = p_gamePhase;
        this.d_Phase=p_phase;
        this.d_Players = p_players;
        this.d_ActivePlayer = p_activePlayer;
        this.d_Card = p_card;
        this.d_PhaseName = p_phaseName;
        // this.d_CardsDealt = p_cardsDealt;
    }

    /**
     * Get the map being edited or played on.
     * @return returns the map being edited or played on.
     */
    public WargameMap getMap() {
        return this.d_Map;
    }

    /**
     * Set the game map with the input argument.
     * @param p_map map to set as the game map.
     */
    public void setMap(WargameMap p_map) {
        this.d_Map = p_map;
    }

    /**
     * Gets the type of the map
     * @return Type of the map, i.e. 'Conquest' format map or 'Domination' format map
     */
    public String getMapType() {
        return d_MapType;
    }

    /**
     * Sets the type of the map
     * @param p_mapType Type of the map, i.e. 'Conquest' format map or 'Domination' format map
     */
    public void setMapType(String p_mapType) {
        this.d_MapType = p_mapType;
    }

    /**
     * Get the current phase of the game.
     * @return returns the current phase of the game.
     */
    public GamePhase getGamePhase() {
        return this.d_GamePhase;
    }

    /**
     * Set the phase of the game with the input argument.
     * @param p_gamePhase new phase of the game.
     */
    public void setGamePhase(GamePhase p_gamePhase) {

        this.d_GamePhase = p_gamePhase;
        if(this.d_GamePhase==GamePhase.ASSIGN_REINFORCEMENTS){
            System.out.println(this.d_ActivePlayer.getPlayerName() + "'s reinforcement phase");
            inform(this);
        } else if (this.d_GamePhase == GamePhase.ISSUEORDER) {
            System.out.println(this.d_ActivePlayer.getPlayerName() + "'s Issue order phase");
            inform(this);
        }
    }

    /**
     * Get the list of players.
     * @return returns the list of players.
     */
    public ArrayList<Player> getPlayers() {
        return this.d_Players;
    }

    /**
     * Set the list of players.
     * @param p_players list of players to play the game.
     */
    public void setPlayers(ArrayList<Player> p_players) {
        this.d_Players = p_players;
    }

    /**
     * Get currently active player.
     * @return returns currently active player.
     */
    public Player getActivePlayer(){
        return this.d_ActivePlayer;
    }

    /**
     * Set the currently active player.
     * @param p_player currently active player.
     */
    public void setActivePlayer(Player p_player){
        this.d_ActivePlayer = p_player;
        if(p_player!=null){
            inform(this);
        }
    }

    /**
     * This function is a setter function for phasename
     * @param p_phaseName is a sting for phasename in which game is running.
     */
    public void set_PhaseName(String p_phaseName)
    {
        this.d_PhaseName = p_phaseName;
    }

    /**
     * This function is a getter function for phaseName
     * @return d_PhaseName
     */
    public String get_PhaseName() {
        return d_PhaseName;
    }

    /**
     * Gets the deck of card.
     * @return Returns the deck of card.
     */
    public GameCard getDeck() {
        return d_Card;
    }

    /**
     * Sets the deck of card.
     * @param p_card Card of card for the game.
     */
    public void setDeck(GameCard p_card) {
        this.d_Card = p_card;
    }

    /**
     * set the trade in phase of card exchange
     * @return trade in phase number
     */
    public int getCardsDealt() {
        return d_CardsDealt;
    }

    /**
     * set the phase for card trade in phase
     * @param p_cardsDealt trade in phase number
     */
    public void setCardsDealt(int  p_cardsDealt) {
        this.d_CardsDealt = p_cardsDealt;
    }

    /**
     * Remove player
     * @param p_p PLayer to be removed.
     */
    public void removePlayer(Player p_p){
        this.d_Players.remove(p_p);
    }


    /**
     * Resets the game data
     */
    public void resetGameData(){
        d_Map = new WargameMap();
        d_GamePhase = GamePhase.STARTPLAY;
        d_Players = new ArrayList<Player>();
        d_ActivePlayer = null;
        d_Card = new GameCard();
        d_CardsDealt = 0;
    }
}
