package Model;

import java.util.ArrayList;

import Controller.GameEngine;

/**
 * This is the State interface that contains the methods that will change their behaviour depending on the type of the
 * state it is in.
 * It maintains the current phase of the game.
 */
public abstract class Phase {
    GameEngine d_Ge;
    String d_PhaseName;

    /**
     * return game state
     * @return d_PhaseName it is current state.
     */
    public String getD_PhaseName() {
        return d_PhaseName;
    }

    /**
     * Method to randomly assign countries to players
     * @param p_map map being loaded.
     * @param p_players list of the players in game
     * @return returns boolean value
     */
    abstract public boolean assignCountries(WargameMap p_map, ArrayList<Player> p_players);

    /**
     * Method to Load the map.
     * @param p_data it is array of strings passed as command
     * @param p_mapName name of the map
     */
    abstract public void loadMap(String[] p_data, String p_mapName) ;

    /**
     * Method to show the loaded map
     * @param p_map reference of the map to be loaded.
     */
    abstract public void showMap(WargameMap p_map) ;

    /**
     * Method to edit the map
     * @param p_data it is array of strings passed as command
     * @param p_mapName name of the map
     */
    abstract public void editMap(String[] p_data, String p_mapName) ;

    /**
     * Method to save the edited or created map.
     * @param p_data it is array of strings passed as command
     * @param p_mapName name of the map
     */
    abstract public void saveMap(String[] p_data, String p_mapName) ;

    /**
     * Method to edit neighbors
     * @param p_data it is array of strings passed as command
     * @param p_countryId Country id/name
     * @param p_neighborCountryId neighbor country id/name.
     */
    abstract public void editNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) ;

    /**
     * Method to edit country.
     * @param p_data it is array of strings passed as command
     * @param p_continentId Continent id/name
     * @param p_countryId Country id/name
     */
    abstract public void editCountry(String[] p_data, String p_continentId, String p_countryId);

    /**
     * Method to edit continent
     * @param p_data it is array of strings passed as command
     * @param p_continentId Continent id/name
     * @param p_controlValue controlvalue of continent
     */
    abstract public void editContinent(String[] p_data, String p_continentId, int p_controlValue);

    /**
     * Method to provide reinforcement.
     */
    abstract public void reinforce() ;

    /**
     * Method to call remove and add players functionality.
     * @param p_data it is array of strings passed as command
     * @param p_players  are the list of players present in the game
     * @param p_playerName name of the player.
     */
    abstract public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) ;

    /**
     * Method will show map with the player having countries.
     * @param p_players are the list of players present in the game
     * @param p_map the map that is loaded.
     */

    abstract public void showMap(ArrayList<Player> p_players, WargameMap p_map);

    /**
     * Method for validation of map.
     */
    abstract public void validatemap();

    /**
     * method to show invalid command passed during particular state.
     */
    public void printInvalidCommandMessage(){
        System.out.println("Invalid command in "+getD_PhaseName()+" state " );
    }

    /**
     * method to issue order for each player.
     */
    public abstract void issue_order(Player p_player);


}

