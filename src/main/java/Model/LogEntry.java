package Model;

import java.util.ArrayList;

/**
 * The LogEntry class stores log entries related to the game state.
 * This class extends the Observable class to notify observers when log entries are updated.
 */
public class LogEntry extends Observable {

    /**
     * Stores the map being edited or the map being played on depending on the
     * player's choice.
     */
    private Map d_Map;

    /**
     * Stores the current phase of the game.
     */
    private Phase d_GamePhase;

    /**
     * Stores the list of players playing the game.
     */
    private ArrayList<Player> d_Players;

    /**
     * Stores currently active player.
     */
    private Player d_ActivePlayer;

    /**
     * Stores the current Command
     */
    String d_Command;

    /**
     * Stores the current phase value
     */
    String d_PhaseValue;

    /**
     * Message on effect of a command
     */
    String d_Message;

    /**
     * Constructor to initialize the LogEntry.
     */
    public LogEntry() {
        d_Map = new Map();
        d_Players = new ArrayList<Player>();
        d_ActivePlayer = null;
        d_GamePhase = null;
        d_PhaseValue = "";
        d_Command = "";
        d_Message = "";
    }

    /**
     * Get the map being edited or played on.
     * 
     * @return returns the map being edited or played on.
     */
    public Map getMap() {
        return this.d_Map;
    }

    /**
     * Set the game map with the input argument.
     * 
     * @param p_map map to set as the game map.
     */
    public void setMap(Map p_map) {
        this.d_Map = p_map;
    }

    /**
     * Get the current phase of the game.
     * 
     * @return returns the current phase of the game.
     */
    public Phase getGamePhase() {
        return this.d_GamePhase;
    }

    /**
     * Sets the current Phase as a String
     * 
     * @param p_phaseValue sets the current Phase as a string
     */
    public void setPhaseValue(String p_phaseValue) {
        d_PhaseValue = p_phaseValue;

    }

    /**
     * Gets the current Phase Value as a string
     * 
     * @return return phase string value
     */
    public String getPhaseValue() {
        return d_PhaseValue;
    }

    boolean d_IsGamePhaseSet = false;
    boolean d_IsCommandSet = false;
    boolean d_IsMessageSet = false;

    /**
     * Set the phase of the game with the input argument.
     * 
     * @param p_gamePhase new phase of the game.
     */
    public void setGamePhase(Phase p_gamePhase) {

        if (d_GamePhase != null && this.d_GamePhase.getD_PhaseName() == p_gamePhase.getD_PhaseName()) {
            return;
        } else {
            d_GamePhase = p_gamePhase;
        }

        setPhaseValue("In " + d_GamePhase.getD_PhaseName() + " Phase:");
        d_IsGamePhaseSet = true;
        notifyObservers(this);
    }

    /**
     * Get the phase of game is set or not
     * 
     * @return return phase of game is set or not
     */
    public boolean getGamePhaseSet() {
        return d_IsGamePhaseSet;
    }

    /**
     * check the Phase of game and set the boolean value
     * 
     * @param p_isGamePhaseSet boolean value to set the IsGamePhaseSet
     */
    public void setGamePhaseSet(boolean p_isGamePhaseSet) {
        d_IsGamePhaseSet = p_isGamePhaseSet;
    }

    /**
     * Get the command of game is set or not
     * 
     * @return return command of game is set or not
     */
    public boolean getCommandSet() {
        return d_IsCommandSet;
    }

    /**
     * check the command of game and set the boolean value
     * 
     * @param p_isCommandSet boolean value to set the IsCommandSet
     */
    public void setCommandSet(boolean p_isCommandSet) {
        d_IsCommandSet = p_isCommandSet;
    }

    /**
     * Get the message of game is set or not
     * 
     * @return return message of game is set or not
     */
    public boolean getMessageSet() {
        return d_IsMessageSet;
    }

    /**
     * check the message of game and set the boolean value
     * 
     * @param p_isMessageSet boolean value to set the IsMessageSet
     */
    public void setMessageSet(boolean p_isMessageSet) {
        d_IsMessageSet = p_isMessageSet;
    }

    /**
     * Sets the command with the given input argument
     * 
     * @param p_command string to the set the command
     */
    public void setCommand(String p_command) {
        d_Command = p_command;
        d_IsCommandSet = true;
        notifyObservers(this);
    }

    /**
     * Returns the command
     * 
     * @return returns string representing the command
     */
    public String getCommand() {
        return d_Command;
    }

    /**
     * Sets the message to the input string
     * 
     * @param p_message string to set the message
     */
    public void setMessage(String p_message) {
        d_Message = p_message;
        d_IsMessageSet = true;
        notifyObservers(this);

    }

    /**
     * Returns the current message
     * 
     * @return return the current message
     */
    public String getMessage() {
        return d_Message;
    }

    /**
     * Get the list of players.
     * 
     * @return returns the list of players.
     */
    public ArrayList<Player> getPlayers() {
        return this.d_Players;
    }

    /**
     * Set the list of players.
     * 
     * @param p_players list of players to play the game.
     */
    public void setPlayers(ArrayList<Player> p_players) {
        this.d_Players = p_players;
    }

    /**
     * Get currently active player.
     * 
     * @return returns currently active player.
     */
    public Player getActivePlayer() {
        return this.d_ActivePlayer;
    }

    /**
     * Set the currently active player.
     * 
     * @param p_player currently active player.
     */
    public void setActivePlayer(Player p_player) {
        this.d_ActivePlayer = p_player;
        if (p_player != null) {
            notifyObservers(this);
        }
    }
}
