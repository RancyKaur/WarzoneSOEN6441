package Model;

import java.util.ArrayList;

import Controller.GameEngine;

/**
 * An abstract class representing a phase in the game.
 */
public abstract class Phase {
	GameEngine d_Ge;
	String d_PhaseName;
	
	/**
     * Gets the name of the phase.
     * 
     * @return The name of the phase.
     */
	public String getD_PhaseName() {
		return d_PhaseName;
	}
	
	/**
     * allocate countries to players in the given map.
     * 
     * @param p_map     The map to allocate countries on.
     * @param p_players The list of players participating in the game.
     * @return True if countries are successfully allocateed, false otherwise.
     */
	abstract public boolean allocateCountries(Map p_map, ArrayList<Player> p_players);
	
	/**
     * Load a map from the provided data and map name.
     * 
     * @param p_data     The data for the map.
     * @param p_mapName  The name of the map.
     */
	abstract public void loadMap(String[] p_data, String p_mapName) ;
	
	/**
     * Display the provided map.
     * 
     * @param p_map The map to be displayed.
     */
	abstract public void displayMap(Map p_map) ;
	
	/**
     * for changing the map
     * 
     * @param p_data    array of strings passed as command
     * @param p_mapName The name of the map.
     */
	abstract public void changeMap(String[] p_data, String p_mapName) ;
	
	/**
	 * Method to save the edited or created map.
	 * @param p_data it is array of strings passed as command 
	 * @param p_mapName name of the map
	 */
	abstract public void saveMap(String[] p_data, String p_mapName) ;
	
	/**
	 * Method to change neighbours 
	 * @param p_data array of strings passed as command 
	 * @param p_countryId Country id/name
	 * @param p_neighborCountryId neighbour country id/name.
	 */
	abstract public void changeNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) ;
	
	/**
	 * Method to change the country.
	 * @param p_data array of strings passed as command  
	 * @param p_continentId Continent id/name
	 * @param p_countryId Country id/name
	 */
	abstract public void changeCountry(String[] p_data, String p_continentId, String p_countryId);
	
	/**
	 * Method to change the continent 
	 * @param p_data it is array of strings passed as command 
	 * @param p_continentId Continent id/name
	 * @param p_controlValue controlvalue of continent
	 */
	abstract public void changeContinent(String[] p_data, String p_continentId, int p_controlValue);
	
	/**
	 * Method to provide reinforcement.
	 */
	abstract public void reinforce() ;
	
	/**
	 * execute game player functionality for calling adding or removing
	 * @param p_data array of strings passed as command 
	 * @param p_players list of players present in the game
	 * @param p_playerName player name
	 */
	abstract public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) ;
	
	/**
     * Show the map for player
     * 
     * @param p_players The list of players.
     * @param p_map     The map that is loaded.
     */
	abstract public void displayMap(ArrayList<Player> p_players, Map p_map);
	
	/**
	 * Method for validation of map.
	 */
	abstract public void validateMap();
	
	/**
     * Prints an invalid command message.
     */
	public void invalidCommandError(){
		System.out.println("Invalid command for : " + getD_PhaseName());
	}
}

